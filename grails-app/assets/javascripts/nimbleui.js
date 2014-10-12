//Growl
function growl(type, msg) {
    growl(type, msg, 2000);
}

function growl(type, msg, period) {
    if (type == 'success')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_tick\'>&nbsp;</span>' });

    if (type == 'error')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_cross\'>&nbsp;</span>' });

    if (type == 'info')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_information\'>&nbsp;</span>' });

    if (type == 'help')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_cross\'>&nbsp;</span>' });

    if (type == 'flagred')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_flag_red\'>&nbsp;</span>' });

    if (type == 'flaggreen')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_flag_green\'>&nbsp;</span>' });

    if (type == 'flagblue')
      $.jGrowl(msg, { life: period, header: '<span class=\'icon icon_flag_blue\'>&nbsp;</span>' });
}

// General
function verifyUnique(elem, elemstatus, endpoint, success, failure) {
   var dataString = 'val=' + $(elem).val();
   $.ajax({
     	type: "POST",
		url: endpoint,
		data: dataString,
		success: function(res) {
		  growl('flaggreen', res, 3000);
		  $(elem).css({'background': '#fff', 'color':'#000'});
		  $(elemstatus).addClass('icon');
		  $(elemstatus).addClass('icon_flag_green');
		  $(elemstatus).removeClass('icon_flag_red');
		},
		error: function (xhr, ajaxOptions, thrownError) {
		  growl('flagred', xhr.responseText);
		  $(elem).css({'color': '#9c3333'});
		  $(elemstatus).addClass('icon');
		  $(elemstatus).addClass('icon_flag_red');
		  $(elemstatus).removeClass('icon_flag_green');
		}
	});
}


function wasConfirmed(title, msg, accept, cancel) {
	
	$("#confirmationtitle").html(title);
	$("#confirmationcontent").html(msg); 
	$("#confirmaccept").html(accept);
	$("#confirmcancel").html(cancel);
	
	$("#confirmationdialog").modal('show');		
}

// Login
$(function() {
	
	/*@TODO - upgrade to use bootstrap dialog
	$("#accountcreationpolicybtn").click(function() {
		$("#accountcreationpolicydialog").dialog('open');
	});
	
	$("#accountcreationpolicycancelbtn").click(function() {
		$("#accountcreationpolicydialog").dialog('close');
	});
	*/
});

// Session Termination
$(function() {
	$(document).ajaxError(function (event, xhr, ajaxOptions, thrownError) {	
	  if ((xhr.status == 403) && (xhr.getResponseHeader("X-Nim-Session-Invalid") != null)) {
	    $("#sessionterminateddialog").modal('show');
	  }
	});
});