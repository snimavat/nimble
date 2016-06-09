<head>
   <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
   <title>
      <g:message code="nimble.view.user.changepassword.title" />
   </title>
   <asset:javascript src="bootstrap-popover"/>
</head>
<body>
   <h3>
      <g:message code="nimble.view.user.changepassword.heading" args="[user.username]" />
      <span>
         <g:message code="nimble.view.user.changepassword.descriptive" />
      </span>
   </h3>
   <div class="box-generic">
      <n:errors bean="${user}"/>
      <g:form action="savepassword" class="form-horizontal">
         <g:hiddenField name="id" value="${user.id.encodeAsHTML()}"/>
         <div class="control-group">
            <label class="control-label" for="pass">
               <g:message code="nimble.label.password" />
               * 
            </label>
            <div class="controls">      
               <input type="password" id="pass" name="pass" class="password">
               <span class="help-inline">
               <a href="#" id="passwordpolicybtn" rel="passwordpolicy" class="empty icon icon_help" title="${message(code:'nimble.template.passwordpolicy.title')}">
               <i class="icon-question-sign"></i>
               </a>          
               </span>
            </div>
         </div>
         <div class="control-group">
            <label class="control-label" for="passConfirm">
               <g:message code="nimble.label.password.confirmation" />
               * 
            </label>
            <div class="controls">      
               <input type="password" id="passConfirm" name="passConfirm">
            </div>
         </div>
         <div class="form-actions">
            <button type="submit" class="btn btn-primary">
               <i class="icon-ok icon-white"></i>
               <g:message code="nimble.link.changepassword" />
            </button>
            <g:link action="show" id="${user.id}" class="btn btn-warning">
               <i class="icon-arrow-left icon-white"></i>
               <g:message code="nimble.link.cancel" />
            </g:link>
         </div>
      </g:form>
      <n:passwordpolicy/>
   </div>
</body>