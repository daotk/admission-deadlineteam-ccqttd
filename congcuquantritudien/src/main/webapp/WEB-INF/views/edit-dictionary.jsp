<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<title>Home</title>
	<link href="css/stylesheet1.css" rel="stylesheet" />
	<script src="ckeditor/ckeditor.js"></script>
</head>
<body>
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
        <form:form method="post" action="" commandName="createQaA">
	        <div class="body">
				<!-- Question Title -->
				<div id="answer" style="width: 95%; margin: 15px;">
				
					<span style="font-size: 15px;">Tiêu đề câu hỏi:</span>
					<div style="width: 100%;">
						<form:textarea path="Title" cssClass="createQaA-title"></form:textarea>	
						 <form:errors path="Title" cssClass="error" />
					</div>
				</div>
				<!-- Question content -->
				<div id="answer" style="width: 95%; margin: 15px;">
					<span style="font-size: 15px;">Nội dung câu hỏi:</span>
					</br>
					<div style="width: 100%;">
						<form:textarea path="Question" cssClass="createQaA-question"></form:textarea>	
						 <form:errors path="Question" cssClass="error" />
					</div>
				</div>	
				<!-- Anwser content -->
				<div id="answer" style="width: 95%; margin: 15px;">
					<span style="font-size: 15px;">Nội dung câu trả lời:</span>
					<div style="width: 100%;">
						<form:textarea path="Anwser" cssClass="createQaA-answer" name="editor1" id="editor1" rows="5" cols="10"></form:textarea>
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
				
	        </div>
 	</form:form>
    </tiles:putAttribute>
</tiles:insertDefinition>
</body>
</html>