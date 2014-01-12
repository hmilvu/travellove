<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
    
<form id="pagerForm" method="post" action="admin/team-inf!mulselect.action?fromSelect=1">
	<input type="hidden" name="travelName" value="<s:property value='%{#request.travelName}'/>" />
	<input type="hidden" name="teamName" value="<s:property value='%{#request.teamName}'/>" />
	<input type="hidden" name="startDate" value="<s:property value='%{#request.startDate}'/>" />
	<input type="hidden" name="endDate" value="<s:property value='%{#request.endDate}'/>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="15" />
</form>

<div class="pageHeader">
	<form action="admin/team-inf!mulselect.action?fromSelect=1" method="post" onsubmit="return dwzSearch(this, 'dialog');">
	<input type="hidden" name="pageNumber" value="0" />
	<div class="searchBar">		
		<ul class="searchContent">
			<s:if test="%{#session.SYS_USER_INF_IN_SESSION.userType == 0 || #session.SYS_USER_INF_IN_SESSION.userType == 1}">
			<li>
				<label>旅行社名称:</label>
				<input type="text" name="travelName" id="travelName" value="<s:property value='%{#request.travelName}'/>"/>
			</li>	 
			</s:if> 
			<li>
				<label>旅行团名称:</label>
				<input type="text" name="teamName" id="teamName" value="<s:property value='%{#request.teamName}'/>"/>
			</li>
			<li>
				<label>开始时间:</label>
				<input type="text" name="startDate" value="<s:property value='%{#request.startDate}'/>" class="date" readonly="true"/>
			</li>
				<li>
				<label>结束时间:</label>
				<input type="text" name="endDate" value="<s:property value='%{#request.endDate}'/>" class="date" readonly="true"/>
			</li> 
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" multLookup="orgId" warn="请选择部门">选择带回</button></div></div></li>
			</ul>
		</div>		
	</div>
	</form>
</div>
<div class="pageContent">	
	<table class="table" width="100%" layoutH="118">
		<thead>
			<tr>
				<th width="30"><input type="checkbox" class="checkboxCtrl" group="orgId" /></th>
				<th width="25">旅行团ID</th>
				<s:if test="%{#session.SYS_USER_INF_IN_SESSION.userType == 0 || #session.SYS_USER_INF_IN_SESSION.userType == 1}">
				<th width="80">旅行社名称</th>
				</s:if>
				<th width="150">旅行团名称</th>
				<th width="15">人数</th>
				<th width="80">开始时间</th>
				<th width="80">结束时间</th>				
			</tr>
		</thead>
		<tbody>
			<s:iterator var="team" value="%{#request.teamList}" status="statu">		
			<tr target="sid_user" rel="<s:property value="%{#team.id}"/>">
				<td><input type="checkbox" name="orgId" value="{id:'<s:property value="%{#team.id}"/>', orgName:'<s:property value="%{#team.name}"/>', orgNum:''}"/></td>
				<td>
				    <s:property value="%{#team.id}"/>
				</td>
				<s:if test="%{#session.SYS_USER_INF_IN_SESSION.userType == 0 || #session.SYS_USER_INF_IN_SESSION.userType == 1}">
				<td>
					<s:property value='%{#team.travelInf.name}' />
				</td>
				</s:if>
				<td>
					<s:property value='%{#team.name}' />
				</td>
				<td>
					<s:property value='%{#team.peopleCount}' />
				</td>
				<td>
					<s:date name="#team.beginDate" format="yyyy-MM-dd" />
				</td>
				<td>
					<s:date name="#team.endDate" format="yyyy-MM-dd" />
				</td>
			</tr>
		</s:iterator>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">			
			<span>共${teamTotalCount}条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount="${teamTotalCount}" numPerPage="15" pageNumShown="5" currentPage="<s:property value='%{#request.pageNumber}'/>"></div>

	</div>
</div>