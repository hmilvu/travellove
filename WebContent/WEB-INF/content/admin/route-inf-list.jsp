<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
    
<form id="pagerForm" method="post" action="admin/route-inf!list.action">
	<input type="hidden" name="name" value="<s:property value='%{#request.name}'/>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="15" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="admin/route-inf!list.action" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					线路名称：<input type="text" name="name" id="name" value="<s:property value='%{#request.name}'/>"/>
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
			<li><a class="add" href="admin/route-inf!add.action" target="navTab" title="创建线路"><span>创建线路</span></a></li>
			<li><a title="确实要删除这些景点吗?" target="selectedTodo" rel="ids" postType="string" href="admin/route-inf!delete.action" class="delete"><span>批量删除</span></a></li>
			<li><a class="edit" href="admin/route-inf!edit.action?uid={sid_user}" target="navTab" warn="请选择一条线路"><span>修改线路</span></a></li>
			<li class="line">line</li>			
		</ul>
	</div>
	<table class="table" width="1120" layoutH="138">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="180">线路名称</th>
				<th width="40">创建人</th>
				<th width="70">创建日期</th>
				<!-- th width="70">操作</th> -->
			</tr>
		</thead>
		<tbody>
		    <s:iterator var="route" value="%{#request.routeList}" status="statu">	
			<tr target="sid_user" rel="<s:property value="%{#route.id}"/>">
				<td><input name="ids" value="<s:property value="%{#route.id}"/>" type="checkbox"></td>
				<td><s:property value="%{#route.routeName}"/></td>				
				<td><s:property value="%{#route.sysUser.name}"/></td>
				<td><s:date name="#route.createDate" format="yyyy-MM-dd" /></td>
				<!-- td><a class="btnAttach" href="admin/member-inf!profile.action?memberId=<s:property value='%{#member.id}'/>" lookupGroup="attachment" width="560" height="300" title="上传头像">上传头像</a></td> -->
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