<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="js/admin/multi-upload.js" type="text/javascript"></script>
<div class="pageContent">
	<form action="itemImageUpload.action" method="post" enctype="multipart/form-data" class="pageForm required-validate"  onsubmit="return iframeCallback(this, checkupload)">
		<input type="hidden" id="itemId" name="itemId" value="<s:property value='%{#request.editItem.id}'/>"/>
		<input type="hidden" id="imgId1" name="imgId1" value="<s:property value='%{#request.imgId1}'/>"/>
		<input type="hidden" id="imgId2" name="imgId2" value="<s:property value='%{#request.imgId2}'/>"/>
		<input type="hidden" id="imgId3" name="imgId3" value="<s:property value='%{#request.imgId3}'/>"/>
		<div class="panel" defH="370">
			<h1><s:property value='%{#request.editView.name}'/></h1>
			<div>
				<table border="0" width="100%">
					<tr height="50">
						<td>
							图片：<input type="file" name="upload1" id="upload1" size="30" onchange="javascript:previewImage(this, 'preview1', 'imghead1');"/>	
						</td>
						<td>
							图片：<input type="file" name="upload2" id="upload2" size="30" onchange="javascript:previewImage(this, 'preview2', 'imghead2');"/>
						</td>
						<td>
							图片：<input type="file" name="upload3" id="upload3" size="30" onchange="javascript:previewImage(this, 'preview3', 'imghead3');"/>
						</td>
					</tr>
					<tr height="250">
						<td>
							<div id="preview1">  
							    <img id="imghead1" name="imghead1" width="200" height="200" border="0" src="<s:property value='%{#request.imageName1}'/>"/>  
							</div> 
						</td>
						<td>
							<div id="preview2">  
							    <img id="imghead2" name="imghead2" width="200" height="200" border="0" src="<s:property value='%{#request.imageName2}'/>"/>  
							</div> 
						</td>
						<td>
							<div id="preview3">  
							    <img id="imghead3" name="imghead3" width="200" height="200" border="0" src="<s:property value='%{#request.imageName3}'/>"/>  
							</div> 
						</td>
					</tr>
					<tr height="40">
						<td>
						<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="deleteImg(1);">删除</button></div></div>
						</td>
						<td>
						<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="deleteImg(2);">删除</button></div></div>
						</td>
						<td>
						<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="deleteImg(3);">删除</button></div></div>
						</td>
					</tr>
				</table>
			</div>
		</div>		
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>		
