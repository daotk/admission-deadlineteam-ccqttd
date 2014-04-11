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
				totalPages: 10,
				 size:'normal',
				 alignment:'center',
				 numberOfPages:3,
				 useBootstrapTooltip:true,
				pageUrl: function(type, page, current){
					return "?page="+page;
				}
			}
			$('#paginator').bootstrapPaginator(options);    
		}else{
			var options = {
				currentPage: 1,
				totalPages: 10,
				 size:'normal',
				 alignment:'center',
				 numberOfPages:3,
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
			
				<td style="background-color: #ffffff;width: 350px; height:100%" valign="top">
					
					<!-- Search -->
						<div id="tfheader" style="height: 85px; width: 700px; float: left;">
							<form id="tfnewsearch" method="post" action="">
								<label >
									<span>Tìm kiếm: </span>
									
									<c:if test="${empty curentkeyword}">
									<input name="actionsubmit" style="width: 550px; margin-left: 14px; margin-top: 5px;" type="search" class="search-term" placeholder="Nhập từ khóa tìm kiếm"> 
									</c:if>
						 			<c:if test="${not empty curentkeyword}">
									<input name="actionsubmit" style="width: 550px; margin-left: 14px; margin-top: 5px;" type="search" class="search-term" placeholder="Nhập từ khóa tìm kiếm" value ="${curentkeyword}"> 
									</c:if>
									
								</label>
							</form>
							<div class="tfclear"></div>
						</div>
						
					<!-- End search -->
					
					<!-- Setting -->
					<form method="post" style="margin-top: 28px;">
						<span style="font-family: Arial, Helvetica, sans-serif; font-size: 14px;">So luong </span>
						<c:if test="${empty testrecord}">
						<input name ="setting" type="text" style="width: 50px; height: 20px; font-size: 10pt; margin-top: 11px;margin-left: 2px;"  placeholder="10"/>
						</c:if>
						<c:if test="${not empty testrecord}">
						<input name ="setting" type="text" style="width: 50px; height: 20px; font-size: 10pt; margin-top: 11px;margin-left: 2px;"  placeholder="10" value ="${testrecord}"/>
						</c:if>
						<span style="font-family: Arial, Helvetica, sans-serif; font-size: 14px;"> dong</span>
						<button class="button" type="submit"  name="actionsubmit" value="settingrecord" style="float: right;margin-top: 9px;margin-right: 5px;border-radius: 3px;"> Thay đổi</button>
					</form>
						
					<!-- End setting -->
						
					<!-- List dictionary -->
					
					<span style="font-size: 13pt; font-weight: bold; margin-left: 20px;color:rgb(100,150,200);">Hỏi - đáp</span>
					<form>
					<c:forEach var="Questionmanagement" items="${listdictionary}" >
					<a href="${pageContext.request.contextPath}/?record=${curentrecord}" style ="text-decoration: none;">
						</a>
						<section id="list_dictionary">
							<h2 class="question">
								Câu hỏi ${Questionmanagement.ID}
								<br/> ${Questionmanagement.question}
							</h2>
							<div class="answer" style="font-size: 12px; margin-top: 5px; margin-left: 25px; text-align: justify; width: 875px;">
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
					</form>
					<!-- End list dictionary -->
					
					<!-- Paging -->
					
					<div id="paginator"></div>
					<!-- End paging -->					
					
					<!-- Send question -->
					<span style="font-size: 13pt; font-weight: bold; margin-left: 20px; color:rgb(100,150,200);">Gửi câu hỏi của bạn!</span>
					<table style="margin-left: 107px; font-size: 10pt;">
						<TBODY>							
						<form:form commandName ="question" id ="newquestion">
							<tr>
								<td><span>Họ tên của bạn</span></td>
								<td><form:input path="QuestionBy" type="text" style="width: 550px; height: 25px; font-size: 10pt; margin-top: 10px;margin-left: 15px;"  placeholder="Họ tên của bạn!"/></td>
								<td><form:errors path="QuestionBy"/></td>
							</tr>
							<tr>
								<td><span>Email của bạn</span></td>
								<td><form:input path ="QuestionEmail" type="text" style="width: 550px; height: 25px; font-size: 10pt; margin-top: 10px;margin-left: 15px;" placeholder="Email của bạn!"/></td>
							<td><form:errors path="QuestionEmail"/></td>
							</tr>
							<tr>
								<td><span>Tiêu đề câu hỏi của bạn</span></td>
								<td><form:input path ="Title" type="text" style="width: 550px; height: 25px; font-size: 10pt; margin-top: 10px;margin-left: 15px;" placeholder="Title của bạn!"/></td>
							<td><form:errors path="Title"/></td>
							</tr>
							<tr>
								<td><span>Nội dung câu hỏi của bạn</span></td>
								
								<td rowspan="6"><form:input path ="Question" type="text" style="width: 550px; height: 200px; font-size: 10pt;margin-left: 15px;  margin-top: 10px;" placeholder="Nội dung câu hỏi của bạn!"/></td>
							<td><form:errors path="Question"/></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<div>${message}</div>
							<tr>
								<td>&nbsp;</td>
								<td><button class="button" type="submit"  name="actionsubmit" value="register" style="float: right;">Gửi câu hỏi</button></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							</form:form>
						</TBODY>
					</table>						
					<!-- End send question -->
				</td>
			</tr>
		</table>
	</tiles:putAttribute>	
</tiles:insertDefinition>
</body>
</html>
