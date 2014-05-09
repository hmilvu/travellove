<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
    
<form id="pagerForm" method="post" action="admin/trigger-config!messageList.action?triggerId=<s:property value="%{#request.triggerId}"/>">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="15" />
</form>

<div class="pageHeader">
	
</div>
<div class="pageContent">
	<table class="table" width="1120" layoutH="65">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">消息接收人/团名称</th>
				<th width="140">内容</th>
				<th width="40">类型</th>
				<th width="40">优先级</th>
				<th width="40">状态</th>
				<th width="40">推送状态</th>
				<th width="20">类型</th>
				<th width="70">发送日期</th>
				<th width="40">操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator var="message" value="%{#request.messageList}" status="statu">	
			<tr target="sid_user" rel="<s:property value="%{#message.id}"/>">
				<td><s:property value="%{#statu.index+1+#request.startNum}"/></td>
				<td>
					<s:if test="%{#message.receiverType == 0}">旅行团：<s:property value="%{#message.receiverName}"/></s:if>
					<s:elseif test="%{#message.receiverType == 1}">团员：<s:property value="%{#message.receiverName}"/></s:elseif>
				</td>
				<td><s:if test="#message.content.length()>40"><s:property value="#message.content.substring(0, 40)"/></s:if><s:else><s:property value="#message.content"/></s:else></td>
				<td>
					<s:if test="%{#message.type == 0}">通知</s:if>
					<s:elseif test="%{#message.type == 1}">留言</s:elseif>
					<s:else>&nbsp;</s:else>
				</td>
				<td>
					<s:if test="%{#message.priority == 0}">置顶</s:if>
					<s:elseif test="%{#message.priority == 1}">普通</s:elseif>
					<s:else>&nbsp;</s:else>
				</td>
				<td>
					<s:if test="%{#message.status == 0}">审核中</s:if>
					<s:elseif test="%{#message.status == 1}">已通过</s:elseif>
					<s:elseif test="%{#message.status == 2}">已废弃</s:elseif>
					<s:else>&nbsp;</s:else>
				</td>
				<td>
					<s:if test="%{#message.pushStatus == 0}">&nbsp;</s:if>
					<s:elseif test="%{#message.pushStatus == 1}">已推送</s:elseif>
					<s:elseif test="%{#message.pushStatus == 2}">推送失败</s:elseif>
					<s:else>&nbsp;</s:else>
				</td>
				<td>
					<s:if test="%{#message.receiverType == 1}">
						<s:if test="%{#message.osType == 1}">IOS</s:if>
						<s:else>Android</s:else>
					</s:if>
				</td>
				<td><s:date name="#message.remindTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>
					<a title="确定要删除推送吗？" target="ajaxTodo" href="admin/trigger-config!deleteMessage.action?id=<s:property value="%{#message.id}"/>&typeValue=<s:property value="%{#request.typeValue}"/>" class="btnDel">删除</a>
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