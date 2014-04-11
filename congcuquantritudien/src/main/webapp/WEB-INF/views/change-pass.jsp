<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<title>Thay đổi mật khẩu</title>
	<link href="css/stylesheet1.css" rel="stylesheet" />
	<script src="ckeditor/ckeditor.js"></script>
<style>
	.error {
color: #ff0000;
font-style: italic;
}
</style>
</head>
<body>
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
        <div class="body">
			<!-- Change password -->
			<div class="title_change_password" style="height: 36px; background-color: #2c406e; float: left; width: 100%; font-size: 13pt; color: #e4e4e4; padding-left: 25px; padding-top: 7px;">
				Đổi mật khẩu
			</div>
		
			<div class="change_password_content" style="height: 86%; float: left; width: 100%; margin-top: 3px;">
				<div style="background-color:white; border: 1px solid #d5d5d5; height: 102%; width: 100%">
				<form:form method="POST" commandName="users">
					<div style="height: 71px;">
						<label style="float: left; padding: 13px 0 0 25px; font-size: 11pt;">Mật khẩu hiện tại:</label>
						<div style="float: right; width: 85%; margin: 12px 0; display: block; position: relative;">
							<form:input path="Password" style="width: 300px; border-radius: 5px; height: 25px; padding-left: 5px;  color: #555555; border: 1px solid #cccccc; line-heigt: 1.428571429;"  type="password"/>
							 <form:errors path="Password" cssClass="error" />
						</div>
					</div>
					<div style="height: 71px;">
						<label style="float: left; padding: 13px 0 0 25px; font-size: 11pt;">Mật khẩu mới:</label>
						<div style="float: right; width: 85%; margin: 12px 0; display: block; position: relative;">
							<form:input path="NewPassword" style="width: 300px; border-radius: 5px; height: 25px; padding-left: 5px; color: #555555; border: 1px solid #cccccc; line-heigt: 1.428571429;"  type="password"/>
							 <form:errors path="NewPassword" cssClass="error" />
						</div>
					</div>
					<div style="height: 71px;">
						<label style="float: left; padding: 13px 0 0 25px; font-size: 11pt;">Nhập lại mật khẩu:</label>
						<div style="float: right; width: 85%; margin: 12px 0; display: block; position: relative;">
							<form:input path="ConfirmPassword" style="width: 300px; border-radius: 5px; height: 25px; padding-left: 5px; color: #555555; border: 1px solid #cccccc; line-heigt: 1.428571429;" type="password"/>
							 <form:errors path="ConfirmPassword" cssClass="error" />
						</div>
					</div>	
					<button class="buttoncontrol" id="save" type="submit"  name="actionsubmit" value="save" style="height: 35px; float: none; margin-left: 150px; ">LƯU</button>
				</form:form>
				${message}
				</div>
				
			</div>
        </div>
 
    </tiles:putAttribute>
</tiles:insertDefinition>
</body>
</html>




		