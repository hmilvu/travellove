$(document).ready(function(){
	
	//IE6，弹出提示下载框
	/*
	var isIE6=$.browser.msie && ($.browser.version == "6.0") && !$.support.style;
	var isIE7=$.browser.msie && ($.browser.version == "7.0");
	if (isIE6||isIE7) { 
		$.Zebra_Dialog('您的IE版本较低，可能影响使用，建议升级IE版本-<strong><a href="http://www.skycn.com/soft/appid/880.html" target="_blank">立即下载</a></strong>', {
		    'buttons':  false,
		    'modal': false,
		    'auto_close': 6000
		});
	}
	*/
	
	//session失效后，跳转至登录页
	var currentHref=window.location.href;
	if(currentHref.indexOf('#')>0){
		window.location.href=appPath;
	}
	
	//回车后登录
	document.onkeydown = function(e){ 
		var ev = document.all ? window.event : e; 
		if(ev.keyCode==13) { 
			login();
		} 
	};
	
});
//登录
function login() {
	var username=$("#username").val();
	if (username == "") {
		$("#msg").html("<font color='red'>账号不能为空！</font>");
		$('#username').focus();
		return;
	}
	
	var password=$("#password").val();
	if ($("#password").val() == "") {
		$("#msg").html("<font color='red'>密码不能为空！</font>");
		$('#password').focus();
		return;
	}
	
	var valificationCode=$("#valificationCode").val();
	if ($("#valificationCode").val() == "") {
		$("#msg").html("<font color='red'>验证码不能为空！</font>");
		$('#valificationCode').focus();
		return;
	}
	$.ajax({
		  type: "POST",
		  url: "admin/login!login.action",
		  data: "username="+username+"&password="+password+"&valificationCode="+valificationCode, 
		  dataType: "json",
		  success: function(data, textStatus){ 
				var tip="登录不成功！";
	        	switch(data.result){
				case "input":
					tip="<font color='red'>账号或密码错误！</font>";
					break;
				case "error":
					tip="<font color='red'>验证码错误！</font>";
					changeValidateCode($("#valificationCodeImg"));
					break;
				case "success":
					tip="正在进入旅游关爱管理系统，请稍候...";
					var	indexUrl="forward!dwz.action";
					var appPath ="/"+location.pathname.split("/")[1] +"/";
					window.location.href =appPath + indexUrl;
					break;
	        	}
				 $("#msg").html(tip);
		   },
		  beforeSend: function(formData, jqForm, options) {
			   $("#msg").html("正在登录，请稍候...");
		   },
		  async: true
		});
}
