<%@page defaultCodec="none" %>
<g:javascript src="jquery/jquery.pstrength.js" contextPath="${pluginContextPath}"/>
<asset:script>

  $(function() {
    $("#passwordpolicy").hide();
    
    //$("#passwordpolicybtn").bt({killTitle:false, contentSelector: $("#passwordpolicy"), width: '350px', closeWhenOthersOpen: true, shrinkToFit: 'true', positions: ['right', 'top', 'left'], margin: 0, padding: 6, fill: '#fff', strokeWidth: 1, strokeStyle: '#c2c2c2', spikeGirth: 12, spikeLength:9, hoverIntentOpts: {interval: 100, timeout: 1000}});
    
    $('#passwordpolicybtn').popover({animation:true, placement:'bottom', title:'A title', content:$('#passwordpolicy').html()})
    $('.password').pstrength();
    $('.password').keyup();
  });
</asset:script>

<div id="passwordpolicy">
    <div>
      <g:message code="nimble.template.passwordpolicy" />
    </div>
</div>