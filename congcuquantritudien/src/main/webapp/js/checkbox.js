$(document).ready(function(){
	$('#select_all').click(function() {
	   $(".check input").each( function() {
		   $(this).attr("checked",status);
		});	   
	});
	$('#delete_all').click(function() {
	   if (confirm("Are you sure you want to delete?")){ 
			 var url = window.location.pathname.split( '/' )[2];
	        var notChecked = [], checked = [];
	        $(":checkbox").each(function() {
	            if(this.checked){
	                checked.push(this.id);
	            } else {
	                notChecked.push(this.id);
	            }
	        });
	        
	        var form = $('<form action="'+url+'" method="post">' +
	        		'<input type="hidden" name="checkboxdata" value="'+checked+'" />' +
	        		'<input type="hidden" name="actionsubmit" value="deleteall" />' +
	        		'</form>');
	        		$('body').append(form);
	        		$(form).submit();   
	    }
	});
});