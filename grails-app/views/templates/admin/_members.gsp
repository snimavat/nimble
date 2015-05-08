<div id="currentmembers"></div>
<g:if test="${!protect}">
    <div id="showaddmembers">
        <a id="showaddmembersbtn" class="btn btn-success">
            <i class="glyphicon glyphicon-plus"></i>
            <g:message code="nimble.link.addmembers"/>
        </a>
    </div>

    <div id="addmembers">
        <h4><g:message code="nimble.template.members.add.heading"/></h4>

        <g:if test="${groupmembers}">
            <form role="form">
                <div class="radio">
                    <label><g:radio name="memberselect" id="searchmembergroups" value="groups"/> <g:message code="nimble.label.groups"/></label>
                </div>

                <div class="radio">
                    <label><g:radio name="memberselect" id="searchmemberusers" checked="true" value="users"/> <g:message code="nimble.label.users"/></label>
                </div>
            </form>
        </g:if>

        <div id="memberaddusers">
            <form class="well form-inline">
                <div class="form-group">
                    <g:textField name="qmembers" class="col-md-3 form-control" placeholder="Search Members"/>
                </div>

                <div class="form-group">
                    <button type="button" onClick="searchMembers(${parent.id.encodeAsHTML()});" class="btn btn-primary">
                        <i class="glyphicon glyphicon-search"></i>
                        <g:message code="nimble.link.search"/>
                    </button>
                </div>

                <div class="form-group">
                    <button type="button" id="closeaddmembersbtn" class="btn btn-warning">
                        <g:message code="nimble.link.close"/>
                    </button>
                </div>
            </form>

            <div id="membersearchresponse">
            </div>
        </div>
        <g:if test="${groupmembers}">
            <div id="memberaddgroups">
                <p>
                    <g:message code="nimble.template.members.add.group.descriptive"/>
                </p>

                <form class="well form-inline">
                    <div class="form-group">
                        <g:textField name="qmembersgroup" class="col-md-3 form-control" placeholder="Search Groups"/>
                    </div>

                    <div class="form-group">
                        <button type="button" onClick="searchGroupMembers(${parent.id.encodeAsHTML()});" class="btn btn-primary">
                            <i class="glyphicon glyphicon-search"></i>
                            <g:message code="nimble.link.search"/>
                        </button>
                        <button type="button" id="closeaddgroupmembersbtn" class="btn btn-warning">
                            <g:message code="nimble.link.close"/>
                        </button>
                    </div>
                </form>

                <div id="membergroupsearchresponse"></div>
            </div>
        </g:if>
    </div>
</g:if>