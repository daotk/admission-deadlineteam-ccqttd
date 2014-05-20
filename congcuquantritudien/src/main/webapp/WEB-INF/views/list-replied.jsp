
<?xml version="1.0" encoding="utf-8" ?>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="./images/icon-browser.ico" rel="shortcut icon" type="image/x-icon" />  
	<title>Danh sách đã trả lời</title>
	
	<!-- CSS -->

	<link href="css/stylesheet1.css" rel="stylesheet" />
	<link rel="stylesheet" href="css/bootstrap.css"/>
	<link rel="stylesheet" href="images/bootstrap/css/jquery.msgbox.css" />
		
	<!-- Java Script -->
	<script src='js/jquery.min.js' type='text/javascript'></script>
	
	<!-- CK Editor -->
	<script src="ckeditor/ckeditor.js" type="text/javascript" charset="utf-8"></script>
	
	<!-- Paging -->
    <script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
	<script src="js/bootstrap-paginator.js"></script>
		
	<script type="text/javascript" src="js/jquery.msgbox.i18n.js"></script>
	<script type="text/javascript" src="js/jquery.msgbox.js"></script>
		
	<!-- Control Check box -->
	<script src='js/checkbox.js' type='text/javascript'></script>
	<!-- Setting pop box -->
	<script src='js/popbox.js' type='text/javascript'></script>
	<!--For Loading -->
	<script>
	$(window).bind("load", function() {
		$('#loading').fadeOut(2000);
	});
	</script>
	<script>
	function limitText(field, maxChar){
	    $(field).attr('maxlength',maxChar);
	}
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
	<SCRIPT>
	function validateNumber(event) {
	    var key = window.event ? event.keyCode : event.which;

	    if ( key < 48 || key > 57 ) {
	        return false;
	    }
	    else return true;
	};
	</SCRIPT>
	<SCRIPT>
	$(document).ready(function(){
	    $('[id^=edit]').keypress(validateNumber);
	});
	</SCRIPT>
		<!-- kepp active class -->
	<script type="text/javascript">
		//Get url parameter
		function getUrlVars() {
		    var vars = {};
		    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
		        vars[key] = value;
		    });
		    return vars;
		}
		//xủ lý lưu active class
	   $(document).ready(function(){
	    	var url = getUrlVars()["topic"];
			if(url!= null){  
				var divid= "div_"+url;
				document.getElementById(divid).style.backgroundColor='#eef0f2';
			}
	
	    });
	</script>
</head>
<body>
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
    <div id="loading"></div>
        <div class="body">
        	<table style="height: 100%;width: 100%;border-collapse: collapse;">
        		<tr style="height: 100%;width: 100%">
					<!-- List question is replied -->
					<td style="background-color: #ffffff;width: 350px; height:100%" valign="top">
						<!-- search form -->			
						<form id="quick-search" method="post" action="dsdatraloi" class="timkiem">
					   		<fieldset class="search-bar">
					   			<p class="search_1" style="width: 31px; height: 29px; float: right;background: url('./images/Search.png') no-repeat scroll 4px 3px;border: 1 solid;border-color: white;"></p>
								<label>									
						 			<input name="actionsubmit" type="search" class="search-term" placeholder="Nhập từ khóa tìm kiếm" value="<c:if test="${not empty actionsubmit}">${actionsubmit}</c:if>">  
								</label>
					   		</fieldset>
					  	</form>
					  	<!-- end search -->
					 	
					 	<!-- select form -->
						<div style="padding: 5px;">
						  	<span id="select_all" style="cursor: pointer;"><!-- img src="images/checkbox.png" style="border: 0; margin-right: 4px; margin-top: -6px" /-->Chọn tất cả</span>
						  	<span id="delete_all"  style="cursor: pointer;padding-left: 10px;"><img src="images/recycle.png" style="border: 0; margin-right: 4px; margin-top: -6px;" />Xóa</span>
					  		
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
					  		<div class="close"><img src="images/close2.png" style="border: 0; margin-right: 5px; margin-top: 5px;" /></div>
					  		<div class="top">
					  			<form method="post" action="dsdatraloi" style="text-align: center;">
					  			<table>
					  				<tr>
					  					<td><label style="width: 160px; display: inline; padding-right: 10px;">Số mục hiển thị:</label></td>
					  					<td><input id="edit1" maxlength="5" style="width: 220px; height: 30px;" type="text" name="change-items" value="${numOfRecord}" placeholder="${numOfRecord}" onkeydown="return isNumber(event);"></input></td>
					  				</tr>
					  				<tr>
					  					<td><label style="width: 160px; display: inline; padding-right: 6px;">Số trang hiển thị:</label></td>
					  					<td><input id="edit1" maxlength="5" style="width: 220px; height: 30px; display: inline;" type="text" name="change-pagin" value="${numOfPagin}" placeholder="${numOfPagin}" onkeydown="return isNumber(event);"></input></td>
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
								<c:forEach var="Questionmanagement" items="${replylust}" >
									<div class="check"><input id="${Questionmanagement.ID}" name="check_${Questionmanagement.ID}" type="checkbox" value="${Questionmanagement.ID}" AUTOCOMPLETE=OFF /></div>
									<a href="${pageContext.request.contextPath}/dsdatraloi?topic=${Questionmanagement.ID}&page=${curentOfPage}" style ="text-decoration: none;">
										<div class="list-question" id="div_${Questionmanagement.ID}">
											<div class="row1">	
												<div class="list-email">${Questionmanagement.questionEmail}</div>
												<div class="list-date">
													<fmt:formatDate value="${Questionmanagement.questionDate}"  pattern="dd/MM/yyyy HH:mm" />
												</div>
											</div>											
											<div class="row3">
												<c:set var="string1" value="${Questionmanagement.question}"/>
												${fn:substring(string1,0, 55)}
											</div>
										</div>
									</a>
								</c:forEach>							
							</div>
						</div>
						<!-- end load list of question -->
						
						<!-- Paging -->
						<div id="paginator"></div>
						<!-- end paging -->
					</td>
				
					<!-- Detail of question is replied -->
					<td style="background-color:#f5f3f3; width: auto; height: 100%" valign="top">	
						<form:form method="post" action="dsdatraloi?page=${curentOfPage}" commandName="questionmanagements">
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
							<c:if test="${not empty questionmanagements.question}">
								<div id="questionarea">
								
									<!-- Question info -->
									<div id="question-info">
										<!-- Email of questioner -->
										<span style="float: left;">Người hỏi: </span>
										<span style="float: left; margin-left: 5px;">${questionmanagements.questionBy} &lt;${questionmanagements.questionEmail}&gt;</span>
									</div>
									
									<div id="question-info">
										<!-- Question date -->
										<span style="float: left;">Ngày nhận: </span>
										<span style="float: left; margin-left: 5px;">
											<fmt:formatDate value="${questionmanagements.questionDate}"  pattern="dd/MM/yyyy HH:mm" />
										</span>
										
									</div>
									
									<!-- Question date info -->
									<div id="question-dateinfo">
										<!-- Answer by -->
										<span style="float: left;">Người trả lời: </span>
										<form:label path="AnswerBy" style="float: left; margin-left: 5px;">${username}</form:label>
									</div>
									<div id="question-dateinfo">
										<!-- Answer date -->
										<span style="float: left;">Ngày trả lời: </span>
										<form:label path="AnwserDate" style="float: left;  margin-left: 5px;">
											<fmt:formatDate value="${questionmanagements.anwserDate}"  pattern="dd/MM/yyyy HH:mm" />
										</form:label>
									</div>
																		
									<!-- Question content -->
									<div id="question-content" style ="overflow-x: auto; height: 100px;">${questionmanagements.question}</div>
									<!-- end question content -->
									
								</div>
								<div id="answerarea">
									<span id="answer">TRẢ LỜI:</span>
									<div id="answer-content" style ="overflow: auto; height: 300px;">
										<form:label path="Answer">${questionmanagements.answer}</form:label>
									</div>
								</div>
								
								<!-- button -->
								<div style="width: 100%;height:35px;padding-top: 20px;">
									<button class="buttoncontrol" id="input" type="submit"  name="actionsubmit" value="dictionary">Đưa vào bộ từ điển</button>
									<button class="buttoncontrol" id="delete" type="submit"  name="actionsubmit" value="delete">Xóa</button>
								</div>	
							</c:if> 																
						</form:form>
					</td>	      		
					<!-- End view question detail -->
	        	</tr>
        	</table>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>
</body>
</html>
