grails.project.work.dir = '.grails'

grails.project.dependency.resolver = "maven" // or ivy
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
		runtime ":twitter-bootstrap:2.3.0"
		runtime ":resources:1.2"
		runtime ":fields:1.2"
		runtime ":jquery:1.9.0"
		compile (":shiro:1.2.0") {
			excludes "servlet-api"
		}
		compile ":mail:1.0.1"

		build(":release:3.1.2", ":rest-client-builder:2.1.1") {
			export = false
		}
	}
}
