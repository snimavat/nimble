<head>
   <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}" />
   <title>
      <g:message code="nimble.view.user.edit.title" />
   </title>
</head>
<body>
   <h3>
      <g:message code="nimble.view.user.edit.heading" args="[user.username]" />
      <span>
         <g:message code="nimble.view.user.edit.descriptive" />
      </span>
   </h3>
   <div class="box-generic">
      <n:errors bean="${user}"/>
      <g:form action="update" class="form-horizontal">
         <f:with bean="user">
            <f:field property="username" label="nimble.label.username" />
            <input type="hidden" name="id" value="${user.id}" />
            <input type="hidden" name="version" value="${user.version}" />
         </f:with>
         <div class="form-actions">
            <button type="submit" class="btn btn-primary">
               <i class="icon-ok icon-white"></i>
               <g:message code="nimble.link.updateuser" />
            </button>
            <g:link action="show" id="${user.id}" class="btn btn-warning">
               <i class="icon-arrow-left icon-white"></i>
               <g:message code="nimble.link.cancel" />
            </g:link>
         </div>
      </g:form>
   </div>
</body>