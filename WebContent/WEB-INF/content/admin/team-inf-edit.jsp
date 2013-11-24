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
			<div class="panel" defH="150">
				<h1>线路包括的景点</h1>
				<div>
					<table class="list nowrap itemDetail" addButton="增加景点" width="100%">
						<thead>
							<tr>
								<th type="text" name="items[#index#].order" defaultVal="#index#" size="10" fieldClass="digits required">次序</th>
								<th type="lookup" name="items[#index#].routeForm.routeName" lookupGroup="items[#index#].routeForm" lookupUrl="admin/route-inf!selectView.action" postField="keywords" size="60" fieldClass="required readonly">线路名称</th>
								<th type="date" name="items[#index#].routeForm.date" size="12" fieldClass="required">开始日期</th>
								<th type="enum" name="items[#index#].routeForm.status" enumUrl="admin/team-inf!status.action" size="22">状态</th>
								<th type="del" width="60">操作</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator var="routeView" value="%{#request.teamRouteList}" status="statu">
							<tr class="unitBox">
								<td>
									<input class="digits required textInput" type="text" size="10" value="<s:property value='%{#routeView.routeOrder}'/>" name="items[<s:property value='%{#statu.index}'/>].order">
								</td>
								<td>
									<input type="hidden" name="items[<s:property value='%{#statu.index}'/>].routeForm.id" value="<s:property value='%{#routeView.routeInf.id}'/>">
									<input class="required readonly textInput" type="text" size="60" lookuppk="id" name="items[<s:property value='%{#statu.index}'/>].routeForm.routeName" value="<s:property value='%{#routeView.routeInf.routeName}'/>">
									<a class="btnLook" title="查找带回" lookuppk="id" lookupgroup="items[<s:property value='%{#statu.index}'/>].routeForm" href="admin/route-inf!selectView.action">查找带回</a>
								</td>
								<td>
									<input class="date textInput required" type="text" size="12" datefmt="yyyy-MM-dd" value="<s:property value='%{#routeView.dateStr}'/>" name="items[<s:property value='%{#statu.index}'/>].routeForm.date">
									<a class="inputDateButton" href="javascript:void(0)">选择</a>
								</td>
								<td>
									<div class="combox">
										<div id="combox_2016890" class="select">
											<a class="" value="<s:property value='%{#routeView.status}'/>" name="items[#index#].routeForm.status" href="javascript:">
												<s:if test="#routeView.status == 0">未完成</s:if>
												<s:else>已完成</s:else>										
											</a>
											<select class="" name="items[<s:property value='%{#statu.index}'/>].routeForm.status">
												<option value="0">未完成</option>
												<option value="1">已完成</option>
											</select>
										</div>
									</div>
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
