<div id="currentpermission" style="margin-top: 20px;"></div>

<div id="showaddpermissions">
    <a id="showaddpermissionsbtn" class="btn btn-success btn-small">
        <i class="glyphicon glyphicon-plus"></i>
        <g:message code="nimble.link.addpermission"/>
    </a>
</div>

<div id="addpermissions">
    <h4>
        <g:message code="nimble.template.permission.add.heading"/>
    </h4>

    <div id="addpermissionserror"></div>

    <form class="well form-inline">

        <div class="form-group"><g:textField size="15" name="first_p" class="col-md-2 form-control"/>
            <strong>:</strong></div>

        <div class="form-group"><g:textField size="15" name="second_p" class="col-md-2 form-control"/>
            <strong>:</strong></div>

        <div class="form-group"><g:textField size="15" name="third_p" class="col-md-2 form-control"/>
            <strong>:</strong></div>

        <div class="form-group"><g:textField size="15" name="fourth_p" class="col-md-2 form-control"/></div>

    </form>
    <button type="button" onClick="createPermission(${parent.id.encodeAsHTML()});" class="btn btn-success">
        <i class="glyphicon glyphicon-ok-sign"></i>
        <g:message code="nimble.link.createpermission"/>
    </button>
    <button type="button" id="closepermissionsaddbtn" class="btn btn-warning">
        <g:message code="nimble.link.close"/>
    </button>
</div>