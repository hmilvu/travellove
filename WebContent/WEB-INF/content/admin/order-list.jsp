<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
    
<form id="pagerForm" method="post" action="admin/order!list.action">
	<input type="hidden" name="name" value="<s:property value='%{#request.name}'/>" />
	<input type="hidden" name="productName" value="<s:property value='%{#request.productName}'/>" />
	<input type="hidden" name="teamName" value="<s:property value='%{#request.teamName}'/>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="15" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="admin/order!list.action" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					团员名称：<input type="text" name="name" value="<s:property value='%{#request.name}'/>"/>
				</td>
				<td>
					产品名称：<input type="text" name="productName" value="<s:property value='%{#request.productName}'/>"/>
				</td>	
				<td>
					旅行团：<input type="text" name="teamName" value="<s:property value='%{#request.teamName}'/>"/>
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
			<li><a title="确实要删除团员的订购吗?" target="selectedTodo" rel="ids" postType="string" href="admin/order!delete.action" class="delete"><span>批量删除</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="1160" layoutH="138">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="80">团员名称</th>
				<th width="60">产品名称</th>
				<th width="60">产品规格</th>
				<th width="40">订购数量</th>
				<th width="40">总价(元)</th>
				<th width="70">订购人</th>
				<th width="70">订购时间</th>
				<th width="40">状态</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator var="orderInf" value="%{#request.orderList}" status="statu">	
			<tr target="sid_user" rel="<s:property value="%{#orderInf.id}"/>">
				<td><input name="ids" value="<s:property value="%{#orderInf.id}"/>" type="checkbox"></td>
				<td><s:property value="%{#orderInf.teamInfo.name}"/>：<s:property value="%{#orderInf.memberInf.memberName}"/></td>
				<td><s:property value="%{#orderInf.itemInf.name}"/></td>
				<td><s:property value="%{#orderInf.itemInf.specification}"/></td>
				<td><s:property value="%{#orderInf.itemCount}"/></td>
				<td><s:property value="%{#orderInf.totalPrice}"/></td>
				<td><s:property value="%{#orderInf.orderName}"/></td>
				<td><s:date name="#orderInf.createDate" format="yyyy-MM-dd HH:mm" /></td>
				<td>
					<s:if test="%{#orderInf.status == 0}">
						预订
					</s:if>
					<s:elseif test="%{#orderInf.status == 1}">
						确认
					</s:elseif>
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