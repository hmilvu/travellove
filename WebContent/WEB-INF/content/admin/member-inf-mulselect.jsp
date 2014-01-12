<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
    
<form id="pagerForm" method="post" action="admin/member-inf!mulselect.action?fromSelect=1">
	<input type="hidden" name="phoneNumber" value="<s:property value='%{#request.phoneNumber}'/>" />
	<input type="hidden" name="name" value="<s:property value='%{#request.name}'/>" />
	<input type="hidden" name="idNumber" value="<s:property value='%{#request.idNumber}'/>" />
	<input type="hidden" name="teamName" value="<s:property value='%{#request.teamName}'/>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="15" />
</form>

<div class="pageHeader">
	<form action="admin/member-inf!mulselect.action?fromSelect=1" method="post" onsubmit="return dwzSearch(this, 'dialog');">
	<input type="hidden" name="pageNumber" value="0" />
	<div class="searchBar">		
		<table class="searchContent">
			<tr>
				<td>
					姓名：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="name" id="name" value="<s:property value='%{#request.name}'/>"/>
				</td>
				<td>
					&nbsp;&nbsp;电话：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="text" name="phoneNumber" id="phoneNumber" value="<s:property value='%{#request.phoneNumber}'/>"/>
				</td>
			</tr>
			<tr>
				<td>
					证件号码：<input type="text" name="idNumber" id="idNumber" value="<s:property value='%{#request.idNumber}'/>"/>
				</td>
				<td>
					<label>团员类型：</label>
					<select class="combox" name="memberType">
						<option value="">所有</option>
						<option value="2" <s:if test="%{#request.memberType == 2}">selected</s:if> >游客</option>
						<option value="0" <s:if test="%{#request.memberType == 0}">selected</s:if> >导游</option>
						<option value="1" <s:if test="%{#request.memberType == 1}">selected</s:if> >司机</option>
					</select>
				</td>
				<td>
					旅行团名称：<input type="text" name="teamName" id="teamName" value="<s:property value='%{#request.teamName}'/>"/>
				</td>
			</tr>
		</table>
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
	<table class="table" width="100%" layoutH="128">
		<thead>
			<tr>
				<th width="30"><input type="checkbox" class="checkboxCtrl" group="orgId" /></th>
				<th width="40">旅行团ID</th>
				<th width="80">旅行团名称</th>
				<th width="60">姓名</th>
				<th width="60">电话</th>
				<th width="40">类型</th>
				<th width="80">证件号码</th>
				<th width="40">创建人</th>
				<th width="70">创建日期</th>			
			</tr>
		</thead>
		<tbody>
			<s:iterator var="member" value="%{#request.memberList}" status="statu">	
			<tr target="sid_user" rel="<s:property value="%{#member.id}"/>">
				<td><input type="checkbox" name="orgId" value="{id:'<s:property value="%{#member.id}"/>', orgName:'<s:property value="%{#member.memberName}"/>', orgNum:''}"/></td>
				<td>
				    <s:property value="%{#member.teamInfo.id}"/>
				</td>
				<td><s:property value="%{#member.teamInfo.name}"/></td>
				<td><s:property value="%{#member.memberName}"/></td>
				<td><s:property value="%{#member.travelerMobile}"/></td>
				<td>
					<s:if test="%{#member.memberType == 2}">游客</s:if>
					<s:elseif test="%{#member.memberType == 0}">导游</s:elseif>
					<s:elseif test="%{#member.memberType == 1}">司机</s:elseif>
					<s:else>&nbsp;</s:else>
				</td>
				<td><s:property value="%{#member.idNo}"/></td>
				<td><s:property value="%{#member.sysUser.name}"/></td>
				<td><s:date name="#member.createDate" format="yyyy-MM-dd" /></td>
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