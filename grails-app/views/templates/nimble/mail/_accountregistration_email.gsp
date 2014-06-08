<html>

<head>

</head>

<body>

<p>
    <g:message code="nimble.template.mail.accountregistration.descriptive" />
</p>

<p>
    <g:if test="${grailsApplication.config.nimble.exposeUserId}">
        <a href="${createLink(absolute:true, controller: 'account', action: 'validateuser', id: user.id, params: [activation: user.actionHash])}"><g:message code="nimble.link.activateaccount" /></a>
    </g:if>
    <g:else>
        <a href="${createLink(absolute:true, controller: 'account', action: 'activateuser', id: user.actionHash)}"><g:message code="nimble.link.activateaccount" /></a>
    </g:else>
</p>

<p>
    <g:message code="nimble.template.mail.accountregistration.trouble" />
</p>
<p>
    <g:if test="${grailsApplication.config.nimble.exposeUserId}">
        ${createLink(absolute:true, controller: 'account', action: 'validateuser', id: user.id, params: [activation: user.actionHash])}
    </g:if>
    <g:else>
        ${createLink(absolute:true, controller: 'account', action: 'activateuser', id: user.actionHash)}
    </g:else>
</p>

</body>

</html>