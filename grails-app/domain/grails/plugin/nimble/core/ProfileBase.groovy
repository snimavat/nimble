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

import org.apache.shiro.crypto.hash.Md5Hash
import grails.util.Holders as ConfigurationHolder // Since Grails 2.x ConfigurationHolder is deprecated 

/**
 * Represents generic details about users that are useful to many applications
 *
 * @author Bradley Beddoes
 */
class ProfileBase implements Serializable {

	String fullName
	String nickName
	String email
	String nonVerifiedEmail
	String emailHash

	Date dateCreated
	Date lastUpdated

	def beforeInsert = { hashEmail() }

	def beforeUpdate = { hashEmail() }

	void hashEmail() {
		// Do MD5 hash of email for Gravatar
		if(email) {
			emailHash = new Md5Hash(email).toHex()
		}
	}

	static belongsTo = [owner:UserBase]

	static mapping = {
		cache usage: 'read-write', include: 'all'
		table ConfigurationHolder.config.nimble.tablenames.profilebase
	}

	static constraints = {
		fullName(nullable: true, blank: false)
		nickName(nullable: true, blank: false)
		email(nullable:true, blank:false, email: true, unique: true)
		nonVerifiedEmail(nullable:true, blank:false, email: true)
		emailHash(nullable: true, blank:true)
		dateCreated(nullable: true) // must be true to enable grails
		lastUpdated(nullable: true) // auto-inject to be useful which occurs post validation
	}
}
