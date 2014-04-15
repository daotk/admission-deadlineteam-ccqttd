<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<title>Thông tin cá nhân</title>
	<link href="css/stylesheet1.css" rel="stylesheet" />
	<link href="./images/icon-browser.ico" rel="shortcut icon" type="image/x-icon" />  
	<script src="ckeditor/ckeditor.js"></script>
</head>
<body>
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
       <div class="body">
					
			<!-- Change password -->
			<div class="title_change_password" style="height: 36px; background-color: #2c406e; float: left; width: 100%; font-size: 13pt; color: #e4e4e4; padding-left: 10px; padding-top: 14px; margin-top: 1px; margin-left: 1px;">
				Thông tin tài khoản người dùng</div>
				
			<div class="change_password_content" style="height: 86%; float: left; width: 100%; margin-top: 3px;">
				<div style="background-color: #f5f3f3; border: 1px solid #d5d5d5; height: 102%; width: 100%">
				<form:form commandName="users">
					<table style="margin-left: 1%; margin-top: 1%;">
						<tr>
							<td><label style="float: left; width: 130px; font-size: 10pt;">Họ tên</label></td>
							<td>							
								<label style="float: left; width: 95%; font-size: 10pt;">${users.fullName}</label>
							</td>
						</tr>	
						<tr>
							<td><label style="float: left;width: 130px; font-size: 10pt;">Email</label></td>
							<td>
								<label style="float: left; width: 95%;font-size: 10pt;">${users.email}</label>
							</td>
						</tr>
						<tr>
							<td><label style="float: left; width: 130px; font-size: 10pt;">Tên đăng nhập</label></td>
							<td>
								<label style="float: left; width: 95%;font-size: 10pt;">${users.userName}</label>
							</td>
						</tr>
					</table>
					</form:form>
				</div>
			</div>
		</div>
    </tiles:putAttribute>
</tiles:insertDefinition>
</body>
</html>