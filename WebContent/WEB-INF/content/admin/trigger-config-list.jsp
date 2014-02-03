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
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="icon" href="admin/trigger-config!changeStatus.action?uid={sid_user}" target="ajaxTodo"><span>打开/关闭</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="1110" layoutH="110">
		<thead>
			<tr>
				<th width="2"></th>
				<th width="60">触发器名称</th>
				<th width="40">开始时间</th>
				<th width="40">触发类型</th>
				<th width="40">触发频次</th>
				<th width="120">触发内容</th>
				<th width="40">状态</th>
				<th width="40">操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator var="trigger" value="%{#request.triggerConfigList}" status="statu">	
			<tr target="sid_user" rel="<s:property value="%{#trigger.id}"/>">
				<td></td>
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
					<a class="btnEdit" href="admin/trigger-config!edit.action?uid=<s:property value="%{#trigger.id}"/>" target="navTab" title="修改触发器">修改触发器</a>
					<a class="btnView" href="admin/trigger-config!messageList.action?triggerId=<s:property value="%{#trigger.id}"/>" target="navTab" title="查看消息">查看消息</a>
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