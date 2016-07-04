<!doctype html>
<html>
   <head>
      <title>
         <g:message code="nimble.template.login.title" />
      </title>
      <asset:stylesheet src="nimble-login.css"/>
   </head>
   <body>
      <div class="login-container">
         <div class="login-content">
            <h2 class="border-bottom">
               <g:message code="nimble.label.login.signin" />
            </h2>          
            <n:flashembed/>  
            <g:if test="${registration}">
               <div class="login-options">
                  <h4>
                     <g:message code="nimble.label.login.signup.heading"/>
                  </h4>
                  <g:message code="nimble.label.login.signup.descriptive"/>
                  <g:link controller="account" action="createuser">
                     <g:message code="nimble.link.login.signup"/>
                  </g:link>
               </div>
               <div style="text-align: center; margin-bottom: 3px;">-- OR --</div>
            </g:if>
            <g:form action="signin" name="login-form" method="post">
               <div class="login-input">
                  <div class="form-group">
                        <input type="hidden" name="targetUri" value="${targetUri}"/>
                        <input type="text" name="username" id="username" placeholder="user@example.com">
                  </div>
                  <div class="form-group">
                        <input type="password" name="password" id="password" placeholder="password">
                     </div>
                  </div>


               <div class="login-actions form-inline">
                  <label class="checkbox" style="display: inline-block;">
                     <input type="checkbox" name="rememberme">
                     <g:message code="nimble.label.rememberme" />
                  </label>
                  <span class="pull-right clearfix">
                     <button type="submit" class="btn btn-primary">
                        <g:message code="nimble.link.login.basic" />
                     </button>
                  </span>
               </div>

               <div class="login-options border-top">
                  <h4>
                     <g:message code="nimble.label.login.forgottenpassword.heading" />
                  </h4>                  
                  <g:message code="nimble.label.login.forgottenpassword.descriptive" />
                  <g:link controller="account" action="forgottenpassword" style="text-transform:lowercase;">
                     <g:message code="nimble.link.resetpassword" />
                  </g:link>
               </div>
            </g:form>
         </div>
      </div>
      <asset:javascript src="jquery"/>
   </body>
</html>