<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link href="./images/icon-browser.ico" rel="shortcut icon" type="image/x-icon" />  
<title>Đăng nhập</title>
<style>
	.error {
	color: #ff0000;
	font-style: italic;
	}
	.success {
	color: #0000ff;
	font-style: italic;
	}
</style>
	<link href="css/stylesheet.css" rel="stylesheet" />	
</head>
<body>
<!-- 
<div id="login-box">
	<div id=logo></div>
	<div class="header"><h2>Quản trị bộ từ điển</h2></div>
   	<form:form  method="post" commandName="users" cssClass="dangnhap">
      <form:input path="UserName" type="text"  placeholder="Tên đăng nhập"/>
      <form:errors path="UserName" cssClass="error" />
      <form:input path="Password" type="password" placeholder="Mật khẩu"/>
      <form:errors path="Password" cssClass="error" />
      <p class="error">${error}</p>
      <hr>
      <a href="./registration" id="register">Bạn chưa có tài khoản?</a>
      <input type="submit" value="Đăng nhập" class="button" />
    </form:form>
</div>
 -->
 
 <table style="width: 100%; height: 640px; margin: 0; padding: 0;">
 
	 <tr>
	 	<td id="login-header">
	 		<img src="images/logoDL.png" style="margin: 0; padding: 0; margin-right: 4px; float: left;" />
	 		<a href="./registration" id="register">Bạn chưa có tài khoản?</a>
	 	</td>
	 </tr>
 	 <tr>
	 	<td id="login-content" ">
	 		<div id="login-box">

	<div class="header"><h2>Quản trị bộ từ điển</h2></div>
   	<form:form  method="post" commandName="users" cssClass="dangnhap">
      <form:input path="UserName" type="text"  placeholder="Tên đăng nhập"/>
      <form:errors path="UserName" cssClass="error" />
      <form:input path="Password" type="password" placeholder="Mật khẩu"/>
      <form:errors path="Password" cssClass="error" />
      <p class="error">${error}</p>
      <hr>
      <a href="./registration" id="register">Bạn chưa có tài khoản?</a>
      <input type="submit" value="ĐĂNG NHẬP" class="button" />
    </form:form>
</div>
	 	</td>
	 </tr>
	 	 <tr>
	 	<td id="login-footer">
	 
	 		<p id="copyright">Copyright © 2014 by DeadlineTeam</p>
	 	</td>
	 </tr>
 </table>
 
 
</body>
</html>