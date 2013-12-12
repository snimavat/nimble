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

import grails.util.Holders as ConfigurationHolder

/**
 * Our permission object encapsulates details that a normal Shiro deployment
 * would put into mapping tables to make life a little easier.
 *
 * @author Bradley Beddoes
 */
class Permission implements Serializable {

	public static final String defaultPerm = "grails.plugin.nimble.auth.WildcardPermission"
	public static final String wildcardPerm = "grails.plugin.nimble.auth.WildcardPermission"
	public static final String adminPerm = "grails.plugin.nimble.auth.AllPermission"

	String type
	String possibleActions = "*"
	String actions = "*"
	String target
	boolean managed

	UserBase user
	Role role
	Group group

	static belongsTo = [user: UserBase, role: Role, group:Group]

	static transients = ["owner"]

	static mapping = {
		cache usage: 'read-write', include: 'all'
		table ConfigurationHolder.config.nimble.tablenames.permission
	}

	static constraints = {
		type(blank: false)
		possibleActions(blank: false)
		actions(blank: false)
		target(blank: false)

		user(nullable:true)
		role(nullable:true)
		group(nullable:true)
	}

	void setOwner(owner) {
		if (owner instanceof UserBase) {
			user = owner
		}
		else if (owner instanceof Role) {
			role = owner
		}
		else if (owner instanceof Group) {
			group = owner
		}
	}

	def getOwner() {
		user ?: role ?: group
	}
}
