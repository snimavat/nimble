<!doctype html>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ page import="grails.plugin.nimble.core.UserBase" %>
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
    <asset:javascript src="nimble.js"/>
    <asset:stylesheet src="nimble-admin.css"/>
    <g:layoutHead/>

</head>

<body>
<g:set var="currentUser" value="${UserBase.get(SecurityUtils.subject.principal)}"/>

<nav class="navbar navbar-default" role="navigation">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <g:link controller="admins"
                    class="navbar-brand">${grailsApplication.config.nimble.admin.header.title}</g:link>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">

                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${currentUser.username}
                        <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <g:link controller="user" action="show" id="${currentUser.id}">
                                <span class="glyphicon glyphicon-home"></span>
                                <g:message code="nimble.link.myaccount"/>
                            </g:link>
                        </li>
                        <li>
                            <g:link controller="auth" action="signout">
                                <span class="glyphicon glyphicon-log-out"></span>
                                <g:message code="nimble.link.logout.basic"/>
                            </g:link>
                        </li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<!-- Navbar -->

<!-- Main content start -->
<div class="container" id="main-container">

    <div id="sidebar" class="col-md-2">
        <g:render template="/templates/nimble/navigation/sidenavigation"/>
    </div>
    <!-- Side bar ends -->

    <div id="main-content" class="col-md-10">
        %{--<div id="breadcrumbs" class="breadcrumb"></div>--}%
        <g:layoutBody/>
    </div>

</div>
<!-- Main container ends -->
<%--  <n:sessionterminated /> --%>

</body>
</html>