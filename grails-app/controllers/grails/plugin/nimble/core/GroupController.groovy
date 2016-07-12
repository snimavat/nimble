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
/**
 * Manages Nimble groups including addition/removal of users and permissions
 *
 * @author Bradley Beddoes
 */
class GroupController {
	static String defaultAction = 'list'
	static Map allowedMethods = [	save: 'POST', update: 'POST', delete: 'POST', validname: 'POST', addmember: 'POST', removemember: 'POST', searchnewmembers: 'POST',
									 createpermission: 'POST', removepermission: 'POST', searchroles: 'POST', grantrole: 'POST', removerole: 'POST']

	GroupService groupService
	RoleService roleService
	PermissionService permissionService

	def list() {
		if (!params.max) {
			params.max = 10
		}
		[groups: Group.list(params)]
	}

	def show(Long id) {
		def group = Group.get(id)
		if (!group) {
			log.warn("Group identified by id '$id' was not located")

			flash.type = "error"
			flash.message = message(code: 'nimble.group.nonexistant', args: [id])
			redirect action: 'list'
			return
		}

		[group: group]
	}

	def create() {
		[group: new Group()]
	}

	def save(String name, String description) {

		log.debug("Attempting to create new group with name '$name' and description '$description'")
		def createdGroup = groupService.createGroup(name, description, false)
		if (createdGroup.hasErrors()) {
			// Error state for non validation or save problems
			log.warn("Failure attempting to create new group with name '$name' and description '$description'")
			flash.type = "error"
			flash.message = message(code: 'nimble.group.create.error', args: [createdGroup.name])
			render view: 'create', model: [group: createdGroup]
			return
		}

		log.info("Created new group with name '$name' and description '$description'")
		flash.type = "success"
		flash.message = message(code: 'nimble.group.create.success', args: [createdGroup.name])
		redirect action: 'show', params: [id: createdGroup.id]
	}

	def edit(Long id) {
		Group group = Group.get(id)
		if (!group) {
			log.warn("Group identified by id '$id' was not located")
			flash.type = "error"
			flash.message = message(code: 'nimble.group.nonexistant', args: [id])
			redirect action: 'list'
			return
		}

		[group: group]
	}

	def update(Long id) {
		Group group = Group.get(id)
		if (!group) {
			log.warn("Group identified by id '$id' was not located")
			flash.type = "error"
			flash.message = message(code: 'nimble.group.nonexistant', args: [id])
			redirect action: 'list'
			return
		}

		if (group.protect) {
			log.warn("Group [$group.id]$group.name is protected and can't be updated via the web interface")
			flash.type = "error"
			flash.message = message(code: 'nimble.group.protected.no.modification', args: [id])
			redirect (action: 'show', id: group.id)
			return
		}

		group.properties['name', 'description'] = params
		if (!group.validate()) {
			log.warn("Attempt to update group [$group.id]$group.name failed")
			flash.type = "error"
			flash.message = message(code: 'nimble.group.update.error', args: [group.name])
			render view: 'edit', model: [group: group]
			return
		}

		Group updatedGroup = groupService.updateGroup(group)
		log.info("Attempt to update group [$group.id]$group.name succeeded")
		flash.type = "success"
		flash.message = message(code: 'nimble.group.update.success', args: [updatedGroup.name])
		redirect action: 'show', params: [id: updatedGroup.id]
	}

	def delete(Long id) {
		Group group = Group.get(id)
		if (!group) {
			log.warn("Group identified by id '$id' was not located")
			flash.type = "error"
			flash.message = message(code: 'nimble.group.nonexistant', args: [id])
			redirect action: 'list'
			return
		}

		if (group.protect) {
			log.warn("Group [$group.id]$group.name is protected and can't be updated via the web interface")
			flash.type = "error"
			flash.message = message(code: 'nimble.group.protected.no.modification', args: [id])
			redirect (action: 'show', id: group.id)
			return
		}

		groupService.deleteGroup(group)
		flash.type = "success"
		flash.message = message(code: 'nimble.group.delete.success', args: [group.name])
		redirect action: 'list'
	}

	// AJAX related actions
	def validname(String val) {
		if (val == null || val.length() < 4) {
			render message(code: 'nimble.group.name.invalid', args: [val])
			response.status = 500
			return
		}

		List<Group> groups = Group.findAllByName(val)
		if (groups != null && groups.size() > 0) {
			render message(code: 'nimble.group.name.invalid', args: [val])
			response.status = 500
			return
		}

		render message(code: 'nimble.group.name.valid', args: [val])
	}

	def listmembers(Long id) {
		Group group = Group.get(id)
		if (!group) {
			log.warn("Group identified by id '$id' was not located")
			render message(code: 'nimble.group.nonexistant', args: [id])
			response.status = 500
			return
		}

		log.debug("Listing members of group $group.name")
		render(template: '/templates/admin/members_list', contextPath: pluginContextPath, model: [parent: group, users: group.users, protect: group.protect, groupmembers: false])
	}

	def addmember(Long id, Long userID) {
		Group group = Group.get(id)
		if (!group) {
			log.warn("Group identified by id '$id' was not located")
			render message(code: 'nimble.group.nonexistant', args: [id])
			response.status = 500
			return
		}

		UserBase user = UserBase.get(userID)
		if (!user) {
			log.warn("User identified by id '$userID' was not located")
			render message(code: 'nimble.user.nonexistant', args: [id])
			response.status = 500
			return
		}

		if (group.protect) {
			log.warn("Group [$group.id]$group.name is protected and can't be updated via the web interface")
			render message(code: 'nimble.group.protected.no.modification', args: [group.name])
			response.status = 500
			return
		}

		groupService.addMember(user, group)
		log.info("Added user [$user.id]$user.username to group $group.name")
		render message(code: 'nimble.group.addmember.success', args: [group.name, user.username])
	}

	def removemember(Long id, Long userID) {
		Group group = Group.get(id)
		if (!group) {
			log.warn("Group identified by id '$id' was not located")
			render message(code: 'nimble.group.nonexistant', args: [id])
			response.status = 500
			return
		}

		UserBase user = UserBase.get(userID)
		if (!user) {
			log.warn("User identified by id '$userID' was not located")
			render message(code: 'nimble.user.nonexistant', args: [id])
			response.status = 500
			return
		}

		if (group.protect) {
			log.warn("Group [$group.id]$group.name is protected and can't be updated via the web interface")
			render message(code: 'nimble.group.protected.no.modification', args: [group.name])
			response.status = 500
			return
		}

		groupService.deleteMember(user, group)
		log.info("Removed user [$user.id]$user.username from group $group.name")
		render message(code: 'nimble.group.removemember.success', args: [group.name, user.username])
	}

	def searchnewmembers(Long id, String q) {
		q = "%" + q + "%"

		Group group = Group.get(id)
		if (!group) {
			log.warn("Group identified by id '$id' was not located")
			render message(code: 'nimble.group.nonexistant', args: [id])
			response.status = 500
			return
		}

		log.debug("Performing search for users matching $q")

		List<UserBase> users = UserBase.findAllByUsernameIlike(q)
		List<ProfileBase> profiles = ProfileBase.findAllByFullNameIlikeOrEmailIlike(q, q)
		List<UserBase> nonMembers = []

		users.each {
			if (!it.groups.contains(group)) {
				nonMembers.add(it)
				log.debug("Adding user identified as [$it.id]$it.username to search results")
			}
		}
		profiles.each {
			if (!it.owner.groups.contains(group) && !nonMembers.contains(it.owner)) {
				nonMembers.add(it.owner)
				log.debug("Adding user identified as [$it.owner.id]$it.owner.username based on profile to search results")
			}
		}
		log.info("Search for new group members complete, returning $nonMembers.size records")
		render(template: '/templates/admin/members_search', contextPath: pluginContextPath, model: [parent: group, users: nonMembers])
	}

	def listpermissions(Long id) {
		Group group = Group.get(id)
		if (!group) {
			log.warn("Group identified by id '$id' was not located")
			render message(code: 'nimble.group.nonexistant', args: [id])
			response.status = 500
			return
		}

		render(template: '/templates/admin/permissions_list', contextPath: pluginContextPath, model: [permissions: group.permissions, parent: group])
	}

	def createpermission(Long id, String first, String second, String third, String fourth, String fifth, String sixth) {
		Group group = Group.get(id)
		if (!group) {
			log.warn("Group identified by id '$id' was not located")
			render message(code: 'nimble.group.nonexistant', args: [id])
			response.status = 500
			return
		}

		LevelPermission permission = new LevelPermission()
		permission.populate(first, second, third, fourth, fifth, sixth)
		permission.managed = false

		if (permission.hasErrors()) {
			log.debug("Unable to create new permission for group [$group.id]$group.name, permission details are invalid")
			render(template: "/templates/errors", contextPath: pluginContextPath, model: [bean: permission])
			response.status = 500
			return
		}

		def savedPermission = permissionService.createPermission(permission, group)
		if (savedPermission.hasErrors()) {
			log.warn("Unable to create new permission for group [$group.id]$group.name")
			render(template: "/templates/errors", contextPath: pluginContextPath, model: [bean: savedPermission])
			response.status = 500
			return
		}

		log.info("Created new permission $savedPermission.id for group [$group.id]$group.name")
		render message(code: 'nimble.permission.create.success', args: [group.name])
	}

	def removepermission(Long id, Long permID) {
		Group group = Group.get(id)
		if (!group) {
			log.warn("Group identified by id '$id' was not located")
			render message(code: 'nimble.group.nonexistant', args: [id])
			response.status = 500
			return
		}

		Permission permission = Permission.get(permID)
		if (!permission) {
			render message(code: 'nimble.permission.nonexistant', args: [permID])
			response.status = 500
			return
		}

		permissionService.deletePermission(permission)
		log.info("Removed permission $permission.id for group [$group.id]$group.name")
		render message(code: 'nimble.permission.remove.success', args: [group.name])
	}

	def listroles(Long id) {
		Group group = Group.get(id)
		if (!group) {
			log.warn("Group identified by id '$id' wass not located")
			render message(code: 'nimble.group.nonexistant', args: [id])
			response.status = 500
			return
		}

		render(template: '/templates/admin/roles_list', contextPath: pluginContextPath, model: [roles: group.roles, ownerID: group.id])
	}

	def searchroles(Long id, String q) {
		q = "%" + q + "%"

		Group group = Group.get(id)
		if (!group) {
			log.warn("Group identified by id '$id' was not located")
			render message(code: 'nimble.group.nonexistant', args: [id])
			response.status = 500
			return
		}

		log.debug("Performing search for roles matching $q")
		List<Role> roles = Role.findAllByNameIlikeOrDescriptionIlike(q, q)
		List<Role> respRoles = []
		roles.each {
			if (!group.roles.contains(it) && !it.protect)
				respRoles.add(it)    // Eject already assigned roles for this group
		}

		log.info("Search for new group roles complete, returning $respRoles.size records")
		render(template: '/templates/admin/roles_search', contextPath: pluginContextPath, model: [roles: respRoles, ownerID: group.id])
	}

	def grantrole(Long id, Long roleID) {
		Group group = Group.get(id)
		if (!group) {
			log.warn("Group identified by id '$id' was not located")
			render message(code: 'nimble.group.nonexistant', args: [id])
			response.status = 500
			return
		}

		if (group.protect) {
			log.warn("Group [$group.id]$group.name is protected and can't be updated via the web interface")
			render message(code: 'nimble.group.protected.no.modification', args: [id])
			response.status = 500
			return
		}

		Role role = Role.get(roleID)
		if (!role) {
			log.warn("Role identified by id '$roleID.id' was not located")
			render message(code: 'nimble.role.nonexistant', args: [roleID])
			response.status = 500
			return
		}

		if (role.protect) {
			log.warn("Role [$roleID.id]$role.name is protected and can not be modified via the web interface")
			render message(code: 'nimble.role.addmember.protected', args: [group.name, role.name])
			response.status = 500
			return
		}

		roleService.addGroupMember(group, role)
		log.info("Granted role [$role.id]$role.name to group [$group.id]$group.name")
		render message(code: 'nimble.role.addmember.success', args: [role.name, group.name])
	}

	def removerole(Long id, Long roleID) {
		Group group = Group.get(id)
		if (!group) {
			log.warn("Group identified by id '$id' was not located")
			render message(code: 'nimble.group.nonexistant', args: [id])
			response.status = 500
			return
		}

		if (group.protect) {
			log.warn("Group [$group.id]$group.name is protected and can't be updated via the web interface")
			render message(code: 'nimble.group.protected.no.modification', args: [id])
			response.status = 500
			return
		}

		Role role = Role.get(roleID)
		if (!role) {
			log.warn("Role identified by id '$roleID.id' was not located")
			render message(code: 'nimble.role.nonexistant', args: [roleID])
			response.status = 500
			return
		}

		if (role.protect) {
			log.warn("Role [$roleID.id]$role.name is protected and can not be modified via the web interface")
			render message(code: 'nimble.role.removemember.protected', args: [group.name, role.name])
			response.status = 500
			return
		}

		roleService.deleteGroupMember(group, role)
		log.info("Removed role [$role.id]$role.name to group [$group.id]$group.name")
		render message(code: 'nimble.role.removemember.success', args: [role.name, group.name])
	}
}
