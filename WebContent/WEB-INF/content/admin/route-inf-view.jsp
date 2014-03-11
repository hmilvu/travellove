<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form name="viewForm" id="viewForm" method="post" action="" class="pageForm required-validate">
	<input type="hidden" name="routeId" value='<s:property value="%{#request.editRoute.id}"/>'/>
	<div class="pageContent">
	<div class="pageFormContent" layoutH="57">
		<dl class="nowrap">
			<dt>线路名称：</dt>
			<dd>
				<s:property value='%{#request.editRoute.routeName}'/>
			</dd>
		</dl>
		<dl class="nowrap">
			<dt>出发地址：</dt>
			<dd>
				<s:property value='%{#request.editRoute.startAddress}'/>
			</dd>
		</dl>
		<dl class="nowrap">
			<dt>出发地址经纬度：</dt>
			<dd>
				<s:property value='%{#request.editRoute.startLongandlati}'/>
			</dd>
		</dl>

		<dl class="nowrap">
			<dt>结束地址：</dt>
			<dd>
				<s:property value='%{#request.editRoute.endAddress}'/>				
			</dd>
		</dl>
		<dl class="nowrap">
			<dt>结束地址经纬度：</dt>
			<dd>
				<s:property value='%{#request.editRoute.endLongandlati}'/>
			</dd>
		</dl>	
		<dl>
			<dt>线路介绍：</dt>
			<dd>
				<textarea name="description" id="description" cols="83" rows="3" readonly class="disabled" maxlength="1024"><s:property value='%{#request.editRoute.description}'/></textarea>
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
				<table class="list nowrap" width="100%">
					<thead>
						<tr>
							<th type="text" name="items[#index#].order" defaultVal="#index#" size="10" fieldClass="digits required">次序</th>
							<th type="lookup" name="items[#index#].viewSpotForm.viewName" lookupGroup="items[#index#].viewSpotForm" lookupUrl="admin/view-spot!selectView.action" postField="keywords" size="60" fieldClass="required readonly">景点名称</th>
							<th type="text" name="items[#index#].numberOfDay" defaultVal="#index#" size="10" fieldClass="digits required">第几天</th>
							<th type="date" name="items[#index#].viewSpotForm.startTime" dateFmt="HH:mm" size=20 mmStep="15" readonly="true" fieldClass="required">开始时间</th>
							<th type="date" name="items[#index#].viewSpotForm.endTime" dateFmt="HH:mm" size=20 mmStep="15" readonly="true" fieldClass="required">结束时间</th>
					</tr>
					</thead>
					<tbody>
						<s:iterator var="routeView" value="%{#request.routeViewSpotList}" status="statu">
						<tr class="unitBox">
							<td>
								<s:property value='%{#routeView.order}'/>
							</td>
							<td>
								<input type="hidden" name="items[<s:property value='%{#statu.index}'/>].viewSpotForm.id" value="<s:property value='%{#routeView.viewSpotInfo.id}'/>">
								<s:property value='%{#routeView.viewSpotInfo.name}'/>
							</td>
							<td>
								<s:property value='%{#routeView.numberOfDay}'/>
							</td>
							<td>
								<s:property value='%{#routeView.startTimeStr}'/>
							</td>
							<td>
								<s:property value='%{#routeView.endTimeStr}'/>
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
			<li><a class="buttonActive" href="admin/route-inf!edit.action?uid=<s:property value="%{#request.editRoute.id}"/>" target="navTab" title="修改线路"><span>修改</span></a></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>
		</ul>
	</div>
</div>
</form>
