<html>
<head>
    <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
    <title>
        <g:message code="nimble.view.group.create.title"/>
    </title>
</head>

<body>

<div class="panel panel-primary">
    <div class="panel-heading"><g:message code="nimble.view.group.create.heading"/></div>
    <div class="panel-body">
        <n:errors bean="${group}"/>
        <g:form method="post" action="save" role="form">

            <div class="form-group">
                <label for="name"><g:message code="nimble.label.name"/></label>
                <g:textField name="name" class="form-control" id="name" value="${group?.name}"/>
            </div>

            <div class="form-group">
                <label for="description"><g:message code="nimble.label.description"/></label>
                <g:textField name="description" class="form-control" id="description" value="${group?.description}"/>
            </div>


            <div class="form-group">
                <button type="submit" class="btn btn-primary">
                    <i class="glyphicon glyphicon-ok"></i>
                    <g:message code="nimble.link.creategroup"/>
                </button>
                <g:link action="list" class="btn btn-warning">
                    <i class="glyphicon glyphicon-arrow-left"></i>
                    <g:message code="nimble.link.cancel"/>
                </g:link>
            </div>
        </g:form>

    </div>

</div>
</body>
</html>