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
	<title>Cấu hình người dùng</title>
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
	    		<li><a href="cauhinh" class="active">Cấu hình người dùng</a></li>
	            <li><a href="cauhinhhethong">Cấu hình hệ thống</a></li>
	            <li><a href="cauhinhmail">Cấu hình mail</a></li>
			</ul>

			<div id="user-grid" style="overflow: auto;background-color: gray; height: 540px;">
				<table id="tableUser" style="margin-left: 1%;">
		            <tr>
		                <th>Tên người dùng</th>
		                <th>Tài khoản</th>
		                <th>Email</th>
		                <th>Quyền</th>
		                <th>Sửa</th>
		            </tr>
		           	<c:forEach var="User" items="${listUser}" >
		           		<tr style="width: 100%; font-size: 10pt;">					
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
							<td style="text-align: center; width: 30px;">
								<a href="${pageContext.request.contextPath}/cauhinh?topic=${User.ID}" style="text-decoration: none;">
									<img src="images/pen.png" />
								</a>
							</td>
		            	</tr>
		            </c:forEach>  
	            </table>
	 		</div>
	 		
 			<form:form method="post" action="" commandName="listUser2" style="text-align: left;">
				<c:if test="${not empty listUser2.fullName}">
					<div id="user-detail" style="margin-left: 3%; margin-top: 1%;">
 						<table>
 						<tr>
 							<td colspan="2">
 								<h3>Phân quyền người dùng</h3>
 							<td></td>
 						</tr>
 						<tr>
 							<td>
 								<label style="width: 110px; font-size: 10pt; float: left; margin-left: 5px;">Tên người dùng:</label>
 							</td>
 							<td>
 								<input style="width: 240px; height: 30px; float: right;" type="text" value="${fullName}"  disabled></input>		
 							</td>
 						</tr>
			    		<tr>
 							<td>
 								<label style="width: 110px; font-size: 10pt; float: left; margin-left: 5px;">Tài khoản:</label>
							</td>
 							<td>
 								<input style="width: 240px; height: 30px; float: right;" type="text"  value="${userName}" disabled ></input>
							</td>
 						</tr>
 						<tr>
 							<td>
 								<label style="width: 110px; font-size: 10pt; float: left; margin-left: 5px;">Email:</label>
							</td>
 							<td>
 								<input style="width: 240px; height: 30px; float: right;" type="text" value="${email}" disabled></input>
							</td>
 						</tr>
			    		<tr>
 							<td>
 								<label style="width: 110px; font-size: 10pt; float: left; margin-left: 5px;">Quyền:</label>
							</td>
 							<td>
 								<input style="width: 240px; height: 30px; float: right;" type="text" name="authorization" value="${authorization}" placeholder="${authorization}" onkeydown="return isNumber(event);" ></input>
							</td>
 						</tr>
 						<tr>
 							<td></td>
 							<td>
 								<b style="font-size: 10pt; float: left;">${message}</b>
							</td>
 						</tr>
 						<tr>
 							<td></td>
 							<td>
 								<button class="buttoncontrol" id="change" type="submit"  name="actionsubmit" value="change" style="float: right; height: 35px; margin-right: 0;">Thay đổi</button>
							</td>
 						</tr>
						</table>
					</div>
				</c:if>		
			</form:form>
			
    </tiles:putAttribute>
</tiles:insertDefinition>
</body>
</html>
