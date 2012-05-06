<%@ page import="grails.plugins.nimble.core.Group" %>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title><g:message code="nimble.view.group.list.title" /></title>
</head>
<body>
    <div class="widget-header">
        <i class="icon-list"></i>
        <g:message code="nimble.view.group.list.heading" />
        <div class="pull-right">
            <g:link controller="group" action="create" class="btn btn-primary"><g:message code="nimble.link.creategroup" /></g:link>
        </div>
    </div>   
  
    <table class="table table-striped">
      <thead>
      <tr>
        <g:sortableColumn property="name" titleKey="nimble.label.name" class="icon icon_arrow_refresh"/>
        <th class=""><g:message code="nimble.label.description" /></th>
        <th class="last"/>
      </tr>
      </thead>
      <tbody>
      <g:each in="${groups}" status="i" var="group">
        <tr>
          <td>${fieldValue(bean: group, field: 'name')}</td>
          <td>${fieldValue(bean: group, field: 'description')}</td>
          <td><g:link controller="group" action="show" id="${group.id.encodeAsHTML()}" class="btn btn-primary">
            <i class="icon-user icon-white"></i>
            <g:message code="nimble.link.view" />
          </g:link>
        </tr>
      </g:each>
      </tbody>
    </table>
  
  <div class="paginateButtons">
    <g:paginate total="${Group.count().encodeAsHTML()}"/>
  </div>

</body>

