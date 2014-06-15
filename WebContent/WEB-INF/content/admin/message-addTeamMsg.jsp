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
			url:'admin/message!createTeamMsg.action',
			data:$("#userForm").formSerialize(),//序列化表单里所有的内容
			success: function(data){				
				switch(data.result){
						case "success":	
							navTabAjaxDone({"statusCode":"200", "message":"保存成功", "navTabId":"消息管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});	
							break;
						case "input":	
							navTabAjaxDone({"statusCode":"300", "message":"预约时间必须至少晚于当前时间五分钟"});	
							break;
						case "input1":	
							navTabAjaxDone({"statusCode":"300", "message":"预约时间格式错误，请关闭重试"});	
							break;
						default:
							navTabAjaxDone({"statusCode":"300", "message":"保存失败，请重试", "navTabId":"消息管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});							
							break;	
					}
			}
			});
}
</script>

<div class="pageContent">
	<form name="userForm" id="userForm" method="post" action="" class="pageForm required-validate">
		<div class="pageFormContent nowrap" layoutH="56">			
			<!-- <dl>
				<dt>标题：</dt>
				<dd>
					<input type="text" name="topic" size="40" maxlength="20" class="required" />
				</dd>
			</dl> -->
			<dl class="nowrap">
				<dt>内容：</dt>
				<dd><textarea cols="80" rows="4" name="content" class="required" maxlength="140"></textarea></dd>
			</dl>
			<div class="divider"></div>
			<dl>
				<dt>优先级：</dt>
				<dd>
					<select id="priority" name="priority" class="combox">
						<option value="0">置顶</option>
						<option value="1" selected>普通</option>						
					</select>
				</dd>
			</dl>
			<dl>
				<dt>消息类型：</dt>
				<dd>
					<select id="type" name="type" class="combox">
						<option value="0" selected>通知</option>
						<option value="1">留言</option>						
					</select>
					<span class="info">通知类型可以预约时间发送；留言类型为立即发送</span>
				</dd>
			</dl>
			<dl>
				<dt>发送类型：</dt>
				<dd>
					<label>网络推送：<input type="checkbox" name="sendPush" value="1" checked/></label>
					<label>短信：<input type="checkbox" name="sendSMS" value="1" checked/></label>
				</dd>
			</dl>
			<dl>
				<dt>发送方式：</dt>
				<dd>
					<select id="remindMode" name="remindMode" class="combox">
						<option value="0" selected>立即</option>
						<option value="1">预约</option>						
					</select>
				</dd>
			</dl>
			<dl>
				<dt>预约发送时间：</dt>
				<dd>
					<input type="text" name="remindTime" class="date" dateFmt="yyyy-MM-dd HH:mm" readonly="true"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
				</dd>
			</dl>				
			<dl>
				<dt>旅行团列表：</dt>
				<dd>
					<input name="selectteam.id" value="" type="hidden">
					<input name="selectteam.orgName" type="text" class="readonly required" readonly="readonly" size="40"/>
					<a class="btnLook" href="admin/team-inf!mulselect.action" lookupGroup="selectteam">查找带回</a>	
					<span class="info">请选择接收消息的旅行团</span>		
				</dd>
			</dl>	
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="save();">发送</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
