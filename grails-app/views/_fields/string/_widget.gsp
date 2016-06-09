<g:if test="${constraints?.widget == 'textarea' }">
    <g:textArea name="${property}" rows="${rows ?: 3}" class="${clazz}">${value}</g:textArea>
</g:if>
<g:else>
<input type="text" id="${property}" name="${property}" value="${value}" class="${clazz}"
 ${placeholder ? "placeholder=\"${placeholder}\"" : ''}
/>
</g:else>