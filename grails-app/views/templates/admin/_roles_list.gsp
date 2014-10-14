<g:if test="${roles?.size() > 0}">
   <table class="table table-striped">
      <thead>
         <tr>
            <th>
               <g:message code="nimble.label.name" />
            </th>
            <th>
               <g:message code="nimble.label.description" />
            </th>
            <th></th>
         </tr>
      </thead>
      <tbody>
         <g:each in="${roles}" status="i" var="role">
            <tr>
               <td>${role.name?.encodeAsHTML()}</td>
               <td>${role.description?.encodeAsHTML()}</td>
               <td>
                  <g:link controller="role" action="show" id="${role.id.encodeAsHTML()}" class="btn btn-info btn-xs">
                     <i class="glyphicon glyphicon-user"></i>
                     <g:message code="nimble.link.view" />
                  </g:link>
                  <g:if test="${role.protect == false}">
                     <button onClick="removeRole('${ownerID.encodeAsHTML()}', '${role.id.encodeAsHTML()}');" class="btn btn-danger btn-xs">
                        <i class="glyphicon glyphicon-remove"></i>
                        <g:message code="nimble.link.remove" />
                     </button>
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
      <g:message code="nimble.template.roles.list.noresults" />
   </p>
</g:else>