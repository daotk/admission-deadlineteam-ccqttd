<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="menu1" id="idmenu1">  
	<div id="button1" style="height: 100%;width:100%; float: left;"  onclick="return hideMultiple();">
		<div  class="menu_button" style="margin-top:250px; margin-left:3px; height:100%; width: 100%;height: 70px ;background-image: url(template/default/images/close.png); background-repeat:no-repeat;vertical-align:middle"></div>
	</div>
	<div id="button2" style="height: 100%;width:100%; display: none;float: left;" onclick="return showMultiple();">
		<div  class="menu_button" style="margin-top:250px; margin-left:3px; height:100%;  width: 100%;height: 70px ;background-image: url(template/default/images/open.png); background-repeat:no-repeat;vertical-align:middle ;" ></div>
	</div>
</div>
<script>
        function hideMultiple(){
            $('#idmenu').hide();
            $('#button1').hide();
            $('#button2').show();
            $.cookie('idmenu', 'hide', { expires: 1 });
            return false;
        }
        function showMultiple(){
            $('#idmenu').show();
            $('#button1').show();
            $('#button2').hide(); 
            $.cookie('idmenu', 'open', { expires: 1 });
            return false;
        }
</script>
