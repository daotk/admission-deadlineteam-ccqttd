<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="menu">  
	<div style="border-bottom: 1px solid #546181;background-color: #122144;">
		<div style="width: 100%;height: 90px ;background-image: url(images/logo2.png); vertical-align:middle"></div>
	</div>
	<div id="menus" class="menu">
		<h3>Quản lý chung</h3>
		<ul class="navigation" id="fist">
			<li class="active" ><a href="home"><img style="float: left; margin: 1px 5px 0 -10px;" src="template/default/images/List-question.png"/>Danh sách chưa trả lời</a></li>
			<li ><a href="dsluutam"><img style="float: left; margin: 1px 5px 0 -10px;" src="template/default/images/List-save.png"/>Danh sách lưu tạm</a></li>
			<li><a href="dsdatraloi"><img style="float: left; margin: 1px 5px 0 -10px;" src="template/default/images/List-replied.png"/>Danh sách đã trả lời</a></li>
			<li><a href="dsdaxoa"><img style="float: left; margin: 1px 5px 0 -10px;" src="template/default/images/List-delete.png"/>Danh sách đã xóa</a></li>
			<li><a href="botudien"><img style="float: left; margin: 1px 5px 0 -10px;" src="template/default/images/Dictionary.png"/>Bộ từ điển</a></li>			
		</ul>
		<h3 style="margin-top: 10px;">Tiện ích</h3>
		<ul class="navigation" id="second">
			<li><a href="taocauhoi"><img style="float: left; margin: 1px 5px 0 -10px;" src="template/default/images/create-new.png"/>Tạo câu hỏi</a></li>
			<c:if test="${not empty Admin}">
				<li><a href="taoindex"><img style="float: left; margin: 1px 5px 0 -10px;" src="template/default/images/Index.png"/>Tạo index</a></li>
				<li><a href="cauhinh"><img style="float: left; margin: 1px 5px 0 -10px;" src="template/default/images/Cofig.png"/>Cấu hình</a></li>
			</c:if>
			<li><a href="trogiup"><img style="float: left; margin: 1px 5px 0 -10px;" src="template/default/images/Help.png"/>Trợ giúp</a></li>
		</ul>
	</div>	
</div>