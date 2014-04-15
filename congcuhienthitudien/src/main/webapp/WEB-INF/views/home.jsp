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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Home</title>
	<!-- CSS -->
	<link rel="stylesheet" href="css/bootstrap.css"/>
	<link rel="stylesheet" href="css/styles.css"/>
	<!-- Paging -->
    <script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
	<script src="js/bootstrap-paginator.js"></script>
	
<script type="text/javascript">
	$(document).ready(function(){
		var noOfPages = '${noOfPages}'; 
		var page = location.search.split("page=")[1];
			if(page != null){
				var options = {
				currentPage: page,
				totalPages: noOfPages,
				 size:'normal',
				 alignment:'center',
				 numberOfPages:10,
				 useBootstrapTooltip:true,
				pageUrl: function(type, page, current){
					return "?page="+page;
				}
			}
			$('#paginator').bootstrapPaginator(options);    
		}else{
			var options = {
				currentPage: 1,
				totalPages: noOfPages,
				 size:'normal',
				 alignment:'center',
				 numberOfPages:10,
				 useBootstrapTooltip:true,
				pageUrl: function(type, page, current){
					return "?page="+page;
				}
			}
			$('#paginator').bootstrapPaginator(options); 
		}
		return false;
		});
	
	$(document).ready(function() {
	    $('#list_dictionary h2').click(function() {
	        $(this).next('.answer').slideToggle(500);
	        $(this).toggleClass('close');
	 
	    });
	}); // end ready
</script>

</head>
<body>
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
		<table style="height: 100%;width: 100%;">
			<tr> 
				<td style="background-color: #ffffff; width: 350px; height:100%" valign="top">
					<!-- search and setting -->					
					<div id="tfheader" style="width: 100%;float: left;display: block; margin-top: 10px;">
						<!-- Search -->
						<div id="search" style="width: 65%; height:100%; float: left;">
							<form id="tfnewsearch" method="post" action="">
								<label>
									<span style="margin-left: 5%;">Tìm kiếm: </span>
									<c:if test="${empty curentkeyword}">
										<input name="actionsubmit" style="font-size:10pt; width: 75%; margin-top: 5px;" type="search" class="search-term" placeholder="Nhập từ khóa cần tìm kiếm"> 
									</c:if>
						 			<c:if test="${not empty curentkeyword}">
										<input name="actionsubmit" style="font-size:10pt; width: 75%; margin-top: 5px;" type="search" class="search-term" placeholder="Nhập từ khóa cần tìm kiếm" value ="${curentkeyword}"> 
									</c:if>
								</label>
							</form>
						</div>
						<!-- End search -->
						
						<!-- Setting -->
						<div id="setting" style="width: 35%; float: left; height: 100%;">
							<div style="height: 100%; float: right;">
								<form method="post" action="./">
									<div style="width: 75%; float: left; height: 100%;">
										<span style="font-family: Arial, Helvetica, sans-serif; font-size: 80%; float: left; margin-top: 5%;">Số lượng </span>
										<c:if test="${empty testrecord}">
											<input name ="setting" type="text" style="width: 22%; height: 50%; font-size: 80%; margin-top: 2%; float: left;"  placeholder="10"/>
										</c:if>
										<c:if test="${not empty testrecord}">
											<input name ="setting" type="text" style="width: 22%; height: 50%; font-size: 80%; margin-top: 2%; margin-left: 1%; float: left;"  placeholder="10" value ="${testrecord}"/>
										</c:if>
										<span style="font-family: Arial, Helvetica, sans-serif; font-size: 80%; float: left; margin-top: 5%; margin-left: 1%;"> câu hỏi 1 trang</span>
									</div>
									<div style="width: 25%; float: left; height: 100%">
										<button class="button" type="submit"  name="actionsubmit" value="settingrecord" style="float: left;margin-top: 3%;border-radius: 3;"> Thay đổi</button>
									</div>
								</form>
								
							</div>
						</div>
						<!-- End setting -->
						
						
					</div>	

					<!-- list question -->
					<div style="float: left; width: 100%;display: block;">
						<!-- title -->
						<div style="height: 3%;float: left; width: 100%; margin-top: 2px;">
							<p style="font-size: 13pt; font-weight: bold; margin-left: 20px;color:rgb(100,150,200);">Hỏi - đáp</p>
						</div>
						
						<!-- list dictionary -->
						<div style="height: 88%;float: left; width: 100%;">
							<form>
								<div>
								<c:forEach var="Questionmanagement" items="${listdictionary}" >
									<a href="${pageContext.request.contextPath}/?record=${curentrecord}" style ="text-decoration: none;"></a>
									<section id="list_dictionary">
										<h2 class="question">
											Câu hỏi ${Questionmanagement.ID}
											<br/> ${Questionmanagement.question}
										</h2>
										<div class="answer" style="font-size: 12px; margin-top: 5px; margin-left: 25px; text-align: justify; width: 95%;">
											<span style="font-weight: bold;">Trả lời</span><BR />
											<p style="text-align: justify;">
												<SPAN style="font-size: 12px; font-family: Arial;">
													${Questionmanagement.anwser}
												</span>
											</p>
										</div>
										<hr />
									</section >
								</c:forEach>
								</div>
							</form>
						</div>
						<!-- End list dictionary -->
					
						<!-- Paging -->
						<div id="paginator"></div>
						<!-- End paging -->					
					</div>
					
					<!-- Send question -->
					<div style="float: left; width: 100%;display: block;">
						<div style="width: 100%; height: 8%; float: left;">
							<span style="font-size: 13pt; font-weight: bold; margin-left: 20px; color:rgb(100,150,200);">Gửi câu hỏi của bạn!</span>
						</div>
						<div style="width: 90%; margin-left: 10%;">
							<c:if test="${not empty message}">
								<span class="success">${message}</span>
							</c:if>
							<c:if test="${not empty error}">
								<span class="error">${error}</span>
							</c:if>	
							<table style="width: 90%;">						
								<form:form commandName ="question" id ="newquestion" action="./" method="post">
									<tr>
										<td style="width: 25%;"><span style="font-size: 80%;">Họ tên của bạn</span></td>
										<td><form:input path="QuestionBy" type="text" style="width: 90%; height: 25px; font-size: 10pt; margin-top: 10px; margin-left: 10px;"  placeholder="Họ tên của bạn!"/></td>
									</tr>
									<tr>
										<td></td>
										<td style="font-size: 80%;"><form:errors path="QuestionBy" style="margin-left: 10px;" cssClass="error"/></td>
									</tr>
									<tr>
										<td><span style="font-size: 80%;">Email của bạn</span></td>
										<td><form:input path ="QuestionEmail" type="text" style="width: 90%; height: 25px; font-size: 10pt; margin-top: 10px; margin-left: 10px;" placeholder="Email của bạn!"/></td>
									</tr>
									<tr>
										<td></td>
										<td style="font-size: 80%; margin-left: 10px;"><form:errors path="QuestionEmail" style="margin-left: 10px;" cssClass="error"/></td>
									</tr>
									<tr>
										<td valign="top"><span  style="font-size: 80%;">Nội dung câu hỏi của bạn</span></td>
										<td><form:textarea path ="Question" type="text" style="width: 90%; height: 200px; font-size: 10pt; margin-left: 10px;  margin-top: 10px;" placeholder="Nội dung câu hỏi của bạn!"/></td>
									</tr>
									<tr>
										<td></td>
										<td style="font-size: 80%;"><form:errors path="Question" style="margin-left: 10px;" cssClass="error" /></td>
									</tr>
									<tr>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td><button class="button" type="submit"  name="actionsubmit" value="register" style="margin-top: 2%; float: right;">Gửi câu hỏi</button></td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
								</form:form>
						</table>						
						</div>
					</div>
					<!-- End send question -->
				</td>
			</tr>
		</table>
	</tiles:putAttribute>	
</tiles:insertDefinition>
</body>
</html>
