<g:if test="${constraints?.widget == 'textarea' }">
    <g:textArea name="${property}" rows="${rows ?: 3}" class="form-control ${clazz}">${value}</g:textArea>
</g:if>
<g:else>
<input type="text" id="${property}" name="${property}" value="${value}" class="form-control ${clazz}"
 ${placeholder ? "placeholder=\"${placeholder}\"" : ''}
/>
</g:else>