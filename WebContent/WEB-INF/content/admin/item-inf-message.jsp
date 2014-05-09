<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form id="pagerForm" method="post" action="admin/item-inf!message.action">
	<input type="hidden" name="uid" value="<s:property value='%{#request.editItem.id}'/>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="15" />
</form>
<div class="pageFormContent" layoutH="20">
	<div class="pageContent">
		<table class="table" width="1100" layoutH="70">
			<thead>
				<tr>
					<th width="10">序号</th>
					<th width="250">内容</th>
					<th width="30">评级</th>
					<th width="30">发布姓名</th>
					<th width="20">发表时间</th>
					<th width="10">操作</th>
				</tr>
			</thead>
			<tbody>
			    <s:iterator var="msg" value="%{#request.messageList}" status="statu">	
				<tr target="sid_user" rel="<s:property value="%{#msg.id}"/>">
					<td>
				   	 <s:property value="%{#statu.index+1+#request.startNum}"/>
					</td>
					<td><s:property value="%{#msg.content}"/></td>
					<td><s:property value="%{#msg.score}"/></td>
					<td>
						团员：<s:property value="%{#msg.receiverName}"/>
					</td>
					<td>
						<s:date name="%{#msg.createDate}" format="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
						<a title="确定要删除此评论吗？" target="ajaxTodo" href="admin/item-inf!deleteMessage.action?id=<s:property value="%{#msg.id}"/>" class="btnDel">删除</a>
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
</div>