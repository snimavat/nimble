<div class="box-generic">
   <g:if test="${users != null && users.size() > 0}">
      <table class="table table-striped">
         <thead>
            <tr>
               <th>
                  <g:message code="nimble.label.username" />
               </th>
               <th>
                  <g:message code="nimble.label.fullname" />
               </th>
               <th>&nbsp;</th>
            </tr>
         </thead>
         <tbody>
            <g:each in="${users}" status="i" var="user">
               <tr>
                  <g:if test="${user.username.length() > 50}">
                     <td>${user.username?.substring(0,50).encodeAsHTML()}...</td>
                  </g:if>
                  <g:else>
                     <td>${user.username?.encodeAsHTML()}</td>
                  </g:else>
                  <td>${user?.profile?.fullName.encodeAsHTML()}</td>
                  <td>
                     <g:link controller="user" action="show" id="${user.id.encodeAsHTML()}" class="btn btn-info btn-mini">
                        <i class="icon-user icon-white"></i>
                        <g:message code="nimble.link.view" />
                     </g:link>
                     <a onClick="grantAdministrator('${user.id.encodeAsHTML()}', '${user.username.encodeAsHTML()}');" class="btn btn-warning btn-mini">
                        <i class=" icon-ok icon-white"></i>
                        <g:message code="nimble.link.grant" />
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
            <g:message code="nimble.view.admins.search.notfound" />
         </strong>
      </p>
   </g:else>
</div>