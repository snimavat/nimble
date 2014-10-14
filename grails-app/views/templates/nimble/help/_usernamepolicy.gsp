<script type="text/javascript">

  $(function() {
    $("#usernamepolicy").hide();
    $('#usernamepolicybtn').popover({animation:true, placement:'bottom', html:true, title:'A title', content:$('#usernamepolicy').html()})

  });
</script>

<div id="usernamepolicy">  
    <div>
      <g:message code="nimble.template.usernamepolicy" />
    </div>
</div>