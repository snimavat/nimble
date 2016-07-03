<%@page defaultCodec="none" %>
 <div class="form-group ${invalid ? 'error' : ''}">
    <label class="control-label col-sm-2" for="${property}">${label} ${required ? '*' : '' }</label>
     <div class="col-sm-8">
        ${widget}
        <g:if test="${help}">        
            <p class="help-block">${help}</p>
        </g:if>
    </div>
 </div>