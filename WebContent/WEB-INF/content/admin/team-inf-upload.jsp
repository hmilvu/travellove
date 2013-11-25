<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type = "text/javascript">
function checkupload(){
	var iframe = $("#callbackframe");
	var doc = iframe[0].contentDocument || iframe[0].content;
	var response = $(doc).find("body").text();;
	response = jQuery.parseJSON(response);
	if(response.code == "0"){
		navTabAjaxDone({"statusCode":"200", "message":"全部导入成功", "navTabId":"团员管理", "forwardUrl":"", "callbackType":"", "rel":""});
		$.pdialog.closeCurrent();	
	} else {
		alertMsg.error('部分游客导入错误！行号：' + response.msg);
		$.pdialog.closeCurrent();
	}
}
</script>

<h2 class="contentTitle">请选择需要上传的文件</h2>
<form action="teamMemberUpload.action" method="post" enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return iframeCallback(this, checkupload)">
<input type="hidden" name="teamId" value="<s:property value='%{#request.teamId}'/>" />
<div class="pageContent">
	<div class="pageFormContent" layoutH="97">
		<dl>
			<dt>附件：</dt><dd><input type="file" name="upload" class="required" size="30" /></dd>
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
			