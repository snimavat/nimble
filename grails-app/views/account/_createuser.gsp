<!doctype html>
<html>
<head>
    <title>
        <g:message code="nimble.view.account.registeraccount.initiate.title"/>
    </title>
    <r:require modules="nimble-login"/>
    <r:layoutResources/>
</head>

<body>
<div class="container" style="margin-top:40px;">

    <div class="col-md-4 col-md-offset-4">
        <h2>
            <g:message code="nimble.view.account.registeraccount.initiate.heading"/>
        </h2>
        <n:flashembed/>
        <n:errors bean="${user}"/>
        <g:form action="saveuser" name="signup-form" method="post">

            <div class="form-group">
                <input class="form-control" type="text" id="username" name="username"
                       value="${fieldValue(bean: user, field: 'username')}"
                       placeholder="${message(code: 'nimble.label.username')}"/>
            </div>

            <div class="form-group">
                <input class="form-control" type="password" size="30" id="pass" name="pass"
                       value="${user.pass?.encodeAsHTML()}" placeholder="${message(code: 'nimble.label.password')}"/>
            </div>

            <div class="form-group">
                <input class="form-control" type="password" size="30" id="passConfirm" name="passConfirm"
                       value="${user.passConfirm?.encodeAsHTML()}"
                       placeholder="${message(code: 'nimble.label.password.confirmation')}"/>
            </div>

            <div class="form-group">
                <input class="form-control" type="text" size="30" id="fullName" name="fullName"
                       value="${user.profile?.fullName?.encodeAsHTML()}"
                       placeholder="${message(code: 'nimble.label.fullname')}"/>
            </div>

            <div class="form-group">
                <input class="form-control" type="text" size="30" id="email" name="email"
                       value="${user.profile?.email?.encodeAsHTML()}"
                       placeholder="${message(code: 'nimble.label.email')}"/>
            </div>


            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-block">
                    <g:message code="nimble.link.registeraccount"/>
                </button>
            </div>

        </g:form>

    </div>

</div>
<r:layoutResources/>
</body>
</html>