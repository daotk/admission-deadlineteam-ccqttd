<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="./images/icon-browser.ico" rel="shortcut icon" type="image/x-icon" />  
	<title>Danh sách đã xóa</title>
	
	<!-- CSS -->
	<link href="css/stylesheet1.css" rel="stylesheet" />
	<link rel="stylesheet" href="css/bootstrap.css"/>
	
	<!-- Java Script -->
	<script src='js/jquery.min.js' type='text/javascript'></script>
	<!-- Setting pop box -->
	<script src='js/popbox.js' type='text/javascript'></script>
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
		var noOfDisplay = '${noOfDisplay}';
		var page = location.search.split("page=")[1];
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
			}
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
			}
			$('#paginator').bootstrapPaginator(options); 
		}
		return false;
		});
	</script>
	<!-- LIMIT TExt filed to accept only numbers as input -->
	<script>
	function isNumber(event) {
		  if (event) {
		    var charCode = (event.which) ? event.which : event.keyCode;
		    if (charCode != 190 && charCode > 31 && 
		       (charCode < 48 || charCode > 57) && 
		       (charCode < 96 || charCode > 105) && 
		       (charCode < 37 || charCode > 40) && 
		        charCode != 110 && charCode != 8 && charCode != 46 )
		       return false;
		  }
		  return true;
		}
	</script>
</head>
<body>
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
        <div id="loading"> </div>
        <div class="body">
        	<table style="height: 100%;width: 100%;border-collapse: collapse;">
        		<tr style="height: 100%;width: 100%">
					<td style="background-color: #ffffff;width: 350px; height:100%" valign="top">
						<!-- search form -->
						<form id="quick-search" method="post" action="dsdaxoa">
						   		<fieldset class="search-bar">
									<label >
							 			<input name="actionsubmit" type="search" class="search-term" placeholder="Nhập từ khóa tìm kiếm" 
							 			value="<c:if test="${not empty actionsubmit}">${actionsubmit}</c:if>">  
									</label>
						   		</fieldset>
						 </form>
						 <!-- end search -->
						 
						<!-- select form --> 
						<div style="padding: 5px;">
						  	<span id="select_all" style="cursor: pointer;"><img src="images/checkbox.png" style="border: 0; margin-right: 4px; margin-top: -6px" />Chọn tất cả</span>
						  	<span id="delete_all"  style="cursor: pointer;padding-left: 10px;"><img src="images/recycle.png" style="border: 0; margin-right: 4px;" />Xóa</span>
					  		
					  		<!-- configuration -->
					  		<div id="container-pop">
								<a class="popup-link-1" style="text-decoration: none;">
								<img src="images/setting.png" style="border: 0; float: left; margin-right: 2px; margin-top: 1px;" />
								</a>
							</div>
							<!-- end configuration -->
					  	</div>
					  	<!-- end select form -->
					  	<div class="popup-box" id="popup-box-1">
					  		<div class="close">X</div>
					  		<div class="top">
								<form method="post" action="dsdaxoa" style="text-align: center;">
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
						<div style="height: 480px;">
							<div class="list-question-content">
									
								<c:forEach var="Questionmanagement" items="${deletequestionlist}">
									<div style="width: 100%;">
										<div class="check"><input id="check_${Questionmanagement.ID}" name="check_${Questionmanagement.ID}" type="checkbox" value="${Questionmanagement.ID}" AUTOCOMPLETE=OFF /></div>
										<a href="${pageContext.request.contextPath}/dsdaxoa?topic=${Questionmanagement.ID}&page=${curentOfPage}" style ="text-decoration: none;">
											<div class="list-question" id="${Questionmanagement.ID}"  onMouseOver="this.style.backgroundColor='#eef0f2'" onMouseOut="this.style.backgroundColor='#ffffff'">
												<div class="row1">	
													<div class="list-email">${Questionmanagement.questionEmail}</div>
													<div class="list-date">
														<fmt:formatDate value="${Questionmanagement.questionDate}"  pattern="dd/MM/yyyy HH:mm" />
													</div>
												</div>
												<div class="row3">
													<c:set var="title" value="${Questionmanagement.question}"/>
													${fn:substring(title,0, 15)}
												</div>
											</div>
										</a>
									</div>
								</c:forEach>
							</div>	
						</div>
						<!-- end load list of question -->
						
						<!-- Paging -->
						<div id="paginator"></div>
						<!-- end paging -->
					</td>
					
					<!-- Detail of question is deleted-->
					<td style="background-color:#f5f3f3; width: auto; height: 100%" valign="top" >
						<form:form method="post" action="" commandName="deletequestion">
							<p class="error">${error}<p>
		        			<p class="success">${message}<p>
							<c:if test="${not empty deletequestion.question}">
								<div id="questionarea">
									<!-- Question info -->
									<div id="question-dateinfo">
										<span style="font-size: 12px;">Người xóa: ${deletequestion.deleteBy}</span>
										<span style="font-size: 12px;float:right ;">Ngày xóa: ${deletequestion.deleteDate}</span>
									</div>
									<!-- End question info -->
									
									<!-- Question content -->
									<div id="question-content">${deletequestion.question}</div>
									<!-- End question content -->
								
								</div>
								
								<div id="answerarea">
									<span id="answer">TRẢ LỜI:</span>
									<div id="answer-content" style ="overflow: auto; height: 300px;">
										<form:label path="Answer">${deletequestion.answer}</form:label>
									</div>
								</div>
								
								<!-- button -->
								<div style="width: 100%; height:35px; padding-top: 20px;">
									<button class="buttoncontrol" id="backup" type="submit"  name="actionsubmit" value="delete">KHÔI PHỤC</button>
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
