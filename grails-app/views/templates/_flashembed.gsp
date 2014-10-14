<g:if test="${flash.message != null && flash.message.length() > 0}">
    <g:if test="${flash.type != null && flash.type.length() > 0}">
        <div class="alert alert-${flash.type.equals('error') ? 'danger' : flash.type}">

            <g:if test="${flash.type.equals('success')}">
                <span class="glyphicon glyphicon-ok"></span>
            </g:if>

            <g:if test="${flash.type.equals('error')}">
                <span class="glyphicon glyphicon-remove"></span>
            </g:if>

            <g:if test="${flash.type.equals('info')}">
                <span class="glyphicon glyphicon-info-sign"></span>
            </g:if>

            <g:if test="${flash.type.equals('help')}">
                <span class="glyphicon glyphicon-leaf"></span>
            </g:if>

            <g:if test="${flash.type.equals('flagred')}">
                <span class="glyphicon glyphicon-remove-sign"></span>
            </g:if>

            <g:if test="${flash.type.equals('flaggreen')}">
                <span class="glyphicon glyphicon-ok-sign"></span>
            </g:if>

            <g:if test="${flash.type.equals('flagblue')}">
                <span class="glyphicon glyphicon-leaf"></span>
            </g:if>

            ${flash.message}
        </div>
    </g:if>
    <g:else>
        <div class="flash-${flash.type}">
            ${flash.message}
        </div>
    </g:else>
</g:if>