<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
function printQrCode(){
	var aa = "";
	$("input[name='ids']:checkbox:checked").each(function(){ 
		aa+=$(this).val() + ",";
	}); 
	if(aa == ""){
		alertMsg.info('请选择会员！');
		return;
	}
	var timenow = new Date().getTime(); 
	window.open ('admin/member-inf!print.action?t='+timenow+'&ids='+aa,'打印二维码','height=700,width=1100,top=50,left=50,toolbar=yes,menubar=yes,scrollbars=yes, resizable=yes, status=yes'); 
}
</script>
    
<form id="pagerForm" method="post" action="admin/member-inf!list.action">
	<input type="hidden" name="phoneNumber" value="<s:property value='%{#request.phoneNumber}'/>" />
	<input type="hidden" name="name" value="<s:property value='%{#request.name}'/>" />
	<input type="hidden" name="idNumber" value="<s:property value='%{#request.idNumber}'/>" />
	<input type="hidden" name="teamName" value="<s:property value='%{#request.teamName}'/>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="15" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="admin/member-inf!list.action" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					姓名：<input type="text" name="name" id="name" value="<s:property value='%{#request.name}'/>"/>
				</td>
				<td>
					电话：<input type="text" name="phoneNumber" id="phoneNumber" value="<s:property value='%{#request.phoneNumber}'/>"/>
				</td>
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
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="admin/member-inf!add.action" target="navTab" title="添加团员"><span>添加团员</span></a></li>
			<li><a title="确实要删除这些会员吗?" target="selectedTodo" rel="ids" postType="string" href="admin/member-inf!delete.action" class="delete"><span>批量删除</span></a></li>
			<li><a class="edit" href="admin/member-inf!edit.action?uid={sid_user}" target="navTab" warn="请选择一个团员"><span>修改团员</span></a></li>
			<li class="line">line</li>
			<!-- li><a class="icon" href="demo/common/dwz-team.xls" target="dwzExport" targetType="navTab" title="实要导出这些团员吗?"><span>导出EXCEL</span></a></li> -->
			<li><a class="icon" href="javascript:printQrCode();"><span>打印二维码</span></a></li>
		</ul>
	</div>
	<table class="table" width="1120" layoutH="138">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="80">旅行团名称</th>
				<th width="60">姓名</th>
				<th width="60">电话</th>
				<th width="50">类型</th>
				<th width="80">证件号码</th>
				<th width="60">创建人</th>
				<th width="70">创建日期</th>
				<th width="30">百度UserId</th>
				<th width="70">操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator var="member" value="%{#request.memberList}" status="statu">	
			<tr target="sid_user" rel="<s:property value="%{#member.id}"/>">
				<td><input name="ids" value="<s:property value="%{#member.id}"/>" type="checkbox"></td>
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
				<td><s:property value="%{#member.baiduUserId}"/></td>
				<td>
					<a href="admin/member-inf!view.action?uid=<s:property value='%{#member.id}'/>" target="navTab" style='color:Peru' title="查看团员">查看</a>
					&nbsp;&nbsp;<a href="admin/member-inf!routeView.action?memberId=<s:property value='%{#member.id}'/>" target="navTab" style='color:Peru' title="查看轨迹">轨迹</a>
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