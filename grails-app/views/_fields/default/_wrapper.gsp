<%@page defaultCodec="none" %>
 <div class="control-group ${invalid ? 'error' : ''}">
    <label class="control-label" for="${property}">${label} ${required ? '*' : '' }</label>
    <div class="controls">
        ${widget}
        <g:if test="${help}">        
            <p class="help-block">${help}</p>
        </g:if>
    </div>
 </div>