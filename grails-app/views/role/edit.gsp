<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
    <title>
        <g:message code="nimble.view.role.edit.title"/>
    </title>
</head>

<body>

<div class="panel panel-primary">
    <div class="panel-heading"><g:message code="nimble.view.role.edit.heading" args="[role.name.encodeAsHTML()]"/></div>

    <div class="panel-body">
        <n:errors bean="${role}"/>
        <g:form method="post" action="update" role="form">
            <input type="hidden" name="id" value="${role.id.encodeAsHTML()}"/>

            <div class="form-group">
                <label for="name"><g:message code="nimble.label.name"/></label>
                <g:textField name="name" class="form-control" id="name" value="${role?.name}"/>
            </div>

            <div class="form-group">
                <label for="name"><g:message code="nimble.label.description"/></label>
                <g:textField name="description" class="form-control" id="description" value="${role?.description}"/>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary">
                    <i class="glyphicon glyphicon-ok"></i>
                    <g:message code="nimble.link.updaterole"/>
                </button>
                <g:link action="show" id="${role.id}" class="btn btn-warning">
                    <i class="glyphicon glyphicon-arrow-left"></i>
                    <g:message code="nimble.link.cancel"/>
                </g:link>

            </div>
        </g:form>

    </div>
</div>

</body>
</html>