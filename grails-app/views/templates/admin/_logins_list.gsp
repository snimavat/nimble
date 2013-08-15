<g:if test="${logins?.size() > 0}">

  <p>
	<g:message code="nimble.template.logins.list.heading" args="[logins.size(), totalCount]"/>
  </p>

  <table class="table table-striped">
    <thead>
	    <tr>
	      <th><g:message code="nimble.label.remoteaddress" /></th>
	      <th><g:message code="nimble.label.remotehost" /></th>
	      <th><g:message code="nimble.label.useragent" /></th>
	      <th><g:message code="nimble.label.time" /></th>
	    </tr>
    </thead>
    <tbody>
    <g:each in="${logins}" status="i" var="login">
      <tr>
        <td>${login.remoteAddr?.encodeAsHTML()}</td>
        <td>${login.remoteHost?.encodeAsHTML()}</td>
        <td>${login.userAgent?.encodeAsHTML()}</td>
        <td>${login.dateCreated?.encodeAsHTML()}</td>
      </tr>
    </g:each>
    </tbody>
  </table>

</g:if>
<g:else>

  <p>
    <g:message code="nimble.template.logins.list.noresults" />
  </p>
  
</g:else>