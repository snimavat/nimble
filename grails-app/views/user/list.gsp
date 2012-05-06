<%@ page import="grails.plugins.nimble.core.UserBase" %>
<head>
   <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}" />
   <title>
      <g:message code="nimble.view.user.list.title" />
   </title>
</head>
<body>
    <div class="widget-header">
        <i class="icon-list"></i>
        <g:message code="nimble.view.user.list.heading" />
        <div class="pull-right">
            <g:link controller="user" action="create" class="btn btn-primary"><g:message code="nimble.link.createuser" /></g:link>
        </div>
    </div>   
   <table class="table table-striped">
      <thead>
         <tr>
            <g:sortableColumn property="username" titleKey="nimble.label.username" class="icon_arrow_refresh" />
            <th>
               <g:message code="nimble.label.fullname" />
            </th>
            <g:sortableColumn property="enabled" titleKey="nimble.label.state" class="icon_arrow_refresh" />
            <th>Actions</th>
         </tr>
      </thead>
      <tbody>
         <g:each in="${users}" status="i" var="user">
            <tr>
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
               <td class="actionButtons">
                  <span class="actionButton">
                     <g:link action="show" id="${user.id}" class="btn btn-primary">
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
      <g:paginate total="${UserBase.count()}" />
   </div>
</body>