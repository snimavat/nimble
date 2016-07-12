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
 * Manages Nimble roles including addition/removal of users, groups and permissions
 *
 * @author Bradley Beddoes
 */
class RoleController {
	static String defaultAction = 'list'
	static Map allowedMethods = [	validname: 'POST', save: 'POST', update: 'POST', delete: 'POST', addmember: 'POST', removemember: 'POST', addgroupmember: 'POST',
									 removegroupmember: 'POST', searchnewmembers: 'POST', searchnewgroupmembers: 'POST', createpermission: 'POST', removepermission: 'POST'	]


	PermissionService permissionService
	RoleService roleService

	def list() {
		if (!params.max) {
			params.max = 10
		}
		[roles: Role.list(params)]
	}

	def show(Long id) {
		Role role = Role.get(id)
		if (!role) {
			log.warn("Role identified by id '$id' was not located")
			flash.type = "error"
			flash.message = message(code: 'nimble.role.nonexistant', args: [id])
			redirect action: 'list'
			return
		}

		[role: role]
	}

	def create() {
		[role: new Role()]
	}

	def save(String name, String description) {

		Role createdRole = roleService.createRole(name, description, false)
		if (createdRole.hasErrors()) {
			log.error("Unable to create new role $name with description $description")
			flash.type = "error"
			flash.message = message(code: 'nimble.role.create.error', args: [createdRole.name])
			render view: 'create', model: [role: createdRole]
			return
		}

		log.info("Created new role $name with description $description")
		flash.type = "success"
		flash.message = message(code: 'nimble.role.create.success', args: [createdRole.name])
		redirect action: 'show', params: [id: createdRole.id]
	}

	def edit(Long id) {
		Role role = Role.get(id)
		if (!role) {
			log.warn("Role identified by id '$id' was not located")
			flash.type = "error"
			flash.message = message(code: 'nimble.role.nonexistant', args: [id])
			redirect action: 'list'
			return
		}

		log.debug("Editing role $role.name")
		[role: role]
	}

	def update(Long id) {
		Role role = Role.get(id)
		if (!role) {
			log.warn("Role identified by id '$id' was not located")
			flash.type = "error"
			flash.message = message(code: 'nimble.role.nonexistant', args: [id])
			redirect action: 'list'
			return
		}

		if (role.protect) {
			log.warn("Role [$role.id]$role.name is protected and can't be updated via the web interface")
			flash.type = "error"
			flash.message = message(code: 'nimble.role.protected.no.modification', args: [id])
			redirect (action: 'show', id: role.id)
			return
		}

		role.properties['name', 'description'] = params
		if (!role.validate()) {
			log.warn("Attempt to update role [$role.id]$role.name failed")
			flash.type = "error"
			flash.message = message(code: 'nimble.role.update.error', args: [role.name])
			render view: 'edit', model: [role: role]
			return
		}

		Role updatedRole = roleService.updateRole(role)
		if (updatedRole.hasErrors()) {
			log.warn("Attempt to update role [$role.id]$role.name failed")
			flash.type = "error"
			flash.message = message(code: 'nimble.role.update.error', args: [role.name])
			render view: 'edit', model: [role: updatedRole]
			return
		}

		log.info("Updated role $updatedRole.name with description $updatedRole.description")
		flash.type = "success"
		flash.message = message(code: 'nimble.role.update.success', args: [role.name])
		redirect action: 'show', params: [id: role.id]
	}

	def delete(Long id) {
		Role role = Role.get(id)
		if (!role) {
			log.warn("Role identified by id '$id' was not located")
			flash.type = "error"
			flash.message = message(code: 'nimble.role.nonexistant', args: [id])
			redirect action: 'list'
			return
		}

		if (role.protect) {
			log.warn("Role [$role.id]$role.name is protected and can't be updated via the web interface")
			flash.type = "error"
			flash.message = message(code: 'nimble.role.protected.no.modification', args: [id])
			redirect (action: 'show', id: role.id)
			return
		}

		roleService.deleteRole(role)
		log.info("Deleted role $role.name with description $role.description")
		flash.type = "success"
		flash.message = message(code: 'nimble.role.delete.success', args: [role.name])
		redirect action: 'list'
	}

	// AJAX related actions
	def validname(String val) {
		if (val == null || val.length() < 4) {
			render message(code: 'nimble.role.name.invalid', args: [val])
			response.status = 500
			return
		}

		List<Role> roles = Role.findAllByName(val)
		if (roles) {
			render message(code: 'nimble.role.name.invalid', args: [val])
			response.status = 500
			return
		}

		render message(code: 'nimble.role.name.valid', args: [val])
	}

	def listmembers(Long id) {
		Role role = Role.get(id)
		if (!role) {
			log.warn("Role identified by id '$id' was not located")
			render message(code: 'nimble.role.nonexistant', args: [id])
			response.status = 500
			return
		}

		log.debug("Listing role members")
		render(template: '/templates/admin/members_list', contextPath: pluginContextPath,
		       model: [parent:role, users: role.users, groups: role.groups, protect: role.protect, groupmembers: true])
	}

	def addmember(Long id, Long userID) {
		Role role = Role.get(id)
		if (!role) {
			log.warn("Role identified by id '$id' was not located")
			render message(code: 'nimble.role.nonexistant', args: [id])
			response.status = 500
			return
		}

		if (role.protect) {
			log.warn("Role [$role.id]$role.name is protected and can't be updated via the web interface")
			render message(code: 'nimble.role.protected.no.modification', args: [id])
			response.status = 500
			return
		}

		UserBase user = UserBase.get(userID)
		if (!user) {
			log.warn("User identified by id '$userID' was not located")
			render message(code: 'nimble.user.nonexistant', args: [userID])
			response.status = 500
			return
		}

		roleService.addMember(user, role)
		log.info("Added user [$user.id]$user.username to role [$role.id]$role.name")
		render message(code: 'nimble.role.addmember.success', args: [role.name, user.username])
	}

	def removemember(Long id, Long userID) {
		Role role = Role.get(id)
		if (!role) {
			log.warn("Role identified by id '$id' was not located")
			render message(code: 'nimble.role.nonexistant', args: [id])
			response.status = 500
			return
		}

		if (role.protect) {
			log.warn("Role [$role.id]$role.name is protected and can't be updated via the web interface")
			render message(code: 'nimble.role.protected.no.modification', args: [id])
			response.status = 500
			return
		}

		UserBase user = UserBase.get(userID)
		if (!user) {
			log.warn("user identified by id '$userID' was not located")
			render message(code: 'nimble.user.nonexistant', args: [userID])
			response.status = 500
			return
		}

		roleService.deleteMember(user, role)
		log.info("Removed user [$user.id]$user.username from role [$role.id]$role.name")
		render message(code: 'nimble.role.removemember.success', args: [role.name, user.username])
	}

	def addgroupmember(Long id, Long groupID) {
		Role role = Role.get(id)
		if (!role) {
			log.warn("Role identified by id '$id' was not located")
			render message(code: 'nimble.role.nonexistant', args: [id])
			response.status = 500
			return
		}

		if (role.protect) {
			log.warn("Role [$role.id]$role.name is protected and can't be updated via the web interface")
			render message(code: 'nimble.role.protected.no.modification', args: [id])
			response.status = 500
			return
		}

		Group group = Group.get(groupID)
		if (!group) {
			log.warn("Group identified by id '$groupID' was not located")
			render message(code: 'nimble.group.nonexistant', args: [groupID])
			response.status = 500
			return
		}

		if (group.protect) {
			log.warn("Group [$group.id]$group.name is protected and can't be updated via the web interface")
			render message(code: 'nimble.group.protected.no.modification', args: [id])
			response.status = 500
			return
		}

		roleService.addGroupMember(group, role)
		log.info("Added group [$group.id]$group.name to role [$role.id]$role.name")
		render message(code: 'nimble.role.addmember.success', args: [role.name, group.name])
	}

	def removegroupmember(Long id, Long groupID) {
		Role role = Role.get(id)
		if (!role) {
			log.warn("Role identified by id '$id' was not located")
			render message(code: 'nimble.role.nonexistant', args: [id])
			response.status = 500
			return
		}

		if (role.protect) {
			log.warn("Role [$role.id]$role.name is protected and can't be updated via the web interface")
			render message(code: 'nimble.role.protected.no.modification', args: [id])
			response.status = 500
			return
		}

		Group group = Group.get(groupID)
		if (!group) {
			log.warn("Group identified by id '$groupID' was not located")
			render message(code: 'nimble.group.nonexistant', args: [groupID])
			response.status = 500
			return
		}

		if (group.protect) {
			log.warn("Group [$group.id]$group.name is protected and can't be updated via the web interface")
			render message(code: 'nimble.group.protected.no.modification', args: [id])
			response.status = 500
			return
		}

		roleService.deleteGroupMember(group, role)
		log.info("Removed group [$group.id]$group.name from role [$role.id]$role.name")
		render message(code: 'nimble.role.removemember.success', args: [role.name, group.name])
	}

	def searchnewmembers(Long id, String q) {
		q = "%" + q + "%"
		log.debug("Performing search for users matching $q")

		List<UserBase> users = UserBase.findAllByUsernameIlike(q, [sort:'username'])
		List<ProfileBase> profiles = ProfileBase.findAllByFullNameIlikeOrEmailIlike(q, q)
		List<UserBase> nonMembers = []

		Role role = Role.get(id)
		if (!role) {
			log.warn("Role identified by id '$id' was not located")
			render message(code: 'nimble.role.nonexistant', args: [id])
			response.status = 500
			return
		}

		users.each {
			if (!it.roles.contains(role)) {
				nonMembers.add(it)
				log.debug("Adding user identified as [$it.id]$it.username to search results")
			}
		}
		profiles.each {
			if (!it.owner.roles.contains(role) && !nonMembers.contains(it.owner)) {
				nonMembers.add(it.owner)
				log.debug("Adding user identified as [$it.owner.id]$it.owner.username based on profile to search results")
			}
		}

		log.info("Search for new role user members complete, returning $nonMembers.size records")
		render(template: '/templates/admin/members_search', contextPath: pluginContextPath, model: [parent: role, users: nonMembers])
	}

	def searchnewgroupmembers(Long id, String q) {
		q = "%" + q + "%"
		log.debug("Performing search for groups matching $q")

		List<Group> groups = Group.findAllByNameIlike(q)
		List<Group> nonMembers = []

		Role role = Role.get(id)
		if (!role) {
			log.warn("Role identified by id '$id' was not located")
			render message(code: 'nimble.role.nonexistant', args: [id])
			response.status = 500
			return
		}

		groups.each {
			if (!it.roles.contains(role))  {
				nonMembers.add(it)    // Eject users that are already admins
				log.debug("Adding group identified as [$it.id]$it.name to search results")
			}
		}
		log.info("Search for new role group members complete, returning $nonMembers.size records")
		render(template: '/templates/admin/members_group_search', contextPath: pluginContextPath, model: [parent: role, groups: nonMembers])
	}

	def listpermissions(Long id) {
		Role role = Role.get(id)
		if (!role) {
			log.warn("Role identified by id '$id' was not located")
			render message(code: 'nimble.role.nonexistant', args: [id])
			response.status = 500
			return
		}

		log.debug("Listing permissions for role [$role.id]$role.name")
		render(template: '/templates/admin/permissions_list', contextPath: pluginContextPath, model: [permissions: role.permissions, parent: role])
	}

	def createpermission(Long id, String first, String second, String third, String fourth, String fifth, String sixth) {
		Role role = Role.get(id)
		if (!role) {
			log.warn("Role identified by id '$id' was not located")
			render message(code: 'nimble.role.nonexistant', args: [id])
			response.status = 500
			return
		}

		LevelPermission permission = new LevelPermission()
		permission.populate(first, second, third, fourth, fifth, sixth)
		permission.managed = false

		if (permission.hasErrors()) {
			log.debug("Submitted permission was not valid")
			render(template: "/templates/errors", contextPath: pluginContextPath, model: [bean: permission])
			response.status = 500
			return
		}

		def savedPermission = permissionService.createPermission(permission, role)
		if (savedPermission.hasErrors()) {
			log.warn("Submitted permission was unable to be assigned to role [$role.id]$role.name")
			render(template: "/templates/errors", contextPath: pluginContextPath, model: [bean: savedPermission])
			response.status = 500
			return
		}

		log.info("Assigned permission $savedPermission.id to role [$role.id]$role.name")
		render message(code: 'nimble.permission.create.success', args: [role.name])
	}

	def removepermission(Long id, Long permID) {
		Role role = Role.get(id)
		if (!role) {
			log.warn("Role identified by id '$id' was not located")
			render message(code: 'nimble.role.nonexistant', args: [id])
			response.status = 500
			return
		}

		Permission permission = Permission.get(permID)
		if (!permission) {
			log.warn("Permission identified by id '$permID' was not located")
			render message(code: 'nimble.permission.nonexistant', args: [id])
			response.status = 500
			return
		}

		permissionService.deletePermission(permission)
		log.info("Removed permission $permission.id from role [$role.id]$role.name")
		render message(code: 'nimble.permission.remove.success', args: [role.name])
	}
}
