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
			url:'admin/team-inf!create.action',
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
		<div class="pageFormContent nowrap" layoutH="46">		
			<s:if test="%{#session.SYS_USER_INF_IN_SESSION.userType == 0 || #session.SYS_USER_INF_IN_SESSION.userType == 1}">	
			<dl>
				<dt>旅行社：</dt>
				<dd>
					<input type="hidden" name="travelLookup.id" value="${travelLookup.id}"/>
					<input type="text" readonly="readonly" class="required" size="30" name="travelLookup.travelName" value="" suggestFields="travelName" suggestUrl="admin/travel-inf!selectList.action" lookupGroup="travelLookup" />
				</dd>
			</dl>
			</s:if>
			<dl>
				<dt>旅行团名称：</dt>
				<dd><input type="text" id="name" name="name" size="30" class="required" maxlength="128"/></dd>
			</dl>
			<dl>
				<dt>人数：</dt>
				<dd><input type="text" id="peopleCount" name="peopleCount" size="30" class="required digits"  maxlength="10"/></dd>
			</dl>
			<dl>
				<dt>开始时间：</dt>
				<dd><input type="text" name="startDate" class="required date" readonly="true"/>
				<a class="inputDateButton" href="javascript:;">选择</a></dd>
			</dl>
			<dl>
				<dt>结束时间：</dt>
				<dd><input type="text" name="endDate" class="required date" readonly="true"/>
				<a class="inputDateButton" href="javascript:;">选择</a></dd>
			</dl>
			<dl>
				<dt>备注：</dt>
				<dd><textarea name="description" id="description" cols="112" rows="4" maxlength="1024"></textarea></dd>
			</dl>	
			<div class="panel" defH="150">
				<h1>旅行团线路</h1>
					<div>
						<table class="list nowrap itemDetail" addButton="添加线路" width="100%">
							<thead>
								<tr>
									<th type="text" name="items[#index#].order" defaultVal="#index#" size="10" fieldClass="digits required">次序</th>
									<th type="lookup" name="items[#index#].routeForm.routeName" lookupGroup="items[#index#].routeForm" lookupUrl="admin/route-inf!selectView.action" postField="keywords" size="60" fieldClass="required readonly">线路名称</th>
									<th type="date" name="items[#index#].routeForm.date" size="12" fieldClass="required">开始日期</th>
									<th type="date" name="items[#index#].routeForm.endDate" size="12" fieldClass="required">结束日期</th>
									<th type="attach" name="items[#index#].attachmentForm.fileName" lookupGroup="items[#index#].attachmentForm" lookupUrl="admin/route-inf!upload.action" size="12">附件</th>
									<th type="del" width="60">操作</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
			</div>		
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
