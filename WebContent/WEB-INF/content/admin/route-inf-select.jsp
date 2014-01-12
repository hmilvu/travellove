<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form id="pagerForm" method="post" action="admin/route-inf!selectView.action">
	<input type="hidden" name="name" value="<s:property value='%{#request.name}'/>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="15" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" method="post" action="admin/route-inf!selectView.action" onsubmit="return dwzSearch(this, 'dialog');">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>线路名称：</label>
				<input type="text" name="name" id="name" value="<s:property value='%{#request.name}'/>"/>
			</li>	 
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="118" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th>线路名称</th>
				<th>创建人</th>
				<th>创建日期</th>
				<th width="80">查找带回</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator var="route" value="%{#request.routeList}" status="statu">	
			<tr>
				<td><s:property value="%{#route.routeName}"/></td>				
				<td><s:property value="%{#route.sysUser.name}"/></td>
				<td><s:date name="#route.createDate" format="yyyy-MM-dd" /></td>
				<td>
					<a class="btnSelect" href="javascript:$.bringBack({id:'<s:property value="%{#route.id}"/>', routeName:'<s:property value="%{#route.routeName}"/>', orgNum:''})" title="查找带回">选择</a>
				</td>
			</tr>
			</s:iterator>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>共${totalCount}条</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="15" pageNumShown="5" currentPage="<s:property value='%{#request.pageNumber}'/>"></div>
	</div>
</div>