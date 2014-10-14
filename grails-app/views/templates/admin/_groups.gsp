<div id="groups">
   <br />
   <div id="assignedgroups"> </div>
   <div id="showaddgroups">
      <a id="showaddgroupsbtn" class="btn btn-success btn-small">
         <i class="glyphicon glyphicon-plus"></i>
         <g:message code="nimble.link.addgroups" />
      </a>
   </div>
   <div id="addgroups">
      <h4>
         <g:message code="nimble.template.groups.add.heading" />
         <span>
            <g:message code="nimble.template.groups.add.descriptive" />
         </span>
      </h4>
      <form class="well form-inline">
         <div class="form-group"><g:textField name="qgroups" class="col-md-3 form-control" placeholder="Search groups"/></div>
         <button type="button" onClick="searchGroups('${parent.id.encodeAsHTML()}');" class="btn btn-primary">
            <i class="glyphicon glyphicon-search"></i>
            <g:message code="nimble.link.search" />
         </button>
         <button type="button" id="closegroupsearchbtn" class="btn btn-warning btn-small">
            <g:message code="nimble.link.close" />
         </button>
      </form>
      <div id="groupsearchresponse"></div>
   </div>
</div>