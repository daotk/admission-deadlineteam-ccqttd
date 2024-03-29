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
<link href="./images/logo-browser.ico" rel="shortcut icon" type="image/x-icon" />  
	<title>Hỏi - Đáp</title>
	<!-- CSS -->
	<link rel="stylesheet" href="css/bootstrap.css"/>
	<link rel="stylesheet" href="css/styles.css"/>
	<link rel="stylesheet" href="images/bootstrap/css/jquery.msgbox.css"/>
	<!-- Java Script -->
	<script src='js/jquery.min.js' type='text/javascript'></script>

	
	<!-- Setting pop box -->
	<script src='js/popbox.js' type='text/javascript'></script>
	
	<!-- Paging -->
    <script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
	<script src="js/bootstrap-paginator.js"></script>
	<script type="text/javascript" src="js/jquery.msgbox.js"></script>	
	
<script type="text/javascript">
	$(document).ready(function(){
		var noOfPages = '${noOfPages}'; 
		var page = location.search.split("page=")[1];
		if(noOfPages>=1){
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
		}
		return false;
		});
	
	
</script>

<script>
$(document).ready(function() {
    $('#list_dictionary h2').click(function() {
    	$(this).next('.answer').fadeToggle(550);      
    });
}); // end ready
</script>
<!-- LIMIT TExt filed to accept only numbers as input -->
	<script>
	 function isNumberKey(evt)
     {
        var charCode = (evt.which) ? evt.which : event.keyCode
        if (charCode > 31 && (charCode < 48 || charCode > 57))
           return false;

        return true;
     }
	</script>

</head>
<body>
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
		<table style="height: 100%;width: 100%;">
			<tr> 
				<td style="background-color: #ffffff; width: 350px; height:100%" valign="top">
					<!-- search and setting -->					
					<div id="tfheader" style="width: 100%;display: block; margin-top: 3%;">
						<!-- Search -->
						<div id="search" style="width: 100%; height:100%;display: inline;">
							<form id="tfnewsearch" method="post" action="./">
								<label >
								<div style="width:100%; float: left;">
									<span style="margin-left: 3%; margin-top:5px; width: 10%; float:left;">Tìm kiếm: </span>
									<c:if test="${empty curentkeyword}">
										<input name="actionsubmit" style="font-size:10pt; width: 68%; float:left;" type="search" class="search-term" placeholder="Nhập từ khóa cần tìm kiếm"> 
									</c:if>
						 			<c:if test="${not empty curentkeyword}">
										<input name="actionsubmit" style="font-size:10pt; width:68%; float:left;" type="search" class="search-term" placeholder="Nhập từ khóa cần tìm kiếm" value ="${curentkeyword}"> 
									</c:if>
									<button class="button" type="submit"  style=" margin-bottom: 10px; margin-left: 5px; width: 10%; height: 30px; float:left;">Tìm kiếm</button>
									<div id="container-pop" style="width: 5%; float:left;">
										<a class="popup-link-1" style="text-decoration: none;">
											<img src="images/setting.png" style=" margin-left: 5px; margin-bottom: 10px;" />
										</a>
									</div> <!-- end configuration -->
								</div>
								</label>
								
							</form>
						</div>
						<!-- End search -->
						
					  	<!-- configuration pop-up -->
					  	<div class="popup-box" id="popup-box-1">
					  		<div class="close"><img src="images/close1.png" style="border: 0; margin-right: 5px; margin-top: 5px" /></div>
					  		<div class="top">
					  		<form method="post" action="./" style="text-align: center;">
					  			<table>
					  				<tr>
					  					<td><label style="width: 160px; display: inline; padding-right: 10px;">Số mục hiển thị:</label></td>
					  					<td><input style="width: 220px; height: 30px; display: inline; margin-top: 6%;" type="text" name="setting" placeholder="${testrecord}"  value ="${testrecord}" onkeypress="return isNumberKey(event)"></input></td>
					  				</tr>
					  				<tr>
					  					<td></td>
					  					<td><button class="buttoncontrol" id="change" type="submit"  name="actionsubmit" value="settingrecord" style="float: right;">Lưu</button></td>
					  				</tr>
					  			</table>
					  			</form>
							</div>
						</div>
						<div id="blackout"></div>
						<!-- end configuration pop-up -->
					
					</div>	
					
					<c:if test="${not empty note}">
						<center><span class="error">${note}</span></center>
					</c:if>
					
					<!-- list question -->
					<div style="float: left; width: 100%;display: block;">
						<!-- title -->
						<div style="height: 3%;float: left; width: 100%;">
							<p style="font-size: 13pt; font-weight: bold; margin-left: 20px;color:rgb(100,150,200);">Hỏi - đáp</p>
						</div>
						<span style="margin-left: 20px;">
								<c:if test="${not empty result}">
									${result}
								</c:if>
						</span>
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
						<div id="paginator" style="width: 100%; height: 100%; float: left;"></div>
						<!-- End paging -->					
					</div>
					
					<!-- Send question -->
					<div style="float: left; width: 100%;display: block;">
						<div style="width: 100%; height: 8%; float: left;">
							<span style="font-size: 13pt; font-weight: bold; margin-left: 20px; color:rgb(100,150,200);">Gửi câu hỏi của bạn!</span>
						</div>
						<div style="width: 90%; margin-left: 10%;">
							
							<table style="width: 90%; float: left;">						
								<form:form commandName ="question" id ="newquestion" action="./" method="post">
									<tr>
										<td style="width: 25%;"></td>
										<td style="float: right">
											<c:if test="${not empty error}">
												<script type="text/javascript">
													var mess = '${error}';
													$.msgbox({
														type: 'error',
														content: mess,
														title: 'Thất bại'
													});
												</script>
											</c:if>	
							
											<c:if test="${not empty warning}">
												<script type="text/javascript">
													var mess = '${warning}';
													$.msgbox({
														type: 'warning',
														content: mess,
														title: 'Cảnh báo'
													});
												</script>
											</c:if>	
											</div>
											<c:if test="${not empty message}">
												<script type="text/javascript">
													var mess = '${message}';
								        				$.msgbox({
														type: 'success',
														content: mess,
														title: 'Thành công'
													});
							        			</script>
						        			</c:if>	
										</td>
									</tr>
									<tr>
										<td style="width: 25%;"><span style="font-size: 80%;">Họ tên của bạn</span></td>
										<td><form:input path="QuestionBy" type="text" style="width: 90%; height: 25px; font-size: 10pt; margin-top: 4px; margin-left: 10px;"  placeholder="Họ tên của bạn!"/></td>
									</tr>
									<tr>
										<td></td>
										<td style="font-size: 80%;"><form:errors path="QuestionBy" style="margin-left: 10px;" cssClass="error"/></td>
									</tr>
									<tr>
										<td><span style="font-size: 80%;">Email của bạn</span></td>
										<td><form:input path ="QuestionEmail" type="text" style="width: 90%; height: 25px; font-size: 10pt; margin-top: 4px; margin-left: 10px;" placeholder="Email của bạn!"/></td>
									</tr>
									<tr>
										<td></td>
										<td style="font-size: 80%; margin-left: 10px;"><form:errors path="QuestionEmail" style="margin-left: 10px;" cssClass="error"/></td>
									</tr> 
									<tr>
										<td valign="top"><span  style="font-size: 80%;">Nội dung câu hỏi của bạn</span></td>
										<td><form:textarea path ="Question" type="text" style="width: 90%; height: 200px; font-size: 10pt; margin-left: 10px;  margin-top: 2px;" placeholder="Nội dung câu hỏi của bạn!"/></td>
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
										<td><button class="button" type="submit"  name="actionsubmit" value="register" style="margin-top: 2%; float: right; margin-right: 8%;">Gửi câu hỏi</button></td>
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
