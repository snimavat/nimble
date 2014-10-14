
 <g:form action="${pageProperty(name:'action')}" class="form-horizontal">
    <g:layoutBody/>	
	<div class="form-actions">
		<button type="submit" class="btn btn-primary">
			<i class="glyphicon glyphicon-ok"></i>
			${pageProperty(name:'submit.label') ?: 'Save'}
		</button>
		<g:link action="${pageProperty(name:'cancelAction') ?: 'list'}" class="btn btn-warning">
			<i class="glyphicon glyphicon-arrow-left"></i> ${pageProperty(name:'cancel.label').trim() ?: 'Cancel'}
        </g:link>
        
	</div>
</g:form>
