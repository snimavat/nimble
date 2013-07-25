
<table class="table table-striped">
  <thead>
	  <tr>
	    <th><g:message code="nimble.label.username" /></th>
	    <th><g:message code="nimble.label.fullname" /></th>
	    <th>&nbsp;</th>
	  </tr>
  </thead>
  <tbody>
  <g:each in="${admins}" status="i" var="user">
    <tr>
      	<g:if test="${user.username.length() > 50}">
        	<td>${user.username?.substring(0,50).encodeAsHTML()}...</td>
		</g:if>
		<g:else>
			<td>${user.username?.encodeAsHTML()}</td>
		</g:else>
      <td>${user?.profile?.fullName.encodeAsHTML()}</td>
      <td>
        <g:link controller="user" action="show" id="${user.id.encodeAsHTML()}" class="btn btn-info">
            <i class="icon-user icon-white"></i>
            <g:message code="nimble.link.view" />
         </g:link>

        <g:if test="${currentAdmin != user}">
          <a onClick="deleteAdministrator('${user.id.encodeAsHTML()}', '${user.username.encodeAsHTML()}');" class="btn btn-warning">
            <i class=" icon-remove icon-white"></i>
            <g:message code="nimble.link.revoke" />
          </a>
        </g:if>
      </td>
    </tr>
  </g:each>
  </tbody>
</table>