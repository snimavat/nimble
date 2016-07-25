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

import grails.transaction.Transactional
import org.codehaus.groovy.grails.commons.GrailsApplication

/**
 * Various Nimble specific pieces of logic, shouldn't need to be called by any
 * host application.
 *
 * @author Bradley Beddoes
 */
@Transactional
class NimbleService {

	GrailsApplication grailsApplication

	/**
	 * Integrates with extended Nimble bootstrap process, sets up basic Nimble environment
	 * once all domain objects etc ave dynamic methods available to them.
	 */
	void init() {

		// Perform all base Nimble setup
		def userRole = Role.findByName(UserService.USER_ROLE)
		if (!userRole) {
			userRole = new Role(description: 'Issued to all users',
			                    name: UserService.USER_ROLE,
			                    protect: true)
			userRole.save()

			if (userRole.hasErrors()) {
				userRole.errors.each { log.error(it) }
				throw new RuntimeException("Unable to create valid users role")
			}
		}

		def adminRole = Role.findByName(AdminsService.ADMIN_ROLE)
		if (!adminRole) {
			adminRole = new Role(description: 'Assigned to users who are considered to be system wide administrators',
			                     name: AdminsService.ADMIN_ROLE,
			                     protect: true)
			adminRole.save()

			if (adminRole.hasErrors()) {
				adminRole.errors.each { log.error(it) }
				throw new RuntimeException("Unable to create valid administrative role")
			}
		}

		// Execute all service init that relies on base Nimble environment
		def ctx = grailsApplication.mainContext
		for (service in grailsApplication.serviceClasses) {
			if(service.clazz.methods.find{it.name == 'nimbleInit'}) {
				ctx.getBean(service.propertyName).nimbleInit()
			}
		}
	}
}
