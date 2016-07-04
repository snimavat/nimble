<html>
   <head>
      <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
      <title>
         <g:message code="nimble.view.group.create.title" />
      </title>
   </head>
   <body>
      <h3>
         <g:message code="nimble.view.group.create.heading" />
         <span>
            <g:message code="nimble.view.group.create.descriptive" />
         </span>
      </h3>
      <div class="box-generic">
         <n:errors bean="${group}"/>
         <g:form method="post" action="save" class="form-horizontal">
            <f:with bean="group">
               <f:field property="name" label="nimble.label.name" />
               <f:field property="description" required="true" label="nimble.label.description" />
            </f:with>
            <div class="form-actions col-md-offset-2">
               <button type="submit" class="btn btn-primary">
                  <i class="icon-ok icon-white"></i>
                  <g:message code="nimble.link.creategroup" />
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