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
	<title>Danh sách chưa trả lời</title>
	
	<link href="css/stylesheet1.css" rel="stylesheet" />
	<link rel="stylesheet" href="css/bootstrap.css">
	
	<script src="js/jquery.min.js"></script>
	<script src="ckeditor/ckeditor.js" type="text/javascript" charset="utf-8"></script>
	<script src='js/jquery.min.js' type='text/javascript'></script>
    <script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
	<script src="js/bootstrap-paginator.js"></script>
	<!--For Loading -->
	<script>
	$(window).bind("load", function() {
		$('#loading').fadeOut(2000);
	});
	</script>
	<!--config paging -->
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
        		<tr style="height: 100%;width: 100%">
				<td style="background-color: #ffffff;width: 30%; height:100%" valign="top">
					<div id="second-sidebar">
                   <form id="quick-search" method="post" action="botudiencosan">
				   		<feildset class="search-bar">
							<label >
					 			<input name="actionsubmit" type="search" class="search-term" placeholder="Nhập từ khóa tìm kiếm"> 
							</label>
				   		</feildset>
				  	</form> 
				  	
				  	<ul class="tabbed" data-persist="true">
			            <li><a href="botudien">Có sẵn</a></li>
			            <li><a href="botudienhientai" class="active">Đã đăng</a></li>
			            <li><a href="botudiendaha">Đã hạ</a></li>
			            <li><a href="botudiendaxoa">Đã xóa</a></li>
		       		 </ul>
		       		 
				<div class="list-question-content">
					<!-- load list of question -->
					<c:if test="${not empty Recentlist}">
					<c:forEach var="Questionmanagement" items="${Recentlist}" >
					<div style="width: 100%;">
						<div class="check"><input id="check_${Questionmanagement.ID}" name="check_${Questionmanagement.ID}" type="checkbox" value="${Questionmanagement.ID}" AUTOCOMPLETE=OFF /></div>
						<a href="${pageContext.request.contextPath}/botudienhientai?topic=${Questionmanagement.ID}&page=${curentOfPage}" style ="text-decoration: none;">
							<div class="list-question" id="${Questionmanagement.ID}"  onMouseOver="this.style.backgroundColor='#eef0f2'" onMouseOut="this.style.backgroundColor='#ffffff'">
							<div class="row1">	
									
									<div class="list-date">
										<fmt:formatDate value="${Questionmanagement.createDate}"  pattern="dd/MM/yyyy HH:mm" />
									</div>
									
							</div>
							<div class="row2">
								<c:set var="title" value="${Questionmanagement.question}"/>
								${fn:substring(title,0, 40)}
							</div>								
							<div class="row3">
								
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
                </div>		
				</td>
				
				<!-- Cot thu 3 -->
				<div>${message}</div>
				<td style="background-color:#f5f3f3; width: auto; height: 100%" valign="top">	
			<form:form method="post" action="" commandName="diction">
				<c:if test="${not empty error}">
					<p class="error">${error}<p>
				</c:if>	
				<c:if test="${not empty message}">
        			<p class="success">${message}<p>
        		</c:if>	
        		<c:if test="${not empty message1}">
        			<p class="success">${message1}<p>
        		</c:if>	
			<c:if test="${not empty diction.question}">
					<div id="question" style="width: 95%;margin: 15px;">
						<div id="question-dateinfo">
							<span style="font-size: 12px;">Người trả lời:  ${username}</span>
						</div>
				
						<div id="question-content">
							${diction.question}
						</div>
					
					</div>
					<div id="answerarea" style="margin-left: 5px; width: 98%; overflow :scroll; height: 300px;">
						<span id="answer">TRẢ LỜI:</span>
						<div id="answer-content">
								${diction.anwser}
						</div>
					</div>
					<div style="width: 100%;height:35px;padding-top: 20px;">
							<button class="buttoncontrol" id="remove" type="submit"  name="actionsubmit" value="remove">HẠ CÂU HỎI</button>				
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
