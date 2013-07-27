<h3>
   <g:message code="nimble.view.user.show.extendedinformation.heading" />
</h3>
<dl>
   <dt>
      <g:message code="nimble.label.fullname" />
   </dt>
   <dd>${user.profile?.fullName?.encodeAsHTML()}</dd>
   <dt>
      <g:message code="nimble.label.email" />
   </dt>
   <dd>${user.profile?.email?.encodeAsHTML()}</dd>
</dl>