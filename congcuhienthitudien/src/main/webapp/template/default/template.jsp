<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<link href="template/default/css/style.css" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
    <div class="page" style="width:100%; background-image:url('./images/images.jpg')">
        <tiles:insertAttribute name="header" />
        <div class="content">
            <tiles:insertAttribute name="body" />
        </div>
        <tiles:insertAttribute name="footer" />
    </div>
</body>
</html>