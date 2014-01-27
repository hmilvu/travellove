<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form id="pagerForm" method="post" action="admin/item-inf!selectView.action">
	<input type="hidden" name="brands" value="<s:property value='%{#request.brands}'/>" />
	<input type="hidden" name="name" value="<s:property value='%{#request.name}'/>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="15" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" method="post" action="admin/route-inf!selectView.action" onsubmit="return dwzSearch(this, 'dialog');">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>品牌：</label>
				<input type="text" name="brands" value="<s:property value='%{#request.brands}'/>"/>
			</li>	 
			<li>
				<label>名称：</label>
				<input type="text" name="name" value="<s:property value='%{#request.name}'/>"/>
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
				<th width="80">名称</th>
				<th width="60">品牌</th>
				<th width="60">规格</th>
				<th width="40">单价（元）</th>
				<th width="60">类型</th>
				<th width="40">创建人</th>
				<th width="70">创建日期</th>
				<th width="80">查找带回</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator var="itemInf" value="%{#request.itemList}" status="statu">	
			<tr target="sid_user" rel="<s:property value="%{#itemInf.id}"/>">
				<td><s:property value="%{#itemInf.name}"/></td>
				<td><s:property value="%{#itemInf.brands}"/></td>
				<td><s:property value="%{#itemInf.specification}"/></td>
				<td><s:property value="%{#itemInf.price}"/></td>
				<td><s:if test="%{#itemInf.type == 0}">
						公共特产
					</s:if>
					<s:else>
						旅行社特产
					</s:else>	
				</td>
				<td><s:property value="%{#itemInf.sysUser.name}"/></td>
				<td><s:date name="#itemInf.createDate" format="yyyy-MM-dd" /></td>
				<td>
					<a class="btnSelect" href="javascript:$.bringBack({id:'<s:property value="%{#itemInf.id}"/>', name:'<s:property value="%{#itemInf.name}"/>', orgNum:''})" title="查找带回">选择</a>
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