<ul class="nav nav-pills nav-stacked" id="dashboard-menu">
    <li class="${controllerName == 'admins' ? 'active' : ''}">
        <g:link controller="admins" action="index">
            <i class="glyphicon glyphicon-tower"></i>
            <span><g:message code="nimble.link.admins"/></span>
        </g:link>
    </li>
    <li class="${controllerName == 'user' ? 'active' : ''}">
        <g:link controller="user" action="list">
            <i class="glyphicon glyphicon-user"></i>
            <span><g:message code="nimble.link.users"/></span>
        </g:link>
    </li>
    <li class="${controllerName == 'role' ? 'active' : ''}">
        <g:link controller="role" action="list">
            <i class="glyphicon glyphicon-tag"></i>
            <span><g:message code="nimble.link.roles"/></span>
        </g:link>
    </li>
    <li class="${controllerName == 'group' ? 'active' : ''}">
        <g:link controller="group" action="list">
            <i class="glyphicon glyphicon-asterisk"></i>
            <span><g:message code="nimble.link.groups"/></span>
        </g:link>
    </li>
</ul>