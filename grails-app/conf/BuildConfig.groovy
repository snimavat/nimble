//grails.project.work.dir = 'target'
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6


grails.project.dependency.resolution = {
	inherits("global")
	log "warn"
	legacyResolve false

	repositories {
		grailsCentral()
		mavenLocal()
		mavenCentral()
	}
	dependencies {
		compile "net.tanesha.recaptcha4j:recaptcha4j:0.0.7"
	}

	plugins {
		runtime ":twitter-bootstrap:2.3.2"
		runtime ":resources:1.2"
		runtime ":fields:1.3"
		runtime ":jquery:1.10.2"

		compile ":shiro:1.1.4"
		compile ":mail:1.0.1"

		build ':release:2.2.1', ':rest-client-builder:1.0.3', {
			export = false
		}
	}
}
