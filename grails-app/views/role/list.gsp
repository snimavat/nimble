<%@ page import="grails.plugin.nimble.core.Role" %>
<head>
   <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
   <title>
      <g:message code="nimble.view.role.list.title" />
   </title>
</head>
<body>
   <h3>
      <g:message code="nimble.view.role.list.heading" />
   </h3>
   <div class="widget widget-heading-simple widget-body-gray">
      <div class="widget-body">
         <div class="row-fluid">
            <div class="span8">
               <g:link controller="role" action="create" class="btn btn-primary btn-small">
                  <g:message code="nimble.link.createrole" />
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
                  <g:sortableColumn property="id" titleKey="nimble.label.id" class="center" />
                  <g:sortableColumn property="name" titleKey="nimble.label.name"/>                  
                  <th>
                     <g:message code="nimble.label.description" />
                  </th>
                  <g:sortableColumn property="dateCreated" titleKey="nimble.label.created" />                  
                  <g:sortableColumn property="lastUpdated" titleKey="nimble.label.lastupdated" />
                  <th class="center">Actions</th>
               </tr>
            </thead>
            <tbody>
               <g:each in="${roles}" status="i" var="role">
                  <tr>
                     <td class="center">
                        <g:link action="show" id="${role.id}">${role.id}</g:link>                     
                     </td>
                     <td>${role.name?.encodeAsHTML()}</td>
                     <td>${role.description?.encodeAsHTML()}</td>
                     <td>
                        <g:formatDate date="${role.dateCreated}"/>
                     </td>
                     <td>
                        <g:formatDate date="${role.lastUpdated}"/>
                     </td>                                          
                     <td class="center actionButtons">
                        <span class="actionButton">
                           <g:link action="show" id="${role.id}" class="btn btn-primary btn-mini">
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
      </div>
   </div>
</body>