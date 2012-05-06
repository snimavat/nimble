<g:if test="${groups != null && groups.size() > 0}">
  <table class="table table-striped">
    <thead>
    <tr>
      <th><g:message code="nimble.label.name" /></th>
      <th><g:message code="nimble.label.description" /></th>
      <th></th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${groups}" status="i" var="group">
      <tr>
        <td>${group.name.encodeAsHTML()}</td>
        <td>${group.description.encodeAsHTML()}</td>
        <td>
          <g:link controller="group" action="show" id="${group.id.encodeAsHTML()}" class="btn btn-info">
            <i class="icon-user icon-white"></i>
            <g:message code="nimble.link.view" />
          </g:link>
          <a onClick="addGroupMember('${parent.id.encodeAsHTML()}', '${group.id.encodeAsHTML()}', '${group.name.encodeAsHTML()}');" class="btn btn-warning">
            <g:message code="nimble.link.grant" />
          </a>
        </td>
      </tr>
    </g:each>
    </tbody>
  </table>
</g:if>
<g:else>
  <div class="info">
  	<strong><g:message code="nimble.template.members.add.group.noresults" /></strong>
  </div>
</g:else>