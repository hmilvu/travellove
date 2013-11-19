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
			url:'admin/route-inf!create.action',
			data:$("#viewForm").formSerialize(),//序列化表单里所有的内容
			success: function(data){				
				switch(data.result){
						case "success":	
							navTabAjaxDone({"statusCode":"200", "message":"保存成功", "navTabId":"线路管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});	
							break;
						case "input":	
							navTabAjaxDone({"statusCode":"300", "message":"经纬度格式不正确，保存失败", "navTabId":"线路管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});	
							break;
						default:
							navTabAjaxDone({"statusCode":"300", "message":"保存失败，请重试", "navTabId":"线路管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});							
							break;	
					}
			}
			});
}
</script>
<form name="viewForm" id="viewForm" method="post" action="" class="pageForm required-validate">
	<div class="pageContent">
	<div class="pageFormContent" layoutH="57">
		<dl class="nowrap">
			<dt>线路名称：</dt>
			<dd>
				<input type="text" name="name" maxlength="64" class="required"/>
			</dd>
		</dl>
		<dl class="nowrap">
			<dt>出发地址：</dt>
			<dd>
				<input name="org3.id" value="" type="hidden">
				<input name="org3.orgName" type="text"/>
				<a class="btnLook" href="location!select.action" lookupGroup="org3">查找带回</a>
				<span class="info">点击查找获得出发地经纬度</span>
			</dd>
		</dl>
		<dl class="nowrap">
			<dt>出发地址经纬度：</dt>
			<dd>
				<input class="readonly" name="org3.orgNum" readonly="readonly" type="text"/>
			</dd>
		</dl>

		<dl class="nowrap">
			<dt>结束地址：</dt>
			<dd>
				<input name="org3.id" value="" type="hidden">
				<input name="org3.orgName" type="text"/>
				<a class="btnLook" href="demo/database/dwzOrgLookup2.html" lookupGroup="org3">查找带回</a>
				<span class="info">点击查找获得结束地经纬度</span>
			</dd>
		</dl>
		<dl class="nowrap">
			<dt>结束地址经纬度：</dt>
			<dd>
				<input class="readonly" name="org3.orgNum" readonly="readonly" type="text"/>
			</dd>
		</dl>		
		<h3 class="contentTitle">&nbsp;</h3>
		<div class="tabs">
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li class="selected"><a href="javascript:void(0)"><span>线路包括景点</span></a></li>						
					</ul>
				</div>
			</div>
			<div class="tabsContent" style="height: 180px;">
				<div>
					<table class="list nowrap itemDetail" addButton="增加景点" width="100%">
						<thead>
							<tr>
								<th type="text" name="items[#index#].order" defaultVal="#index#" size="10" fieldClass="digits required">次序</th>
								<th type="lookup" name="items[#index#].org.orgName" lookupGroup="items[#index#].org" lookupUrl="admin/view-spot!selectView.action" suggestUrl="" suggestFields="" postField="keywords" size="60" fieldClass="required readonly">景点名称</th>
								<th type="del" width="60">操作</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>				
			</div>
			<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
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
