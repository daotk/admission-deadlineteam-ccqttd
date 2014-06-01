<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="menu1" id="idmenu1">  
	<div style="border-bottom: 1px solid #546181;background-color: #122144;">
		<div id="button1"  onclick="return hideMultiple();" class="menu_button" style="width: 100%;height: 70px ;background-image: url(template/default/images/menu_button.png); vertical-align:middle"></div>
		<div id="button2"  onclick="return showMultiple();" class="menu_button" style="width: 100%;height: 70px ;background-image: url(template/default/images/menu_button.png); vertical-align:middle ; display: none;"></div>
	</div>
</div>
<script>
        function hideMultiple(){
            $('#idmenu').hide();
            $('#button1').hide();
            $('#button2').show(); 
            return false;
        }
        function showMultiple(){
            $('#idmenu').show();
            $('#button1').show();
            $('#button2').hide();  
            return false;
        }
</script>
