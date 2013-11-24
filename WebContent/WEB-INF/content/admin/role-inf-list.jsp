<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
  
<form id="pagerForm" method="post" action="admin/role-inf!list.action">
	<input type="hidden" name="roleName" value="<s:property value='%{#request.roleName}'/>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="15" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="admin/role-inf!list.action" method="post">
	<input type="hidden" name="pageNumber" value="0" />
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					角色名称：<input type="text" name="roleName" id="roleName" value="<s:property value='%{#request.roleName}'/>"/>
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div>
				</td>				
			</tr>
		</table>
		<div class="subBar"></div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="admin/role-inf!add.action" title="添加角色" target="navTab"><span>添加角色</span></a></li>
			<li><a class="delete" href="admin/role-inf!delete.action?uid={sid_user}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="admin/role-inf!edit.action?uid={sid_user}" target="navTab" title="修改角色"><span>修改角色</span></a></li>
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
			<tr target="sid_user" rel="<s:property value="%{#role.id}"/>">
				<td></td>
				<td>
				    <s:property value="%{#statu.index+1+#request.startNum}"/>
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
		<div class="pagination" targetType="navTab" totalCount="${roleTotalCount}" numPerPage="15" pageNumShown="5" currentPage="<s:property value='%{#request.pageNumber}'/>"></div>

	</div>
</div>