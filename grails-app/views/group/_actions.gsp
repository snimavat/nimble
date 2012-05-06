<ul class="dropdown-menu">
   <li>
      <g:link controller="group" action="edit" id="${group.id}">
         <g:message code="nimble.link.edit" />
      </g:link>
   </li>
   <li>
      <n:confirmaction action="document.deletegroup.submit();" title="${message(code: 'nimble.template.delete.confirm.title')}" msg="${message(code: 'nimble.group.delete.confirm')}" accept="${message(code: 'nimble.link.accept')}" cancel="${message(code: 'nimble.link.cancel')}" class="">
         <g:message code="nimble.link.delete" />
      </n:confirmaction>
   </li>
</ul>