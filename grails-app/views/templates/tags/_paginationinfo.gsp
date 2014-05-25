<g:if test="${total > 0 }">
<g:message code="nimble.tags.paginationinfo.showing" default="Showing"/> ${name.toLowerCase()} ${from + 1 }
<g:message code="nimble.tags.paginationinfo.to" default="to"/> ${to} <g:message code="nimble.tags.paginationinfo.total" default="of total"/> ${total}
</g:if>
<g:else>
<g:message code="nimble.tags.paginationinfo.showing" default="Showing"/> ${name.toLowerCase()} 0
<g:message code="nimble.tags.paginationinfo.total" default="of total"/> ${total}
</g:else>
