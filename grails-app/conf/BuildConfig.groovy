grails.project.work.dir = 'target'

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
		mavenLocal()
		mavenCentral()
	}

	dependencies {
		compile "net.tanesha.recaptcha4j:recaptcha4j:0.0.7"
	}

	plugins {

	    compile ":scaffolding:2.0.3"

		runtime ":twitter-bootstrap:3.1.1"
		runtime ":resources:1.2.7"
		runtime ":fields:1.3"
		runtime ":jquery:1.11.0.1"
		compile (":shiro:1.2.1") {
			excludes "servlet-api"
		}
		compile ":mail:1.0.4"

		build ':release:3.0.1', ':rest-client-builder:2.0.1', {
			export = false
		}
	}
}
