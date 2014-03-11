<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="pageContent">
	<form name="userForm" id="userForm" method="post" action="" class="pageForm required-validate">
		<input type="hidden" id="memberId" name="memberId" value="<s:property value='%{#request.editMember.id}'/>"/>
		<div class="pageFormContent" layoutH="56">			
			<dl>
				<dt>团员类型：</dt>
				<dd><s:if test="%{#request.editMember.memberType == 2}">游客</s:if>
					<s:if test="%{#request.editMember.memberType == 0}">导游</s:if>
					<s:if test="%{#request.editMember.memberType == 1}">司机</s:if>
				</dd>
			</dl>	
			<dl>
				<dt>旅行团：</dt>
				<dd>
					<s:property value='%{#request.editMember.teamInfo.name}'/>
				</dd>
			</dl>		
			<dl>
				<dt>姓名：</dt>
				<dd>
					<s:property value='%{#request.editMember.memberName}'/>
				</dd>
			</dl>
			<dl>
				<dt>昵称：</dt>
				<dd>
					<s:property value='%{#request.editMember.nickname}'/>
				</dd>
			</dl>
			<dl>
				<dt>手机号：</dt>
				<dd>
					<s:property value='%{#request.editMember.travelerMobile}'/>
				</dd>
			</dl>
			<dl>
				<dt>密码：</dt>
				<dd>
					<s:property value='%{#request.editMember.password}'/>
				</dd>
			</dl>
			<dl>
				<dt>性别：</dt>
				<dd>
					<s:if test="%{#request.editMember.sex == 0}">男</s:if>
					<s:if test="%{#request.editMember.sex == 1}">女</s:if>				
				</dd>
			</dl>	
			<dl>
				<dt>年龄：</dt>
				<dd>
					<s:property value='%{#request.editMember.age}'/>
				</dd>
			</dl>
			<dl>
				<dt>证件类型类型：</dt>
				<dd>
					<s:if test="%{#request.editMember.idType == 0}">身份证</s:if>
					<s:if test="%{#request.editMember.idType == 1}">护照</s:if>
					<s:if test="%{#request.editMember.idType == 2}">军官证</s:if>
				</dd>
			</dl>	
			<dl>
				<dt>证件号：</dt>
				<dd>
					<s:property value='%{#request.editMember.idNo}'/>
				</dd>
			</dl>	
			<dl>
				<dt>兴趣爱好：</dt>
				<dd>
					<s:property value='%{#request.editMember.interest}'/>
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
					<textarea name="profile" id="profile" cols="80" rows="4" maxlength="1024" readonly class="disabled"><s:property value='%{#request.editMember.profile}'/></textarea>
				</dd>
			</dl>			
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><a class="buttonActive" href="admin/member-inf!edit.action?uid=<s:property value='%{#request.editMember.id}'/>" target="navTab" title="修改团员"><span>修改</span></a></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
