<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="pageContent">
	<form name="userForm" id="userForm" method="post" action="" class="pageForm required-validate">
		<input type="hidden" id="itemInfId" name="itemInfId" value="<s:property value='%{#request.editItem.id}'/>"/>
		<div class="pageFormContent nowrap" layoutH="56">			
			<dl>
				<dt>名称：</dt>
				<dd>
					<s:property value='%{#request.editItem.name}'/>
				</dd>
			</dl>
			<dl>
				<dt>品牌：</dt>
				<dd>
					<s:property value='%{#request.editItem.brands}'/>
				</dd>
			</dl>
			<dl>
				<dt>规格：</dt>
				<dd>
					<s:property value='%{#request.editItem.specification}'/>
				</dd>
			</dl>
			<dl>
				<dt>单价（元）：</dt>
				<dd>
					<s:property value='%{#request.editItem.price}'/>
				</dd>
			</dl>
			<dl>
				<dt>姓名：</dt>
				<dd>
					<s:property value='%{#request.editItem.contactName}'/>
				</dd>
			</dl>
			<dl>
				<dt>手机号：</dt>
				<dd>
					<s:property value='%{#request.editItem.contactPhone}'/>
				</dd>
			</dl>
			<dl>
				<dt>产品介绍：</dt>
				<dd>
					<textarea name="description" cols="80" rows="4" maxlength="1000" readonly class="disabled"><s:property value='%{#request.editItem.description}'/></textarea>
				</dd>
			</dl>			
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><a class="buttonActive" href="admin/item-inf!edit.action?uid=<s:property value='%{#request.editItem.id}'/>" target="navTab" title="修改销售品"><span>修改</span></a></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
