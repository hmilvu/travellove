<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
    <form id="pagerForm" method="post" action="demo_page1.html">
	<input type="hidden" name="status" value="${param.status}">
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="demo_page1.html" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					角色名称：<input type="text" name="keyword" />
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
			<li><a class="add" href="admin/role-inf!add.action" target="dialog" mask="true" title="添加角色"><span>添加</span></a></li>
			<li><a class="delete" href="admin/role-inf!delete.action" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="demo_page4.html?uid={sid_user}" target="navTab"><span>修改</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="4"></th>
				<th width="20">序号</th>
				<th width="30%">角色名称</th>
				<th width="60%">描述</th>				
			</tr>
		</thead>
		<s:iterator var="role" value="%{#request.roleList}" status="statu">		
			<tr>
				<td></td>
				<td>
					<s:property value='%{#statu.index+1}' />
				</td>
				<td>
					<s:property value='%{#role.name}' />
				</td>
				<td>
					<s:property value='%{#role.description}' />
				</td>
			</tr>
		</s:iterator>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>共${roleTotalCount}条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount="${roleTotalCount}" numPerPage="20" pageNumShown="5" currentPage="1"></div>

	</div>
</div>