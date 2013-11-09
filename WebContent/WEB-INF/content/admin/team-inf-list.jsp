<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
    
<form id="pagerForm" method="post" action="admin/team-inf!list.action">
	<input type="hidden" name="travelName" value="<s:property value='%{#request.travelName}'/>" />
	<input type="hidden" name="teamName" value="<s:property value='%{#request.teamName}'/>" />
	<input type="hidden" name="startDate" value="<s:property value='%{#request.startDate}'/>" />
	<input type="hidden" name="endDate" value="<s:property value='%{#request.endDate}'/>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="15" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="admin/team-inf!list.action" method="post">
	<input type="hidden" name="pageNumber" value="0" />
	<div class="searchBar">		
		<table class="searchContent">
			<tr>
				<s:if test="%{#session.SYS_USER_INF_IN_SESSION.userType == 0 || #session.SYS_USER_INF_IN_SESSION.userType == 1}">
				<td>
					旅行社名称：<input type="text" name="travelName" id="travelName" value="<s:property value='%{#request.travelName}'/>"/>
				</td>				
				</s:if>
				<td>
					旅行团名称：<input type="text" name="teamName" id="teamName" value="<s:property value='%{#request.teamName}'/>"/>
				</td>
				<td>
					开始时间：
					<input type="text" name="startDate" value="<s:property value='%{#request.startDate}'/>" class="date" readonly="true"/>
				</td>
				<td>
					结束时间：
					<input type="text" name="endDate" value="<s:property value='%{#request.endDate}'/>" class="date" readonly="true"/>
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
			<li><a class="add" href="admin/team-inf!add.action" target="navTab" title="添加旅行团"><span>添加旅行团</span></a></li>
			<li><a class="delete" href="admin/team-inf!delete.action?uid={sid_user}" target="ajaxTodo" title="确定要删除吗?"><span>删除旅行团</span></a></li>
			<li><a class="edit" href="admin/team-inf!edit.action?uid={sid_user}" target="navTab" title="修改旅行团"><span>修改旅行团</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="admin/member-inf!list.action" target="navTab" title="团员管理"><span>团员管理</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="4"></th>
				<th width="15">序号</th>
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
				<td></td>
				<td>
				    <s:property value="%{#statu.index+1+#request.startNum}"/>
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