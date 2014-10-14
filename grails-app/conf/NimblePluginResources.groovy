modules = {
	'nimble-core' {
		//dependsOn "nimble-layout-css, nimble-widgets-css, nimble-typography-css"
		//resource id:'core-css', url:[plugin:'nimble', dir:'css', file:'nimble-core.css'], disposition:'head'
		resource id:'core-js', url:[plugin:'nimble', dir:'js', file:'nimblecore.js'], disposition:'head'
	}

	'nimble-layout-css' {
		resource id:'css', url:[plugin:'nimble', dir:'css', file:'nimble-layout.css'], disposition:'head'
	}

	'nimble-typography-css' {
		resource id:'css', url:[plugin:'nimble', dir:'css', file:'nimble-typography.css'], disposition:'head'
	}

	'nimble-widgets-css' {
		resource id:'css', url:[plugin:'nimble', dir:'css', file:'nimble-widgets.css'], disposition:'head'
	}

	'nimble-login-css' {
		resource id:'css', url:[plugin:'nimble', dir:'css', file:'nimble-login.css'], disposition:'head'
	}

	'nimble-ui' {
		dependsOn "nimble-core"
		resource id:'ui-js', url:[plugin:'nimble', dir:'js', file:'nimbleui.js'], disposition:'head'
	}

	'jgrawl' {
		dependsOn 'jquery'
		resource id:'css', url:[plugin:'nimble', dir:'css', file:'jquery.jgrowl.min.css'], disposition:'head', nominify: true
		resource id:'js', url:[plugin:'nimble', dir:'js/jquery', file:'jquery.jgrowl-1.2.13.min.js'] , disposition:'head', nominify: true
	}

	'jquery-pstrength' {
		dependsOn 'jquery'
		resource id:'js', url:[plugin:'nimble', dir:'js/jquery', file:'jquery.pstrength.js']
	}

	'nimble-admin' {
		dependsOn 'jquery, jgrawl, jquery-pstrength'
		dependsOn 'nimble-ui, nimble-core'
		dependsOn 'bootstrap-css, bootstrap-modal, bootstrap-alert, bootstrap-tab, bootstrap-dropdown, bootstrap-collapse'
	}

	'nimble-login' { dependsOn 'nimble-login-css, jquery, bootstrap-css' }
}
