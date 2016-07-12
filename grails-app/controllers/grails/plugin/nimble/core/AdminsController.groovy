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

/**
 * Manages addition and removal of super administrator role to user accounts
 *
 * @author Bradley Beddoes
 */
class AdminsController {

	static Map allowedMethods = [list: 'POST', create: 'POST', delete: 'POST', search: 'POST']

	AdminsService adminsService

	def index() { }

	def list() {
		Role adminAuthority = Role.findByName(AdminsService.ADMIN_ROLE)
		UserBase authenticatedUser = UserBase.get(SecurityUtils.getSubject()?.getPrincipal())

		if(!authenticatedUser) {
			log.error("Not able to determine currently authenticated user")
			response.sendError(403)
			return
		}

		render(template:'list', model:[currentAdmin:authenticatedUser, admins: adminAuthority?.users])
	}

	def create(Long id) {
		UserBase user = UserBase.get(id)
		if (!user) {
			log.warn("User identified by id $id was not located")

			response.sendError(500)
			render message(code: 'nimble.user.nonexistant', args: [id])
			return
		}

		boolean result = adminsService.add(user)
		if (result) {
			log.debug("User identified as [$user.id]$user.username was added as an administrator")
			render message(code: 'nimble.admin.grant.success', args: [user.username])
			return
		}

		log.warn("User identified as [$user.id]$user.username was unable to be made an administrator")
		response.sendError(500)
		render message(code: 'nimble.admin.grant.failed', args: [user.username])
	}

	def delete(Long id) {
		UserBase user = UserBase.get(id)

		if (!user) {
			log.warn("User identified by id $id was not located")
			render message(code: 'nimble.user.nonexistant', args: [id])
			response.sendError(500)
			return
		}

		if(user == authenticatedUser) {
			log.warn("Administrators are not able to remove themselves from the administrative role")
			render message(code: 'nimble.admin.revoke.self', args: [user.username])
			response.sendError(500)
			return
		}

		boolean result = adminsService.remove(user)
		if (result) {
			render message(code: 'nimble.admin.revoke.success', args: [user.username])
			return
		}

		log.warn("User identified as [$user.id]$user.username was unable to be removed as an administrator")
		render message(code: 'nimble.admin.revoke.error', args: [user.username])
		response.sendError(500)
	}

	def search(String q) {
		q = "%" + q + "%"

		log.debug("Performing search for users matching $q")

		List<UserBase> users = UserBase.findAllByUsernameIlike(q, [max:50])
		List<ProfileBase> profiles = ProfileBase.findAllByFullNameIlikeOrEmailIlike(q, q, [max:50, sort:"fullName"])
		List<UserBase> nonAdmins = []

		users.each {
			boolean admin = false
			it.roles.each { role ->
				if(role.name == AdminsService.ADMIN_ROLE) {
					admin = true
				}
			}
			if(!admin) {
				nonAdmins.add(it)
			}
		}

		profiles.each {
			boolean admin = false
			it.owner.roles.each { role ->
				if(role.name == AdminsService.ADMIN_ROLE) {
					admin = true
				}
			}
			if(!admin && !nonAdmins.contains(it.owner)) {
				nonAdmins.add(it.owner)
			}
		}

		log.info("Search for new administrators complete, returning $nonAdmins.size records")
		render template:"search", model:[users: nonAdmins]   // Should always be the case
	}
}
