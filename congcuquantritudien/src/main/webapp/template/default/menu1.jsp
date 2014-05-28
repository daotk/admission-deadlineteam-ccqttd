<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="menu1" id="idmenu1">  
	<div style="border-bottom: 1px solid #546181;background-color: #122144;">
		<div  onclick="return showMultiple();" class="menu_button" style="width: 100%;height: 70px ;background-image: url(template/default/images/menu_button.png); vertical-align:middle"></div>
	</div>
</div>
<script>
        function showMultiple(){
            $('#idmenu').show();
            $('#idmenu1').hide();
            $.cookie('button-menu', 'menu', { expires: 1 });
            return false;
        }
        function hideMultiple(){
            $('#idmenu1').show();
            $('#idmenu').hide();
            $.cookie('button-menu', 'menu1', { expires: 1 });
            return false;
        }
</script>
