<%@page defaultCodec="none" %>
<g:javascript src="jquery/jquery.pstrength.js" contextPath="${pluginContextPath}"/>
<r:script>

  $(function() {
    $("#passwordpolicy").hide();
    $('#passwordpolicybtn').popover({animation:true,html:true, placement:'bottom', title:'A title', content:$('#passwordpolicy').html()})
    $('.password').pstrength();
    $('.password').keyup();
  });
</r:script>

<div id="passwordpolicy">
    <div>
      <g:message code="nimble.template.passwordpolicy" />
    </div>
</div>