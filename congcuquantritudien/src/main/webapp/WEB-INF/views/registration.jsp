<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/login-script.js"></script>
	<script src="js/jquery.min.js"></script>
	
	<script type="text/javascript">
	$(document).ready(function() {
	  var n1 = Math.round(Math.random() * 1000000 + 1); 
	  $("#cau_hoi1").val(n1);
	  $(".ten_form1").submit(function (e) {
		  if (eval($("#cau_hoi").val()) != $("#tra_loi1").val()) {
		      $("#tra_loi1").css('box-shadow', '0px 0px 7px red');
		      alert('Ket qua ban nhap sai roi. Xin vui long nhap lai!');
		      e.preventDefault(); 
		    } 
		  });
	});
	</script>
</head>
<body>

  <table style="width: 100%; height: 640px; margin: 0; padding: 0;">
 
	 <tr>
	 	<td id="login-header">
	 		<img src="images/logoDL.png" style="margin: 0; padding: 0; margin-right: 4px; float: left;" />
	 		<a href="login" id="login">Đăng nhập</a>
	 	</td>
	 </tr>
 	 <tr>
	 	<td id="login-content" ">
			 <div id="register-box">

		    <div class="header"><h2 style="margin-bottom: 0;">Đăng kí tài khoản</h2></div>
		    <form:form  method="post" commandName="users" cssClass="cau_hoi1">
		        <form:input path="FullName" type="text"  placeholder="Họ tên"/>
		       	<form:errors path="FullName" cssClass="error" />
				<form:input path="UserName" type="text" placeholder="Tên đăng nhập"/>
				<form:errors path="UserName" cssClass="error" />
				<form:input path="Password" type="password" placeholder="Mật khẩu"/>
				<form:errors path="Password" cssClass="error" />
				<form:input path="ConfirmPassword"  type="password" placeholder="Xác nhận mật khẩu"/>
				<form:errors path="ConfirmPassword" cssClass="error" />
				<form:input path="Email" type="text" placeholder="Địa chỉ email"/>
				<form:errors path="Email" cssClass="error" />	
				<input type="capcha" name="capcha" placeholder="Nhập dãy số bên cạnh" required="" id="tra_loi1"/>
		      	<input type="cauhoi" id="cau_hoi1" onselect="return false" onmousedown="return false" />
		        <hr>
		        <p class="error">${error}<p>
		        <p class="success">${message}<p>
		      	<a href="home" id="login">Đăng nhập!</a>
		
		        <button class="button" type="submit"  name="actionsubmit" value="register">GỬI</button>
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