<head>
    <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
    <title>
        <g:message code="nimble.view.admins.title"/>
    </title>

    <asset:script type="text/javascript">
        var adminListEndpoint = "${createLink(controller: 'admins', action: 'list')}";
      var adminSearchEndpoint = "${createLink(action: 'search')}";
      var adminDeleteEndpoint = "${createLink(action: 'delete')}";
      var adminGrantEndpoint = "${createLink(action: 'create')}";
      $(function() {
        listAdministrators();
      });
    </asset:script>

</head>

<body>

<div class="panel panel-primary">
    <div class="panel-heading"><g:message code="nimble.view.admins.heading"/></div>
    <div class="panel-body">
        <div id="admins"></div>
    </div>
</div>
<h4><g:message code="nimble.view.admins.addadmin.heading"/></h4>
<form class="form-inline well error">
    <g:textField name="q" class="form-control"/>
    <a onClick="searchAdministrators();" href="javascript:" class="btn btn-success btn-small">
        <i class="glyphicon glyphicon-search"></i>
        <g:message code="nimble.link.search"/>
    </a>
</form>

<div id="user-searchresponse" class=""></div>
<asset:deferredScripts />
</body>