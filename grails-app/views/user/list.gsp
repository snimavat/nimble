<%@ page import="grails.plugin.nimble.core.UserBase" %>
<head>
   <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}" />
   <title>
      <g:message code="nimble.view.user.list.title" />
   </title>
</head>
<body>
   <h3>
      <g:message code="nimble.view.user.list.heading" />
   </h3>
   <div class="widget widget-heading-simple widget-body-gray">
      <div class="widget-body">
         <div class="row-fluid">
            <div class="span12">
               <g:link controller="user" action="create" class="btn btn-primary btn-small">
                  <g:message code="nimble.link.createuser" />
               </g:link>
            </div>
            <div class="pagination-info pull-right">
               <n:pageinfo list="${users}" total="${UserBase.count()}" name="Users"/>
            </div>
            <table class="table table-striped table-bordered table-white">
               <thead>
                  <tr>
                     <g:sortableColumn property="id" titleKey="nimble.label.id" class="center" />
                     <g:sortableColumn property="username" titleKey="nimble.label.username"/>
                     <th>
                        <g:message code="nimble.label.fullname" />
                     </th>
                     <g:sortableColumn property="enabled" titleKey="nimble.label.state" />
                     <g:sortableColumn property="dateCreated" titleKey="nimble.label.created" />
                     <g:sortableColumn property="lastUpdated" titleKey="nimble.label.lastupdated" />
                     <th class="center">Actions</th>
                  </tr>
               </thead>
               <tbody>
                  <g:each in="${users}" status="i" var="user">
                     <tr>
                        <td class="center">
                           <g:link action="show" id="${user.id}">${user.id}</g:link>
                        </td>
                        <g:if test="${user.username.length() > 50}">
                           <td>${user.username?.substring(0,50).encodeAsHTML()}...</td>
                        </g:if>
                        <g:else>
                           <td>${user.username?.encodeAsHTML()}</td>
                        </g:else>
                        <g:if test="${user.profile?.fullName}">
                           <td valign="top" class="value">${user.profile?.fullName?.encodeAsHTML()}</td>
                        </g:if>
                        <g:else>
                           <td>&nbsp;</td>
                        </g:else>
                        <td>
                           <g:if test="${user.enabled}">
                              <span class="icon icon_tick">&nbsp;</span>
                              <g:message code="nimble.label.enabled" />
                           </g:if>
                           <g:else>
                              <span class="icon icon_cross">&nbsp;</span>
                              <g:message code="nimble.label.disabled" />
                           </g:else>
                        </td>
                        <td>
                           <g:formatDate date="${user.dateCreated}"/>
                        </td>
                        <td>
                           <g:formatDate date="${user.lastUpdated}"/>
                        </td>
                        <td class="center actionButtons">
                           <span class="actionButton">
                              <g:link action="show" id="${user.id}" class="btn btn-primary btn-mini">
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
               <g:paginate total="${UserBase.count()}"/>
            </div>
         </div>
      </div>
   </div>
</body>