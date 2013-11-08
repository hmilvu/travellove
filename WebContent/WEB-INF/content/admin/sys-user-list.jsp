<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
    
<form id="pagerForm" method="post" action="admin/sys-user!list.action">
	<input type="hidden" name="username" value="<s:property value='%{#request.username}'/>" />
	<input type="hidden" name="name" value="<s:property value='%{#request.name}'/>" />
	<input type="hidden" name="userType" value="<s:property value='%{#request.userType}'/>" />
	<input type="hidden" name="travelName" value="<s:property value='%{#request.travelName}'/>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="15" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="admin/sys-user!list.action" method="post">
	<input type="hidden" name="pageNumber" value="0" />
	<div class="searchBar">		
		<table class="searchContent">
			<tr>
				<td>
					登陆名：<input type="text" name="username" id="username" value="<s:property value='%{#request.username}'/>"/>
				</td>				
				<td>
					用户姓名：<input type="text" name="name" id="name" value="<s:property value='%{#request.name}'/>"/>
				</td>
				<td>
					<label>用户类型：</label>
					<select id="userType" name="userType" class="combox">
						<option value="-1">请选择</option>
						<option value="0" <s:if test="%{#request.userType == 0}">selected</s:if> >系统管理员</option>
						<option value="1" <s:if test="%{#request.userType == 1}">selected</s:if> >系统用户</option>
						<option value="2" <s:if test="%{#request.userType == 2}">selected</s:if> >旅行社用户</option>
					</select>
				</td>
				<td>
					旅行社名称：<input type="text" name="travelName" id="travelName" value="<s:property value='%{#request.travelName}'/>"/>
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
			<s:if test="%{#session.SYS_USER_INF_IN_SESSION.userType == 0}">
				<li><a class="add" href="admin/sys-user!addAdmin.action" target="navTab" title="添加系统管理员"><span>添加系统管理员</span></a></li>
			</s:if>
			<s:if test="%{#session.SYS_USER_INF_IN_SESSION.userType == 0 || #session.SYS_USER_INF_IN_SESSION.userType == 1}">
				<li><a class="add" href="admin/sys-user!addSysUser.action" target="navTab" title="添加系统用户"><span>添加系统用户</span></a></li>
			</s:if>
			<li><a class="add" href="admin/sys-user!addTravelUser.action" target="navTab" title="添加旅行社用户"><span>添加系旅行社用户</span></a></li>
			<li><a class="edit" href="admin/sys-user!edit.action?uid={sid_user}" target="navTab" title="修改用户"><span>修改用户</span></a></li>
			<li><a class="edit" href="admin/sys-user!changePassword.action?uid={sid_user}" target="dialog" mask="true" title="修改用户"><span>修改密码</span></a></li>
			<li><a class="delete" href="admin/sys-user!delete.action?uid={sid_user}" target="ajaxTodo" title="确定要删除吗?"><span>删除用户</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="4"></th>
				<th width="15">序号</th>
				<th width="80">登陆名</th>
				<th width="150">姓名</th>
				<th width="80">用户类型</th>
				<th width="80">所属旅行社</th>
				<th width="80">状态</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator var="user" value="%{#request.userList}" status="statu">		
			<tr target="sid_user" rel="<s:property value="%{#user.id}"/>">
				<td></td>
				<td>
				    <s:property value="%{#statu.index+1+#request.startNum}"/>
				</td>
				<td>
					<s:property value='%{#user.username}' />
				</td>
				<td>
					<s:property value='%{#user.name}' />
				</td>
				<td>
					<s:if test="%{#user.userType == 0}">系统管理员</s:if>
            		<s:elseif test="%{#user.userType == 1}">系统用户</s:elseif>
					<s:elseif test="%{#user.userType == 2}">旅行社用户</s:elseif>
				</td>
				<td>
					<s:if test="%{#user.userType == 2}">
						<s:property value='%{#user.travelInf.name}' />
					</s:if>
					<s:else>&nbsp;</s:else>
				</td>
				<td>
					<s:if test="%{#user.status == 0}">未激活</s:if>
            		<s:elseif test="%{#user.status == 1}">正常</s:elseif>
					<s:elseif test="%{#user.status == 2}">禁用</s:elseif>
				</td>
			</tr>
		</s:iterator>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">			
			<span>共${userTotalCount}条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount="${userTotalCount}" numPerPage="15" pageNumShown="5" currentPage="<s:property value='%{#request.pageNumber}'/>"></div>

	</div>
</div>