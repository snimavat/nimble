<head>
   <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
   <title>
      <g:message code="nimble.view.user.changepassword.title" />
   </title>
   <r:require modules="bootstrap-popover"/>
</head>
<body>

   <div class="row">

       <div class="col-md-3">
           <h3>
               <g:message code="nimble.view.user.changepassword.heading" args="[user.username]" />
               <span>
                   <g:message code="nimble.view.user.changepassword.descriptive" />
               </span>
           </h3>

           <n:errors bean="${user}"/>
           <g:form action="savepassword">
               <g:hiddenField name="id" value="${user.id.encodeAsHTML()}"/>

               <div class="form-group">

                   <label class="control-label" for="pass">
                       <g:message code="nimble.label.password" />*
                       &nbsp;
                   </label>
                   <a href="#" id="passwordpolicybtn" rel="passwordpolicy" class="empty icon icon_help"
                      title="${message(code:'nimble.template.passwordpolicy.title')}">
                       <i class="glyphicon glyphicon-question-sign"></i>
                   </a>
                   <input type="password" id="pass" name="pass" class="form-control password">
               </div>

               <div class="form-group">
                   <label class="control-label" for="passConfirm">
                       <g:message code="nimble.label.password.confirmation" />
                       *
                   </label>
                   <div class="controls">
                       <input type="password" id="passConfirm" name="passConfirm" class="form-control">
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
   </div>
</body>