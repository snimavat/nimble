<%@ page import="grails.plugin.nimble.core.UserBase; grails.plugin.nimble.core.Group" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
    <title>
        <g:message code="nimble.view.group.list.title"/>
    </title>
</head>

<body>

<div class="panel panel-primary">
    <div class="panel-heading"><g:message code="nimble.view.group.list.heading"/></div>
    <div class="panel-body">
        <p>
            <g:link controller="group" action="create" class="btn btn-primary btn-small">
                <i class="glyphicon glyphicon-plus"></i>
                <g:message code="nimble.link.creategroup"/>
            </g:link>
        </p>
        <div class="pagination-info pull-right">
            <n:pageinfo list="${groups}" total="${Group.count()}" name="Groups"/>
        </div>

        <table class="table">
            <thead>
            <tr>
                <g:sortableColumn property="id" titleKey="nimble.label.id" class="center"/>
                <g:sortableColumn property="name" titleKey="nimble.label.name"/>
                <th><g:message code="nimble.label.description"/></th>
                <g:sortableColumn property="dateCreated" titleKey="nimble.label.created"/>
                <g:sortableColumn property="lastUpdated" titleKey="nimble.label.lastupdated"/>
                <th class="center">Actions</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${groups}" status="i" var="group">
                <tr>
                    <td class="center">
                        <g:link action="show" id="${group.id}">${group.id}</g:link>
                    </td>
                    <td>${fieldValue(bean: group, field: 'name')}</td>
                    <td>${fieldValue(bean: group, field: 'description')}</td>
                    <td>
                        <g:formatDate date="${group.dateCreated}"/>
                    </td>
                    <td>
                        <g:formatDate date="${group.lastUpdated}"/>
                    </td>
                    <td class="center actionButtons">
                        <span class="actionButton">
                            <g:link controller="group" action="show" id="${group.id.encodeAsHTML()}" class="btn btn-primary btn-xs">
                                <i class="glyphicon glyphicon-asterisk"></i>
                                <g:message code="nimble.link.view"/>
                            </g:link>
                        </span>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
        <div class="paginateButtons">
            <g:paginate total="${Group.count()}"/>
        </div>
    </div>
</div>

</body>