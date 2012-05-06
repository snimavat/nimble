<h3><g:message code="nimble.view.user.show.extendedinformation.heading" /></h3>

<div class="row-fluid">
    <div class="span6">
        <table class="table no-border">
            <tbody>
                <tr>
                    <th><g:message code="nimble.label.fullname" /></th>
                    <td>${user.profile?.fullName?.encodeAsHTML()}</td>
                </tr>
                <tr>
                    <th><g:message code="nimble.label.email" /></th>
                    <td>${user.profile?.email?.encodeAsHTML()}</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>