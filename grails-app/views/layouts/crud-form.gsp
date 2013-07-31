
 <g:form action="${pageProperty(name:'action')}" class="form-horizontal">
    <g:layoutBody/>	
	<div class="form-actions">
		<button type="submit" class="btn btn-primary">
			<i class="icon-ok icon-white"></i>
			${pageProperty(name:'submit.label') ?: 'Save'}
		</button>
		<g:link action="${pageProperty(name:'cancelAction') ?: 'list'}" class="btn btn-warning">
			<i class="icon-arrow-left icon-white"></i> ${pageProperty(name:'cancel.label').trim() ?: 'Cancel'}
        </g:link>
        
	</div>
</g:form>
