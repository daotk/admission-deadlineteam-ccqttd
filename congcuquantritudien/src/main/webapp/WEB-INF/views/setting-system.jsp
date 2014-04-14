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
	<title>Home</title>
	
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
	            <li><a href="cauhinhhethong" class="active">Cấu hình hệ thống</a></li>
	            <li><a href="cauhinhmail">Cấu hình mail</a></li>
		</ul>
		
 			<form method="post" action=""  style="text-align: left;">
			    	<label style="width: 160px; display: inline; font-size: 10pt; margin-right: 10px; padding-left: 10px;">Driver:</label>
			    	<input style="width: 320px; height: 30px; margin-left: 51px; margin-top: 5px; " type="text" name="driver" value="${driver}"  ></input>
					</br>
					<label style="width: 160px; display: inline; font-size: 10pt; margin-right: 48px; padding-left: 10px;">Url:</label>
					<input style="width: 320px; height: 30px; display: inline; margin-left: 31px; margin-top: 5px;" type="text"  name="url" value="${url}"  ></input>
					</br>
					<label style="width: 160px; display: inline; font-size: 10pt; margin-right: 73px; padding-left: 10px;">Tên đăng nhập:</label>
					<input style="width: 320px; height: 30px; display: inline; margin-left: -68px; margin-top: 5px;" type="text" name="username" value="${username}" ></input>
					</br>
					<label style="width: 160px; display: inline; font-size: 10pt; margin-right: 73px; padding-left: 10px;">Mật khẩu:</label>
					<input style="width: 320px; height: 30px; display: inline; margin-left: -30px; margin-top: 5px;" type="text" name="password" value="${password}" ></input>
					</br>
					</br>
					<button class="buttoncontrol" id="change" type="submit"  name="actionsubmit" value="change" style="height: 35px; float: none; margin: -10px 0 12px 16%; ">Thay đổi</button>
					</br>
					<b style="font-size: 10pt; margin-left: 10px; margin-bottom: 5px;">${message}</b>
			</form>
 		
 		
    </tiles:putAttribute>
</tiles:insertDefinition>
</body>
</html>
