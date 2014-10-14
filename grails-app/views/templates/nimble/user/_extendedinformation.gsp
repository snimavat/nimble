<table class="table table-bordered" style="width: 50%;margin-top: 20px;" cellpadding="5px;" cellspacing="5px">
    <tr>
        <td class="bg-primary"><g:message code="nimble.label.fullname"/></td>
        <td>${user.profile.fullName?.encodeAsHTML() ?: message(code: 'nimble.label.none')}</td>
    </tr>
    <tr>
        <td class="bg-primary"><g:message code="nimble.label.email"/></td>
        <td>${user.profile.email?.encodeAsHTML() ?: g.message(code: 'nimble.label.none')}</td>
    </tr>
</table>
