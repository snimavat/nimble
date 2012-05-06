 <div class="control-group ${invalid ? 'error' : ''}">
    <label class="control-label" for="${property}">${label} ${required ? '*' : '' }</label>
    <div class="controls">
        ${widget}
    </div>
 </div>