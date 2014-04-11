<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <style type="text/css">
    body {
    	font-family: Segoe UI,Tahoma,Arial,Verdana;
    	font-size: 12px;
        margin:0px;
        padding:0px;
        height:100%;

        overflow:hidden;
    }
    .page {
    	width:98%;
    	min-width:600px;
    	margin: 0 auto;
        min-height:100%;
        position:relative;
       
    }
    .header {
    	height:4%;
    	min-height: 20px;
    	background: #2c406e;
        width:100%;
  		color: #fff;
    }
     
    .content {
    		height:92.5%;
            padding-bottom:20px; /* Height of the footer element */
            overflow:hidden;
            width: 100%;
    }
     
    .menu {
    	background-color:#2c406e;
        width:180px;
        float:left;  
		color: white;
		height:100%;
    }
 
    .body {
        margin:0px 0px 0px 180px;
    }
     
    .footer {
        clear:both;
        position:absolute;
        bottom:0;
        left:0;
        width:100%;
        height:3%;
        min-height:20px;      
        color: #ffffff;
		background: #2c406e;
  		border-top: 5px solid #2c406e;
  		font-size: 7.5pt;
    }  
    </style>
    <link href="template/default/css/styles.css" rel="stylesheet" />
	<script type="text/javascript" src="template/default/js/default.min.js"></script>
</head>
<body>
    <div class="page">
        <tiles:insertAttribute name="header" />
        <div class="content">
            <tiles:insertAttribute name="menu" />
            <tiles:insertAttribute name="body" />
        </div>
        <tiles:insertAttribute name="footer" />
    </div>
</body>
</html>