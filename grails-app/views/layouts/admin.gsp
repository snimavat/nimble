<!doctype html>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="grails.plugins.nimble.core.UserBase"%>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <title>
         <g:layoutTitle default="Grails"/>
      </title>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
      <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
      <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
      <g:layoutHead/>
      <r:require modules="nimble-admin"/>
      <r:layoutResources />
   </head>
   <body>
      <div class="navbar">
         <div class="navbar-inner">
            <div class="container-fluid">
               <a class="brand" href="#">Nimble</a>
               <!-- 
                  <ul class="nav ace-nav pull-right">
                    <li class="grey">
                        <a class="dropdown-toggle" href="#" data-toggle="dropdown">
                                        <span class="badge badge-grey">4</span>
                        </a>                    
                    </li>
                  </ul>
                   -->
            </div>
         </div>
      </div>
      <!-- Navbar -->
      <div class="main-container" id="main-container">
         <!-- Main content start -->
         <div id="sidebar" class="sidebar">
            <g:render template="/templates/nimble/navigation/sidenavigation" />
         </div>
         <!-- Side bar ends -->    
         <div id="main-content" class="main-content">
            <div id="breadcrumbs" class="breadcrumbs">  </div>
            <div class="container-fluid">
               <div class="row-fluid">
                  <div id="right" class="span12">                     
                     <g:layoutBody />
                  </div>
               </div>
            </div>
         </div>
      </div>
      <!-- Main container ends -->      
      <n:sessionterminated />
      <g:javascript library="application"/>
      <r:layoutResources />
   </body>
</html>