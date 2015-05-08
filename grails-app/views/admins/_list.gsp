
<table class="table">
  <thead>
	  <tr>
	    <th><g:message code="nimble.label.id" /></th>
	    <th><g:message code="nimble.label.username" /></th>
	    <th><g:message code="nimble.label.fullname" /></th>
	    <th class="">Actions</th>
	  </tr>
  </thead>
  <tbody>
  <g:each in="${admins.sort {a,b -> a.username.compareTo(b.username)}}" status="i" var="user">
    <tr>
        <td>
        	<g:link controller="user" action="show" id="${user.id}">${user.id}</g:link>
        </td>
      	<g:if test="${user.username.length() > 50}">
        	<td>${user.username?.substring(0,50).encodeAsHTML()}...</td>
		</g:if>
		<g:else>
			<td>${user.username?.encodeAsHTML()}</td>
		</g:else>
      <td>${user?.profile?.fullName.encodeAsHTML()}</td>
      <td>
        <g:link controller="user" action="show" id="${user.id.encodeAsHTML()}" class="btn btn-primary btn-xs">
            <i class="glyphicon glyphicon-user"></i>
            <g:message code="nimble.link.view" />
         </g:link>

        <g:if test="${currentAdmin != user}">
          <a onClick="deleteAdministrator('${user.id.encodeAsHTML()}', '${user.username.encodeAsHTML()}');" class="btn btn-warning btn-xs">
            <i class=" glyphicon glyphicon-remove"></i>
            <g:message code="nimble.link.revoke" />
          </a>
        </g:if>
      </td>
    </tr>
  </g:each>
  </tbody>
</table>