<head>
    <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
    <title>
        <g:message code="nimble.view.role.show.title" args="[role.name.encodeAsHTML()]"/>
    </title>
    <asset:script>
        <njs:permission parent="${role}"/>
        <njs:member parent="${role}"/>
    </asset:script>
</head>

<body>

<div class="panel panel-primary">
    <div class="panel-heading"><g:message code="nimble.view.role.show.heading" args="[role.name.encodeAsHTML()]"/></div>

    <div class="panel-body">

        <g:if test="${!role.protect}">
            <div class="pull-right btn-group">
                <g:link action="edit" id="${role.id}" class="btn btn-success btn-small">
                    <g:message code="nimble.link.edit"/>
                </g:link>
                <n:confirmaction clazz="btn btn-small btn-danger" action="document.deleterole.submit();" title="${message(code: 'nimble.template.delete.confirm.title')}"
                                 msg="${message(code: 'nimble.role.delete.confirm')}" accept="${message(code: 'nimble.link.accept')}" cancel="${message(code: 'nimble.link.cancel')}" class="">
                    <g:message code="nimble.link.delete"/>
                </n:confirmaction>
            </div>
        </g:if>

        <br/><br/>

        <div id="tabs" class="tabbable">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="#tab-details" data-toggle="tab">
                        <g:message code="nimble.label.details"/>
                    </a>
                </li>
                <li>
                    <a href="#tab-permissions" data-toggle="tab">
                        <g:message code="nimble.label.permissions"/>
                    </a>
                </li>
                <li>
                    <a href="#tab-members" data-toggle="tab">
                        <g:message code="nimble.label.members"/>
                    </a>
                </li>
            </ul>

            <div class="tab-content">
                <div id="tab-details" class="tab-pane active">

                    <table class="table table-bordered" style="width: 50%;margin-top: 20px;" cellpadding="5px;" cellspacing="5px">
                        <tr>
                            <td class="bg-primary"><g:message code="nimble.label.name"/></td>
                            <td>${fieldValue(bean: role, field: 'name')}</td>
                        </tr>
                        <tr>
                            <td class="bg-primary"><g:message code="nimble.label.description"/></td>
                            <td>${fieldValue(bean: role, field: 'description')}</td>
                        </tr>
                        <tr>
                            <td class="bg-primary"><g:message code="nimble.label.protected"/></td>
                            <td><g:if test="${role.protect}">
                                <g:message code="nimble.label.yes"/>
                            </g:if>
                                <g:else>
                                    <g:message code="nimble.label.no"/>
                                </g:else></td>
                        </tr>
                    </table>
                </div>

                <div id="tab-permissions" class="tab-pane">
                    <g:render template="/templates/admin/permissions" contextPath="${pluginContextPath}" model="[parent: role]"/>
                </div>

                <div id="tab-members" class="tab-pane">
                    <g:render template="/templates/admin/members" model="[parent: role, protect: role.protect, groupmembers: true]"/>
                </div>
            </div>
        </div>

    </div>
</div>

<g:form action="delete" name="deleterole">
    <g:hiddenField name="id" value="${role.id.encodeAsHTML()}"/>
</g:form>
<n:confirm/>
<asset:deferredScripts/>
</body>