grails.project.work.dir = 'target'
grails.project.dependency.resolver = "maven"
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

	    runtime ":twitter-bootstrap:3.3.4"
		runtime ":jquery:1.11.1"

        compile ":asset-pipeline:1.9.9"
        compile ":mail:1.0.7"
		compile (":shiro:1.2.1") {
			excludes "servlet-api"
		}

		build ':release:3.0.1', ':rest-client-builder:2.0.3', {
			export = false
		}
	}
}
