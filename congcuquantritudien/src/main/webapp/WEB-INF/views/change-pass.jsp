<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<title>Thay đổi mật khẩu</title>
	<link href="./images/icon-browser.ico" rel="shortcut icon" type="image/x-icon" />  
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
			<div class="title_change_password" style="height: 36px; background-color: #2c406e; float: left; width: 100%; font-size: 13pt; color: #e4e4e4; padding-left: 25px;padding-top: 15px; margin-top: 1px; margin-left: 1px;">
				Đổi mật khẩu
			</div>
		
			<div class="change_password_content" style="height: 86%; float: left; width: 100%; margin-top: 3px;">
				<div style="background-color: #f5f3f3; border: 1px solid #d5d5d5; height: 102%; width: 100%">
				<form:form method="POST" commandName="users">
					<table style="margin-left: 1%; margin-top: 1%;">
						<tr>
							<td><label style="float: left; font-size: 10pt;">Mật khẩu hiện tại:</label></td>
							<td>
								<div style="float: left;">
									<form:input path="Password" style="width: 300px; border-radius: 5px; height: 25px; padding-left: 5px;  color: #555555; border: 1px solid #cccccc; line-heigt: 1.428571429;"  type="password"/>
								 </div>
							</td>
							<td>
								<div style="float: left;">
									<form:errors path="Password" cssClass="error" />
								</div>							
							</td>
						</tr>
						<tr>
							<td><label style="float: left; font-size: 10pt;">Mật khẩu mới:</label></td>
							<td>
								<div style="float: left;">
									<form:input path="NewPassword" style="width: 300px; border-radius: 5px; height: 25px; padding-left: 5px; color: #555555; border: 1px solid #cccccc; line-heigt: 1.428571429;"  type="password"/>
								</div>
							</td>
							<td>
								<div style="float: left;">
							 		<form:errors path="NewPassword" cssClass="error" />
								</div>
							</td>
						</tr>
						<tr>
							<td><label style="float: left; font-size: 10pt;">Nhập lại mật khẩu:</label></td>
							<td>
								<div style="float: left;">
									<form:input path="ConfirmPassword" style="width: 300px; border-radius: 5px; height: 25px; padding-left: 5px; color: #555555; border: 1px solid #cccccc; line-heigt: 1.428571429;"  type="password"/>
								</div>
							</td>
							<td>
								<div style="float:left;">
									<form:errors path="ConfirmPassword" cssClass="error" />
								</div>
							</td>
						</tr>
						<tr>
							<td></td>
							<td style="font: bold 10pt 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif; float: left;">${message}</td>
							<td style="font: bold 10pt 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif; float: left;" class ="error">${error}</td>
						</tr>
						<tr>
							<td></td>
							<td><button class="buttoncontrol" id="save" type="submit"  name="actionsubmit" value="change" style="height: 35px; float: right; margin-right: 0;" onclick="$('#loading').show();">Lưu</button></td>
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




		