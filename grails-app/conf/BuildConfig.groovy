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
		compile "org.grails.plugins:twitter-bootstrap:3.3.4"
		compile "org.grails.plugins:asset-pipeline:2.13.1"
		compile "org.grails.plugins:fields:1.5.1"
		runtime "org.grails.plugins:jquery:1.11.1"
		compile("org.grails.plugins:shiro:1.2.1") {
			excludes "servlet-api"
		}

		compile "org.grails.plugins:mail:1.0.7"

		build(":release:3.1.2", ":rest-client-builder:2.1.1") {
			export = false
		}
	}
}
