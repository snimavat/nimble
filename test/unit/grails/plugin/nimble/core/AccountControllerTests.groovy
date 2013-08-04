/*
 *  Nimble, an extensive application base for Grails
 *  Copyright (C) 2010 Bradley Beddoes
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package grails.plugin.nimble.core

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

import org.apache.shiro.SecurityUtils
import org.apache.shiro.crypto.hash.Sha256Hash
import org.apache.shiro.subject.Subject
import org.junit.After
import org.junit.Before

@TestFor(AccountController)
@Mock(UserBase)
class AccountControllerTests {

	private suMock
	private pass = 'pass'
	private passConfirm = pass
	private currentPassword = 'currentPassword'

	@Before
	void setUp() {
		AccountController.metaClass.getAuthenticatedUser = { ->
			def principal = SecurityUtils.getSubject()?.getPrincipal()
			if (!principal) {
				return null
			}

			def authUser = UserBase.get(principal)
			if (!authUser) {
				throw new RuntimeException("User was not able to be obtained for id $principal")
			}
			return authUser
		}
	}

	@After
	void tearDown() {
		suMock?.verify()
	}

	private createValidUser() {
		def crypt = new Sha256Hash(currentPassword).toHex()

		//further fill out user....

		def user = new UserBase(username:'username', passwordHash:crypt, profile: new ProfileBase())
		mockDomain UserBase, [user]
		user
	}

	private createValidRecaptchaMock() {
		def rsMock = mockFor(RecaptchaService)
		rsMock.demand.verifyAnswer {session, request, params -> true }
		controller.recaptchaService = rsMock.createMock()

		return rsMock
	}

	private createValidUserServiceMock() {
		mockFor(UserService)
	}

	void testAllowedMethods() {
		def post = 'POST'

		assertNull controller.allowedMethods.createuser
		assertNull controller.allowedMethods.createduser
		assertNull controller.allowedMethods.validateuser
		assertNull controller.allowedMethods.forgottenpassword
		assertNull controller.allowedMethods.forgottenpasswordcomplete
		assertNull controller.allowedMethods.changepassword

		assertEquals post, controller.allowedMethods.saveuser
		assertEquals post, controller.allowedMethods.validusername
		assertEquals post, controller.allowedMethods.forgottenpasswordprocess
		assertEquals post, controller.allowedMethods.updatepassword
	}

	void testChangePasswordComplete() {

		def user = createValidUser()

		suMock = mockFor(SecurityUtils)
		suMock.demand.static.getSubject {-> [getPrincipal: { -> user.id }] as Subject}

		def model = controller.changepassword()
		assertEquals user, model.user
	}

	void testUpdatePasswordComplete() {
		def user = createValidUser()

		suMock = mockFor(SecurityUtils)
		suMock.demand.static.getSubject {-> [getPrincipal: { -> user.id }] as Subject}

		def rsMock = createValidRecaptchaMock()

		def usMock = createValidUserServiceMock()
		usMock.demand.validatePass { u, boolean b -> true }

		usMock.demand.changePassword{u ->
			assertEquals user, u
			assertEquals pass, u.pass
			assertEquals passConfirm, u.passConfirm
			return user
		}
		controller.userService = usMock.createMock()

		controller.updatepassword(currentPassword, pass, passConfirm)

		assertEquals '/account/changedpassword', response.redirectedUrl

		rsMock.verify()
		usMock.verify()
	}

	void testUpdatePasswordNoCurrent() {

		def user = createValidUser()
		assertFalse user.hasErrors()

		suMock = mockFor(SecurityUtils)
		suMock.demand.static.getSubject {-> [getPrincipal: { -> user.id }] as Subject}

		def pass = 'pass'
		def passConfirm = pass
		def currentPassword = 'currentPassword'

		controller.updatepassword(null, pass, passConfirm)

		assertEquals user, model.user
		assertEquals '/account/changepassword', view
		assertTrue user.hasErrors()
	}

	void testUpdatePasswordNotHuman() {
		def user = createValidUser()
		assertFalse user.hasErrors()

		suMock = mockFor(SecurityUtils)
		suMock.demand.static.getSubject {-> [getPrincipal: { -> user.id }] as Subject}

		def rsMock = mockFor(RecaptchaService)
		rsMock.demand.verifyAnswer { session, request, params -> false }
		controller.recaptchaService = rsMock.createMock()

		controller.updatepassword(currentPassword, pass, passConfirm)

		assertEquals user, model.user
		assertEquals '/account/changepassword', view
		assertTrue user.hasErrors()

		rsMock.verify()
	}

	void testUpdatePasswordEmptyCurrent() {
		def user = createValidUser()
		assertFalse user.hasErrors()

		suMock = mockFor(SecurityUtils)
		suMock.demand.static.getSubject {-> [getPrincipal: { -> user.id }] as Subject}

		controller.updatepassword('', pass, passConfirm)

		assertEquals user, model.user
		assertEquals '/account/changepassword', view
		assertTrue user.hasErrors()
	}

	void testUpdatePasswordInvalidPass() {
		def user = createValidUser()

		suMock = mockFor(SecurityUtils)
		suMock.demand.static.getSubject {-> [getPrincipal: { -> user.id }] as Subject}

		def rsMock = createValidRecaptchaMock()

		def usMock = createValidUserServiceMock()
		usMock.demand.validatePass { u, boolean b ->
			assertEquals user, u
			assertEquals 'notvalid', u.pass
			assertEquals passConfirm, u.passConfirm
			u.errors.rejectValue('pass', 'user.password.xyz')
			false
		}
		controller.userService = usMock.createMock()

		assertFalse user.hasErrors()

		controller.updatepassword(currentPassword, 'notvalid', passConfirm)

		assertEquals user, model.user
		assertEquals '/account/changepassword', view
		assertTrue user.hasErrors()

		usMock.verify()
		rsMock.verify()
	}

	void testCreateUserComplete() {

		controller.grailsApplication.config.nimble.localusers.registration.enabled = true

		def model = controller.createuser()
		assertNotNull model.user
	}

	void testSaveUserComplete() {
		createValidUser()
	}
}
