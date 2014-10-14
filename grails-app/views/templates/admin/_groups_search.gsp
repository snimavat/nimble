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
                     <i class="glyphicon glyphicon-tag"></i>
                     <g:message code="nimble.link.view" />
                  </g:link>
                  <a onClick="grantGroup('${ownerID.encodeAsHTML()}', '${group.id.encodeAsHTML()}');" class="btn btn-warning btn-mini">
                     <i class="glyphicon glyphicon-plus"></i>
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
         <g:message code="nimble.template.groups.add.noresults" />
      </strong>
   </p>
</g:else>