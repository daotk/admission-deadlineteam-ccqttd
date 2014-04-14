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
	<title>Bộ từ điển đã xóa</title>
	
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
				   				<fieldset class="search-bar">
									<label >
							 			<input name="actionsubmit" type="search" class="search-term" placeholder="Nhập từ khóa tìm kiếm"> 
									</label>
				   				</fieldset>
				  			</form> 
				  	
						  	<ul class="tabbed" data-persist="true">
					            <li><a href="botudien">Có sẵn</a></li>
					            <li><a href="botudienhientai" >Đã đăng</a></li>
					            <li><a href="botudiendaha">Đã hạ</a></li>
					            <li><a href="botudiendaxoa" class="active">Đã xóa</a></li>
				       		</ul>
       		 				
       		 				<!-- select form -->
							<div style="padding: 5px;">
								<span id="select_all" style="cursor: pointer;">Chọn tất cả</span>
								<span id="delete_all"  style="cursor: pointer;padding-left: 10px;"><img src="images/recycle.png" style="border: 0; margin-right: 4px; margin-top: -6px;" />Xóa</span>
						  		
						  		<!-- configuration -->
						  		<div id="container-pop">
									<a class="popup-link-1" style="text-decoration: none;"/>
									<img src="images/setting.png" style="border: 0; float: left; margin-right: 2px; margin-top: 1px;" />
								</div>
								<!-- end configuration -->
					  		</div>
					  		<!-- end select form -->
					  		
					  		<!-- configuration pop-up -->
					  		<div class="popup-box" id="popup-box-1"><div class="close">X</div>
					  			<div class="top">
									<form method="post" action="dsdatraloi" style="text-align: center;">
							    		<label style="width: 160px; display: inline; padding-right: 10px;">Số mục hiển thị:</label>
							    		<input style="width: 180px; height: 30px;" type="text" name="change-items" value="${numOfRecord}" placeholder="${numOfRecord}" onkeydown="return isNumber(event);"></input>
										<br>
										<label style="width: 160px; display: inline; padding-right: 6px;">Số trang hiển thị:</label>
										<input style="width: 180px; height: 30px; display: inline;" type="text" name="change-pagin" value="${numOfPagin}" placeholder="${numOfPagin}" onkeydown="return isNumber(event);"></input>
										<button class="buttoncontrol" id="change" type="submit"  name="actionsubmit" value="change" style="height: 35px; float: none; ">Thay đổi</button>
							   		</form>
								</div>
							</div>
							<div id="blackout"></div>
						    <!-- end configuration pop-up -->
						    
       		 				<!-- load list of question -->
       		 				<div class="list-question-content">
								<c:if test="${not empty deletelist}">
									<c:forEach var="Questionmanagement" items="${deletelist}" >
										<div style="width: 100%;">
											<div class="check"><input id="check_${Questionmanagement.ID}" name="check_${Questionmanagement.ID}" type="checkbox" value="${Questionmanagement.ID}" AUTOCOMPLETE=OFF /></div>
											<a href="${pageContext.request.contextPath}/botudiendaxoa?topic=${Questionmanagement.ID}&page=${curentOfPage}" style ="text-decoration: none;">
												<div class="list-question" id="${Questionmanagement.ID}"  onMouseOver="this.style.backgroundColor='#eef0f2'" onMouseOut="this.style.backgroundColor='#ffffff'">
													<div class="row1">	
														<div class="list-date">
															<fmt:formatDate value="${Questionmanagement.createDate}"  pattern="dd/MM/yyyy HH:mm" />
														</div>
													</div>
													<div class="row3">
														<c:set var="title" value="${Questionmanagement.question}"/>
														${fn:substring(title,0, 55)}
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
				
					<!-- Detail -->
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
								<div id="questionarea">
									<div id="question-dateinfo">
										<span style="font-size: 12px;">Người xóa: ${deleteuser}</span>
										<span style="font-size: 12px;float:right ;">Ngày xóa: ${diction.deleteDate}</span>
									</div>
									<div id="question-dateinfo">
										<span style="font-size: 12px;">Người trả lời:  ${username}</span>
									</div>
									<div id="question-content">
										${diction.question}
									</div>		
								</div>
						
								<div id="answerarea">
									<span id="answer">TRẢ LỜI:</span>
									<div id="answer-content" style="overflow: auto; height:300px;">
										${diction.anwser}
									</div>
								</div>
								<div style="width: 100%;height:35px;padding-top: 20px;">
									<button class="buttoncontrol" id="backup" type="submit"  name="actionsubmit" value="restore">KHÔI PHỤC</button>
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
