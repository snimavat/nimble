<html>
   <head>
      <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}" />
      <title>
         <g:message code="nimble.view.group.show.title" args="[group.name.encodeAsHTML()]" />
      </title>
      <asset:script>
         <njs:permission parent="${group}" />
         <njs:role parent="${group}" />
         <njs:member parent="${group}" />
      </asset:script>
   </head>
   <body>
      <h3>
         <g:message code="nimble.view.group.show.heading" args="[group.name.encodeAsHTML()]" />
         <span>
         </span>
      </h3>
      <div class="row-fluid">
         <div class="span5">
            <div class="box-generic">
               <header>
                  <h2>
                     <g:message code="nimble.view.group.show.details.heading" />
                  </h2>
                  <g:if test="${!group.protect}">
                     <div class="pull-right btn-group">
                        <g:link action="edit" id="${group.id}" class="btn btn-success btn-small">
                           <g:message code="nimble.link.edit" />
                        </g:link>
                        <n:confirmaction clazz="btn btn-small btn-danger" action="document.deletegroup.submit();" title="${message(code: 'nimble.template.delete.confirm.title')}" msg="${message(code: 'nimble.group.delete.confirm')}" accept="${message(code: 'nimble.link.accept')}" cancel="${message(code: 'nimble.link.cancel')}" class="">
                           <g:message code="nimble.link.delete" />
                        </n:confirmaction>
                     </div>
                  </g:if>
               </header>
               <dl>
                  <dt>
                     <g:message code="nimble.label.name" />
                  </dt>
                  <dd>${fieldValue(bean: group, field: 'name')}</dd>
                  <dt>
                     <g:message code="nimble.label.description" />
                  </dt>
                  <dd>${fieldValue(bean: group, field: 'description')}</dd>
                  <dt>
                     <g:message code="nimble.label.protected" />
                  </dt>
                  <dd>
                     <g:if test="${group.protect}">
                        <g:message code="nimble.label.yes" />
                     </g:if>
                     <g:else>
                        <g:message code="nimble.label.no" />
                     </g:else>
                  </dd>
               </dl>
            </div>
         </div>
      </div>
      <div class="box-generic">
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
      </div>
   </body>
</html>