<g:if test="${inline}">
    <g:each in="${type.values()}" var="item">
		<label class="radio inline">    
		    <%-- <input type="checkbox" name="${property}" id="${property}" ${value ? 'checked' : '' }/>  --%>    			   
		    <g:radio name="${property}" value="${item.name()}" checked="${item == value }"/>&nbsp; ${item.name } 
		</label>        
    </g:each>
</g:if>
<g:else>
<g:select name="${property}" from="${type.values()}" value="${value?.name()}" keys="${type.values()*.name() }" optionValue="name" noSelection="['':'--- Select ---']"/>
</g:else>