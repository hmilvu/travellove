<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
    
<form id="pagerForm" method="post" action="admin/travel-inf!list.action">
	<input type="hidden" name="travelName" value="<s:property value='%{#request.travelName}'/>" />
	<input type="hidden" name="personName" value="<s:property value='%{#request.personName}'/>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="15" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="admin/travel-inf!list.action" method="post">
	<input type="hidden" name="pageNumber" value="0" />
	<div class="searchBar">		
		<table class="searchContent">
			<tr>
				<td>
					旅行社名称：<input type="text" name="travelName" id="travelName" value="<s:property value='%{#request.travelName}'/>"/>
				</td>				
				<td>
					联系人/负责人：<input type="text" name="personName" id="personName" value="<s:property value='%{#request.personName}'/>"/>
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div>
				</td>
			</tr>
		</table>
		<div class="subBar">			
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="admin/travel-inf!add.action" target="navTab" title="添加旅行社"><span>添加旅行社</span></a></li>
			<li><a class="delete" href="admin/travel-inf!delete.action?uid={sid_user}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="admin/travel-inf!edit.action?uid={sid_user}" target="navTab" title="修改旅行社"><span>修改旅行社</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="4"></th>
				<th width="15">序号</th>
				<th width="80">旅行社名称</th>
				<th width="150">地址</th>
				<th width="80">电话</th>
				<th width="80">联系人</th>
				<th width="80">负责人</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator var="travel" value="%{#request.travelList}" status="statu">		
			<tr target="sid_user" rel="<s:property value="%{#travel.id}"/>">
				<td></td>
				<td>
				    <s:property value="%{#statu.index+1+#request.startNum}"/>
				</td>
				<td>
					<s:property value='%{#travel.name}' />
				</td>
				<td>
					<s:property value='%{#travel.address}' />
				</td>
				<td>
					<s:property value='%{#travel.phone}' />
				</td>
				<td>
					<s:property value='%{#travel.contact}' />
				</td>
				<td>
					<s:property value='%{#travel.linker}' />
				</td>
			</tr>
		</s:iterator>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">			
			<span>共${travelTotalCount}条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount="${travelTotalCount}" numPerPage="15" pageNumShown="5" currentPage="<s:property value='%{#request.pageNumber}'/>"></div>

	</div>
</div>