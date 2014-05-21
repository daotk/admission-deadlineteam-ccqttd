
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
	<title>Tạo câu hỏi</title>
	<link href="./images/icon-browser.ico" rel="shortcut icon" type="image/x-icon" />  
	<link href="css/stylesheet1.css" rel="stylesheet" />
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="images/bootstrap/css/jquery.msgbox.css"/>
	
	<script src="js/jquery.min.js"></script>
	<script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="js/bootstrap.min.js" type="text/javascript"></script>
	<script src="js/tab-nav.js"></script>
	<script src='js/popbox.js' type='text/javascript'></script>
	<script src="ckeditor/ckeditor.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="js/jquery.msgbox.js"></script>	
	<script>
	$(window).bind("load", function() {
		$('#loading').fadeOut(2000);
	});
	</script>
	<!-- LIMIT TExt filed to accept only numbers as input -->
</head>
<body>
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
        <div id="loading"> </div>
        <div class="body">
        	<!-- Create dictionary -->
			<div class="title_create_dictionary" style="height: 36px; background-color: #2c406e; float: left; width: 100%; font-size: 13pt; color: #e4e4e4; padding-left: 25px;padding-top: 15px; margin-top: 1px; margin-left: 1px;">
				Tạo câu hỏi
			</div>
			
        	<table>
        		<form:form method="post" action="" commandName="createQaA">
					<!-- Question content -->
					<div id="answer" style="width: 95%; margin: 80px 0 0 15px;">
						<span style="font-size: 10pt; font-weight: normal;">Nội dung câu hỏi:</span>
						<br>
						<div style="width: 100%; font-size: 10pt;">
							<form:textarea path="Question" cssClass="createQaA-question"></form:textarea>	
							<form:errors path="Question" cssClass="error" />
						</div>
					</div>
						
					<!-- Answer content -->
					<div id="answer" style="width: 95%; margin: 15px;">
						<span style="font-size: 10pt; font-weight: normal;">Nội dung câu trả lời:</span>
						<div style="width: 100%;">
							<form:textarea path="Anwser" style="width: 100%; margin: 0px;" cssClass="createQaA-answer" name="editor1" id="editor1" rows="10" cols="10"></form:textarea>
				            <script>
				            	// Replace the <textarea id="editor1"> with a CKEditor
				            	// instance, using default configuration.
				                CKEDITOR.replace( 'editor1' );
				                CKEDITOR.config.resize_enabled = false;
				            </script>
				           <form:errors path="Anwser" cssClass="error" />
						</div>
					</div>
					
					
					<!-- Buttons -->
					<div style="width: 100%;height:35px;padding-top: 20px;">	
						<button class="buttoncontrol" id="save" type="submit"  name="actionsubmit" value="save" style="margin-right: 3%;">Lưu</button>
						<p id="quotation" style="font: bold 12px 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif;"><p>
					</div>			      
				</form:form>
        	</table>
        	<!-- Question Title -->
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
      	</div>
    </tiles:putAttribute>
</tiles:insertDefinition>
</body>
</html>
