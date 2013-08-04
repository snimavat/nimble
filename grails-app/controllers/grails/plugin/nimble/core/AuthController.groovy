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

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.DisabledAccountException
import org.apache.shiro.authc.IncorrectCredentialsException
import org.apache.shiro.authc.UsernamePasswordToken

/**
 * @author Bradley Beddoes
 */
class AuthController {

	private static final String TARGET = 'grails.plugin.nimble.controller.AuthController.TARGET'

	def shiroSecurityManager
	def userService

	static Map allowedMethods = [ signin: 'POST' ]

	static defaultAction = 'list'

	def login(String targetUri, String username, rememberMe) {
		def local = nimbleConfig.localusers.authentication.enabled
		def registration = nimbleConfig.localusers.registration.enabled

		if(targetUri) {
			session.setAttribute(AuthController.TARGET, targetUri)
		}

		render(template: "/templates/nimble/login/login",
		       model: [local: local, registration: registration, username: username, rememberMe: (rememberMe != null), targetUri: targetUri])
	}

	def signin(String username, String password, rememberme) {
		def authToken = new UsernamePasswordToken(username, password)

		if (rememberme) {
			authToken.rememberMe = true
		}

		log.info("Attempting to authenticate user, $username. RememberMe is $authToken.rememberMe")

		try {
			SecurityUtils.subject.login(authToken)
			userService.createLoginRecord(request)

			def targetUri = session.getAttribute(AuthController.TARGET) ?: "/"
			session.removeAttribute(AuthController.TARGET)

			log.info("Authenticated user, $username")
			if (userService.events["login"]) {
				log.info("Executing login callback")
				def newUri = userService.events["login"](authenticatedUser, targetUri, request)
				if (newUri != null) {
					targetUri = newUri
				}
			}
			log.info("Directing to content $targetUri")
			redirect(uri: targetUri)
			return
		}
		catch (IncorrectCredentialsException e) {
			log.info "Credentials failure for user '${username}'."
			log.debug(e)

			flash.type = 'error'
			flash.message = message(code: "nimble.login.failed.credentials")
		}
		catch (DisabledAccountException e) {
			log.info "Attempt to login to disabled account for user '${username}'."
			log.debug(e)

			flash.type = 'error'
			flash.message = message(code: "nimble.login.failed.disabled")
		}
		catch (AuthenticationException e) {
			log.info "General authentication failure for user '${username}'."
			log.debug(e)

			flash.type = 'error'
			flash.message = message(code: "nimble.login.failed.general")
		}
		redirect(action: 'login')
	}

	def logout() { signout() }

	def signout() {
		log.info("Signing out user ${authenticatedUser?.username}")

		if(userService.events["logout"]) {
			log.info("Executing logout callback")
			userService.events["logout"](authenticatedUser)
		}

		SecurityUtils.subject?.logout()
		redirect(uri: '/')
	}

	def unauthorized() { response.sendError(403) }

	private getNimbleConfig() {
		grailsApplication.config.nimble
	}
}
