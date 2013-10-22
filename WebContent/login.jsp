<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%@include file="noCache.jsp" %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>o2o管理平台</title>

<link rel="icon" href="favicon.ico" type="image/x-icon" /> 
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />  

<link rel="stylesheet" type="text/css" href="lib/zebra/css/zebra_dialog.css" />
<link rel="stylesheet" type="text/css" href="lib/dwz/themes/css/login.css"  />

<script type="text/javascript" src="lib/dwz/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="lib/cookies/jquery.cookie.js"></script>
<script type="text/javascript" src="lib/zebra/zebra_dialog.js"></script>

<script src="js/base.js" type="text/javascript"></script>
<script type="text/javascript" src="js/login.js"></script>

</head>
<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<a href="#"><img src="lib/dwz/themes/default/images/login_logo.jpg" /></a>
			</h1>
			<div class="login_headerContent">
				<div class="navList">
					<ul>
						<!-- 
						<li><a href="#">设为首页</a></li>
						<li><a href="#">反馈</a></li>
						<li><a href="#" target="_blank">帮助</a></li>
						 -->
					</ul>
				</div>
				<h2 class="login_title"><img src="lib/dwz/themes/default/images/login_title.png" /></h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm">
				<form action="index.html">
					<p>
						<label>账号：</label>
						<input type="text" name="account" id="account" size="20" class="login_input" />
					</p>
					<p>
						<label>密码：</label>
						<input type="password" name="password" id="password" size="20" class="login_input" />
					</p>
					<p>
						<label>验证码：</label>
						<input class="login_input" type="text" size="20" id="valificationCode" />					
					</p>
					<p>
						<span  style="cursor:pointer" onclick="changeValidateCode($('#valificationCodeImg'))">
						<img id="valificationCodeImg"  src="valificationCode.action" alt="验证码" />&nbsp;看不清，换一张</span>
					</p>
					<p>
						 <input id="rememberMe" type="checkbox"/>&nbsp;记住我一周
					</p>
					<div class="login_bar">
						<input class="sub" type="button" value=""  onclick="login();"/>
					</div>
					<p></p>
					<span id="msg"></span>
				</form>
			</div>
			<div class="login_banner"><img src="lib/dwz/themes/default/images/login_banner.png" /></div>
			<div class="login_main">
				<div class="login_inner">
					<p>o2o管理平台，专业化服务管理平台</p>
					<p>订单及时分派，灵活快速</p>
					<p>服务全程掌握，高效管理</p>
				</div>
			</div>
		</div>
		<div id="login_footer">
			技术支持：福建邮科
		</div>
	</div>
</body>
</html>