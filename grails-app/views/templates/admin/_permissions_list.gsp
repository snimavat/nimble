<g:if test="${permissions?.size() > 0}">
  <table class="table no-border table-striped">
    <thead>
    <tr>
      <th class="first"><g:message code="nimble.label.type" /></th>
      <th class=""><g:message code="nimble.label.target" /></th>
      <th class=""><g:message code="nimble.label.managed" /></th>
      <th class="last"></th>
    </tr>
    </thead>
    <tbody>

    <g:each in="${permissions}" status="i" var="perm">
      <tr>
        <td>${perm.type.tokenize('.').last().encodeAsHTML()}</td>
        <td>${perm.target.encodeAsHTML()}</td>
        <td>
          <g:if test="${perm.managed}"><span class="icon icon_tick"><g:message code="nimble.label.yes" /></span></g:if>
          <g:else><span class="icon icon_cross"><g:message code="nimble.label.no" /></span></g:else>
        </td>
        <td>
          <g:if test="${!perm.managed}">
			<a href="#" onClick="removePermission('${parent.id.encodeAsHTML()}', '${g.fieldValue(bean:perm, field:'id')}');" class="btn btn-danger">
			     <i class="icon-remove icon-white"></i>
			     <g:message code="nimble.link.remove" />
		    </a>
		  </g:if>
          <g:else>&nbsp;</g:else>
        </td>
      </tr>
    </g:each>

    </tbody>
  </table>
</g:if>
<g:else>
  <p>
    <g:message code="nimble.template.permission.list.noresults" />
  </p>
</g:else>