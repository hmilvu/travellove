<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
function save(){
	var $form = $($("#viewForm"));

	if (!$form.valid()) {
		return false;
	}
	$.ajax({
			type:'POST',
			url:'admin/route-inf!update.action',
			data:$("#viewForm").formSerialize(),//序列化表单里所有的内容
			success: function(data){				
				switch(data.result){
						case "success":	
							navTabAjaxDone({"statusCode":"200", "message":"保存成功", "navTabId":"线路管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});	
							break;
						case "input":	
							navTabAjaxDone({"statusCode":"300", "message":"经纬度格式不正确，保存失败", "navTabId":"线路管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});	
							break;
						default:
							navTabAjaxDone({"statusCode":"300", "message":"保存失败，请重试", "navTabId":"线路管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});							
							break;	
					}
			}
			});
}
</script>
<form name="viewForm" id="viewForm" method="post" action="" class="pageForm required-validate">
	<input type="hidden" name="routeId" value='<s:property value="%{#request.editRoute.id}"/>'/>
	<div class="pageContent">
	<div class="pageFormContent" layoutH="57">
		<dl class="nowrap">
			<dt>线路名称：</dt>
			<dd>
				<input type="text" name="name" maxlength="64" class="required" size="50" value="<s:property value='%{#request.editRoute.routeName}'/>"/>
			</dd>
		</dl>
		<dl class="nowrap">
			<dt>出发地址：</dt>
			<dd>
				<input class="required" name="startLocation.name" type="text" postField="keyword" lookupGroup="startLocation"  size="50" value="<s:property value='%{#request.editRoute.startAddress}'/>"/>
				<a class="btnLook" href="admin/location!select.action" lookupGroup="startLocation">查找带回</a>	
				<span class="info">查找获得出发地经纬度</span>
			</dd>
		</dl>
		<dl class="nowrap">
			<dt>出发地址经纬度：</dt>
			<dd>
				<input class="readonly required" name="startLocation.longandlati" readonly="readonly" type="text" size="50" value="<s:property value='%{#request.editRoute.startLongandlati}'/>"/>
			</dd>
		</dl>

		<dl class="nowrap">
			<dt>结束地址：</dt>
			<dd>
				<input class="required" name="endLocation.name" type="text" postField="keyword" lookupGroup="endLocation"  size="50" value="<s:property value='%{#request.editRoute.endAddress}'/>"/>
				<a class="btnLook" href="admin/location!select.action" lookupGroup="endLocation">查找带回</a>
				<span class="info">查找获得结束地经纬度</span>
			</dd>
		</dl>
		<dl class="nowrap">
			<dt>结束地址经纬度：</dt>
			<dd>
				<input class="readonly required" name="endLocation.longandlati" readonly="readonly" type="text" size="50" value="<s:property value='%{#request.editRoute.endLongandlati}'/>"/>
			</dd>
		</dl>	
		<dl>
			<dt>线路介绍：</dt>
			<dd>
				<textarea name="description" id="description" cols="83" rows="3" class="textInput" maxlength="1024"><s:property value='%{#request.editRoute.description}'/></textarea>
			</dd>
		</dl>	
		<dl>&nbsp;</dl>
		<dl>&nbsp;</dl>
		<dl>&nbsp;</dl>
		<dl>&nbsp;</dl>
		<dl>&nbsp;</dl>
		<div class="panel" defH="150">
			<h1>线路包括的景点</h1>
			<div>
				<table class="list nowrap itemDetail" addButton="增加景点" width="100%">
					<thead>
						<tr>
							<th type="text" name="items[#index#].order" defaultVal="#index#" size="10" fieldClass="digits required">次序</th>
							<th type="lookup" name="items[#index#].viewSpotForm.viewName" lookupGroup="items[#index#].viewSpotForm" lookupUrl="admin/view-spot!selectView.action" postField="keywords" size="60" fieldClass="required readonly">景点名称</th>
							<th type="date" name="items[#index#].viewSpotForm.startDate" dateFmt="yyyy-MM-dd HH:mm" size=20 fieldClass="required">开始日期</th>
							<th type="date" name="items[#index#].viewSpotForm.endDate" dateFmt="yyyy-MM-dd HH:mm" size=20 fieldClass="required">结束日期</th>
							<th type="del" width="60">操作</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator var="routeView" value="%{#request.routeViewSpotList}" status="statu">
						<tr class="unitBox">
							<td>
								<input class="digits required textInput" type="text" size="10" value="<s:property value='%{#routeView.order}'/>" name="items[<s:property value='%{#statu.index}'/>].order">
							</td>
							<td>
								<input type="hidden" name="items[<s:property value='%{#statu.index}'/>].viewSpotForm.id" value="<s:property value='%{#routeView.viewSpotInfo.id}'/>">
								<input class="required readonly textInput" type="text" size="60" lookuppk="id" name="items[<s:property value='%{#statu.index}'/>].viewSpotForm.viewName" value="<s:property value='%{#routeView.viewSpotInfo.name}'/>">
								<a class="btnLook" title="查找带回" lookuppk="id" lookupgroup="items[<s:property value='%{#statu.index}'/>].viewSpotForm" href="admin/view-spot!selectView.action">查找带回</a>
							</td>
							<td>
								<input class="date textInput required" type="text" size="20" dateFmt="yyyy-MM-dd HH:mm" value="<s:property value='%{#routeView.startDateStr}'/>" name="items[<s:property value='%{#statu.index}'/>].viewSpotForm.startDate">
								<a class="inputDateButton" href="javascript:void(0)">选择</a>
							</td>
							<td>
								<input class="date textInput required" type="text" size="20" dateFmt="yyyy-MM-dd HH:mm" value="<s:property value='%{#routeView.endDateStr}'/>" name="items[<s:property value='%{#statu.index}'/>].viewSpotForm.endDate">
								<a class="inputDateButton" href="javascript:void(0)">选择</a>
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
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="save();">保存</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>
		</ul>
	</div>
</div>
</form>
