<!doctype html>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="grails.plugins.nimble.core.UserBase"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Grails"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
		
		<g:layoutHead/>
		
        <style type="text/css">
          body {
            padding-top: 60px;            
          }
        </style>
                       		
		<r:require modules="nimble-admin"/>
        <r:layoutResources />
	</head>
	
	<body>	
        <!-- Top navigation -->
        <div class="navbar navbar-fixed-top">
          <div class="navbar-inner">
            <div class="container-fluid">
              <a class="brand" href="#">Nimble</a>              
                <p class="navbar-text pull-right">
                   <g:message code="nimble.lable.loggedinas"/> <n:principalLink><n:principal/></n:principalLink>&nbsp;|&nbsp;
                    <g:link controller="auth" action="logout"><g:message code="nimble.link.logout.basic"/></g:link>                    
                </p>                
            </div>
          </div>
        </div> <!-- Navigation end -->
        <div class="container-fluid">
           <div class="row-fluid">
               <!-- left -->
               <div class="span3">
                   <div id="left" class="well left">
                        <g:render template="/templates/nimble/navigation/sidenavigation" />
                   </div>
               </div><!-- left end -->
               
               <div class="span9">
                 <div id="main-content">
                     <g:layoutBody/>
                  </div><!-- main content -->
               </div> <!-- Right -->
           </div> <!-- Row -->
        </div>  <!-- container -->
        <n:sessionterminated />  			
		<g:javascript library="application"/>
        <r:layoutResources />
	</body>
</html>