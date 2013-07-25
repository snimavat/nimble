<%@ page import="grails.plugins.nimble.core.Group" %>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
   <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
   <title>
      <g:message code="nimble.view.group.list.title" />
   </title>
</head>
<body>
   <h3>
      <g:message code="nimble.view.group.list.heading" />
   </h3>
   <div class="widget widget-heading-simple widget-body-gray">
      <div class="widget-body">
         <div class="row-fluid">
            <div class="span8">
               <g:link controller="group" action="create" class="btn btn-primary">
                  <g:message code="nimble.link.creategroup" />
               </g:link>
            </div>
            <div class="span4">
               <form class="form-search pull-right">
                  <input type="text" class="input-medium search-query">
                  <button type="submit" class="btn">Search</button>
               </form>
            </div>
         </div>
         <table class="table table-striped table-bordered table-white">
            <thead>
               <tr>
                  <g:sortableColumn property="id" titleKey="nimble.label.id" class="center icon_arrow_refresh" />
                  <g:sortableColumn property="name" titleKey="nimble.label.name" class="icon icon_arrow_refresh"/>
                  <th>
                     <g:message code="nimble.label.description" />
                  </th>
                  <g:sortableColumn property="dateCreated" titleKey="nimble.label.created" class="icon_arrow_refresh" />
                  <g:sortableColumn property="lastUpdated" titleKey="nimble.label.lastupdated" class="icon_arrow_refresh" />
                  <th class="center">Actions</th>
               </tr>
            </thead>
            <tbody>
               <g:each in="${groups}" status="i" var="group">
                  <tr>
                     <td class="center">
                        <g:link action="show" id="${group.id}">${group.id}</g:link>
                     </td>
                     <td>${fieldValue(bean: group, field: 'name')}</td>
                     <td>${fieldValue(bean: group, field: 'description')}</td>
                     <td>
                        <g:formatDate date="${group.dateCreated}"/>
                     </td>
                     <td>
                        <g:formatDate date="${group.lastUpdated}"/>
                     </td>
                     <td class="center actionButtons">
                        <span class="actionButton">
                           <g:link controller="group" action="show" id="${group.id.encodeAsHTML()}" class="btn btn-primary">
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
            <g:paginate total="${Group.count().encodeAsHTML()}"/>
         </div>
      </div>
   </div>
</body>