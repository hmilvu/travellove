<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="pageContent">
	<form method="post" action="admin/role-inf!create.action" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>角色名称：</label>
				<input name="name" id="name" class="required" type="text" size="30" value="" alt="请输入角色名称"/>
			</p>
			<p>
				<label>角色描述：</label>
				<textarea name="description" id="description" style="width:85%;height:120px"></textarea>
			</p>
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
			