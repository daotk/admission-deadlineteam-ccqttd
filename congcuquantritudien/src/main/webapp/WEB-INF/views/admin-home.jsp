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
	<link href="./images/icon-browser.ico" rel="shortcut icon" type="image/x-icon" />  
	<title>Danh sách chưa trả lời</title>
	
	<!-- CSS -->
	<link href="css/stylesheet1.css" rel="stylesheet" />
	<link rel="stylesheet" href="css/bootstrap.css"/>
	
	<!-- Java Script -->
	<script src='js/jquery.min.js' type='text/javascript'></script>
	
	<!-- CK Editor -->
	<script src="ckeditor/ckeditor.js" type="text/javascript" charset="utf-8"></script>
	
	<!-- Paging -->
    <script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
	<script src="js/bootstrap-paginator.js"></script>
		
	<!-- Control Check box -->
	<script src='js/checkbox.js' type='text/javascript'></script>
	
	<!--For Loading -->
	<script>
	$(window).bind("load", function() {
		$('#loading').fadeOut(2000);
	});
	</script>
	
	<!-- Info Paging -->
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
				totalPages: noOfPages,
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
	</script>
</head>
<body>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
    <div id="loading"> </div>
        <div class="body">
        	<table style="height: 100%;width: 100%;border-collapse: collapse;">
        		<!--  TEST DEMO -->
        		<tr style="height: 100%; width: 100%;">
				<td style="background-color: #ffffff; width: 350px; height:100%;" valign="top">				
					<form id="quick-search" method="post" action="home" class="timkiem">
				   		<feildset class="search-bar">
							<label >
					 			<input name="actionsubmit" type="search" class="search-term" placeholder="Nhập từ khóa tìm kiếm"> 
							</label>
				   		</feildset>
				  	</form>
				  	<div style="padding: 5px;">
					  	<span id="select_all" style="cursor: pointer;">Chọn tất cả</span>
					  	<span id="delete_all"  style="cursor: pointer;padding-left: 10px;">Xóa</span>
				  	</div>
					<div class="list-question-content">
					<!-- load list of question -->
					<c:if test="${not empty listquestionmanagement}">
					<c:forEach var="Questionmanagement" items="${listquestionmanagement}" >
					<div style="width: 100%;">
						<div class="check"><input id="${Questionmanagement.ID}" name="check_" type="checkbox" value="${Questionmanagement.ID}" AUTOCOMPLETE=OFF /></div>
						<a href="${pageContext.request.contextPath}/home?topic=${Questionmanagement.ID}&page=${curentOfPage}" style ="text-decoration: none;">
							<div class="list-question" id="${Questionmanagement.ID}"  onMouseOver="this.style.backgroundColor='#eef0f2'" onMouseOut="this.style.backgroundColor='#ffffff'">
							<div class="row1">	
									<div class="list-email">${Questionmanagement.questionEmail}</div>
									<div class="list-date">
										<fmt:formatDate value="${Questionmanagement.questionDate}"  pattern="dd/MM/yyyy HH:mm" />
									</div>								
							</div>
							<div class="row2">
								<c:set var="title" value="${Questionmanagement.title}"/>
								${fn:substring(title,0, 30)}
							</div>								
							<div class="row3">
								<c:set var="string1" value="${Questionmanagement.question}"/>
								${fn:substring(string1,0, 50)}
							</div>
							</div>
						</a>
					</div>
					</c:forEach>
					</c:if>
					</div>
					<!-- end load list of question -->
					<!-- Paging -->
					<div id="paginator"></div>	
				</td>
				<!-- Cot thu 3 -->
				<td style="background-color:#ffffff; width: auto; height: 100%" valign="top">			
					<form:form method="post" action="" commandName="questionmanagements">
					<p class="error">${error}<p>
        			<p class="success">${message}<p>
					<c:if test="${not empty questionmanagements.title}">
						<div id="questionarea" style="width: 95%;margin: 15px;">
							<div id="question-subject" style="width: 100%; font-size: 16px;">							
							<form:label path="Title">${questionmanagements.title}</form:label>
							</div>
							<div id="question-info" style="width: 100%;padding-top: 5px;padding-bottom: 5px;">
								<span style="font-size: 15px;">${questionmanagements.questionBy} &lt;${questionmanagements.questionEmail}&gt;</span>
								<span style="font-size: 15px;float: right;">
									<fmt:formatDate value="${questionmanagements.questionDate}"  pattern="dd/MM/yyyy HH:mm" />
								</span>
							</div>
							<div id="question-content" style="width: 100%;">${questionmanagements.question}</div>
						</div>				
						<div id="answerarea" style="width: 100%;">
							<span style="font-size: 15px;font-weight: bold; color: #4e4e4e;padding-left: 10px;">TRẢ LỜI:${anwser}</span>
							<div style="width: 98%; padding: 5px 10px 0 10px;">
								<form:textarea path="Answer" name="editor1" id="editor1" rows="5" cols="10"></form:textarea>
			            		<script>
			                		// Replace the <textarea id="editor1"> with a CKEditor
			                		// instance, using default configuration.
			                		CKEDITOR.replace( 'editor1' );
			                		CKEDITOR.config.resize_enabled = false;
			                		CKEDITOR.config.removePlugins = 'elementspath';
			                		CKEDITOR.config.entities = false;
			                		CKEDITOR.config.htmlEncodeOutput = false;
			            		</script>
							</div>
						</div>				
						<div style="width: 100%;height:35px;padding-top: 20px;">
								<button class="buttoncontrol" id="delete" type="submit"  name="actionsubmit" value="delete">XÓA</button>
								<button class="buttoncontrol" id="save" type="submit"  name="actionsubmit" value="save">LƯU</button>
								<button class="buttoncontrol" id="send" type="submit"  name="actionsubmit" value="send">GỬI</button>
						</div>
					</c:if>								
					</form:form>
				</td>
        </tr>
        	</table>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>
</body>
</html>
