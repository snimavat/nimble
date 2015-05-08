<head>
    <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
    <title>
        <g:message code="nimble.view.user.create.title"/>
    </title>
</head>

<body>

<div class="panel panel-primary">
    <div class="panel-heading"><g:message code="nimble.view.user.create.heading"/></div>

    <div class="panel-body">
        <n:errors bean="${user}"/>
        <g:form method="post" action="save" role="form">

            <div class="form-group">
                <label for="username"><g:message code="nimble.label.username"/></label>
                <span class="help-inline">
                    <a href="#" id="usernamepolicybtn" rel="usernamepolicy" title="${message(code: 'nimble.template.usernamepolicy.title')}">
                        <i class="glyphicon glyphicon-question-sign"></i>
                    </a>
                </span>

                <input type="text" class="form-control" id="username" name="username" value="${fieldValue(bean: user, field: 'username')}">

            </div>

            <div class="form-group">
                <label for="pass"><g:message code="nimble.label.password"/></label>
                <span class="help-inline">
                    <a href="#" id="passwordpolicybtn" rel="passwordpolicy" title="${message(code: 'nimble.template.passwordpolicy.title')}">
                        <i class="glyphicon glyphicon-question-sign"></i>
                    </a>
                </span>

                <input type="password" class="form-control password" id="pass" name="pass" value="${user.pass?.encodeAsHTML()}">

            </div>

            <div class="form-group">
                <label for="passConfirm"><g:message code="nimble.label.password.confirmation"/></label>
                <input type="password" id="passConfirm" class="form-control" name="passConfirm" value="${user.passConfirm?.encodeAsHTML()}">
            </div>

            <div class="form-group">
                <label for="fullName"><g:message code="nimble.label.fullname"/></label>
                <input type="text" id="fullName" class="form-control" name="fullName" value="${user.profile?.fullName?.encodeAsHTML()}">
            </div>

            <div class="form-group">
                <label for="email"><g:message code="nimble.label.email"/></label>
                <input type="email" id="email"  class="form-control" name="email" value="${user.profile?.email?.encodeAsHTML()}">
            </div>


            <div class="form-group">

                <button type="submit" class="btn btn-primary">
                    <i class="glyphicon glyphicon-ok"></i>
                    <g:message code="nimble.link.createuser"/>
                </button>
                <g:link action="list" class="btn btn-warning">
                    <i class="glyphicon glyphicon-arrow-left"></i>
                    <g:message code="nimble.link.cancel"/>
                </g:link>

            </div>
        </g:form>

    </div>

</div>

<n:usernamepolicy/>
<n:passwordpolicy/>
</body>