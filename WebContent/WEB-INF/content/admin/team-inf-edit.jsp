<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
function save(){
	var $form = $($("#teamForm"));

	if (!$form.valid()) {
		return false;
	}
	$.ajax({
			type:'POST',
			url:'admin/team-inf!update.action',
			data:$("#teamForm").formSerialize(),//序列化表单里所有的内容
			success: function(data){				
				switch(data.result){
						case "success":	
							navTabAjaxDone({"statusCode":"200", "message":"保存成功", "navTabId":"旅行团管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});	
							break;
						default:
							navTabAjaxDone({"statusCode":"300", "message":"保存失败，请重试", "navTabId":"旅行团管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});							
							break;	
					}
			}
			});
}
</script>

<div class="pageContent">
	<form name="teamForm" id="teamForm" method="post" action="" class="pageForm required-validate">
	<input type="hidden" id="teamId" name="teamId" value='<s:property value="%{#request.editTeam.id}"/>'/>
		<div class="pageFormContent nowrap" layoutH="56">		
			<dl>
				<dt>旅行团名称：</dt>
				<dd><input type="text" id="name" name="name" size="30" class="required" maxlength="128" value='<s:property value="%{#request.editTeam.name}"/>'/></dd>
			</dl>
			<dl>
				<dt>人数：</dt>
				<dd><input type="text" id="peopleCount" name="peopleCount" size="30" class="required digits" maxlength="10" value='<s:property value="%{#request.editTeam.peopleCount}"/>'/></dd>
			</dl>
			<dl>
				<dt>开始时间：</dt>
				<dd><input type="text" name="startDate" class="required date" readonly="true" value='<s:date name="#request.editTeam.beginDate" format="yyyy-MM-dd"/>'/>
				<a class="inputDateButton" href="javascript:;">选择</a></dd>
			</dl>
			<dl>
				<dt>结束时间：</dt>
				<dd><input type="text" name="endDate" class="required date" readonly="true" value='<s:date name="#request.editTeam.endDate" format="yyyy-MM-dd"/>'/>
				<a class="inputDateButton" href="javascript:;">选择</a></dd>
			</dl>
			<dl>
				<dt>备注：</dt>
				<dd><textarea name="description" id="description" cols="112" rows="4" maxlength="1024"><s:property value="%{#request.editTeam.description}"/></textarea></dd>
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
