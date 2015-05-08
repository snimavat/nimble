<!doctype html>
<html>
   <head>
      <title>
         <g:message code="nimble.view.account.forgottenpassword.initiate.title" />
      </title>
       <asset:stylesheet src="bootstrap" />
       <asset:stylesheet src="nimble-login" />
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
                  <div class="form-group">
                        <input type="text" size="30" name="email" id="email" placeholder="user@example.com" class="form-control">
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