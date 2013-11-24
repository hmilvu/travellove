<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
function save(){
	var $form = $($("#replyForm"));
	if (!$form.valid()) {
		return false;
	}
	$.ajax({
			type:'POST',
			url:'admin/message!createReply.action',
			data:$("#replyForm").formSerialize(),//序列化表单里所有的内容
			success: function(data){				
				switch(data.result){
						case "success":	
							navTabAjaxDone({"statusCode":"200", "message":"发布成功", "navTabId":"replyNavTab", "forwardUrl":"", "callbackType":"", "rel":""});	
							break;
						default:
							navTabAjaxDone({"statusCode":"300", "message":"发布失败，请重试", "navTabId":"replyNavTab", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});							
							break;	
					}
			}
			});
}
</script>  
<form id="pagerForm" method="post" action="admin/message!reply.action">
	<input type="hidden" name="messageId" value="<s:property value='%{#request.message.id}'/>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="15" />
</form>
<div class="pageFormContent" layoutH="20">
	<div class="pageHeader">	
		<fieldset>
			<legend>通知/消息</legend>
			<dl>
				<dt>标题：</dt>
				<dd><input name="field1" type="text" size="83" readonly="readonly" value="<s:property value='%{#request.message.topic}'/>"/></dd>
			</dl>
			<dl>&nbsp;
			</dl>
			<dl>
				<dt>状态：</dt>
				<dd>
					<s:if test="%{#request.message.status == 0}">审核中</s:if>
					<s:elseif test="%{#request.message.status == 1}">已通过</s:elseif>
					<s:elseif test="%{#request.message.status == 2}">已废弃</s:elseif>
					<s:else>&nbsp;</s:else>
				</dd>
			</dl>
			<dl>
				<dt>优先级：</dt>
				<dd>
					<s:if test="%{#request.message.priority == 0}">置顶</s:if>
					<s:elseif test="%{#request.message.priority == 1}">普通</s:elseif>
					<s:else>&nbsp;</s:else>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>内容：</dt>
				<dd><textarea name="textarea4" readonly="true" cols="80" rows="2"><s:property value='%{#request.message.content}'/></textarea></dd>
			</dl>			
		</fieldset>
	
		<form rel="pagerForm" id="replyForm" name="replyForm" class="pageForm required-validate" action="" method="post">
			<input type="hidden" name="messageId" value='<s:property value="%{#request.message.id}"/>'/>
			<div class="pageFormContent" layoutH="340">	
			<fieldset>
				<legend>回复</legend>
				<dl class="nowrap">
					<dt>内容：</dt>
					<dd><textarea name="content" class="required" cols="80" rows="2"></textarea></dd>
				</dl>
				<dl>
					<dt>&nbsp;</dt>
					<dd><button type="button" onclick="javascript:save();">发表回复</button></dd>
				</dl>
			</fieldset>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<table class="table" width="1160" layoutH="128">
			<thead>
				<tr>
					<th width="10">序号</th>
					<th width="280">内容</th>
					<th width="30">发布姓名</th>
					<th width="20">发表时间</th>
					<th width="10">操作</th>
				</tr>
			</thead>
			<tbody>
			    <s:iterator var="reply" value="%{#request.replyList}" status="statu">	
				<tr target="sid_user" rel="<s:property value="%{#reply.id}"/>">
					<td>
				   	 <s:property value="%{#statu.index+1+#request.startNum}"/>
					</td>
					<td><s:property value="%{#reply.content}"/></td>
					<td>
						<s:if test="#reply.memberInf != null">
							团员：<s:property value="%{#reply.memberInf.memberName}"/>
						</s:if>
						<s:elseif test="#reply.sysUser != null">
							系统用户：<s:property value="%{#reply.sysUser.name}"/>
						</s:elseif>
					</td>
					<td>
						<s:date name="%{#reply.createDate}" format="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
						<a title="确定要删除此回复吗？" target="ajaxTodo" href="admin/message!deleteReply.action?id=<s:property value="%{#reply.id}"/>" class="btnDel">删除</a>
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
</div>