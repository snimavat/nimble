<!doctype html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
  <title><g:layoutTitle default="Grails"/></title>

  <g:layoutHead/>

  <g:if test="${grailsApplication.config.nimble.layout.customcss != ''}">
    <link rel="stylesheet" href="${grailsApplication.config.nimble.layout.customcss}"/>
  </g:if>
  
  <asset:stylesheet src="nimble-login.css"/>
</head>
<body>

<div id="doc">
  <div id="hd">
	<g:render template='/templates/nimble/nimbleheader' model="['navigation':true]"/>
  </div>

  <div id="bd" class="clear">
    <g:layoutBody/>
  </div>

  <div id="ft"> </div>
</div>


<g:render template="/templates/sessionterminated" contextPath="${pluginContextPath}"/>
<asset:javascript src="jquery"/>
</body>
</html>
