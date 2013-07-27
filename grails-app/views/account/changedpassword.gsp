<html>
   <head>
      <meta name="layout" content="${grailsApplication.config.nimble.layout.application}"/>
      <title>
         <g:message code="nimble.view.account.changepassword.complete.title" />
      </title>
   </head>
   <body>
      <h3>
         <g:message code="nimble.view.account.changepassword.complete.heading" />
         <span>
            <g:message code="nimble.view.account.changepassword.complete.descriptive" />
         </span>
      </h3>
      <div class="box-generic">
         <n:errors bean="${user}"/>
      </div>
   </body>
</html>