<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
    
<form id="pagerForm" method="post" action="admin/view-spot!list.action">
	<input type="hidden" name="name" value="<s:property value='%{#request.name}'/>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="15" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="admin/view-spot!list.action" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					触发器名称：<input type="text" name="name" id="name" value="<s:property value='%{#request.name}'/>"/>
				</td>				
			</tr>
		</table>		
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="1110" layoutH="110">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="60">触发器名称</th>
				<th width="40">开始时间</th>
				<th width="40">触发类型</th>
				<th width="40">触发频率（次）</th>
				<th width="120">触发内容</th>
				<th width="40">状态</th>
				<th width="40">操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator var="trigger" value="%{#request.triggerConfigList}" status="statu">	
			<tr target="sid_user" rel="<s:property value="%{#trigger.id}"/>">
				<td><input name="ids" value="<s:property value="%{#trigger.id}"/>" type="checkbox"></td>
				<td><s:property value="%{#trigger.triggerName}"/></td>	
				<td><s:property value="%{#trigger.startTime}"/></td>				
				<td>
					<s:if test="%{#trigger.triggerType == 0}">
						每小时
					</s:if>
					<s:else>
						每天
					</s:else>	
				</td>
				<td><s:property value="%{#trigger.times}"/></td>
				<td><s:property value="%{#trigger.content}"/></td>
				<td>
					<s:if test="%{#trigger.triggerStatus == 0}">
						关闭
					</s:if>
					<s:else>
						打开
					</s:else>	
				</td>
				<td>
					<a class="btnEdit" href="admin/trigger-config!edit.action?uid=<s:property value="%{#trigger.id}"/>" target="navTab" title="修改">修改</a>
					<a href="admin/trigger-config!changeStatus.action?uid=<s:property value="%{#trigger.id}"/>" target="navTab" title="打开/关闭">打开/关闭</a>
				</td>
			</tr>
			</s:iterator>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>共${totalCount}条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount="${totalCount}" numPerPage="15" pageNumShown="5" currentPage="<s:property value='%{#request.pageNumber}'/>"></div>

	</div>
</div>