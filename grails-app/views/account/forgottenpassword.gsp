<!doctype html>
<html>
   <head>
      <title>
         <g:message code="nimble.view.account.forgottenpassword.initiate.title" />
      </title>
      <r:require modules="nimble-login" />
      <r:layoutResources />
   </head>
   <body>
      <div class="login-container">
         <div class="login-content">
            <h2 class="border-bottom">
               <g:message code="nimble.view.account.forgottenpassword.initiate.heading" />
            </h2>
            
            <n:flashembed />
            <div class="login-options">
            <span>
               <g:message code="nimble.view.account.forgottenpassword.initiate.descriptive" />
            </span>         
			</div>               
            <g:form action="forgottenpasswordprocess" method="POST">
               <div class="login-input">
                  <div class="control-group">
                     <div class="controls "> 
                        <input type="text" size="30" name="email" id="email" placeholder="${message(code: 'nimble.label.email')}">
                     </div>
                  </div>
               </div>
               <div class="login-actions">
                  <span>
                  <button type="submit" class="btn btn-primary">
                     <g:message code="nimble.link.resetpassword" />
                  </button>
                  </span>
               </div>
            </g:form>
         </div>
      </div>
      <r:layoutResources />
   </body>
</html>