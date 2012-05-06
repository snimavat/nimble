<ul class="dropdown-menu">
   <g:if test="${!role.protect}">
      <li>
         <g:link controller="role" action="edit" id="${role.id}">
            <g:message code="nimble.link.edit" />
         </g:link>
      </li>
      <li>
         <n:confirmaction action="document.deleterole.submit();" title="${message(code: 'nimble.template.delete.confirm.title')}" msg="${message(code: 'nimble.role.delete.confirm')}" accept="${message(code: 'nimble.link.accept')}" cancel="${message(code: 'nimble.link.cancel')}" class="">
            <g:message code="nimble.link.delete" />
         </n:confirmaction>
      </li>
   </g:if>
</ul>