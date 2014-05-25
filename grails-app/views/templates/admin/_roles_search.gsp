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
                  <g:link controller="role" action="show" id="${role.id.encodeAsHTML()}" class="btn btn-info btn-mini">
                     <i class="glyphicon glyphicon-tag icon-white"></i>
                     <g:message code="nimble.link.view" />
                  </g:link>
                  <a onClick="grantRole('${ownerID.encodeAsHTML()}', '${role.id.encodeAsHTML()}');" class="btn btn-success btn-mini">
                     <i class="glyphicon glyphicon-question-sign"></i>
                     <g:message code="nimble.link.assign" />
                  </a>
               </td>
            </tr>
         </g:each>
      </tbody>
   </table>
</g:if>
<g:else>
   <p>
      <strong>
         <g:message code="nimble.template.roles.add.noresults" />
      </strong>
   </p>
</g:else>