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
	            <li><a href="cauhinh" class="active">Cấu hình người dùng</a></li>
	            <li><a href="cauhinhhethong">Cấu hình hệ thống</a></li>
	            <li><a href="cauhinhmail">Cấu hình mail</a></li>
		</ul>

		<div id="user-grid">
			 <table id="tableUser">
            <tr>
                <th>Tên người dùng</th>
                <th>Tài khoản</th>
                <th>Email</th>
                <th>Quyền</th>
                <th>Sửa</th>
            </tr>
           <c:forEach var="User" items="${listUser}" >
            <tr>
					<div style="width: 100%;">						
								<td>
									<div>${User.fullName}</div>
								</td>
								<td>
									<div>${User.userName}</div>
								</td>
								<td>
									<div>${User.email}</div>
								</td>
								<td>
									<div>${User.authorization}</div>
								</td>
								<td style="text-align: center; width: 30px;" >
										<a href="${pageContext.request.contextPath}/cauhinh?topic=${User.ID}" style="text-decoration: none;">
										<img src="images/pen.png" /></a>
								</td>
            </tr>
            </c:forEach>  
            </table>
 			</div>
 			
 			
 			<form:form method="post" action="" commandName="listUser2" style="text-align: left;">
				<c:if test="${not empty listUser2.fullName}">
				<div id="user-detail">
 					<h3 style="background: none repeat scroll 0 0 #143066;">Phân quyền người dùng</h3>
			    	<label style="width: 160px; display: inline; font-size: 10pt; margin-right: 10px; padding-left: 10px;">Tên người dùng:</label>
			    	<input style="width: 220px; height: 30px; margin-left: 5px; margin-top: 5px; " type="text" value="${fullName}"  disabled></input>
					</br>
					<label style="width: 160px; display: inline; font-size: 10pt; margin-right: 48px; padding-left: 10px;">Tài khoản:</label>
					<input style="width: 220px; height: 30px; display: inline; margin-left: 5px; margin-top: 5px;" type="text"  value="${userName}" disabled ></input>
					</br>
					<label style="width: 160px; display: inline; font-size: 10pt; margin-right: 73px; padding-left: 10px;">Email:</label>
					<input style="width: 220px; height: 30px; display: inline; margin-left: 5px; margin-top: 5px;" type="text" value="${email}" disabled></input>
					</br>
					<label style="width: 160px; display: inline; font-size: 10pt; margin-right: 70px; padding-left: 10px;">Quyền:</label>
					<input style="width: 220px; height: 30px; display: inline; margin-left: 5px; margin-top: 5px;" type="text" name="authorization" value="${authorization}" placeholder="${authorization}" onkeydown="return isNumber(event);" ></input>
					</br>
					
					</br>
					<button class="buttoncontrol" id="change" type="submit"  name="actionsubmit" value="change" style="height: 35px; float: none; margin: -10px 0 12px 42%; ">Thay đổi</button>
					</br>
					<b style="font-size: 10pt; margin-left: 10px; margin-bottom: 5px;">${message}</b>
				</div>
				</c:if>		
			</form:form>
 			
 		
    </tiles:putAttribute>
</tiles:insertDefinition>
</body>
</html>
