<g:if test="${radio == 'true'}">      
     <label class="radio inline">    
         <g:radio name="${property}" value="true" checked="${value }"/>&nbsp; <g:message code="nimble.label.true" />
     </label>
     <label class="radio inline">    
         <g:radio name="${property}" value="false" checked="${!value}"/>&nbsp; <g:message code="nimble.label.false" />
     </label>                            
</g:if>
<g:else>
	<label class="checkbox">    
	    <%-- <input type="checkbox" name="${property}" id="${property}" ${value ? 'checked' : '' }/>  --%>    
	    <g:checkBox name="${property}" checked="${value}"/>  
	     <g:if test="${help}">        
	           &nbsp;${help}
	     </g:if>     
	</label>
</g:else>
