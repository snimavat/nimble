<!doctype html>
<html>
<head>
    <title>
        <g:message code="nimble.view.account.forgottenpassword.initiate.title"/>
    </title>
    <asset:stylesheet src="bootstrap.css"/>
    <asset:stylesheet src="nimble-login.css"/>
</head>

<body>
<div class="container" style="margin-top:40px;">

<div class="col-md-4 col-md-offset-4">
    <h2><g:message code="nimble.view.account.forgottenpassword.initiate.heading"/></h2>
    <p><g:message code="nimble.view.account.forgottenpassword.initiate.descriptive"/></p>
    <n:flashembed/>
    <g:form action="forgottenpasswordprocess" method="POST">

        <div class="form-group">
            <input class="form-control" type="text" size="30" name="email" id="email" placeholder="user@example.com">
        </div>

        <div class="form-group">
            <button type="submit" class="btn btn-primary">
                <g:message code="nimble.link.resetpassword"/>
            </button>
        </div>
    </g:form>
</div>

</div>
</body>
</html>