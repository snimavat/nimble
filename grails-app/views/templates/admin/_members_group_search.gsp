<g:if test="${groups != null && groups.size() > 0}">
   <table class="table table-striped">
      <thead>
         <tr>
            <th>
               <g:message code="nimble.label.groupname" />
            </th>
            <th></th>
         </tr>
      </thead>
      <tbody>
         <g:each in="${groups}" status="i" var="group">
            <tr>
               <g:if test="${group.name.length() > 30}">
                  <td>${group.name?.substring(0,30).encodeAsHTML()}...</td>
               </g:if>
               <g:else>
                  <td>${group.name?.encodeAsHTML()}</td>
               </g:else>
               <td>
                  <g:link controller="group" action="show" id="${group.id.encodeAsHTML()}" class="btn btn-info btn-mini">
                     <i class="glyphicon glyphicon-user"></i>
                     <g:message code="nimble.link.view" />
                  </g:link>
                  <a onClick="addMember('${parent.id.encodeAsHTML()}', '${group.id.encodeAsHTML()}', '${group.name.encodeAsHTML()}');" class="btn btn-success btn-mini">
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
      <strong>
         <g:message code="nimble.template.members.add.user.noresults" />
      </strong>
   </div>
</g:else>