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

import grails.test.GrailsUnitTestCase

import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * @author Bradley Beddoes
 */
class UserTests extends GrailsUnitTestCase {

	private username = 'username'
	private passwordHash = 'passwordHash'
	private actionHash = 'actionHash'
	private enabled = true
	private external = true
	private federated = true
	private remoteapi = true
	private expiration = new Date()
	private profile = new ProfileBase()
	private role1 = new Role()
	private role2 = new Role()
	private group1 = new Group()
	private group2 = new Group()
	private pw1 = 'pw1'
	private pw2 = 'pw2'
	private login1 = new LoginRecord()
	private login2 = new LoginRecord()
	private follows1 = new UserBase()
	private follows2 = new UserBase()
	private follower1 = new UserBase()
	private follower2 = new UserBase()
	private dateCreated = new Date()
	private lastUpdated = new Date()

	protected void setUp() {
		super.setUp()
		ConfigurationHolder.config = [nimble: [tablenames: [user: 'user']]] as ConfigObject
	}

	private UserBase createValidUser() {
		new UserBase(username:username, passwordHash:passwordHash, actionHash:actionHash, enabled:enabled,
		external:external, federated:federated, remoteapi:remoteapi, expiration:expiration,
		profile:profile, roles:[role1, role2],
		groups:[group1, group2], passwdHistory:[pw1, pw2], loginRecords:[login1, login2],
		follows:[follows1, follows2], followers:[follower1, follower2],
		dateCreated:dateCreated, lastUpdated:lastUpdated)
	}

	void testUserCreation() {
		def user = createValidUser()

		assertEquals username, user.username
		assertEquals passwordHash, user.passwordHash
		assertEquals actionHash, user.actionHash
		assertEquals enabled, user.enabled
		assertEquals external, user.external
		assertEquals federated, user.federated
		assertEquals remoteapi, user.remoteapi
		assertEquals expiration, user.expiration
		assertEquals profile, user.profile
		assertEquals dateCreated, user.dateCreated
		assertEquals lastUpdated, user.lastUpdated

		assertTrue user.roles.containsAll([role1, role2])
		assertTrue user.groups.containsAll([group1, group2])
		assertTrue user.passwdHistory.containsAll([pw1, pw2])
		assertTrue user.loginRecords.containsAll([login1, login2])
		assertTrue user.follows.containsAll([follows1, follows2])
		assertTrue user.followers.containsAll([follower1, follower2])
	}

	void testUsernameConstraint() {
		mockForConstraintsTests(UserBase)

		def user = createValidUser()
		assertTrue user.validate()

		user.username = null
		assertFalse user.validate()

		user.username = ''
		assertFalse user.validate()

		// Unique usernames
		def user2 = createValidUser()
		user2.username = 'username2'
		user.username = username
		mockForConstraintsTests(UserBase, [user, user2])

		assertTrue user.validate()
		assertTrue user2.validate()

		user2.username = username
		assertFalse user.validate()
		assertFalse user2.validate()
		assertTrue user.errors.errorCount == 1
		assertEquals 'username', user.errors.fieldError.field

		user2.username = 'username2'
		assertTrue user.validate()
		assertTrue user2.validate()

		// Min length
		user.username = 'ab'
		assertFalse user.validate()

		user.username = username
		assertTrue user.validate()

		// Max length
		user.username = 'abc'.center(2256)
		assertFalse user.validate()
	}

	void testPasswordHashConstraint() {
		mockForConstraintsTests(UserBase)

		def user = createValidUser()
		assertTrue user.validate()

		user.passwordHash = null
		assertTrue user.validate()

		user.passwordHash = ''
		assertFalse user.validate()
	}

	void testActionHashConstraint() {
		mockForConstraintsTests(UserBase)

		def user = createValidUser()
		assertTrue user.validate()

		user.actionHash = null
		assertTrue user.validate()

		user.actionHash = ''
		assertFalse user.validate()
	}

	void testProfileConstraint() {
		mockForConstraintsTests(UserBase)

		def user = createValidUser()
		assertTrue user.validate()

		user.profile = null
		assertFalse user.validate()
	}

	void testExpirationConstraint() {
		mockForConstraintsTests(UserBase)

		def user = createValidUser()
		assertTrue user.validate()

		user.expiration = null
		assertTrue user.validate()
	}

	void testDateCreatedConstraint() {
		mockForConstraintsTests(UserBase)

		def user = createValidUser()
		assertTrue user.validate()

		user.dateCreated = null
		assertTrue user.validate()
	}

	void testLastUpdatedConstraint() {
		mockForConstraintsTests(UserBase)

		def user = createValidUser()
		assertTrue user.validate()

		user.lastUpdated = null
		assertTrue user.validate()
	}
}
