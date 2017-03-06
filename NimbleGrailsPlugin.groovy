import grails.plugin.nimble.core.UserBase
import grails.util.Environment

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.credential.Sha256CredentialsMatcher
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.mail.javamail.JavaMailSenderImpl

class NimbleGrailsPlugin {
	def version = "1.0.1"
	def grailsVersion = "2.5 > *"
	def title = "Nimble"
	def author = "Sudhir Nimavat"
	def authorEmail = "sudhir@nimavat.me"
	def description = "Nimble is an extensive application base environment for Grails."
	def documentation = "http://snimavat.github.io/nimble/index.html"	
	def issueManagement = [ system: "GITHUB", url: "https://github.com/snimavat/nimble/issues" ]
	def scm = [system: 'GitHub', url: 'https://github.com/snimavat/nimble']
	def license = "APACHE"
	
	def observe = [
		'controllers',
		'services',
		'filters'
	]

	def pluginExcludes = [
		'grails-app/conf/NimbleConfig.groovy',
		'grails-app/conf/NimbleUrlMappings.groovy',
		'grails-app/conf/NimbleSecurityFilters.groovy',
		'grails-app/conf/NimbleBootStrap.groovy',
	]

	def doWithSpring = {
		loadNimbleConfig(application)
		credentialMatcher(Sha256CredentialsMatcher) { storedCredentialsHexEncoded = true }

		// Redefine mailSender
		def mailConfig = application.config.nimble.messaging.mail
		mailSender(JavaMailSenderImpl) {
			host = mailConfig.host ?: "localhost"
			defaultEncoding = mailConfig.encoding ?: "utf-8"
			if(mailConfig.port) port = mailConfig.port
			if(mailConfig.username) username = mailConfig.username
			if(mailConfig.password) password = mailConfig.password
			if(mailConfig.protocol) protocol = mailConfig.protocol
			if(mailConfig.props instanceof Map && mailConfig.props)
				javaMailProperties = mailConfig.props
		}
	}

	def doWithDynamicMethods = { ctx ->
		// Supply functionality to filters
		application.filtersClasses.each { filter ->
			log.debug("Injecting Nimble methods to Filter ${filter}")
			injectAuthn(filter.clazz, application)
		}

		// Supply functionality to controllers
		application.controllerClasses.each { controller ->
			log.debug("Injecting Nimble methods to Controller ${controller}")
			injectAuthn(controller, application)
		}

		// Supply functionality to services
		application.serviceClasses.each { service ->
			log.debug("Injecting Nimble methods to Service ${service}")
			injectAuthn(service, application)
		}
	}

	def onChange = { event ->
		if (!(event.source instanceof Class)) {
			return
		}

		Class clazz = event.source
		log.debug "onChange : Re-adding dynamic methods to $clazz.name"
		injectAuthn(clazz, event.application)
	}

	private void injectAuthn(clazz, application) {
		clazz.metaClass.getAuthenticatedSubject = { -> SecurityUtils.getSubject() }

		clazz.metaClass.getAuthenticatedUser = { ->
			def principal = SecurityUtils.getSubject()?.getPrincipal()
			UserBase authUser

			if(principal != null) {

				if(application.config?.nimble?.implementation?.user) {
					authUser = application.classLoader.loadClass(application.config.nimble.implementation.user).get(principal)
				}
				else {
					authUser = UserBase.get(principal)
				}

				if (!authUser) {
					throw new RuntimeException("User was not able to be obtained for id $principal")
				}

			} else {
				log.debug("No currently authenticated user found")
			}

			return authUser
		}
	}

	private ConfigObject loadNimbleConfig(GrailsApplication grailsApplication) {
		def config = grailsApplication.config
		GroovyClassLoader classLoader = new GroovyClassLoader(getClass().classLoader)

		// Merging default Nimble config into main application config
		config.merge(new ConfigSlurper(Environment.current.name).parse(classLoader.loadClass('DefaultNimbleConfig')))

		// Merging user-defined Nimble config into main application config if provided
		try {
			config.merge(new ConfigSlurper(Environment.current.name).parse(classLoader.loadClass('NimbleConfig')))
		} catch (Exception ignored) {
			// ignore, just use the defaults
		}

		return config
	}
}
