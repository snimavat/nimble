<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title><g:message code="nimble.view.admins.title" /></title>

  <r:script disposition='head'>
    var adminListEndpoint = "${createLink(controller:'admins', action:'list')}";
	var adminSearchEndpoint = "${createLink(action:'search')}";
	var adminDeleteEndpoint = "${createLink(action:'delete')}";
	var adminGrantEndpoint = "${createLink(action:'create')}";

    $(function() {	  
      listAdministrators();
    });
  </r:script>

</head>

<body>

  <p>
    <g:message code="nimble.view.admins.descriptive" />
  </p>

  <div id="admins"> </div>

  <h3><g:message code="nimble.view.admins.addadmin.heading" /></h3>
  <p>
    <g:message code="nimble.view.admins.addadmin.descriptive" />
  </p>

	<div class="row">
		<div class="span9">
			<form class="form-inline well error">
				<g:textField name="q" class="span3" />
				<a onClick="searchAdministrators();" href="javascript:return false;" class="btn btn-success">
				    <i class="icon-search icon-white"></i>
				    <g:message code="nimble.link.search" />
				</a>
			</form>
		</div>
	</div>

	<div id="searchresponse" class="clear"> </div>
</body>