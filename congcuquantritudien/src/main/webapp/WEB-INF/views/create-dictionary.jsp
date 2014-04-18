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
	
	<script src="js/jquery.min.js"></script>
	<script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="js/bootstrap.min.js" type="text/javascript"></script>
	<script src="js/tab-nav.js"></script>
	<script src='js/popbox.js' type='text/javascript'></script>

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
				            <form:errors path="Anwser" cssClass="error" />
				            <script>
				            	// Replace the <textarea id="editor1"> with a CKEditor
				            	// instance, using default configuration.
				                CKEDITOR.replace( 'editor1' );
				                CKEDITOR.config.resize_enabled = false;
				            </script>
						</div>
					</div>
					
					
					<!-- Buttons -->
					<div style="width: 100%;height:35px;padding-top: 20px;">	
						<button class="buttoncontrol" id="save" type="submit"  name="actionsubmit" value="save" style="margin-right: 40px;">LƯU</button>
						<p id="quotation">${message}<p>
					</div>			      
				</form:form>
        	</table>
        	<!-- Question Title -->
			
      	</div>
    </tiles:putAttribute>
</tiles:insertDefinition>
</body>
</html>
