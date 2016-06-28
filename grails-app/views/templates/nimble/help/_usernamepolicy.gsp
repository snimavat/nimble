<asset:script>

  $(function() {
    $("#usernamepolicy").hide();
    $('#usernamepolicybtn').popover({animation:true, placement:'bottom', title:'A title', content:$('#usernamepolicy').html()})

  });
</asset:script>

<div id="usernamepolicy">  
    <div>
      <g:message code="nimble.template.usernamepolicy" />
    </div>
</div>