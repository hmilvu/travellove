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
			url:'admin/trigger-config!update.action',
			data:$("#userForm").formSerialize(),//序列化表单里所有的内容
			success: function(data){				
				switch(data.result){
						case "success":	
							navTabAjaxDone({"statusCode":"200", "message":"更新成功", "navTabId":"触发器管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});	
							break;
						default:
							navTabAjaxDone({"statusCode":"300", "message":"更新失败，请重试", "navTabId":"触发器管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});							
							break;	
					}
			}
			});
}
</script>

<div class="pageContent">
	<form name="userForm" id="userForm" method="post" action="" class="pageForm required-validate">
		<input type="hidden" id="triggerId" name="triggerId" value="<s:property value='%{#request.editTriggerConfig.id}'/>"/>
		<div class="pageFormContent nowrap" layoutH="56">			
			<dl>
				<dt>触发器名称：</dt>
				<dd>
					<s:property value='%{#request.editTriggerConfig.triggerName}'/>
				</dd>
			</dl>	
			<dl>
				<dt>开始时间：</dt>
				<dd>
					<input class="date textInput required" type="text" size="20" dateFmt="HH:mm:ss" mmStep="15" readonly="true" value="<s:property value='%{#request.editTriggerConfig.startTime}'/>" name="startTime">
					<a class="inputDateButton" href="javascript:void(0)">选择</a>
				</dd>
			</dl>		
			<dl>
				<dt>触发类型：</dt>
				<dd>
					<select class="combox required" name="triggerType">
						<option <s:if test="%{#request.editTriggerConfig.triggerType == 0}">selected</s:if> value="0">每小时</option>
						<option <s:if test="%{#request.editTriggerConfig.triggerType == 1}">selected</s:if> value="1">每天</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>触发频次：</dt>
				<dd>
					<select class="combox required" name="times">
						<option <s:if test="%{#request.editTriggerConfig.times == 1}">selected</s:if> value="1">1</option>
						<option <s:if test="%{#request.editTriggerConfig.times == 2}">selected</s:if> value="2">2</option>
						<option <s:if test="%{#request.editTriggerConfig.times == 3}">selected</s:if> value="3">3</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>触发阀值：</dt>
				<dd>
					<input type="text" name="conditionValue" maxlength="64" class="required number" size="30" value="<s:property value='%{#request.editTriggerConfig.conditionValue}'/>"/><s:property value='%{#request.editTriggerConfig.unitage}'/>
				</dd>
			</dl>
			<dl>
				<dt>触发条件：</dt>
				<dd>
					<s:property value='%{#request.editTriggerConfig.triggerCondition}'/>
				</dd>
			</dl>
			<dl>
				<dt>触发内容：</dt>
				<dd>
					<s:property value='%{#request.editTriggerConfig.content}'/>
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
