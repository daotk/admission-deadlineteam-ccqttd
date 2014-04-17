<?xml version="1.0" encoding="utf-8" ?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<html>
<head>
	<title>Cấu hình mail</title>
	<link href="./images/icon-browser.ico" rel="shortcut icon" type="image/x-icon" />  
	<link href="css/stylesheet1.css" rel="stylesheet" />
	<link rel="stylesheet" href="css/bootstrap.css">
	
	<script src="js/jquery.min.js"></script>
	<script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="js/bootstrap.min.js" type="text/javascript"></script>
	<script src="js/tab-nav.js"></script>
	<script src='js/popbox.js' type='text/javascript'></script>

	<!--For Loading -->
	<script>
	$(window).bind("load", function() {
		$('#loading').fadeOut(2000);
	});
	</script>
	<!-- LIMIT TExt filed to accept only numbers as input -->
</head>
<body>
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
        <div id="loading"> </div>
        <div class="body">
	      	<ul class="tabbed" data-persist="true" style="width: 100%;">
		            <li><a href="cauhinh" >Cấu hình người dùng</a></li>
		            <li><a href="cauhinhhethong" >Cấu hình hệ thống</a></li>
		            <li><a href="cauhinhmail" class="active">Cấu hình mail</a></li>
			</ul>

 			<form method="post" action=""  style="text-align: left;">
 				<table style="margin-left: 2%; margin-top: 2%;">
 					<tr>
 						<td><label style="width: 160px; font-size: 10pt;">Tên đăng nhập:</label></td>
 						<td><input style="width: 320px; height: 30px; margin-top: 4%;" type="text" name="usernamemail" value="${usernamemail}"  ></input></td>
 					</tr>
 					<tr>
 						<td><label style="width: 160px; font-size: 10pt;">Mật khẩu:</label></td>
 						<td><input style="width: 320px; height: 30px; margin-top: 4%;" type=text  name="passwordmail" value="${passwordmail}"  ></input></td>
 					</tr>
 					<tr>
 						<td><label style="width: 160px; font-size: 10pt;">Host:</label></td>
 						<td><input style="width: 320px; height: 30px; margin-top: 4%;" type="text" name="host" value="${host}" ></input></td>
 					</tr>
 					<tr>
 						<td><label style="width: 160px; font-size: 10pt;">Port:</label></td>
 						<td><input style="width: 320px; height: 30px; margin-top: 4%;" type="text" name="port" value="${port}" ></input></td>
 					</tr>
 					<tr>
 						<td></td>
 						<td><b style="font-size: 10pt;float: left;">${message}</b></td>
 					</tr>
 					<tr>
 						<td></td>
 						<td><button class="buttoncontrol" id="change" type="submit"  name="actionsubmit" value="change" style="height: 35px; float: right; margin-right: 0;">Thay đổi</button></td>
 					</tr>
				</table>
			</form>
 		</div>
    </tiles:putAttribute>
</tiles:insertDefinition>
</body>
</html>
