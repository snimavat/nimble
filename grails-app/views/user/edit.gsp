<head>
    <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
    <title>
        <g:message code="nimble.view.user.edit.title"/>
    </title>
</head>

<body>

<div class="panel panel-primary">
    <div class="panel-heading"><g:message code="nimble.view.user.edit.heading" args="[user.username]"/></div>

    <div class="panel-body">
        <n:errors bean="${user}"/>
        <g:form method="post" action="update" role="form">

            <div class="form-group">
                <label for="username"><g:message code="nimble.label.username"/></label>
                <span class="help-inline">
                    <a href="#" id="usernamepolicybtn" rel="usernamepolicy" title="${message(code: 'nimble.template.usernamepolicy.title')}">
                        <i class="glyphicon glyphicon-question-sign"></i>
                    </a>
                </span>
                <input type="hidden" name="id" value="${user.id}"/>
                <input type="hidden" name="version" value="${user.version}"/>
                <input type="text" class="form-control" id="username" name="username" value="${fieldValue(bean: user, field: 'username')}">

            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary">
                    <i class="glyphicon glyphicon-ok"></i>
                    <g:message code="nimble.link.updateuser"/>
                </button>
                <g:link action="show" id="${user.id}" class="btn btn-warning">
                    <i class="glyphicon glyphicon-arrow-left"></i>
                    <g:message code="nimble.link.cancel"/>
                </g:link>

            </div>
        </g:form>
    </div>
</div>

</body>