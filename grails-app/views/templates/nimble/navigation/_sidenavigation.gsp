<ul  class="nav nav-list" id="dashboard-menu">
   <!--  
   <li class="nav-header">
      <i class="icon-home"></i>
      <span><g:message code="nimble.template.sidenavigation.heading" /></span>
   </li>
   -->   
   <li>       
      <g:link controller="user" action="list">
         <i class="icon-home"></i>
         <span><g:message code="nimble.link.users" /></span>
      </g:link>
      
   </li>
   <li>
      <g:link controller="role" action="list">
         <i class="icon-home"></i>
         <span><g:message code="nimble.link.roles" /></span>      
      </g:link>
   </li>
   <li>
      <g:link controller="group" action="list">
         <i class="icon-home"></i>
         <span><g:message code="nimble.link.groups" /></span>                     
      </g:link>
   </li>
   <li>
      <g:link controller="admins" action="index">
         <i class="icon-home"></i>
         <span><g:message code="nimble.link.admins" /></span>                                    
      </g:link>
   </li>
</ul>