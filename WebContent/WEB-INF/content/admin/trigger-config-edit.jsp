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
				<dt>停止时间：</dt>
				<dd>
					<input class="date textInput required" type="text" size="20" dateFmt="HH:mm:ss" mmStep="15" readonly="true" value="<s:property value='%{#request.editTriggerConfig.endTime}'/>" name="endTime">
					<a class="inputDateButton" href="javascript:void(0)">选择</a>
				</dd>
			</dl>		
			<dl>
				<dt>触发类型：</dt>
				<dd><s:if test="%{#request.editTriggerConfig.triggerType == 0}">每小时</s:if>
					<s:if test="%{#request.editTriggerConfig.triggerType == 1}">每天</s:if>
					<!-- select class="combox required" name="triggerType">
						<option <s:if test="%{#request.editTriggerConfig.triggerType == 0}">selected</s:if> value="0">每小时</option>
						<option <s:if test="%{#request.editTriggerConfig.triggerType == 1}">selected</s:if> value="1">每天</option>
					</select> -->
				</dd>
			</dl>
			<dl>
				<dt>触发频次：</dt>
				<dd> 
					<select class="combox required" name="times">
						<option <s:if test="%{#request.editTriggerConfig.times == 1}">selected</s:if> value="1">1</option>
						<s:if test="%{#request.editTriggerConfig.typeValue != 6}">
						<option <s:if test="%{#request.editTriggerConfig.times == 2}">selected</s:if> value="2">2</option>
						<option <s:if test="%{#request.editTriggerConfig.times == 3}">selected</s:if> value="3">3</option>
						</s:if>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>触发阀值：</dt>
				<dd>
					<input type="text" name="conditionValue" maxlength="64" class="required" size="30" value="<s:property value='%{#request.editTriggerConfig.conditionValue}'/>"/><s:property value='%{#request.editTriggerConfig.unitage}'/>
				</dd>
			</dl>
			<dl>
				<dt>触发条件：</dt>
				<dd>
					<s:property value='%{#request.editTriggerConfig.triggerCondition}'/>
				</dd>
			</dl>
			<dl>
				<dt>发送类型：</dt>
				<dd>
				<s:if test="%{#request.editTriggerConfig.typeValue == 6}">
					<label>网络推送：<input type="checkbox" name="sendPush" value="1" disabled/></label>
					<label>短信：<input type="checkbox" name="sendSMS" value="1" checked disabled/></label>
				</s:if>
				<s:else>
					<label>网络推送：<input type="checkbox" name="sendPush" value="1" <s:if test="%{#request.editTriggerConfig.sendPush == 1}">checked</s:if>/></label>
					<label>短信：<input type="checkbox" name="sendSMS" value="1" <s:if test="%{#request.editTriggerConfig.sendSMS == 1}">checked</s:if>/></label>
				</s:else>
				</dd>
			</dl>
			<dl>
				<dt>触发内容：</dt>
				<dd>
					<textarea name="content" cols="80" rows="4" maxlength="140" class="required"><s:property value='%{#request.editTriggerConfig.content}'/></textarea>		
				</dd>
			</dl>	
			<s:if test="%{#request.editTriggerConfig.typeValue == 3}">
				<div class="panel" defH="380">
					<h1>触发景点列表</h1>
					<div>
						<table class="list nowrap itemDetail" addButton="添加触发景点" width="100%">
							<thead>
								<tr>
									<th type="text" name="items[#index#].order" defaultVal="#index#" size="10" fieldClass="digits required">次序</th>
									<th type="lookup" name="items[#index#].viewSpotForm.viewName" lookupGroup="items[#index#].viewSpotForm" lookupUrl="admin/view-spot!selectView.action" postField="keywords" size="60" fieldClass="required readonly">景点名称</th>
									<th type="del" width="60">操作</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator var="routeView" value="%{#request.triggerViewSpotList}" status="statu">
								<tr class="unitBox">
									<td>
										<input class="digits required textInput" type="text" size="10" value="<s:property value='%{#statu.index+1}'/>" name="items[<s:property value='%{#statu.index}'/>].order">
									</td>
									<td>
										<input type="hidden" name="items[<s:property value='%{#statu.index}'/>].viewSpotForm.id" value="<s:property value='%{#routeView.viewSpotInfo.id}'/>">
										<input class="required readonly textInput" type="text" size="60" lookuppk="id" name="items[<s:property value='%{#statu.index}'/>].viewSpotForm.viewName" value="<s:property value='%{#routeView.viewSpotInfo.name}'/>">
										<a class="btnLook" title="查找带回" lookuppk="id" lookupgroup="items[<s:property value='%{#statu.index}'/>].viewSpotForm" href="admin/view-spot!selectView.action">查找带回</a>
									</td>
									<td>
										<a class="btnDel " href="javascript:void(0)">删除</a>
									</td>
								</tr>
								</s:iterator>
							</tbody>
						</table>				
					</div>
				</div>
			</s:if>				
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
