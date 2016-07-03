<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
      <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
      <title>
         <g:message code="nimble.view.role.create.title" />
      </title>
   </head>
   <body>
      <h3>
         <g:message code="nimble.view.role.create.heading" />
         <span>
            <g:message code="nimble.view.role.create.descriptive" />
         </span>
      </h3>
      <div class="box-generic">
         <n:errors bean="${role}"/>
         <g:form action="save" class="form-horizontal">
            <f:with bean="role">
               <f:field property="name" label="nimble.label.name" />
               <f:field property="description" label="nimble.label.description" />
            </f:with>
            <div class="form-actions col-md-offset-2">
               <button type="submit" class="btn btn-primary">
                  <i class="icon-ok icon-white"></i>
                  <g:message code="nimble.link.createrole" />
               </button>
               <g:link action="list" class="btn btn-warning">
                  <i class="icon-arrow-left icon-white"></i>
                  <g:message code="nimble.link.cancel" />
               </g:link>
            </div>
         </g:form>
      </div>
   </body>
</html>