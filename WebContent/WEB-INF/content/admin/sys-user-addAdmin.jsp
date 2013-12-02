<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
function save(){
	var $form = $($("#userForm"));

	if (!$form.valid()) {
		return false;
	}
	$.ajax({
			type:'POST',
			url:'admin/sys-user!createAdmin.action',
			data:$("#userForm").formSerialize(),//序列化表单里所有的内容
			success: function(data){				
				switch(data.result){
						case "success":	
							navTabAjaxDone({"statusCode":"200", "message":"保存成功", "navTabId":"用户管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});	
							break;
						case "input":	
							navTabAjaxDone({"statusCode":"300", "message":"登陆名已存在，请修改后重试"});	
							break;
						default:
							navTabAjaxDone({"statusCode":"300", "message":"保存失败，请重试", "navTabId":"用户管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});							
							break;	
					}
			}
			});
}
</script>

<div class="pageContent">
	<form name="userForm" id="userForm" method="post" action="" class="pageForm required-validate">
		<div class="pageFormContent nowrap" layoutH="56">			
			<dl>
				<dt>登陆名：</dt>
				<dd>
					<input type="text" id="username" name="username" maxlength="20" class="required" />
				</dd>
			</dl>
			<dl>
				<dt>密码：</dt>
				<dd>
					<input id="password" type="password" name="password" class="required alphanumeric" minlength="6" maxlength="20" alt="字母、数字、下划线 6-20位"/>
				</dd>
			</dl>
			<dl>
				<dt>确认密码：</dt>
				<dd>
					<input type="password" id="repassword" name="repassword" class="required" equalto="#password"/>
				</dd>
			</dl>
			<dl>
				<dt>用户状态：</dt>
				<dd>
					<select id="status" name="status" class="combox">
						<option value="0">未激活</option>
						<option value="1" selected>正常</option>						
					</select>
				</dd>
			</dl>
			<dl>
				<dt>姓名：</dt>
				<dd>
					<input type="text" id="name" name="name" maxlength="20" class="required" />
				</dd>
			</dl>
			<dl>
				<dt>手机号：</dt>
				<dd>
					<input type="text" id="mobile" name="mobile" maxlength="20" class="required phone" alt="请输入您的手机号"/>
				</dd>
			</dl>
			<dl>
				<dt>电话：</dt>
				<dd>
					<input type="text" id="telNumber" name="telNumber" maxlength="20" class="required phone" alt="请输入您的固话号码"/>
				</dd>
			</dl>
			<dl>
				<dt>Email：</dt>
				<dd>
					<input type="text" id="email" name="email" maxlength="30" class="required email" />
				</dd>
			</dl>			
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="save();">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
