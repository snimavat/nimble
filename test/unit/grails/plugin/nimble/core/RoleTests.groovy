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

import grails.util.Holders as ConfigurationHolder

/**
 * @author Bradley Beddoes
 */
class RoleTests extends GrailsUnitTestCase {

	private description = 'description'
	private String name = 'name'
	private protect = true

	private user1 = new UserBase()
	private user2 = new UserBase()
	private user3 = new UserBase()

	private group1 = new Group()
	private group2 = new Group()

	private dateCreated = new Date()
	private lastUpdated = new Date()

	protected void setUp() {
		super.setUp()
		ConfigurationHolder.config = [nimble: [tablenames: [role: 'role']]] as ConfigObject
	}

	private Role createValidRole() {
		new Role(description:description, name:name, protect:protect,
		users:[user1, user2, user3], groups:[group1, group2],
		dateCreated:dateCreated, lastUpdated:lastUpdated)
	}

	void testRoleCreation() {
		def role = createValidRole()

		assertEquals description, role.description
		assertEquals name, role.name
		assertEquals protect, role.protect
		assertTrue role.users.containsAll([user1, user2, user3])
		assertTrue role.groups.containsAll([group1, group2])
	}

	void testNameConstraint() {
		mockForConstraintsTests(Role)
		def role = createValidRole()

		assertTrue role.validate()

		role.name = ''
		assertFalse role.validate()

		role.name = null
		assertFalse role.validate()

		// must be unique
		def role2 = createValidRole()
		role2.name = 'name2'
		role.name = name
		mockForConstraintsTests(Role, [role, role2])

		assertTrue role.validate()
		assertTrue role2.validate()

		role2.name = name
		assertFalse role.validate()
		assertFalse role2.validate()

		role2.name = 'name2'
		assertTrue role.validate()
		assertTrue role2.validate()

		//min size
		role.name = '123'
		assertFalse role.validate()

		//max size
		role.name = 'abcd'.center(532)
		assertFalse role.validate()
	}

	void testDescriptionConstraint() {
		mockForConstraintsTests(Role)
		def role = createValidRole()

		assertTrue role.validate()

		role.description = ''
		assertFalse role.validate()

		role.description = null
		assertTrue role.validate()
	}

	void testDateCreatedConstraint() {
		mockForConstraintsTests(Role)

		def role = createValidRole()
		assertTrue role.validate()

		role.dateCreated = null
		assertTrue role.validate()
	}

	void testLastUpdatedConstraint() {
		mockForConstraintsTests(Role)

		def role = createValidRole()
		assertTrue role.validate()

		role.lastUpdated = null
		assertTrue role.validate()
	}
}
