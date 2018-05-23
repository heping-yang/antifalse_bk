<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>平台公告</title>
		<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0, user-scalable=0">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no, email=no">
		<!--清理缓存解决方案：在调试阶段或者频繁更新的页面加入以下头信息-->
		<!--<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />-->
		<!--强制竖屏-->
		<meta name="screen-orientation" content="portrait">
		<!-- QQ浏览器强制竖屏-->
		<meta name="x5-orientation" content="portrait">
		<!-- UC浏览器强制全屏-->
		<!--<meta name="full-screen" content="yes">-->
		<!-- QQ浏览器强制全屏-->
		<!--<meta name="x5-fullscreen" content="true">-->
		<!-- UC浏览器应用模式-->
		<meta name="browsermode" content="application">
		<!-- QQ浏览器应用模式-->
		<!--<meta name="x5-page-mode" content="app">--> 
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/cssreset.css">
	</head>

	<body>
		<div class="page">
			  <div class="notice_show">
			  		 <h3>${announcement.title}</h3>
			  		 <p class="notice_date">${announcement.addtime}</p>
			  		 <img class="notice_img" src="${pageContext.request.contextPath}/resources/images/notice.jpg"/>
			  		 <div class="notice_cont">
						${announcement.content}
			  		 </div>
			  </div>
			  <div class="bottom">
			  	宁夏银桥教育培训中心
			  </div>
		</div>
		<!--page-->
		
		 
		<script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script> 
		<script src="${pageContext.request.contextPath}/resources/js/jquery.tap.min.js" type="text/javascript" charset="utf-8"></script>

		<script type="text/javascript">
			$(document).ready(function() {
				 
			})
		</script>
	</body>

</html>