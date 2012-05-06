<div id="members" class="section">

  <h3><g:message code="nimble.template.members.heading" /></h3>
  <div id="currentmembers"> </div>

  <g:if test="${!protect}">
    <div id="showaddmembers">
      <a id="showaddmembersbtn" class="btn btn-primary">
        <i class="icon-plus icon-white"></i>
        <g:message code="nimble.link.addmembers" />
      </a>
    </div>

    <div id="addmembers">
      <h4><g:message code="nimble.template.members.add.heading" /></h4>
      <p>
        <g:message code="nimble.template.members.add.user.descriptive" />
      </p>
                  
      <div id="memberaddusers">
	    <form class="well form-inline">
	      <g:if test="${groupmembers}">  
	       <div class="control-group">  
	         <label class="radio inline">    
	             <g:radio name="memberselect" id="searchmembergroups" value="groups"/>&nbsp; <g:message code="nimble.label.groups" />
	         </label>
	         <label class="radio inline">    
	             <g:radio name="memberselect" id="searchmemberusers" checked="true" value="users"/>&nbsp;  <g:message code="nimble.label.users" />
	         </label>                 
	         </div>           
	      </g:if>
	      
        <div class="control-group">  
          <g:textField name="qmembers" class="span3" placeholder="search"/>
          <button type="button" onClick="searchMembers(${parent.id.encodeAsHTML()});" class="btn btn-primary">
            <i class="icon-search icon-white"></i>
            <g:message code="nimble.link.search" />
          </button>
          <button type="button" id="closeaddmembersbtn" class="btn btn-warning"><g:message code="nimble.link.close" /></button>
        </div>
	      
	    </form>      

        <div id="membersearchresponse">
        </div>
      </div>

      <g:if test="${groupmembers}">
        <div id="memberaddgroups">
          <p>
            <g:message code="nimble.template.members.add.group.descriptive" />
          </p>

	       <form class="well form-inline">	          
	        <div class="control-group">  	          
	          <g:textField name="qmembersgroup" class="span3" placeholder="search"/>
	          <button type="button" onClick="searchGroupMembers(${parent.id.encodeAsHTML()});" class="btn btn-primary">
	            <i class="icon-search icon-white"></i>
	            <g:message code="nimble.link.search" />
	          </button>
	          <button type="button" id="closeaddgroupmembersbtn" class="btn btn-warning"><g:message code="nimble.link.close" /></button>
	        </div>	         
	        </form>      
	        
          <div id="membergroupsearchresponse"></div>
        </div>
      </g:if>

    </div>
  </g:if>
</div>