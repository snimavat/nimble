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
		runtime(":twitter-bootstrap:2.3.2", ":resources:1.2", ":fields:1.3") { export = false }
		compile(":shiro:1.1.3", ":mail:1.0.1") { export = false }
		build(":release:2.2.1", ":rest-client-builder:1.0.3") { export = false }

	}
}
