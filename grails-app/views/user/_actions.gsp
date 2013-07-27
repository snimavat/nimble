<ul class="dropdown-menu">
   <li id="disableuser">
      <a onClick="disableUser('${user.id}'); return false;" href="#">
         <g:message code="nimble.link.disableaccount" />
      </a>
   </li>
   <li id="enableuser">
      <a onClick="enableUser('${user.id}'); return false;" href="#">
         <g:message code="nimble.link.enableaccount" />
      </a>
   </li>
   <li id="disableuserapi">
      <a onClick="disableAPI('${user.id}'); return false;" href="#">
         <g:message code="nimble.link.disableapi" />
      </a>
   </li>
   <li id="enableuserapi">
      <a onClick="enableAPI('${user.id}'); return false;" href="#">
         <g:message code="nimble.link.enableapi" />
      </a>
   </li>
</ul>