<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
    
<form id="pagerForm" method="post" action="admin/item-inf!list.action">
	<input type="hidden" name="brands" value="<s:property value='%{#request.brands}'/>" />
	<input type="hidden" name="name" value="<s:property value='%{#request.name}'/>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="15" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="admin/item-inf!list.action" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					品牌：<input type="text" name="brands" value="<s:property value='%{#request.brands}'/>"/>
				</td>
				<td>
					名称：<input type="text" name="name" value="<s:property value='%{#request.name}'/>"/>
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
			<li><a class="add" href="admin/item-inf!add.action" target="navTab" title="添加销售品"><span>添加销售品</span></a></li>
			<li><a title="确实要删除这些产品吗?" target="selectedTodo" rel="ids" postType="string" href="admin/item-inf!delete.action" class="delete"><span>批量删除</span></a></li>
			<li><a class="edit" href="admin/item-inf!edit.action?uid={sid_user}" target="navTab" warn="请选择一个团员"><span>修改销售品</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="1120" layoutH="138">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="80">名称</th>
				<th width="60">品牌</th>
				<th width="60">规格</th>
				<th width="40">单价（元）</th>
				<th width="60">类型</th>
				<th width="40">创建人</th>
				<th width="70">创建日期</th>
				<th width="70">操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator var="itemInf" value="%{#request.itemList}" status="statu">	
			<tr target="sid_user" rel="<s:property value="%{#itemInf.id}"/>">
				<td><input name="ids" value="<s:property value="%{#itemInf.id}"/>" type="checkbox"></td>
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
					<a class="btnAttach" href="admin/item-inf!upload.action?uid=<s:property value="%{#itemInf.id}"/>" target="navTab" title="上传图片">上传图片</a>
					<a class="btnAssign" href="admin/item-inf!message.action?uid=<s:property value="%{#itemInf.id}"/>" target="navTab" title="查看评论">查看评论</a>
				&nbsp;&nbsp;<a href="admin/item-inf!view.action?uid=<s:property value='%{#itemInf.id}'/>" target="navTab" style='color:Peru' title="查看销售品">查看</a>
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