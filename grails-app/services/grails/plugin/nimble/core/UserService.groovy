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

import grails.plugin.nimble.auth.CorePermissions
import grails.transaction.Transactional
import org.codehaus.groovy.grails.commons.GrailsApplication

import javax.servlet.http.HttpServletRequest

import org.apache.shiro.SecurityUtils
import org.apache.shiro.crypto.hash.Sha256Hash

/**
 * Provides methods for interacting with Nimble users.
 *
 * @author Bradley Beddoes
 */
@Transactional
class UserService {

	public static final String USER_ROLE = "USER"

	PermissionService permissionService
	GrailsApplication grailsApplication
	Map events = [:]

	/**
	 * Activates a disabled user account
	 *
	 * @param user The user to enable
	 *
	 * @throws RuntimeException When internal state requires transaction rollback
	 */
	UserBase enableUser(UserBase user) {
		user.enabled = true

		def savedUser = user.save()
		if (savedUser) {
			log.info("Successfully enabled user [$user.id]$user.username")
			return savedUser
		}

		log.error("Error enabling user [$user.id]$user.username")
		user.errors.each { log.error it }

		throw new RuntimeException("Error enabling user [$user.id]$user.username")
	}

	/**
	 * Disables an active user account
	 *
	 * @param user The user to disable
	 *
	 * @throws RuntimeException When internal state requires transaction rollback
	 */
	UserBase disableUser(UserBase user) {
		user.enabled = false

		def savedUser = user.save()
		if (savedUser) {
			log.info("Successfully disabled user [$user.id]$user.username")
			return savedUser
		}

		log.error("Error disabling user [$user.id]$user.username")
		user.errors.each { log.error it }

		throw new RuntimeException("Error disabling user [$user.id]$user.username")
	}

	/**
	 * Enables remote api access flag
	 *
	 * @param user The user to enable remote api for
	 *
	 * @throws RuntimeException When internal state requires transaction rollback
	 */
	UserBase enableRemoteApi(UserBase user) {
		user.remoteapi = true

		def savedUser = user.save()
		if (savedUser) {
			log.info("Successfully enabled user [$user.id]$user.username remote api")
			return savedUser
		}

		log.error("Error enabling user [$user.id]$user.username remote api")
		user.errors.each { log.error it }

		throw new RuntimeException("Error enabling user [$user.id]$user.username remote api")
	}

	/**
	 * Disables remote api access flag
	 *
	 * @param user The user to disable remote api for
	 *
	 * @throws RuntimeException When internal state requires transaction rollback
	 */
	UserBase disableRemoteApi(UserBase user) {
		user.remoteapi = false

		def savedUser = user.save()
		if (savedUser) {
			log.info("Successfully disabled user [$user.id]$user.username remote api")
			return savedUser
		}

		log.error("Error disabling user [$user.id]$user.username remote api")
		user.errors.each { log.error it }

		throw new RuntimeException("Error disabling user [$user.id]$user.username remote api")
	}

	/**
	 * Changes password for a local user.
	 *
	 * @pre Passed user object must have been validated to ensure
	 * that hibernate does not auto persist the object to the repository prior to service invocation
	 *
	 * @param user The user whose password should be changed
	 *
	 * @return The user object, with errors populated if change problem occured
	 */
	UserBase changePassword(UserBase user) {
		user.validate()
		validatePass(user)

		if (!user.hasErrors()) {
			def savedUser = user.save()
			if (savedUser) {
				log.info("Successfully changed password for user [$user.id]$user.username")
				return savedUser
			}
		}

		log.error("Error changing password for user [$user.id]$user.username")
		user.errors.each { log.error it }

		throw new RuntimeException("Error saving user [$user.id]$user.username password modification")
	}

	/**
	 * Creates a new user account in data repository.
	 *
	 * @pre User object must be newly created and not yet associated with backend data repository
	 *
	 * @param user A populated user object to persist
	 *
	 * @return The stored user object if successful or the supplied user with errors on failure
	 *
	 * @throws RuntimeException When internal state requires transaction rollback
	 */
	UserBase createUser(UserBase user) {
		user.validate()

		if (!user.external) {
			if (validatePass(user)) {
				generateValidationHash(user)
			}
			else {
				return user
			}
		}

		if (!user.hasErrors()) {

			// Add default role for users
			def defaultRole = Role.findByName(UserService.USER_ROLE)

			if(!defaultRole) {
				log.error("Unable to locate default user role, aborting user creation")
				throw new RuntimeException("Unable to locate default user role, aborting user creation")
			}

			user.addToRoles(defaultRole)

			def savedUser = user.save()
			if (savedUser) {
				defaultRole.addToUsers(savedUser)
				defaultRole.save()

				if (defaultRole.hasErrors()) {
					log.error("Unable to assign default role to new user [$savedUser.id]$savedUser.username")
					defaultRole.errors.each { log.error(it) }

					throw new RuntimeException("Unable to assign default role to new user [$savedUser.id]$savedUser.username")
				}

				// Add default permission set
				// Allow personal profile edit
				Permission profileEdit = new Permission(managed:true, type: Permission.defaultPerm, target:"${CorePermissions.editPermission}:$savedUser.id")
				permissionService.createPermission(profileEdit, savedUser)

				savedUser.save()
				if (savedUser.hasErrors()) {
					log.error("Unable to assign default permissions to new user [$savedUser.id]$savedUser.username")
					savedUser.errors.each { log.error(it) }

					throw new RuntimeException("Unable to assign default permissions to new user [$savedUser.id]$savedUser.username")
				}

				log.info("Successfully created user [$user.id]$user.username")
				return savedUser
			}
		}

		// Validation or save errors ocured
		log.debug("Submitted details for new user account are invalid")
		user.errors.each { log.debug it }
		return user
	}

	/**
	 * Updates a current user account.
	 *
	 * @pre Passed user object must have been validated to ensure
	 * that hibernate does not auto persist the object to the repository prior to service invocation
	 *
	 * @param user The user to update
	 *
	 * @throws RuntimeException When internal state requires transaction rollback
	 */
	UserBase updateUser(UserBase user) {

		def updatedUser = user.save()
		if (updatedUser) {
			log.error("Updated user [$user.id]$user.username")
			return updatedUser
		}

		log.error("Unable to update user [$user.id]$user.username")
		user.errors.each { log.error(it) }

		throw new RuntimeException("Unable to update user [$user.id]$user.username")
	}

	/**
	 * Generates a new Hash to provide to the user (generally over email) to validate some account action.
	 *
	 * @param user The user undertaking an action requiring validation
	 *
	 * @pre Passed user object must have been validated to ensure
	 * that hibernate does not auto persist the object to the repository prior to service invocation
	 */
	void generateValidationHash(UserBase user) {
		String input = user.username + user.profile?.fullName + user.profile?.email + System.currentTimeMillis()

		def enc = new Sha256Hash(input)
		user.actionHash = enc.toHex()
		log.debug("Generated validation hash of $user.actionHash for user [$user.id]$user.username")
	}

	/**
	 * Generates a random password for a user.
	 *
	 * @param user The user to set  random password for
	 *
	 * @pre Passed user object must have been validated to ensure
	 * that hibernate does not auto persist the object to the repository prior to service invocation
	 */
	void setRandomPassword(UserBase user) {
		String input = user.username + user.profile?.fullName + user.profile?.email + System.currentTimeMillis()

		def enc = new Sha256Hash(input)

		user.pass = enc.toBase64()[0..12]
		def pwEnc = new Sha256Hash(user.pass)
		def crypt = pwEnc.toHex()
		user.passwordHash = crypt
		user.addToPasswdHistory(crypt)

		user.save()

		if (user.hasErrors()) {
			log.error("Unable to assign random password to user [$user.id]$user.username")
			user.errors.each { log.error(it) }

			throw new RuntimeException("Unable to assign random password to user [$user.id]$user.username")
		}

		log.debug("Assigned random password to user [$user.id]$user.username")
	}

	/**
	 * Stores details of a successful login by a user.
	 */
	void createLoginRecord(HttpServletRequest request) {
		def user = UserBase.get(SecurityUtils.getSubject()?.getPrincipal())
		if(!user) {
			log.error("Attempt to create login record for unauthenticated session")
			throw new RuntimeException("Attempt to create login record for unauthenticated session")
		}

		log.debug("Creating new record for user [$user.id]$user.username login")

		def record = new LoginRecord()
		String resolvedIp = getClientIpAddress(request)

		record.remoteAddr = resolvedIp
		record.remoteHost = request.getRemoteHost()
		record.userAgent = request.getHeader("User-Agent")

		record.owner = user
		user.addToLoginRecords(record)

		record.save()

		if (record.hasErrors()) {
			log.error("Unable to save login record for user [$user.id]$user.username")
			record.errors.each { log.error(it) }
			throw new RuntimeException("Unable to save login record for user [$user.id]$user.username")
		}

		user.save()

		if (user.hasErrors()) {
			log.error("Unable to update user [$user.id]$user.username with new login record")
			user.errors.each { log.error(it) }

			throw new RuntimeException("Unable to update user [$user.id]$user.username with new login record")
		}

		log.info("User [$user.id]$user.username logged in successfully from remote host $record.remoteHost with UA $record.userAgent")
	}

	/**
	 * Get Original ip address based on forward header if request is forwarded by a proxy
	 * 
	 * @param request
	 * @return String ip
	 */
	private String getClientIpAddress(request) {
		String ip = request.getHeader("X-Forwarded-For")
		if(isNullOrUnknown(ip)) {
			ip = request.getHeader("Proxy-Client-IP")
		}
		if(isNullOrUnknown(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP")
		}
		if(isNullOrUnknown(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR")
		}
		if(isNullOrUnknown(ip)) {
			ip = request.getRemoteAddr()
		}

		if(ip.contains(",")) {
			ip = ip.split(",")[0]
		}

		return ip
	}

	private boolean isNullOrUnknown(String str) {
		(null == str || str.trim().length() == 0 || "unknown".equalsIgnoreCase(str))
	}

	/**
	 * Applies password processing rules to determine if the user
	 * pass and passConfirm values are valid. If not error objects are added to the user
	 * object. If the pass is valid it is encrypted and set as the value of user.passwd and
	 * added to the password history
	 */
	boolean validatePass(UserBase user) {
		validatePass(user, false)
	}

	boolean validatePass(UserBase user, boolean checkOnly) {
		log.debug("Validating user entered password")

		if (user.pass == null || user.pass.length() < nimbleConfig.passwords.minlength) {
			log.debug("Password to short")
			user.errors.rejectValue('pass', 'nimble.user.password.required')
			return false
		}

		if (user.passConfirm == null || user.passConfirm.length() < nimbleConfig.passwords.minlength) {
			log.debug("Confirmation password to short")
			user.errors.rejectValue('passConfirm', 'nimble.user.passconfirm.required')
			return false
		}

		if (!user.pass.equals(user.passConfirm)) {
			log.debug("Password does not match confirmation")
			user.errors.rejectValue('pass', 'nimble.user.password.nomatch')
			return false
		}

		if (nimbleConfig.passwords.mustcontain.lowercase && !(user.pass =~ /^.*[a-z].*$/)) {
			log.debug("Password does not contain lower case letters")
			user.errors.rejectValue('pass', 'nimble.user.password.no.lowercase')
			return false
		}

		if (nimbleConfig.passwords.mustcontain.uppercase && !(user.pass =~ /^.*[A-Z].*$/)) {
			log.debug("Password does not contain uppercase letters")
			user.errors.rejectValue('pass', 'nimble.user.password.no.uppercase')
			return false
		}

		if (nimbleConfig.passwords.mustcontain.numbers && !(user.pass =~ /^.*[0-9].*$/)) {
			log.debug("Password does not contain numbers")
			user.errors.rejectValue('pass', 'nimble.user.password.no.numbers')
			return false
		}

		if (nimbleConfig.passwords.mustcontain.symbols && !(user.pass =~ /^.*\W.*$/)) {
			log.debug("Password does not contain symbols")
			user.errors.rejectValue('pass', 'nimble.user.password.no.symbols')
			return false
		}

		def pwEnc = new Sha256Hash(user.pass)
		def crypt = pwEnc.toHex()

		if(!nimbleConfig.passwords.allowreuse) {
			if (user.passwdHistory != null && user.passwdHistory.contains(crypt)) {
				log.debug("Password was previously utilized")
				user.errors.rejectValue('pass', 'nimble.user.password.duplicate')
				return false
			}
		}

		if (!user.hasErrors() && !checkOnly) {
			user.passwordHash = crypt
			user.addToPasswdHistory(crypt)
		}

		return true
	}

	private getNimbleConfig() {
		grailsApplication.config.nimble
	}

}
