// search bar display default
$(document).ready(function(){
$('#quick-search').fadeIn(0);
});

// when check boxes are checked above twice then display control bar
$(document).ready(function(){
var countChecked = function() {
  var n = $( "input:checked" ).length;
  if ( n >= 2 ) {
	$('#quick-search').fadeOut(0);
	$('.list-parent-controls').delay(0).fadeIn(0);
  }else{
	$('.list-parent-controls').fadeOut(0);
	$('#quick-search').fadeIn(0);
  }
};
countChecked();
 
$( "input[type=checkbox]" ).on( "click", countChecked );
});


$(document).ready(function(){
	$('.list-question').click(function () {
		var href = $(this).attr('id');
	  if ($(this).find('input:checkbox[name='+href+']').is(":checked")) {

		$(this).find('input:checkbox[name='+href+']').attr("checked", false);
	  }
	  else {
		  $(this).find('input:checkbox[name='+href+']').prop("checked", true);
	  }
	});
	/*
	
	 $('input[type=checkbox]').click(function (e) {
		 e.stopPropagation();
	 });
	 */
});