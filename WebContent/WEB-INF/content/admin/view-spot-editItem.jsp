<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
function save(){
	var $form = $($("#viewForm"));

	if (!$form.valid()) {
		return false;
	}
	$.ajax({
			type:'POST',
			url:'admin/view-spot!updateItem.action',
			data:$("#viewForm").formSerialize(),//序列化表单里所有的内容
			success: function(data){				
				switch(data.result){
						case "success":	
							navTabAjaxDone({"statusCode":"200", "message":"保存成功", "navTabId":"景点管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});	
							break;
						default:
							navTabAjaxDone({"statusCode":"300", "message":"保存失败，请重试", "navTabId":"景点管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});							
							break;	
					}
			}
			});
}
</script>
<form name="viewForm" id="viewForm" method="post" action="" class="pageForm required-validate">
	<input type="hidden" name="viewSpotId" value='<s:property value="%{#request.editView.id}"/>'/>
	<div class="pageContent">
	<div class="pageFormContent" layoutH="57">
		<dl class="nowrap">
			<dt>景点名称：</dt>
			<dd>
				<s:property value='%{#request.editView.name}'/>
			</dd>
		</dl>
		<dl></dl>
		<div class="panel" defH="280">
			<h1>相关销售品</h1>
			<div>
				<table class="list nowrap itemDetail" addButton="增加销售品" width="100%">
					<thead>
						<tr>
							<th type="text" name="items[#index#].order" defaultVal="#index#" size="10" fieldClass="digits required">次序</th>
							<th type="lookup" name="items[#index#].viewItemForm.name" lookupGroup="items[#index#].viewItemForm" lookupUrl="admin/item-inf!selectView.action" postField="keywords" size="60" fieldClass="required readonly">销售品名称</th>
							<th type="del" width="60">操作</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator var="itemInf" value="%{#request.itemInfList}" status="statu">
						<tr class="unitBox">
							<td>
								<input class="digits required textInput" type="text" size="10" value="<s:property value='%{#statu.index+1}'/>" name="items[<s:property value='%{#statu.index}'/>].order">
							</td>
							<td>
								<input type="hidden" name="items[<s:property value='%{#statu.index}'/>].viewItemForm.id" value="<s:property value='%{#itemInf.id}'/>">
								<input class="required readonly textInput" type="text" size="60" lookuppk="id" name="items[<s:property value='%{#statu.index}'/>].viewItemForm.name" value="<s:property value='%{#itemInf.name}'/>">
								<a class="btnLook" title="查找带回" lookuppk="id" lookupgroup="items[<s:property value='%{#statu.index}'/>].viewItemForm" href="admin/item-inf!selectView.action">查找带回</a>
							</td>
							<td>
								<a class="btnDel " href="javascript:void(0)">删除</a>
							</td>
						</tr>
						</s:iterator>
					</tbody>
				</table>				
			</div>
		</div>
		
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="save();">保存</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>
		</ul>
	</div>
</div>
</form>
