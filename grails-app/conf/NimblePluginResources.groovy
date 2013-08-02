
modules = {
	'nimble-core' {
		resource id:'core-css', url:[plugin:'nimble', dir:'css', file:'nimble-core.css'], disposition:'head'
		resource id:'core-js', url:[plugin:'nimble', dir:'js', file:'nimblecore.js'], disposition:'head'
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
		resource id:'css', url:[plugin:'nimble', dir:'css', file:'jquery.jgrowl.css'], disposition:'head'
		resource id:'js', url:[plugin:'nimble', dir:'js/jquery', file:'jquery.jgrowl_minimized.js'] , disposition:'head', nominify: true
	}

	'jquery-url' {
		dependsOn 'jquery'
		resource id:'js', url:[plugin:'nimble', dir:'js/jquery', file:'jquery.url.packed.js'], disposition:'head'
	}

	'jquery-bt' {
		dependsOn 'jquery'
		resource id:'js', url:[plugin:'nimble', dir:'js/jquery', file:'jquery.bt.js'], disposition:'head'
	}

	'jquery-pstrength' {
		dependsOn 'jquery'
		resource id:'js', url:[plugin:'nimble', dir:'js/jquery', file:'jquery.pstrength.js']
	}

	'nimble-admin' {
		dependsOn 'jquery, jgrawl, jquery-url, jquery-bt, jquery-pstrength'
		dependsOn 'nimble-core, nimble-ui'
		dependsOn 'bootstrap-css, bootstrap-responsive-css, bootstrap-modal, bootstrap-alert, bootstrap-tab, bootstrap-dropdown'
	}

	'nimble-login' { dependsOn 'nimble-login-css, jquery, bootstrap-css' }

}