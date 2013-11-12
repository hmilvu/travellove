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
			url:'admin/member-inf!create.action',
			data:$("#userForm").formSerialize(),//序列化表单里所有的内容
			success: function(data){				
				switch(data.result){
						case "success":	
							navTabAjaxDone({"statusCode":"200", "message":"保存成功", "navTabId":"团员管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});	
							break;
						default:
							navTabAjaxDone({"statusCode":"300", "message":"保存失败，请重试", "navTabId":"团员管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});							
							break;	
					}
			}
			});
}
</script>

<div class="pageContent">
	<form name="userForm" id="userForm" method="post" action="" class="pageForm required-validate">
		<div class="pageFormContent" layoutH="56">			
			<dl>
				<dt>团员类型：</dt>
				<dd>
					<select class="combox required" name="memberType">
						<option value="2">游客</option>
						<option value="0">导游</option>
						<option value="1">司机</option>
					</select>
				</dd>
			</dl>	
			<dl>
				<dt>旅行团：</dt>
				<dd>
					<input type="hidden" name="teamLookup.id" value="${teamLookup.id}"/>
					<input type="text" readonly="readonly" class="required" name="teamLookup.teamName" value="" suggestFields="teamName" suggestUrl="admin/team-inf!selectList.action" lookupGroup="teamLookup" />
				</dd>
			</dl>		
			<dl>
				<dt>姓名：</dt>
				<dd>
					<input type="text" name="name" maxlength="20" class="required" />
				</dd>
			</dl>
			<dl>
				<dt>昵称：</dt>
				<dd>
					<input type="text" name="nickname" maxlength="20" />
				</dd>
			</dl>
			<dl>
				<dt>手机号：</dt>
				<dd>
					<input type="text" name="phoneNumber" maxlength="20" class="required phone" alt="请输入团员的手机号"/>
				</dd>
			</dl>
			<dl>
				<dt>密码：</dt>
				<dd>
					<input type="text" name="password" maxlength="20" class="required" value="000000" alt="请输入团员的登陆密码"/>
				</dd>
			</dl>
			<dl>
				<dt>性别：</dt>
				<dd>
					<select class="combox required" name="sex">
						<option value="2">请选择</option>
						<option value="0">男</option>
						<option value="1">女</option>
					</select>
				</dd>
			</dl>	
			<dl>
				<dt>年龄：</dt>
				<dd>
					<input type="text" name="age" class="digits required" maxlength="20" />
				</dd>
			</dl>
			<dl>
				<dt>证件类型类型：</dt>
				<dd>
					<select class="combox required" name="idType">
						<option value="0">身份证</option>
						<option value="1">护照</option>
						<option value="2">军官证</option>
					</select>
				</dd>
			</dl>	
			<dl>
				<dt>证件号：</dt>
				<dd>
					<input type="text" name="idNo" maxlength="20" class="required"/>
				</dd>
			</dl>	
			<dl>
				<dt>兴趣爱好：</dt>
				<dd>
					<input type="text" name="interest" size="83" maxlength="128"/>
				</dd>
			</dl>	
			<dl>
				<dt>&nbsp;</dt>
				<dd>
					&nbsp;
				</dd>
			</dl>	
			<dl>
				<dt>个人简介：</dt>
				<dd>
					<textarea name="profile" id="profile" cols="80" rows="4" maxlength="1024"></textarea>
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
