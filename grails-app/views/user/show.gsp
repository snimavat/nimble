<head>
    <meta name="layout" content="${grailsApplication.config.nimble.layout.administration}"/>
    <title>
        <g:message code="nimble.view.user.show.title" args="[user.username?.encodeAsHTML()]"/>
    </title>
    <asset:script>
        <njs:user user="${user}"/>
        <njs:permission parent="${user}"/>
        <njs:role parent="${user}"/>
        <njs:group parent="${user}"/>
    </asset:script>
</head>

<body>

<div class="panel panel-primary">
    <div class="panel-heading"><g:message code="nimble.view.user.show.details.heading"/></div>

    <div class="panel-body">
            <div class="btn-group pull-right">
                <g:link controller="user" action="edit" id="${user.id}" class="btn btn-success btn-small">
                    <g:message code="nimble.link.edit"/>
                </g:link>
                <g:if test="${user.external}">
                    <g:link controller="user" action="changelocalpassword" id="${user.id}" class="btn btn-small btn-success">
                        <g:message code="nimble.link.changelocalpassword"/>
                    </g:link>
                </g:if>
                <g:else>
                    <g:link controller="user" action="changepassword" id="${user.id}" class="btn btn-small btn-success">
                        <g:message code="nimble.link.changepassword"/>
                    </g:link>
                </g:else>
                <a href="#" class="btn btn-info btn-small dropdown-toggle" data-toggle="dropdown">
                    <g:message code="nimble.link.more" default="More"/>
                    <span class="caret"></span>
                </a>
                <g:render template="actions" model="[user: user]" />
            </div>


        <br /><br /><br />

        <div id="tabs">
            <ul class="nav nav-tabs">

                <li class="active">
                    <a href="#tab-basicinfo" data-toggle="tab">
                        <g:message code="nimble.label.details"/>

                    </a>
                </li>

                <li>
                    <a href="#tab-extendedinfo" data-toggle="tab">
                        <g:message code="nimble.label.extendedinformation"/>
                    </a>
                </li>
                <li>
                    <a href="#tab-permissions" data-toggle="tab">
                        <g:message code="nimble.label.permissions"/>
                    </a>
                </li>
                <li>
                    <a href="#tab-roles" data-toggle="tab">
                        <g:message code="nimble.label.roles"/>
                    </a>
                </li>
                <li>
                    <a href="#tab-groups" data-toggle="tab">
                        <g:message code="nimble.label.groups"/>
                    </a>
                </li>
                <li>
                    <a href="#tab-logins" data-toggle="tab">
                        <g:message code="nimble.label.logins"/>
                    </a>
                </li>
            </ul>

            <div class="tab-content">

                <div id="tab-basicinfo" class="tab-pane active">

                    <table class="table table-bordered" style="width: 50%;margin-top: 20px;" cellpadding="5px;" cellspacing="5px">
                        <tr>
                            <td class="bg-primary"><g:message code="nimble.label.username"/></td>
                            <td>${user.username?.encodeAsHTML()}</td>
                        </tr>
                        <tr>
                            <td class="bg-primary"><g:message code="nimble.label.fullname"/></td>
                            <td>${user.profile.fullName?.encodeAsHTML() ?: message(code: 'nimble.label.none')}</td>
                        </tr>
                        <tr>
                            <td class="bg-primary"><g:message code="nimble.label.email"/></td>
                            <td>${user.profile.email?.encodeAsHTML() ?: g.message(code: 'nimble.label.none')}</td>
                        </tr>
                        <tr>
                            <td class="bg-primary"><g:message code="nimble.label.created"/></td>
                            <td><g:formatDate format="E dd/MM/yyyy HH:mm:s:S" date="${user.dateCreated}"/></td>
                        </tr>
                        <tr>
                            <td class="bg-primary"><g:message code="nimble.label.lastupdated"/></td>
                            <td><g:formatDate format="E dd/MM/yyyy HH:mm:s:S" date="${user.lastUpdated}"/></td>
                        </tr>
                        <tr>
                            <td class="bg-primary"><g:message code="nimble.label.type"/></td>
                            <td><g:if test="${user.external}"><g:message code="nimble.label.external.managment"/></g:if>
                                <g:else><g:message code="nimble.label.local.managment"/></g:else></td>
                        </tr>
                        <tr>
                            <td class="bg-primary"><g:message code="nimble.label.state"/></td>
                            <td><div id="disableduser"><g:message code="nimble.label.enabled"/></div>

                                <div id="enableduser"><g:message code="nimble.label.disabled"/></div></td>
                        </tr>
                        <tr>
                            <td class="bg-primary"><g:message code="nimble.label.remoteapi"/></td>
                            <td><div id="enabledapi">Enabled</div>

                                <div id="disabledapi">Disabled</div></td>
                        </tr>
                    </table>
                </div>

                <div id="tab-extendedinfo" class="tab-pane">
                    <g:render template="/templates/nimble/user/extendedinformation" contextPath="/" model="[user: user]"/>
                </div>

                <div id="tab-permissions" class="tab-pane">
                    <g:render template="/templates/admin/permissions" contextPath="${pluginContextPath}" model="[parent: user]"/>
                </div>

                <div id="tab-roles" class="tab-pane">
                    <g:render template="/templates/admin/roles" contextPath="${pluginContextPath}" model="[parent: user]"/>
                </div>

                <div id="tab-groups" class="tab-pane">
                    <g:render template="/templates/admin/groups" contextPath="${pluginContextPath}" model="[parent: user]"/>
                </div>

                <div id="tab-logins" class="tab-pane">
                    <g:render template="/templates/admin/logins" contextPath="${pluginContextPath}" model="[parent: user]"/>
                </div>
            </div>
        </div>

    </div>
</div>
<asset:deferredScripts/>
</body>