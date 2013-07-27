<h4>
   <g:message code="nimble.template.members.list.users.heading" />
</h4>
<g:if test="${users?.size() > 0}">
   <table class="table table-striped">
      <thead>
         <tr>
            <th>
               <g:message code="nimble.label.username" />
            </th>
            <th>
               <g:message code="nimble.label.fullname" />
            </th>
            <th></th>
         </tr>
      </thead>
      <tbody>
         <g:each in="${users}" status="i" var="user">
            <tr>
               <g:if test="${user.username.length() > 30}">
                  <td>${user.username?.substring(0,30).encodeAsHTML()}...</td>
               </g:if>
               <g:else>
                  <td>${user.username?.encodeAsHTML()}</td>
               </g:else>
               <g:if test="${user.profile?.fullName}">
                  <td>${user.profile?.fullName?.encodeAsHTML()}</td>
               </g:if>
               <g:else>
                  <td>&nbsp;</td>
               </g:else>
               <td>
                  <g:link controller="user" action="show" id="${user.id.encodeAsHTML()}" class="btn btn-info btn-mini">
                     <i class="icon-user icon-white"></i>
                     <g:message code="nimble.link.view" />
                  </g:link>
                  <g:if test="${!protect}">
                     <a onClick="removeMember('${parent.id.encodeAsHTML()}', '${user.id.encodeAsHTML()}', '${user.username.encodeAsHTML()}');" class="btn btn-danger btn-mini">
                        <i class="icon-remove icon-white"></i>
                        <g:message code="nimble.link.remove" />
                     </a>
                  </g:if>
               </td>
            </tr>
         </g:each>
      </tbody>
   </table>
</g:if>
<g:else>
   <p>
      <g:message code="nimble.template.members.list.users.noresults" />
   </p>
</g:else>
<g:if test="${groupmembers}">
   <h4>
      <g:message code="nimble.template.members.list.groups.heading" />
   </h4>
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
                  <td>${group.name.encodeAsHTML()}</td>
                  <td>${group.description.encodeAsHTML()}</td>
                  <td>
                     <g:link controller="group" action="show" id="${group.id.encodeAsHTML()}" class="btn btn-info btn-mini">
                        <i class="icon-user icon-white"></i>
                        <g:message code="nimble.link.view" />
                     </g:link>
                     <g:if test="${!protect}">
                        <a onClick="removeGroupMember('${parent.id.encodeAsHTML()}', '${group.id.encodeAsHTML()}', '${group.name.encodeAsHTML()}');" class="btn btn-danger btn-mini">
                           <i class="icon-remove icon-white"></i>
                           <g:message code="nimble.link.remove" />
                        </a>
                     </g:if>
                  </td>
               </tr>
            </g:each>
         </tbody>
      </table>
   </g:if>
   <g:else>
      <p>
         <g:message code="nimble.template.members.list.groups.noresults" />
      </p>
   </g:else>
</g:if>