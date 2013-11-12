<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<h2 class="contentTitle">请选择需要上传的图片</h2>

<form action="admin/member-inf!uploadProfile.action" method="post" enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return iframeCallback(this, $.bringBack)">
<input type="hidden" id="memberId" name="memberId" value='<s:property value="%{#request.memberId}"/>'/>
<div class="pageContent">
	<div class="pageFormContent" layoutH="97">
		<dl>
			<img src="images/member/<s:property value='%{#request.avatarUrl}'/>" width="175" height="275"/>
		</dl>
		<dl>
			<dt>头像文件：</dt><dd><input type="file" name="image" class="required" size="30" /></dd>
		</dl>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">上传</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>
		</ul>
	</div>
</div>
</form>
