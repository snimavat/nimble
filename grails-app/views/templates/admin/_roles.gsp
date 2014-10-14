<div id="assignedroles">
</div>
<div id="showaddroles">
   <a id="showaddrolesbtn" class="btn btn-success btn-small">
      <i class="glyphicon glyphicon-plus"></i>
      <g:message code="nimble.link.addroles" />
   </a>
</div>
<div id="addroles">
   <h4>
      <g:message code="nimble.template.roles.add.heading" />
      <span><g:message code="nimble.template.roles.add.descriptive" /></span>
   </h4>
   <form class="well form-inline">
      <div class="form-group">
        <g:textField name="qroles" class="col-md-3 form-control" placeholder="Search roles"/>
      </div>
      <button type="button" onClick="searchRoles(${parent.id.encodeAsHTML()});" class="btn btn-primary btn-small">
         <i class="glyphicon glyphicon-search"></i>
         <g:message code="nimble.link.search" />
      </button>
      <button type="button" id="closerolesearchbtn" class="btn btn-warning btn-small">
         <g:message code="nimble.link.close" />
      </button>
   </form>
   <div id="rolesearchresponse" class="clear">
   </div>
</div>