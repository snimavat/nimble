<head>
   <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
   <title>
      <g:message code="nimble.view.admins.title" />
   </title>
   <asset:script>
      var adminListEndpoint = "${createLink(controller:'admins', action:'list')}";
      var adminSearchEndpoint = "${createLink(action:'search')}";
      var adminDeleteEndpoint = "${createLink(action:'delete')}";
      var adminGrantEndpoint = "${createLink(action:'create')}";
      $(function() {      
      listAdministrators();
      });
   </asset:script>
</head>
<body>
   <h3>
      <g:message code="nimble.view.admins.heading" />
      <span><g:message code="nimble.view.admins.descriptive" /></span>      
   </h3>
   <div class="widget widget-heading-simple widget-body-gray">
      <div class="widget-body">
         <div id="admins"></div>
      </div>
   </div>
   <h4>
      <g:message code="nimble.view.admins.addadmin.heading" />
   </h4>
   <p>
      <g:message code="nimble.view.admins.addadmin.descriptive" />
   </p>
   <form class="form-inline well error">
      <g:textField name="q" class="span3" />
      <a onClick="searchAdministrators();" href="javascript:" class="btn btn-success btn-small">
         <i class="icon-search icon-white"></i>
         <g:message code="nimble.link.search" />
      </a>
   </form>
   <div id="user-searchresponse" class=""> </div>
</body>