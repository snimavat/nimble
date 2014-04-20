<head>
   <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}" />
   <title>
      <g:message code="nimble.view.user.edit.title" />
   </title>
</head>
<body>

   <div class="row">

       <h3>
           <g:message code="nimble.view.user.edit.heading" args="[user.username]" />
           <span>
               <g:message code="nimble.view.user.edit.descriptive" />
           </span>
       </h3>

       <div class="col-md-4">
           <n:errors bean="${user}"/>
           <g:form action="update">

               <div class="form-group">
                   <f:with bean="user">
                       <f:field property="username" label="nimble.label.username" class="form-control" />
                       <input type="hidden" name="id" value="${user.id}" />
                       <input type="hidden" name="version" value="${user.version}" />
                   </f:with>
               </div>


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


   </div>
</body>