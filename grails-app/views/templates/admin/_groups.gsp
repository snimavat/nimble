<div id="groups">
  <h3><g:message code="nimble.template.groups.heading" /></h3>

  <div id="assignedgroups"> </div>

  <div id="showaddgroups">
    <a id="showaddgroupsbtn" class="btn btn-success">
        <i class="icon-plus icon-white"></i>
        <g:message code="nimble.link.addgroups" />
    </a>
  </div>

  <div id="addgroups">
    <h4><g:message code="nimble.template.groups.add.heading" /></h4>
    <p><g:message code="nimble.template.groups.add.descriptive" /></p>

    <form class="well form-inline">
      <g:textField name="qgroups" class="span3" placeholder="search groups"/>
      <button type="button" onClick="searchGroups('${parent.id.encodeAsHTML()}');" class="btn btn-primary">
        <i class="icon-search icon-white"></i>
        <g:message code="nimble.link.search" />
      </button>
      <button type="button" id="closegroupsearchbtn" class="btn btn-warning"><g:message code="nimble.link.close" /></button>
    </form>
    
    <div id="groupsearchresponse"></div>

  </div>
</div>