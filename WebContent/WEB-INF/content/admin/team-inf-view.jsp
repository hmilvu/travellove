<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="pageContent">
	<form name="teamForm" id="teamForm" method="post" action="" class="pageForm required-validate">
	<input type="hidden" id="teamId" name="teamId" value='<s:property value="%{#request.editTeam.id}"/>'/>
		<div class="pageFormContent nowrap" layoutH="46">		
			<dl>
				<dt>旅行团名称：</dt>
				<dd><s:property value="%{#request.editTeam.name}"/></dd>
			</dl>
			<dl>
				<dt>人数：</dt>
				<dd><s:property value="%{#request.editTeam.peopleCount}"/></dd>
			</dl>
			<dl>
				<dt>开始时间：</dt>
				<dd><s:date name="#request.editTeam.beginDate" format="yyyy-MM-dd"/>
				</dd>
			</dl>
			<dl>
				<dt>结束时间：</dt>
				<dd><s:date name="#request.editTeam.endDate" format="yyyy-MM-dd"/></dd>
			</dl>
			<dl>
				<dt>备注：</dt>
				<dd><textarea name="description" id="description" cols="112" rows="4" maxlength="1024" readonly class="disabled"><s:property value="%{#request.editTeam.description}"/></textarea></dd>
			</dl>	
			<div class="panel" defH="150">
				<h1>旅行团线路</h1>
				<div>
					<table class="list nowrap" addButton="增加线路" width="100%">
						<thead>
							<tr>
								<th type="text" name="items[#index#].order" defaultVal="#index#" size="10" fieldClass="digits required">次序</th>
								<th type="lookup" name="items[#index#].routeForm.routeName" lookupGroup="items[#index#].routeForm" lookupUrl="admin/route-inf!selectView.action" postField="keywords" size="60" fieldClass="required readonly">线路名称</th>
								<th type="date" name="items[#index#].routeForm.date" size="12" fieldClass="required">开始日期</th>
								<th type="date" name="items[#index#].routeForm.endDate" size="12" fieldClass="required">结束日期</th>
								<!-- th type="enum" name="items[#index#].routeForm.status" enumUrl="admin/team-inf!status.action" size="22">状态</th> -->
								<th type="attach" name="items[#index#].attachmentForm.fileName" lookupGroup="items[#index#].attachmentForm" lookupUrl="admin/route-inf!upload.action" size="12">附件</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator var="routeView" value="%{#request.teamRouteList}" status="statu">
							<tr class="unitBox">
								<td>
									<s:property value='%{#routeView.routeOrder}'/>
								</td>
								<td>
									<input type="hidden" name="items[<s:property value='%{#statu.index}'/>].routeForm.id" value="<s:property value='%{#routeView.routeInf.id}'/>">
									<s:property value='%{#routeView.routeInf.routeName}'/>									
								</td>
								<td>
									<s:property value='%{#routeView.dateStr}'/>
								</td>
								<td>
									<s:property value='%{#routeView.endDateStr}'/>
								</td>
								<!-- td>
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
								</td> -->
								<td>
									<input type="hidden" name="items[<s:property value='%{#statu.index}'/>].attachmentForm.id" value="<s:property value='%{#routeView.attachmentId}'/>">
									<input class="textInput readonly" type="text" readonly="readonly" size="12" name="items[<s:property value='%{#statu.index}'/>].attachmentForm.fileName"  value="<s:property value='%{#routeView.attachmentFileName}'/>">
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
				<li><a class="buttonActive" href="admin/team-inf!edit.action?uid=<s:property value='%{#request.editTeam.id}'/>" target="navTab" title="修改旅行团"><span>修改</span></a>
				</li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
