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
					景点名称：<input type="text" name="name" id="name" value="<s:property value='%{#request.name}'/>"/>
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
			<li><a class="add" href="admin/view-spot!add.action" target="navTab" title="添加景点"><span>添加景点</span></a></li>
			<li><a title="确实要删除这些景点吗?" target="selectedTodo" rel="ids" postType="string" href="admin/view-spot!delete.action" class="delete"><span>批量删除</span></a></li>
			<li><a class="edit" href="admin/view-spot!edit.action?uid={sid_user}" target="navTab" warn="请选择一个景点"><span>修改景点</span></a></li>
			<li class="line">line</li>			
		</ul>
	</div>
	<table class="table" width="1120" layoutH="138">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="600">景点名称</th>
				<th width="60">类型</th>
				<th width="40">创建人</th>
				<th width="70">创建日期</th>
				<th width="40">操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator var="viewSpot" value="%{#request.viewSpotList}" status="statu">	
			<tr target="sid_user" rel="<s:property value="%{#viewSpot.id}"/>">
				<td><input name="ids" value="<s:property value="%{#viewSpot.id}"/>" type="checkbox"></td>
				<td><s:property value="%{#viewSpot.name}"/></td>		
				<td>
					<s:if test="%{#viewSpot.type == 0}">
						公共景点
					</s:if>
					<s:else>
						旅行社景点
					</s:else>				
				</td>		
				<td><s:property value="%{#viewSpot.sysUser.name}"/></td>
				<td><s:date name="#viewSpot.createDate" format="yyyy-MM-dd" /></td>
				<td>
					<a class="btnAttach" href="admin/view-spot!upload.action?uid=<s:property value="%{#viewSpot.id}"/>" target="navTab" title="上传图片">上传图片</a>
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