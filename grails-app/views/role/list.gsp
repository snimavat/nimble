<%@ page import="grails.plugins.nimble.core.Role" %>
<head>
  <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
  <title><g:message code="nimble.view.role.list.title" /></title>
</head>

<body> 
    <div class="widget-header">
        <i class="icon-list"></i>
         <g:message code="nimble.view.role.list.heading" />
        <div class="pull-right">
            <g:link controller="role" action="create" class="btn btn-primary"><g:message code="nimble.link.createrole" /></g:link>
        </div>
    </div>   

  <table class="table table-striped">
    <thead>
    <tr>
      <g:sortableColumn property="name" titleKey="nimble.label.name" class="icon_arrow_refresh"/>
      <th><g:message code="nimble.label.name" /></th>
      <th class="last">&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${roles}" status="i" var="role">
      <tr>
        <td>${role.name?.encodeAsHTML()}</td>
        <td>${role.description?.encodeAsHTML()}</td>
        <td class="actionButtons">
          <span class="actionButton">
            <g:link action="show" id="${role.id}" class="btn btn-primary">
                <i class="icon-user icon-white"></i>
                <g:message code="nimble.link.view" />
            </g:link>
          </span>
        </td>
      </tr>
    </g:each>
    </tbody>
  </table>

  <div class="paginateButtons">
    <g:paginate total="${Role.count()}"/>
  </div>

</body>
