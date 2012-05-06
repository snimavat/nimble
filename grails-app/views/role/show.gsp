<head>
   <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}" />
   <title>
      <g:message code="nimble.view.role.show.title" args="[role.name.encodeAsHTML()]" />
   </title>
   <r:script>
      <njs:permission parent="${role}" />
      <njs:member parent="${role}" />
   </r:script>
</head>
<body>
   <div class="row-fluid">
      <div class="span5">
         <div class="well">
            <g:if test="${!role.protect}">
            <div class="btn-group pull-right">
               <a class="btn dropdown-toggle btn-primary" data-toggle="dropdown" href="#">
                  <i class="icon-cog icon-white"></i>
                  <g:message code="nimble.label.actions" />
                  <span class="caret"></span>
               </a>
               <g:render template="actions" model="[role:role]"></g:render>
            </div>
            </g:if>
            <h2>
               <g:message code="nimble.view.role.show.heading" args="[role.name.encodeAsHTML()]" />
            </h2>
            <h3>
               <g:message code="nimble.view.role.show.details.heading" />
            </h3>
            <table class="table no-border">
               <tbody>
                  <tr>
                     <td valign="top" class="name">
                        <g:message code="nimble.label.name" />
                     </td>
                     <td valign="top" class="value">${fieldValue(bean: role, field: 'name')}</td>
                  </tr>
                  <tr>
                     <td valign="top" class="name">
                        <g:message code="nimble.label.description" />
                     </td>
                     <td valign="top" class="value">${fieldValue(bean: role, field: 'description')}</td>
                  </tr>
                  <tr>
                     <td valign="top" class="name">
                        <g:message code="nimble.label.protected" />
                     </td>
                     <td valign="top" class="value">
                        <g:if test="${role.protect}">
                           <span class="icon icon_tick">
                              <g:message code="nimble.label.yes" />
                           </span>
                        </g:if>
                        <g:else>
                           <span class="icon icon_cross">
                              <g:message code="nimble.label.no" />
                           </span>
                        </g:else>
                     </td>
                  </tr>
               </tbody>
            </table>
         </div>
      </div>
   </div>
   <div id="tabs" class="tabbable">
      <ul class="nav nav-tabs">
         <li class="active">
            <a href="#tab-permissions" data-toggle="tab">
               <g:message code="nimble.label.permissions" />
            </a>
         </li>
         <li>
            <a href="#tab-members" data-toggle="tab">
               <g:message code="nimble.label.members" />
            </a>
         </li>
      </ul>
      <div class="tab-content">
         <div id="tab-permissions" class="tab-pane active">
            <g:render template="/templates/admin/permissions" contextPath="${pluginContextPath}" model="[parent:role]" />
         </div>
         <div id="tab-members" class="tab-pane">
            <g:render template="/templates/admin/members" model="[parent:role, protect:role.protect, groupmembers:true]" />
         </div>
      </div>
   </div>
   <g:form action="delete" name="deleterole">
      <g:hiddenField name="id" value="${role.id.encodeAsHTML()}" />
   </g:form>
   <n:confirm />
</body>