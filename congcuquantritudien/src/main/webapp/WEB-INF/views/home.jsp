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
	<!-- Setting pop box -->
	<script src='js/popbox.js' type='text/javascript'></script>
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
		var noOfDisplay = '${noOfDisplay}';
		var page = location.search.split("page=")[1];
		if(noOfPages>=1){   
			if(page != null){
				var options = {
				currentPage: page,
				totalPages: noOfPages,
				 size:'normal',
				 alignment:'center',
				 numberOfPages: noOfDisplay,
				 useBootstrapTooltip:true,
				pageUrl: function(type, page, current){
					return "?page="+page;
				}
			};
			$('#paginator').bootstrapPaginator(options);    
			}else{
				var options = {
					currentPage: 1,
					totalPages: noOfPages,
					 size:'normal',
					 alignment:'center',
					 numberOfPages:noOfDisplay,
					 useBootstrapTooltip:true,
					pageUrl: function(type, page, current){
						return "?page="+page;
					}
				};
				$('#paginator').bootstrapPaginator(options); 
			}
		}
		return false;
		});
	</script>
	<!-- LIMIT TExt filed to accept only numbers as input -->
	<script>
	 function isNumber(evt)
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
    <div id="loading"></div>
    <div class="body">
        	<table style="height: 100%;width: 100%;border-collapse: collapse;">
        		<tr style="height: 100%; width: 100%;">
        			<!-- List question is received -->
					<td style="background-color: #ffffff; width: 350px; height:100%;" valign="top">	
						<!-- search form -->			
						<form id="quick-search" method="post" action="home" class="timkiem">
					   		<fieldset class="search-bar">
								<label >
						 			<input name="actionsubmit" type="search" class="search-term" placeholder="Nhập từ khóa tìm kiếm" 
						 			value="<c:if test="${not empty actionsubmit}">${actionsubmit}</c:if>"> 
								</label>
					   		</fieldset>
					  	</form>
					  	<!-- end search -->
				  	
					  	<!-- Select form -->
					  	<div style="padding: 5px;">
						  	<span id="select_all" style="cursor: pointer;"><!-- img src="images/checkbox.png" style="border: 0; margin-right: 4px; margin-top: -6px" /-->Chọn tất cả</span>
						  	<span id="delete_all"  style="cursor: pointer;padding-left: 10px;"><img src="images/recycle.png" style="border: 0; margin-right: 4px; margin-top: -6px" />Xóa</span>
					  		
					  		<!-- configuration -->
					  		<div id="container-pop">
								<a class="popup-link-1" style="text-decoration: none;">
									<img src="images/setting.png" style="border: 0; float: left; margin-right: 2px; margin-top: 1px;" />
								</a>
							</div>
							<!-- end configuration -->
					  	</div>
					  	<!-- end select form -->
				  	
					  	<!-- configuration pop-up -->
					  	<div class="popup-box" id="popup-box-1">
					  		<div class="close"><img src="images/close2.png" style="border: 0; margin-right: 5px; margin-top: 5px" /></div>
					  		<div class="top">
					  		<form method="post" action="home" style="text-align: center;">
					  			<table>
					  				<tr>
					  					<td><label style="width: 160px; display: inline; padding-right: 10px;">Số mục hiển thị:</label></td>
					  					<td><input style="width: 220px; height: 30px;" type="text" name="change-items" value="${numOfRecord}" placeholder="${numOfRecord}" onkeydown="return isNumber(event);"></input></td>
					  				</tr>
					  				<tr>
					  					<td><label style="width: 160px; display: inline; padding-right: 6px;">Số trang hiển thị:</label></td>
					  					<td><input style="width: 220px; height: 30px; display: inline;" type="text" name="change-pagin" value="${numOfPagin}" placeholder="${numOfPagin}" onkeydown="return isNumber(event);"></input></td>
					  				</tr>
					  				<tr>
					  					<td></td>
					  					<td><button class="buttoncontrol" id="change" type="submit"  name="actionsubmit" value="change" style="height: 35px; margin-right: 0;">Thay đổi</button></td>
					  				</tr>
					  			</table>
					  			</form>
							</div>
						</div>
						<div id="blackout"></div>
						<!-- end configuration pop-up -->
					
						<!-- load list of question -->  
						<div style="height: 81%;">      	
							<div class="list-question-content">
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
													<div class="row3" style="text-align: justify;">
														<c:set var="string1" value="${Questionmanagement.question}"/>
														${fn:substring(string1,0, 55)}
													</div>
												</div>
											</a>
										</div>
									</c:forEach>
								</c:if>
							</div>
						</div>
						<!-- end load list of question -->
					
						<!-- Paging -->
						<div id="paginator"></div>
					  	<!-- end paging -->
					</td>
					<!-- Detail of question is received -->
					<td style="background-color:#f5f3f3; width: auto; height: 100%" valign="top">			
						<form:form method="post" action="" commandName="questionmanagements">
							<c:if test="${not empty error}">
								<p class="error">${error}<p>
							</c:if>	
							<c:if test="${not empty message}">
		        				<p class="success">${message}<p>
		        			</c:if>	
		        			<c:if test="${not empty message1}">
		        				<p class="success">${message1}<p>
		        			</c:if>	
							<c:if test="${not empty questionmanagements.question}">
								<div id="questionarea">
								
									<!-- Question info -->
									<div id="question-info">
										<span style="float:left;">Người hỏi: </span>
										<span style="margin-left: 5px;">${questionmanagements.questionBy} &lt;${questionmanagements.questionEmail}&gt;</span>
										<span style="float: right;">
											<fmt:formatDate value="${questionmanagements.questionDate}"  pattern="dd/MM/yyyy HH:mm" />
										</span>
										<span style="float:right; margin-right: 5px;">Ngày nhận câu hỏi: </span>
									</div>
									<!-- end question info -->
									
									<!-- Question content -->
									<div id="question-content" style ="width:100%;">${questionmanagements.question}</div>
									<!-- end question content -->
									
								</div>
												
								<div id="answerarea">
									<span id="answer">TRẢ LỜI:${anwser}</span>
									<div id="answer-content">
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
								
								<!-- Button -->			
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
