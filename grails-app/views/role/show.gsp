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
   <h3>
      <g:message code="nimble.view.role.show.heading" args="[role.name.encodeAsHTML()]" />
      <span>
        <g:message code="nimble.view.role.show.descriptive" args="[role.name.encodeAsHTML()]" />
      </span>
   </h3>
   <div class="row-fluid">
      <div class="span5">
         <div class="box-generic">
            <header>
               <h2>
                  <g:message code="nimble.view.role.show.details.heading" />
               </h2>
               <g:if test="${!role.protect}">
                  <div class="pull-right btn-group">
                     <g:link action="edit" id="${role.id}" class="btn btn-success btn-small">
                        <g:message code="nimble.link.edit" />
                     </g:link>
                     <n:confirmaction clazz="btn btn-small btn-danger" action="document.deleterole.submit();" title="${message(code: 'nimble.template.delete.confirm.title')}" msg="${message(code: 'nimble.role.delete.confirm')}" accept="${message(code: 'nimble.link.accept')}" cancel="${message(code: 'nimble.link.cancel')}" class="">
                        <g:message code="nimble.link.delete" />
                     </n:confirmaction>
                  </div>
               </g:if>
            </header>
            <dl>
               <dt>
                  <g:message code="nimble.label.name" />
               </dt>
               <dd>${role.name}</dd>
               <dt>
                  <g:message code="nimble.label.description" />
               </dt>
               <dd>${role.description }</dd>
               <dt>
                  <g:message code="nimble.label.protected" />
               </dt>
               <dd>
                  <g:if test="${role.protect}">
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
   </div>
</body>