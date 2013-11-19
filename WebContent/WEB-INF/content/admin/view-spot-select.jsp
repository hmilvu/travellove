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
	<table class="table" layoutH="118" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th width="5"></th>
				<th width="180">景点名称</th>
				<th width="40">创建人</th>
				<th width="70">创建日期</th>
				<th width="80">查找带回</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator var="viewSpot" value="%{#request.viewSpotList}" status="statu">	
			<tr target="sid_user" rel="<s:property value="%{#viewSpot.id}"/>">	
				<s:set name="longandlati" value="%{#viewSpot.longitude + ',' + #viewSpot.latitude}" />			
				<td>&nbsp;</td>			
				<td><s:property value="%{#viewSpot.name}"/></td>				
				<td><s:property value="%{#viewSpot.sysUser.name}"/></td>
				<td><s:date name="#viewSpot.createDate" format="yyyy-MM-dd" /></td>
				<td><a class="btnSelect" href="javascript:$.bringBack({id:'<s:property value="%{#viewSpot.id}"/>', orgName:'<s:property value="%{#viewSpot.name}"/>', orgNum:'<s:property value="#longandlati"/>'})" title="添加此景点">选择</a></td>
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