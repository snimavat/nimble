<head>
   <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
   <title>
      <g:message code="nimble.view.user.create.title" />
   </title>
</head>
<body>
   <h3>
      <g:message code="nimble.view.user.create.heading" />
      <span>
         <g:message code="nimble.view.user.create.descriptive" />
      </span>
   </h3>
   <div class="box-generic">
      <n:errors bean="${user}"/>
      <g:form action="save" class="form-horizontal">
         <div class="form-group">
            <label class="control-label col-md-2" for="username"><g:message code="nimble.label.username" />*</label>
            <div class="col-sm-8">
               <input type="text" id="username" name="username" value="${fieldValue(bean: user, field: 'username')}" class="form-control"/>
               <span class="help-inline">
                  <a href="#" id="usernamepolicybtn" rel="usernamepolicy" title="${message(code:'nimble.template.usernamepolicy.title')}">
                  <i class="icon-question-sign"></i>
               </a>          
               </span>            
            </div>
         </div>
         <div class="form-group">
            <label class="control-label col-md-2" for="pass"><g:message code="nimble.label.password" />*</label>
            <div class="col-sm-8">
               <input type="password" id="pass" name="pass" class="password form-control" value="${user.pass?.encodeAsHTML()}">
               <span class="help-inline">
               <a href="#" id="passwordpolicybtn" rel="passwordpolicy" title="${message(code:'nimble.template.passwordpolicy.title')}">
               <i class="icon-question-sign"></i>
               </a>          
               </span>
            </div>
         </div>
         <div class="form-group">
            <label class="control-label col-md-2" for="passConfirm">
               <g:message code="nimble.label.password.confirmation" />
               * 
            </label>
            <div class="col-md-8">
               <input type="password" id="passConfirm" name="passConfirm" value="${user.passConfirm?.encodeAsHTML()}" class="form-control">
            </div>
         </div>
         <div class="form-group">
            <label class="control-label col-md-2" for="fullName">
               <g:message code="nimble.label.fullname" />
            </label>
            <div class="col-md-8">
               <input type="text" id="fullName" name="fullName"  value="${user.profile?.fullName?.encodeAsHTML()}" class="form-control">
            </div>
         </div>
         <div class="form-group">
            <label class="control-label col-md-2" for="email">
               <g:message code="nimble.label.email" />
            </label>
            <div class="col-md-8">
               <input type="email" id="email" name="email"  value="${user.profile?.email?.encodeAsHTML()}" class="form-control">
            </div>
         </div>
         <div class="form-actions col-md-offset-2">
            <button type="submit" class="btn btn-primary">
               <i class="icon-ok icon-white"></i>
               <g:message code="nimble.link.createuser" />
            </button>
            <g:link action="list" class="btn btn-warning">
               <i class="icon-arrow-left icon-white"></i>
               <g:message code="nimble.link.cancel" />
            </g:link>
         </div>
      </g:form>
   </div>

   <asset:javascript src="bootstrap-tooltip"/>
   <asset:javascript src="bootstrap-popover"/>
   <n:usernamepolicy/>
   <n:passwordpolicy/>
</body>