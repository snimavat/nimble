<!doctype html>
<html>
<head>
    <title>
        <g:message code="nimble.template.login.title"/>
    </title>
    <r:require modules="nimble-login"/>
    <r:layoutResources/>
</head>

<body>

<div class="container" style="margin-top:40px;">

    <div class="col-md-4 col-md-offset-4">

        <!-- Sign in -->
        <div class="col-md-12">

            <h2><g:message code="nimble.label.login.signin"/></h2>
            <n:flashembed/>
            <g:form action="signin" name="login-form" method="post">

                <div class="form-group">
                    <input type="hidden" name="targetUri" value="${targetUri}"/>
                    <input class="form-control" type="text" name="username" id="username"
                           placeholder="${message(code: 'nimble.label.username')}">
                </div>

                <div class="form-group">
                    <input class="form-control" type="password" name="password" id="password"
                           placeholder="${message(code: 'nimble.label.password')}">
                </div>

                <div class="form-group">
                    <label class="checkbox">
                        <input type="checkbox" name="rememberme">
                        <g:message code="nimble.label.rememberme"/>
                    </label>
                </div>

                <div class="form-group">
                    <button type="submit" class="btn-block btn btn-primary">
                        <g:message code="nimble.link.login.basic"/>
                    </button>
                </div>

                <div class="form-group">

                    <div class="row center-block">
                        <div class="col-md-6">
                            <g:link controller="account" action="createuser">
                                <b><g:message code="nimble.link.login.signup"/></b>
                            </g:link>
                        </div>

                        <div class="col-md-6">
                            <g:link controller="account" action="forgottenpassword">
                                <b><g:message code="nimble.link.resetpassword"/></b>
                            </g:link>
                        </div>
                    </div>

                </div>

            </g:form>
        </div>

    </div>

</div>



<r:layoutResources/>
</body>
</html>