<h3>
   <g:message code="nimble.template.roles.heading" />
</h3>
<div id="assignedroles">
</div>
<div id="showaddroles">
   <a id="showaddrolesbtn" class="btn btn-success btn-small">
      <i class="icon-plus icon-white"></i>
      <g:message code="nimble.link.addroles" />
   </a>
</div>
<div id="addroles">
   <h4>
      <g:message code="nimble.template.roles.add.heading" />
      <span><g:message code="nimble.template.roles.add.descriptive" /></span>
   </h4>
   <form class="well form-inline">
      <g:textField name="qroles" class="span3" placeholder="search roles"/>
      <button type="button" onClick="searchRoles(${parent.id.encodeAsHTML()});" class="btn btn-primary btn-small">
         <i class="icon-search icon-white"></i>
         <g:message code="nimble.link.search" />
      </button>
      <button type="button" id="closerolesearchbtn" class="btn btn-warning btn-small">
         <g:message code="nimble.link.close" />
      </button>
   </form>
   <div id="rolesearchresponse" class="clear">
   </div>
</div>