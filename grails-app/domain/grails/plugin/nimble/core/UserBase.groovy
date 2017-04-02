package grails.plugin.nimble.core

import grails.util.Holders as ConfigurationHolder

/**
 * Represents a user within a Nimble Application
 *
 * @author Bradley Beddoes
 */

class UserBase implements Serializable {

	String username
	String realm
	String passwordHash
	String actionHash

	boolean enabled
	boolean external = false
	boolean federated = false
	boolean remoteapi = false

	Date expiration
	Date dateCreated
	Date lastUpdated

	static belongsTo = [Role, Group]
	static hasOne = [profile:ProfileBase]

	static hasMany = [
		passwdHistory: String,
		loginRecords: LoginRecord,
		roles: Role,
		groups: Group,
		permissions: Permission
	]

	static fetchMode = [
		groups: 'eager'
	]

	static mapping = {
		sort username:'desc'

		cache usage: 'read-write', include: 'all'
		table ConfigurationHolder.config.nimble.tablenames.user

		profile fetch: 'join'

		//roles cache: true, cascade: 'none'
		groups cache: true, cascade: 'none'
		permissions cache: true, sort:'target'
	}

	static constraints = {
		username(blank: false, unique: true, maxSize: 255, 
			minSize: ConfigurationHolder.config.nimble.localusers.usernames.minlength)
		passwordHash(nullable: true, blank: false
		actionHash(nullable: true, blank: false)
		realm(nullable: true, blank: false)
		expiration(nullable: true)
		dateCreated(nullable: true) 
		lastUpdated(nullable: true) 
	}

	// Transients
	static transients = ['pass', 'passConfirm']
	String pass
	String passConfirm
}
