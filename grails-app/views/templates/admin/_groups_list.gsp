<g:if test="${groups?.size() > 0}">
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
         <g:each in="${groups}" status="i" var="group">
            <tr>
               <td>${group.name?.encodeAsHTML()}</td>
               <td>${group.description?.encodeAsHTML()}</td>
               <td>
                  <g:link controller="group" action="show" id="${group.id.encodeAsHTML()}" class="btn btn-info btn-mini">
                     <i class="icon-user icon-white"></i>
                     <g:message code="nimble.link.view" />
                  </g:link>
                  <g:if test="${group.protect == false}">
                     <a onClick="removeGroup('${ownerID.encodeAsHTML()}', '${group.id.encodeAsHTML()}');" class="btn btn-danger btn-mini">
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
      <g:message code="nimble.template.groups.list.noresults" />
   </p>
</g:else>