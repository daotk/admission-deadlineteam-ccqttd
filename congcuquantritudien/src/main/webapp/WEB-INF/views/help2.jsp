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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="./images/icon-browser.ico" rel="shortcut icon" type="image/x-icon" />  
	<title>Trợ giúp</title>
	
	<!-- CSS -->
	<link href="css/stylesheet1.css" rel="stylesheet" />
	<link rel="stylesheet" href="css/bootstrap.css"/>
	
	<!-- Java Script -->
	<script src='js/jquery.min.js' type='text/javascript'></script>
	
	<!-- CK Editor -->
	<script src="ckeditor/ckeditor.js" type="text/javascript" charset="utf-8"></script>
	
	<!-- Paging -->
    <script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
	<script src="js/bootstrap-paginator.js"></script>
		
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
	<script>
	 function isNumber(evt)
     {
        var charCode = (evt.which) ? evt.which : event.keyCode
        if (charCode > 31 && (charCode < 48 || charCode > 57))
           return false;

        return true;
     }
	</script>
<script> 
$(document).ready(function(){
  $(".list-question1").click(function(){
    $(".answer1").slideToggle("slow");
  });
  $(".list-question2").click(function(){
	$(".answer2").slideToggle("slow");
  });
  $(".list-question3").click(function(){
	$(".answer3").slideToggle("slow");
	});
  $(".list-question4").click(function(){
	$(".answer4").slideToggle("slow");
	});
  $(".list-question5").click(function(){
	$(".answer5").slideToggle("slow");
	});
  $(".list-question6").click(function(){
	$(".answer6").slideToggle("slow");
	});
});
</script>
</head>
<body>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
    	<div id="loading"> </div>
        <div class="body">
        	<table style="height: 100%;width: 100%;border-collapse: collapse;">
        		<tr style="height: 100%;width: 100%">
					<td style="background-color: #ffffff;width: 30%; height:100%" valign="top">
						<div id="second-sidebar">
										  			
				  			<!-- tab of dictionary -->
						  	<ul class="tabbed" data-persist="true" style="width: 342px;">
					            <li><a href="trogiup">Quản lý chung</a></li>
					            <li><a href="tienich"  class="active">Tiện ích</a></li>
					            <li><a href="nguoidung">Người dùng</a></li>
				       		 </ul>
				       		 
						    <!-- load list of question -->
							<div class="list-question-content" style="height: 555px;overflow: scroll;overflow-x: hidden;">
								<div style="width: 97%; float: left;">
									<div class="list-question1" style="width: 98%; float: left; margin-left: 2%;">										
										<div class="row3" style="background-color: #F1F1F1;">
											Sử dụng chức năng tạo câu hỏi.				
										</div>						
									</div>
									<div class="answer1" style="font-size: 12px; margin-top: 5px; margin-left: 25px; text-align: justify; width: 95%;display:none; color: black;">
										<span style="font-weight: bold;">Trả lời</span><BR />
											<ul>
												<li style="margin-left: 3%;">
													<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
														Đăng nhập vào <span style="font: bold 12px 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif;">CÔNG CỤ QUẢN TRỊ BỘ TỪ ĐIỂN</span>
													</span>
												</li>
												<li style="margin-left: 3%;">
													<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
														Chọn <span style="font: bold 12px 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif;">TẠO CÂU HỎI</span>
													</span>
												</li>
												<li style="margin-left: 3%;">
												<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
													Nhập nội dung câu hỏi và nội dung câu trả lời mà bạn muốn vào khung câu hỏi và câu trả lời.
												</span>
												</li>
												<li style="margin-left: 3%;">
												<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
													Chọn Lưu nếu bạn muốn lưu nội dung câu hỏi đã nhập. Sau đó câu hỏi và nội dung trả lời sẽ được lưu lại và chuyển đến danh sách <span style="font: bold 12px 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif;">CÓ SẴN</span> trong bộ từ điển.
												</span>
												</li>
												<li style="margin-left: 3%;">
												<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
													Chọn vào giao diện khác nếu không muốn lưu thông tin câu hỏi
												</span>
												
											</ul>
									</div>
									
									<div class="list-question2" style="width: 98%; float: left; margin-left: 2%;">
										<div class="row3" style="background-color: #F1F1F1;">
											Sử dụng chức năng tạo index.
										</div>
									</div>
									<div class="answer2" style="font-size: 12px; margin-top: 5px; margin-left: 25px; text-align: justify; width: 95%;display:none; color: black;">
										<span style="font-weight: bold;">Trả lời</span><BR />
											<ul>
												<li style="margin-left: 3%;">
													<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
														Đăng nhập vào <span style="font: bold 12px 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif;">CÔNG CỤ QUẢN TRỊ BỘ TỪ ĐIỂN</span>
													</span>
												</li>
												<li style="margin-left: 3%;">
													<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
														Chọn <span style="font: bold 12px 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif;">TẠO INDEX</span> để cập nhật lại index.
													</span>
												</li>
											</ul>
									</div>

									<div class="list-question3" style="width: 98%; float: left; margin-left: 2%;">
										<div class="row3" style="background-color: #F1F1F1;">
											Sử dụng chức năng cấu hình > cấu hình người dùng.
										</div>
									</div>
									<div class="answer3" style="font-size: 12px; margin-top: 5px; margin-left: 25px; text-align: justify; width: 95%;display:none; color: black;">
										<span style="font-weight: bold;">Trả lời</span><BR />
											<ul>
												<li style="margin-left: 3%;">
													<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
														Đăng nhập vào <span style="font: bold 12px 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif;">CÔNG CỤ QUẢN TRỊ BỘ TỪ ĐIỂN</span>
													</span>
												</li>
												<li style="margin-left: 3%;">
													<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
														Chọn <span style="font: bold 12px 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif;">CẤU HÌNH</span>
													</span>
												</li>
												<li style="margin-left: 3%;">
													<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
														Chọn <span style="font: bold 12px 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif;">CẤU HÌNH NGƯỜI DÙNG</span>
													</span>
												</li>
												<li style="margin-left: 3%;">
												<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
													Chọn <img src="images/pen.png" /> để hiển thị thông tin người  dùng
												</span>
												</li>
												<li style="margin-left: 3%;">
												<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
													Cập nhật Quyền của người dùng nếu cần
												</span>
												</li>
												<li style="margin-left: 3%;">
												<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
													Chọn Lưu để lưu lại những cập nhật.
												</span>
												</li>
											</ul>
									</div>
									
									<div class="list-question4" style="width: 98%; float: left; margin-left: 2%;">
										<div class="row3" style="background-color: #F1F1F1;">
											Sử dụng chức năng cấu hình > cấu hình hệ thống.
										</div>
									</div>
									<div class="answer4" style="font-size: 12px; margin-top: 5px; margin-left: 25px; text-align: justify; width: 95%;display:none; color: black;">
										<span style="font-weight: bold;">Trả lời</span><BR />
											<ul>
												<li style="margin-left: 3%;">
													<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
														Đăng nhập vào <span style="font: bold 12px 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif;">CÔNG CỤ QUẢN TRỊ BỘ TỪ ĐIỂN</span>
													</span>
												</li>
												<li style="margin-left: 3%;">
													<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
														Chọn <span style="font: bold 12px 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif;">CẤU HÌNH HỆ THỐNG</span>
													</span>
												</li>
												<li style="margin-left: 3%;">
												<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
													Cập nhật thông tin hệ thống
												</span>
												</li>
												<li style="margin-left: 3%;">
												<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
													Chọn Lưu để lưu lại thông tin cấu hình hệ thống
												</span>
												</li>
											</ul>
									</div>
									
									<div class="list-question5" style="width: 98%; float: left; margin-left: 2%;">
										<div class="row3" style="background-color: #F1F1F1;">
											Sử dụng chức năng cấu hình > cấu hình email.
										</div>
									</div>
									<div class="answer5" style="font-size: 12px; margin-top: 5px; margin-left: 25px; text-align: justify; width: 95%;display:none; color: black;">
										<span style="font-weight: bold;">Trả lời</span><BR />
											<ul>
												<li style="margin-left: 3%;">
													<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
														Đăng nhập vào <span style="font: bold 12px 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif;">CÔNG CỤ QUẢN TRỊ BỘ TỪ ĐIỂN</span>
													</span>
												</li>
												<li style="margin-left: 3%;">
													<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
														Chọn <span style="font: bold 12px 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif;">CẤU HÌNH EMAIL</span>
													</span>
												</li>
												<li style="margin-left: 3%;">
													<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
														Cập nhật thông tin cấu hình email.
													</span>
												</li>
												<li style="margin-left: 3%;">
												<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
													Chọn Lưu để lưu lại thông tin cấu hình email.
												</span>
												</li>
											</ul>
									</div>
									
									<div class="list-question6" style="width: 98%; float: left; margin-left: 2%;">
										<div class="row3" style="background-color: #F1F1F1;">
											Sử dụng chức năng trợ giúp.
										</div>
									</div>
									<div class="answer6" style="font-size: 12px; margin-top: 5px; margin-left: 25px; text-align: justify; width: 95%;display:none; color: black;">
										<span style="font-weight: bold;">Trả lời</span><BR />
											<ul>
												<li style="margin-left: 3%;">
													<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
														Đăng nhập vào <span style="font: bold 12px 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif;">CÔNG CỤ QUẢN TRỊ BỘ TỪ ĐIỂN</span>
													</span>
												</li>
												<li style="margin-left: 3%;">
													<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
														Chọn <span style="font: bold 12px 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif;">TRỢ GIÚP</span>
													</span>
												</li>
												<li style="margin-left: 3%;">
													<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
														Chọn <span style="font: bold 12px 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif;">QUẢN LÝ CHUNG</span> để tìm hiểu thông tin các mục trong phần quản lý chung trên thanh menu.
													</span>
												</li>
												<li style="margin-left: 3%;">
												<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
													Chọn <span style="font: bold 12px 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif;">TIỆN ÍCH</span> để tìm hiểu thông tin các mục trong phần tiện ích trên thanh menu.
												</span>
												</li>
												<li style="margin-left: 3%;">
												<SPAN style="font-size: 12px; font-family: Arial;text-align: justify;">
													Chọn <span style="font: bold 12px 'Segoe UI WPC','Segoe UI',Tahoma,'Microsoft Sans Serif',Verdana,sans-serif;">NGƯỜI DÙNG</span> để tìm hiểu thông tin các mục trong phần người dùng trên thanh menu.
												</span>
												</li>
											</ul>
									</div>
									
								</div>
							</div>
							<!-- end load list of question -->
						</div>
						
					</td>	
					
				</tr>
        	</table>         
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>
</body>
</html>
