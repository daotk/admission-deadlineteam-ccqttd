$(document).ready(function(){
	    $('#select_all').click(function(event) {  //on click 
	        if(this.checked) { // check select status
	            $('.check input').each(function() { //loop through each checkbox
	                this.checked = true;  //select all checkboxes with class "checkbox1"               
	            });
	        }else{
	            $('.check input').each(function() { //loop through each checkbox
	                this.checked = false; //deselect all checkboxes with class "checkbox1"                       
	            });         
	        }
	    });
	    
	$('#delete_all').click(function() {
		 var notChecked = [], checked = [];
	        $(":checkbox").each(function() {
	            if(this.checked){
	            	if(this.id != 'select_all'){
	            		checked.push(this.id);
	            	}
	            } else {
	                notChecked.push(this.id);
	            }
	        });
	        if(checked!=""){
	        	 var result =confirm("bạn có muốn xóa câu hỏi?");
	        	 if(result == true){
	   					 var url = window.location.pathname.split( '/' )[2]; 
						 if(url==""){url="home";}
				        var form = $('<form action="'+url+'" method="post">' +
				        		'<input type="hidden" name="checkboxdata" value="'+checked+'" />' +
				        		'<input type="hidden" name="actionsubmit" value="deleteall" />' +
				        		'</form>');
				        		$('body').append(form);
				        		$(form).submit();   
	        		
	        	}
	        }else {
	        	alert("Bạn chưa chọn câu hỏi để xóa!");
			}
	});
	$('#up_all').click(function() {
		 var notChecked = [], checked = [];
	        $(":checkbox").each(function() {
	            if(this.checked){
	            	if(this.id != 'select_all'){
	            		checked.push(this.id);
	            	}
	            } else {
	                notChecked.push(this.id);
	            }
	        });
	        if(checked!=""){
			   if (confirm("bạn có muốn đăng câu hỏi?")){ 
					 var url = window.location.pathname.split( '/' )[2]; 
					 if(url==""){url="home";}
			        var form = $('<form action="'+url+'" method="post">' +
			        		'<input type="hidden" name="checkboxdata" value="'+checked+'" />' +
			        		'<input type="hidden" name="actionsubmit" value="upall" />' +
			        		'</form>');
			        		$('body').append(form);
			        		$(form).submit();   
			    }
	        }else {
	        	alert("Bạn chưa chọn câu hỏi để đăng!");
			}
	});
	$('#down_all').click(function() {
		 var notChecked = [], checked = [];
	        $(":checkbox").each(function() {
	            if(this.checked){
	            	if(this.id != 'select_all'){
	            		checked.push(this.id);
	            	}
	            } else {
	                notChecked.push(this.id);
	            }
	        });
	        if(checked!=""){
			   if (confirm("bạn có muốn hạ câu hỏi?")){ 
					 var url = window.location.pathname.split( '/' )[2]; 
					 if(url==""){url="home";}
			        var form = $('<form action="'+url+'" method="post">' +
			        		'<input type="hidden" name="checkboxdata" value="'+checked+'" />' +
			        		'<input type="hidden" name="actionsubmit" value="removeall" />' +
			        		'</form>');
			        		$('body').append(form);
			        		$(form).submit();   
			    }
	        }else {
	        	alert("Bạn chưa chọn câu hỏi để hạ!");
			}
	});
	$('#restore_all').click(function() {
		 var notChecked = [], checked = [];
	        $(":checkbox").each(function() {
	            if(this.checked){
	            	if(this.id != 'select_all'){
	            		checked.push(this.id);
	            	}
	            } else {
	                notChecked.push(this.id);
	            }
	        });
	        if(checked!=""){
			   if (confirm("Bạn có muốn khôi phục?")){ 
					 var url = window.location.pathname.split( '/' )[2]; 
					 if(url==""){url="home";}
			        var form = $('<form action="'+url+'" method="post">' +
			        		'<input type="hidden" name="checkboxdata" value="'+checked+'" />' +
			        		'<input type="hidden" name="actionsubmit" value="restoreall" />' +
			        		'</form>');
			        		$('body').append(form);
			        		$(form).submit();   
			    }
	        }else {
	        	alert("Bạn chưa chọn câu hỏi để khôi phục!");
			}	
	});
});