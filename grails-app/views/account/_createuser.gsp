<!doctype html>
<html>
   <head>
      <title>
         <g:message code="nimble.view.account.registeraccount.initiate.title" />
      </title>
      <asset:stylesheet src="nimble-login.css"/>
   </head>
   <body>
      <div class="login-container">
         <div class="login-content">
            <h2 class="border-bottom">
               <g:message code="nimble.view.account.registeraccount.initiate.heading" />
            </h2>
            <n:flashembed/>
            <n:errors bean="${user}"/>
            <g:form action="saveuser" name="signup-form" method="post">
               <div class="login-input">
                  <div class="control-group">
                     <div class="controls ">                         
                        <input type="text" id="username" name="username" value="${fieldValue(bean: user, field: 'username')}" placeholder="username"/>                                                  
                     </div>
                  </div>
                  <div class="control-group">
                     <div class="controls">
                        <input type="password" size="30" id="pass" name="pass" value="${user.pass?.encodeAsHTML()}" placeholder="password"/> 
                     </div>
                  </div>
                  <div class="control-group">
                     <div class="controls">
                        <input type="password" size="30" id="passConfirm" name="passConfirm" value="${user.passConfirm?.encodeAsHTML()}" placeholder="confirm password"/> 						                   
                     </div>
                  </div>
                  <div class="control-group">
                     <div class="controls">
                        <input type="text" size="30" id="fullName" name="fullName" value="${user.profile?.fullName?.encodeAsHTML()}" placeholder="full name"]> 						                   
                     </div>
                  </div>
                  <div class="control-group">
                     <div class="controls">
                        <input type="text" size="30" id="email" name="email" value="${user.profile?.email?.encodeAsHTML()}" placeholder="email"/> 						                   
                     </div>
                  </div>
               </div>
               <div class="login-actions">
                  <span class="pull-right clearfix">
                     <button type="submit" class="btn btn-primary">
                        <g:message code="nimble.link.registeraccount" />
                     </button>
                  </span>
               </div>
            </g:form>
         </div>
      </div>
      <asset:javascript src="jquery"/>
   </body>
</html>