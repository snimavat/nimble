<html>
   <head>
      <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}" />
      <title>
         <g:message code="nimble.view.group.show.title" args="[group.name.encodeAsHTML()]" />
      </title>
      <r:script>
         <njs:permission parent="${group}" />
         <njs:role parent="${group}" />
         <njs:member parent="${group}" />
      </r:script>
   </head>
   <body>
      <div class="row-fluid">
         <div class="span5">
            <div class="well">
               <g:if test="${!group.protect}">
                  <div class="btn-group pull-right">
                     <a class="btn dropdown-toggle btn-primary" data-toggle="dropdown" href="#">
                        <i class="icon-cog icon-white"></i>
                        <g:message code="nimble.label.actions" />
                        <span class="caret"></span>
                     </a>
                     <g:render template="actions" model="[group:group]"></g:render>
                  </div>
               </g:if>
               <h2><g:message code="nimble.view.group.show.heading" args="[group.name.encodeAsHTML()]" /></h2>
               <h3><g:message code="nimble.view.group.show.details.heading" /></h3>
               <table class="table no-border">
                  <tbody>
                     <tr>
                        <th>
                           <g:message code="nimble.label.name" />
                        </th>
                        <td>${fieldValue(bean: group, field: 'name')}</td>
                     </tr>
                     <tr>
                        <th>
                           <g:message code="nimble.label.description" />
                        </th>
                        <td>${fieldValue(bean: group, field: 'description')}</td>
                     </tr>
                     <tr>
                        <th>
                           <g:message code="nimble.label.protected" />
                        </th>
                        <td>
                           <g:if test="${group.protect}">
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
               <a href="#tab-roles" data-toggle="tab">
                  <g:message code="nimble.label.roles" />
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
               <g:render template="/templates/admin/permissions" contextPath="${pluginContextPath}" model="[parent:group]" />
            </div>
            <div id="tab-roles" class="tab-pane">
               <g:render template="/templates/admin/roles" contextPath="${pluginContextPath}" model="[parent:group]" />
            </div>
            <div id="tab-members" class="tab-pane">
               <g:render template="/templates/admin/members" contextPath="${pluginContextPath}" model="[parent:group, protect:group.protect, groupmembers:false]" />
            </div>
         </div>
      </div>
      <g:form action="delete" name="deletegroup">
         <g:hiddenField name="id" value="${group.id.encodeAsHTML()}" />
      </g:form>
      <n:confirm />
   </body>
</html>