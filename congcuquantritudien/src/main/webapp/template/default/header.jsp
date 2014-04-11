<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<div class="header">         	
		<div style="width:300px; float: left;margin-left: 10px; margin-top: 5px; font-size: 12px;">CÔNG CỤ QUẢN TRỊ BỘ TỪ ĐIỂN</div>
		
		<div style="height:100%;float: right;padding-right: 20px;">
	    	<!-- Account -->
	    	<div class="viewprofilebutton">
	    		<img style="float: left; width: 22px; padding-top:2px;" src="template/default/images/userPic.png"/>
	    		<span style="float: left; margin-top: 4px;margin-left: 3px;">Xin chào</span>
	    		<a style="float: left; margin-top: 4px;margin-left: 3px; text-decoration: none;color: #fff;" href="profile">${fullname}</a>
	    	</div>
	    	
	    	<!-- Change password -->
	    	<div class="changepassbutton">
	    		<img style="float: left; width: 10px; height: 11px; margin-top: 6px;" src="template/default/images/settings.png"/>
	    		<a style="float: left; margin-top: 4px;margin-left: 3px; text-decoration: none;color: #fff;" href="changepass">Đổi mật khẩu</a>
	    	</div>
	    	
	    	<!-- Logout -->
	    	<div class="logoutbutton">
	    		<img style="float: left; width: 11px; height: 11px; margin-top: 6px;" src="template/default/images/logout.png"/>
	    		<a style="float: left; margin-top: 4px;margin-left: 3px; text-decoration: none;color: #fff;" href="logout">Logout</a>
	    	</div>
    	</div>	
</div>

