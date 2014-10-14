<%@ page import="grails.plugin.nimble.core.Role" %>
<head>
    <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
    <title>
        <g:message code="nimble.view.role.list.title"/>
    </title>
</head>

<body>

<div class="panel panel-primary">
    <div class="panel-heading"><g:message code="nimble.view.role.list.heading"/></div>

    <div class="panel-body">
        <p>
            <g:link controller="role" action="create" class="btn btn-primary btn-small">
                <i class="glyphicon glyphicon-plus"></i>
                <g:message code="nimble.link.createrole"/>
            </g:link>
        </p>

        <div class="pagination-info pull-right">
            <n:pageinfo list="${roles}" total="${Role.count()}" name="Roles"/>
        </div>
        <table class="table">
            <thead>
            <tr>
                <g:sortableColumn property="id" titleKey="nimble.label.id" class="center"/>
                <g:sortableColumn property="name" titleKey="nimble.label.name"/>
                <th>
                    <g:message code="nimble.label.description"/>
                </th>
                <g:sortableColumn property="dateCreated" titleKey="nimble.label.created"/>
                <g:sortableColumn property="lastUpdated" titleKey="nimble.label.lastupdated"/>
                <th class="center">Actions</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${roles}" status="i" var="role">
                <tr>
                    <td class="center">
                        <g:link action="show" id="${role.id}">${role.id}</g:link>
                    </td>
                    <td>${role.name?.encodeAsHTML()}</td>
                    <td>${role.description?.encodeAsHTML()}</td>
                    <td>
                        <g:formatDate date="${role.dateCreated}"/>
                    </td>
                    <td>
                        <g:formatDate date="${role.lastUpdated}"/>
                    </td>
                    <td class="center actionButtons">
                        <span class="actionButton">
                            <g:link action="show" id="${role.id}" class="btn btn-primary btn-mini">
                                <i class="glyphicon glyphicon-tag"></i>
                                <g:message code="nimble.link.view"/>
                            </g:link>
                        </span>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>

        <div class="paginateButtons">
            <g:paginate total="${Role.count()}"/>
        </div>
    </div>
</div>
</body>