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

import grails.plugin.nimble.InstanceGenerator

import org.apache.shiro.crypto.hash.Sha256Hash

/**
 * Manages all common user account tasks.
 *
 * @author Bradley Beddoes
 */
class AccountController {

	static Map allowedMethods = [saveuser: 'POST', validusername: 'POST', forgottenpasswordprocess: 'POST', updatepassword: 'POST']

	def userService
	def recaptchaService

	def changepassword() {
		[user:authenticatedUser]
	}

	def changedpassword() {}

	def updatepassword(String currentPassword, String pass, String passConfirm) {
		def user = authenticatedUser

		if(!currentPassword) {
			log.warn("User [$user?.id]$user?.username attempting to change password but has not supplied current password")
			user.errors.reject('nimble.user.password.required')
			render (view:"changepassword", model:[user:user])
			return
		}

		def pwEnc = new Sha256Hash(currentPassword)
		def crypt = pwEnc.toHex()

		def human = recaptchaService.verifyAnswer(session, request.getRemoteAddr(), params)
		if (human) {

			if(!crypt.equals(user.passwordHash)) {
				log.warn("User [$user.id]$user.username attempting to change password but has supplied invalid current password")
				user.errors.reject('nimble.user.password.nomatch')
				render (view:"changepassword", model:[user:user])
				return
			}

			user.pass = pass
			user.passConfirm = passConfirm

			if(user.validate() && userService.validatePass(user, true)) {
				userService.changePassword(user)
				if(!user.hasErrors()) {
					log.info("Changed password for user [$user.id]$user.username successfully")
					redirect action: "changedpassword"
					return
				}
			}

			log.error("User [$user.id]$user.username password change was considered invalid")
			user.errors.allErrors.each { log.debug it }
			render (view:"changepassword", model:[user:user])
			return
		}

		log.debug("Captcha entry was invalid for user account creation")
		resetNewUser(user)
		user.errors.reject('nimble.invalid.captcha')
		render(view: 'changepassword', model: [user: user])
		return
	}

	def createuser() {
		if (!nimbleConfig.localusers.registration.enabled) {
			log.warn("Account registration is not enabled for local users, skipping request")
			response.sendError(404)
			return
		}

		def user = InstanceGenerator.user(grailsApplication)
		user.profile = InstanceGenerator.profile(grailsApplication)

		log.debug("Starting new user creation")
		[user: user]
	}

	def saveuser() {
		if (!nimbleConfig.localusers.registration.enabled) {
			log.warn("Account registration is not enabled for local users, skipping request")
			response.sendError(404)
			return
		}

		def user = InstanceGenerator.user(grailsApplication)
		user.profile = InstanceGenerator.profile(grailsApplication)
		user.profile.owner = user
		user.properties['username', 'pass', 'passConfirm'] = params
		user.profile.properties['fullName', 'email'] = params
		user.enabled = nimbleConfig.localusers.provision.active
		user.external = false

		user.validate()

		log.debug("Attempting to create new user account identified as $user.username")

		// Enforce username restrictions on local accounts, letters + numbers only
		if (user.username == null || user.username.length() < nimbleConfig.localusers.usernames.minlength || !user.username.matches(nimbleConfig.localusers.usernames.validregex)) {
			log.debug("Supplied username of $user.username does not meet requirements for local account usernames")
			user.errors.rejectValue('username', 'nimble.user.username.invalid')
		}

		// Enforce email address for account registrations
		if (user.profile.email == null || user.profile.email.length() == 0) {
			user.profile.email = 'invalid'
		}

		if (user.hasErrors()) {
			log.debug("Submitted values for new user are invalid")
			user.errors.each { log.debug it }

			resetNewUser(user)
			render(view: 'createuser', model: [user: user])
			return
		}

		def savedUser
		def human = recaptchaService.verifyAnswer(session, request.getRemoteAddr(), params)

		if (human) {
			savedUser = userService.createUser(user)
			if (savedUser.hasErrors()) {
				log.debug("UserService returned invalid account details when attempting account creation")
				resetNewUser(user)
				render(view: 'createuser', model: [user: user])
				return
			}
		}
		else {
			log.debug("Captcha entry was invalid for user account creation")
			resetNewUser(user)
			user.errors.reject('nimble.invalid.captcha')
			render(view: 'createuser', model: [user: user])
			return
		}

		log.info("Sending account registration confirmation email to $user.profile.email with subject $nimbleConfig.messaging.registration.subject")

		if(nimbleConfig.messaging.enabled) {
			sendMail {
				to user.profile.email
				from nimbleConfig.messaging.mail.from
				subject nimbleConfig.messaging.registration.subject
				html g.render(template: "/templates/nimble/mail/accountregistration_email", model: [user: savedUser]).toString()
			}
		}
		else {
			log.debug "Messaging disabled would have sent: \n${user.profile.email} \n Message: \n ${g.render(template: "/templates/nimble/mail/accountregistration_email", model: [user: user]).toString()}"
		}

		log.info("Created new account identified as $user.username with internal id $savedUser.id")

		redirect action: 'createduser'
	}

	def createduser() {
		render(view: 'createduser', useractive: nimbleConfig.localusers.provision.active)
	}

	def validateuser(long id, String activation) {
		def user = UserBase.get(id)

		if (!user) {
			log.warn("User identified as [$id] was not located")
			flash.type = "error"
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'nimble.user.label'), id])
			redirect controller: 'auth', action: 'login'
			return
		}

		if (!user.enabled && user.actionHash != null && user.actionHash.equals(activation)) {
			user.enabled = true

			// Reset hash
			userService.generateValidationHash(user)
			user.save()

			if (user.hasErrors()) {
				log.warn("Unable to enable user identified as [$user.id]$user.username after successful action hash validation")
				user.errors.each { log.debug(it) }

				flash.type = "error"
				flash.message = message(code: 'nimble.user.validate.error')
				redirect controller: 'auth', action: 'login'
				return
			}

			log.info("Successful validate for user identified as [$user.id]$user.username")

			flash.type = "success"
			flash.message = message(code: 'nimble.user.validate.success')
			redirect controller: 'auth', action: 'login'

			return
		}

		if (!user.enabled)
			log.warn("Attempt to validate user identified by [$user.id]$user.username and when the account is already active")

		if (!user.actionHash == null)
			log.warn("Attempt to validate user identified by [$user.id]$user.username and when the account action hash is null")

		if (!user.actionHash.equals(activation))
			log.warn("Attempt to validate user identified by [$user.id]$user.username but activation action hash did not match data store")

		flash.type = "error"
		flash.message = message(code: 'nimble.user.validate.error')
		redirect controller: 'auth', action: 'login'
	}

	def validusername(String val) {
		if (val == null || val.length() < nimbleConfig.localusers.usernames.minlength || !val.matches(nimbleConfig.localusers.usernames.validregex)) {
			render message(code: 'nimble.user.username.invalid')
			response.status = 500
			return
		}

		def users = UserBase.findAllByUsername(val)
		if (users != null && users.size() > 0) {
			render message(code: 'nimble.user.username.invalid')
			response.status = 500
			return
		}

		render message(code: 'nimble.user.username.valid')
	}

	def forgottenpassword() {}

	def forgottenpasswordemail() {}

	def forgottenpasswordprocess(String email) {
		def profile = ProfileBase.findByEmail(email)
		if (!profile) {
			log.debug("User account for supplied email address $email was not found when attempting to process forgotten password")
			flash.type = "error"
			flash.message = message(code: 'nimble.user.forgottenpassword.noaccount')
			redirect(action: "forgottenpassword")
			return
		}

		def user = profile.owner

		if (user.external || user.federated) {
			log.info("User identified by [$user.id]$user.username is external or federated")

			log.info("Sending account password reset email to $user.profile.email with subject $nimbleConfig.messaging.passwordreset.external.subject")
			if(nimbleConfig.messaging.enabled) {
				sendMail {
					to user.profile.email
					from nimbleConfig.messaging.mail.from
					subject nimbleConfig.messaging.passwordreset.external.subject
					html g.render(template: "/templates/nimble/mail/forgottenpassword_external_email", model: [user: user]).toString()
				}
			}
			else {
				log.debug "Messaging disabled would have sent: \n${user.profile.email} \n Message: \n ${g.render(template: "/templates/nimble/mail/forgottenpassword_external_email", model: [user: user]).toString()}"
			}

			redirect(action: "forgottenpasswordcomplete", id: user.id)
			return
		}

		def human = recaptchaService.verifyAnswer(session, request.getRemoteAddr(), params)
		if (human) {

			userService.setRandomPassword(user)

			log.info("Sending account password reset email to $user.profile.email with subject $nimbleConfig.messaging.passwordreset.subject")
			if(nimbleConfig.messaging.enabled) {
				sendMail {
					to user.profile.email
					from nimbleConfig.messaging.mail.from
					subject nimbleConfig.messaging.passwordreset.subject
					html g.render(template: "/templates/nimble/mail/forgottenpassword_email", model: [user: user]).toString()
				}
			}
			else {
				log.debug "Messaging disabled would have sent: \n${user.profile.email} \n Message: \n ${g.render(template: "/templates/nimble/mail/forgottenpassword_email", model: [user: user]).toString()}"
			}

			log.info("Successful password reset for user identified as [$user.id]$user.username")

			redirect(action: "forgottenpasswordcomplete")
			return
		}

		log.debug("Captcha entry was invalid when attempting to process forgotten password for user identified by [$user.id]$user.username")
		flash.type = "error"
		flash.message = message(code: 'nimble.invalid.captcha')
		redirect(action: "forgottenpassword")
	}

	def forgottenpasswordcomplete() {}

	private void resetNewUser(user) {
		log.debug("New user creation failed, resetting user input to accepted state")

		if (user.profile?.email.equals('invalid')) {
			user.profile.email = ''
		}

		user.pass = ""
		user.passConfirm = ""
	}

	private getNimbleConfig() {
		grailsApplication.config.nimble
	}
}
