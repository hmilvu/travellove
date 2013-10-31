<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>旅游关爱管理系统</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" /> 
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" /> 
<link href="themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
<!--[if IE]>
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<!--[if lte IE 9]>
<script src="js/speedup.js" type="text/javascript"></script>
<![endif]-->

<script src="js/jquery-1.7.2.js" type="text/javascript"></script>
<script src="js/jquery.cookie.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script src="js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="xheditor/xheditor-1.2.1.min.js" type="text/javascript"></script>
<script src="xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
<script src="uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>

<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
<script src="js/dwz.core.js" type="text/javascript"></script>
<script src="js/dwz.util.date.js" type="text/javascript"></script>
<script src="js/dwz.validate.method.js" type="text/javascript"></script>
<script src="js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="js/dwz.barDrag.js" type="text/javascript"></script>
<script src="js/dwz.drag.js" type="text/javascript"></script>
<script src="js/dwz.tree.js" type="text/javascript"></script>
<script src="js/dwz.accordion.js" type="text/javascript"></script>
<script src="js/dwz.ui.js" type="text/javascript"></script>
<script src="js/dwz.theme.js" type="text/javascript"></script>
<script src="js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="js/dwz.navTab.js" type="text/javascript"></script>
<script src="js/dwz.tab.js" type="text/javascript"></script>
<script src="js/dwz.resize.js" type="text/javascript"></script>
<script src="js/dwz.dialog.js" type="text/javascript"></script>
<script src="js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="js/dwz.cssTable.js" type="text/javascript"></script>
<script src="js/dwz.stable.js" type="text/javascript"></script>
<script src="js/dwz.taskBar.js" type="text/javascript"></script>
<script src="js/dwz.ajax.js" type="text/javascript"></script>
<script src="js/dwz.pagination.js" type="text/javascript"></script>
<script src="js/dwz.database.js" type="text/javascript"></script>
<script src="js/dwz.datepicker.js" type="text/javascript"></script>
<script src="js/dwz.effects.js" type="text/javascript"></script>
<script src="js/dwz.panel.js" type="text/javascript"></script>
<script src="js/dwz.checkbox.js" type="text/javascript"></script>
<script src="js/dwz.history.js" type="text/javascript"></script>
<script src="js/dwz.combox.js" type="text/javascript"></script>
<script src="js/dwz.print.js" type="text/javascript"></script>
<!--
<script src="bin/dwz.min.js" type="text/javascript"></script>
-->
<script src="js/dwz.regional.zh.js" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
	DWZ.init("js/dwz.frag.xml", {
		loginUrl:"login.jsp",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"themes"}); // themeBase 相对于index页面的主题base路径
		}
	});
});
</script>
</head>

<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="http://j-ui.com">标志</a>
				<ul class="nav">					
					<li><a href="changepwd.html" target="dialog" width="600">设置</a></li>
					<li><a href="http://www.cnblogs.com/dwzjs" target="_blank">用户名</a></li>
					<li><a href="login!logOut.action">退出</a></li>
				</ul>				
			</div>

			<!-- navMenu -->
			
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>

				<div class="accordion" fillSpace="sidebar">
					<div class="accordionHeader">
						<h2><span>Folder</span>旅游关爱管理系统</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="tabsPage.html" target="navTab">主框架面板</a>
								<ul>
									<li><a href="admin/travel-inf!list.action" target="navTab" rel="travel">旅行社管理</a></li>
									<li><a href="http://www.baidu.com" target="navTab" rel="page1">页面一(外部页面)</a></li>
									<li><a href="demo_page2.html" target="navTab" rel="external" external="true">iframe navTab页面</a></li>
									<li><a href="demo_page1.html" target="navTab" rel="page1" fresh="false">替换页面一</a></li>
									<li><a href="demo_page2.html" target="navTab" rel="page2">页面二</a></li>
									<li><a href="demo_page4.html" target="navTab" rel="page3" title="页面三（自定义标签名）">页面三</a></li>
									<li><a href="demo_page4.html" target="navTab" rel="page4" fresh="false">测试页面（fresh="false"）</a></li>
									<li><a href="w_editor.html" target="navTab">表单提交会话超时</a></li>
									<li><a href="demo/common/ajaxTimeout.html" target="navTab">navTab会话超时</a></li>
									<li><a href="demo/common/ajaxTimeout.html" target="dialog">dialog会话超时</a></li>
									<li><a href="index_menu.html" target="_blank">横向导航条</a></li>
								</ul>
							</li>
							
							<li><a>常用组件</a>
								<ul>
									<li><a href="w_panel.html" target="navTab" rel="w_panel">面板</a></li>
									<li><a href="w_tabs.html" target="navTab" rel="w_tabs">选项卡面板</a></li>
									<li><a href="w_dialog.html" target="navTab" rel="w_dialog">弹出窗口</a></li>
									<li><a href="w_alert.html" target="navTab" rel="w_alert">提示窗口</a></li>
									<li><a href="w_list.html" target="navTab" rel="w_list">CSS表格容器</a></li>
									<li><a href="demo_page1.html" target="navTab" rel="w_table">表格容器</a></li>
									<li><a href="w_removeSelected.html" target="navTab" rel="w_table">表格数据库排序+批量删除</a></li>
									<li><a href="w_tree.html" target="navTab" rel="w_tree">树形菜单</a></li>
									<li><a href="w_accordion.html" target="navTab" rel="w_accordion">滑动菜单</a></li>
									<li><a href="w_editor.html" target="navTab" rel="w_editor">编辑器</a></li>
									<li><a href="w_datepicker.html" target="navTab" rel="w_datepicker">日期控件</a></li>
									<li><a href="demo/database/db_widget.html" target="navTab" rel="db">suggest+lookup+主从结构</a></li>
									<li><a href="demo/database/treeBringBack.html" target="navTab" rel="db">tree查找带回</a></li>
									<li><a href="demo/sortDrag/1.html" target="navTab" rel="sortDrag">单个sortDrag示例</a></li>
									<li><a href="demo/sortDrag/2.html" target="navTab" rel="sortDrag">多个sortDrag示例</a></li>
								</ul>
							</li>
									
							<li><a>表单组件</a>
								<ul>
									<li><a href="w_validation.html" target="navTab" rel="w_validation">表单验证</a></li>
									<li><a href="w_button.html" target="navTab" rel="w_button">按钮</a></li>
									<li><a href="w_textInput.html" target="navTab" rel="w_textInput">文本框/文本域</a></li>
									<li><a href="w_combox.html" target="navTab" rel="w_combox">下拉菜单</a></li>
									<li><a href="w_checkbox.html" target="navTab" rel="w_checkbox">多选框/单选框</a></li>
									<li><a href="demo_upload.html" target="navTab" rel="demo_upload">iframeCallback表单提交</a></li>
									<li><a href="w_uploadify.html" target="navTab" rel="w_uploadify">uploadify多文件上传</a></li>
								</ul>
							</li>
							<li><a>组合应用</a>
								<ul>
									<li><a href="demo/pagination/layout1.html" target="navTab" rel="pagination1">局部刷新分页1</a></li>
									<li><a href="demo/pagination/layout2.html" target="navTab" rel="pagination2">局部刷新分页2</a></li>
								</ul>
							</li>
							<li><a>系统管理</a>
								<ul>
									<li><a href="admin/role-inf!list.action" target="navTab" rel="role">角色管理</a></li>									
								</ul>
							</li>							
						</ul>
					</div>				
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="accountInfo">							
						</div>
						<div class="pageFormContent" layoutH="80" style="margin-right:230px">							
						</div>						
						<div style="width:230px;position: absolute;top:60px;right:0" layoutH="80">
						</div>
					</div>
					
				</div>
			</div>
		</div>

	</div>

	<div id="footer">Copyright &copy; 2010 旅游关爱</div>

</body>
</html>