<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
    
<form id="pagerForm" method="post" action="admin/message!list.action">
	<input type="hidden" name="topic" value="<s:property value='%{#request.topic}'/>" />
	<input type="hidden" name="type" value="<s:property value='%{#request.type}'/>" />
	<input type="hidden" name="status" value="<s:property value='%{#request.status}'/>" />
	<input type="hidden" name="priority" value="<s:property value='%{#request.priority}'/>" />
	<input type="hidden" name="teamName" value="<s:property value='%{#request.teamName}'/>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="15" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="admin/member-inf!list.action" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					旅行团名称：<input type="text" name="teamName" value="<s:property value='%{#request.teamName}'/>"/>
				</td>
				<!-- td>
					标题：<input type="text" name="topic" value="<s:property value='%{#request.topic}'/>"/>
				</td> -->
				<td>
					<label>类型：</label>
					<select class="combox" name="type">
						<option value="">所有</option>
						<option value="0" <s:if test="%{#request.type == 0}">selected</s:if> >通知</option>
						<option value="1" <s:if test="%{#request.type == 1}">selected</s:if> >留言</option>
					</select>
				</td>
				<td>
					<label>状态：</label>
					<select class="combox" name="status">
						<option value="">所有</option>
						<option value="0" <s:if test="%{#request.status == 0}">selected</s:if> >审核中</option>
						<option value="1" <s:if test="%{#request.status == 1}">selected</s:if> >已通过</option>
						<option value="2" <s:if test="%{#request.status == 2}">selected</s:if> >已废弃</option>
					</select>
				</td>
				<td>
					<label>优先级：</label>
					<select class="combox" name="priority">
						<option value="">所有</option>
						<option value="0" <s:if test="%{#request.priority == 0}">selected</s:if> >置顶</option>
						<option value="1" <s:if test="%{#request.priority == 1}">selected</s:if> >普通</option>
					</select>
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
			<li><a class="add" href="admin/message!addTeamMsg.action" target="navTab" title="向旅行团发消息"><span>向旅行团发消息</span></a></li>
			<li><a class="add" href="admin/message!addMemberMsg.action" target="navTab" title="向团员发消息"><span>向团员发消息</span></a></li>
			<li><a title="确实要删除这些消息吗?" target="selectedTodo" rel="ids" postType="string" href="admin/message!delete.action" class="delete"><span>批量删除</span></a></li>
			<li><a class="edit" href="admin/message!edit.action?uid={sid_user}" target="navTab" warn="请选择一条消息"><span>修改消息</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="1120" layoutH="138">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="60">消息接收人/团名称</th>
				<th width="120">内容</th>
				<th width="40">类型</th>
				<th width="40">优先级</th>
				<th width="40">状态</th>
				<th width="40">推送状态</th>
				<th width="70">创建日期</th>
				<th width="70">操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator var="message" value="%{#request.messageList}" status="statu">	
			<tr target="sid_user" rel="<s:property value="%{#message.id}"/>">
				<td><input name="ids" value="<s:property value="%{#message.id}"/>" type="checkbox"></td>
				<td>
					<s:if test="%{#message.receiverType == 0}">旅行团：<s:property value="%{#message.receiverName}"/></s:if>
					<s:elseif test="%{#message.receiverType == 1}">团员：<s:property value="%{#message.receiverName}"/></s:elseif>
				</td>
				<td><s:if test="#message.content.length()>20"><s:property value="#message.content.substring(0, 20)"/></s:if><s:else><s:property value="#message.content"/></s:else></td>
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
				<td><s:date name="#message.createDate" format="yyyy-MM-dd" /></td>
				<td>
					<a class="btnEdit" href="admin/message!audit.action?messageId=<s:property value='%{#message.id}'/>" target="navTab" title="审核消息">审核消息</a>
					<a class="btnView" href="admin/message!reply.action?messageId=<s:property value='%{#message.id}'/>" target="navTab" title="查看回复" rel="replyNavTab">查看回复</a>
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