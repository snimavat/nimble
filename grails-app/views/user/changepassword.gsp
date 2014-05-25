<head>
    <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
    <title>
        <g:message code="nimble.view.user.changepassword.title"/>
    </title>
    <r:require modules="bootstrap-popover"/>
</head>

<body>

<div class="panel panel-primary">
    <div class="panel-heading"><g:message code="nimble.view.user.changepassword.heading" args="[user.username]"/></div>

    <div class="panel-body">
        <n:errors bean="${user}"/>
        <g:form method="post" action="savepassword" role="form">
            <g:hiddenField name="id" value="${user.id.encodeAsHTML()}"/>

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

                <button type="submit" class="btn btn-primary">
                    <i class="glyphicon glyphicon-ok"></i>
                    <g:message code="nimble.link.changepassword"/>
                </button>
                <g:link action="show" id="${user.id}" class="btn btn-warning">
                    <i class="glyphicon glyphicon-arrow-left"></i>
                    <g:message code="nimble.link.cancel"/>
                </g:link>

            </div>
        </g:form>
    </div>
</div>
<n:passwordpolicy/>
</body>