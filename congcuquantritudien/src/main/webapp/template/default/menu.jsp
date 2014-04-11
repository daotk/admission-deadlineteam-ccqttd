<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="menu">  
	<div style="border-bottom: 1px solid #546181;background-color: #122144;">
		<div style="width: 100%;height: 90px ;background-image: url(images/logo2.png); vertical-align:middle"></div>
	</div>
	<div id="menus" class="menu">
		<h3>Quản lý chung</h3>
		<ul class="navigation" id="fist">
			<li class="active" id="home"><a href="home">Danh sách chưa trả lời</a></li>
			<li id="dsluutam"><a href="dsluutam">Danh sách lưu tạm</a></li>
			<li id="dsdatraloi"><a href="dsdatraloi">Danh sách đã trả lời</a></li>
			<li id="botudien"><a href="botudien">Bộ từ điển</a></li>
			<li id="dsdaxoa"><a href="dsdaxoa">Danh sách đã xóa</a></li>
		</ul>
		<h3>Tiện ích</h3>
		<ul class="navigation" id="second">
			<li><a href="taocauhoi">Tạo câu hỏi</a></li>
			<li><a href="#">Tạo index</a></li>
			<c:if test="${not empty isAdmin}">
				<li><a href="#">Cấu hình</a></li>
			</c:if>
			<li><a href="#">Trợ giúp</a></li>
		</ul>
	</div>	
</div>