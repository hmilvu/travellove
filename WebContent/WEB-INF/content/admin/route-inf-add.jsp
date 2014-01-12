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
				<input type="text" name="name" maxlength="64" class="required" size="50"/>
			</dd>
		</dl>
		<dl class="nowrap">
			<dt>出发地址：</dt>
			<dd>
				<input class="required" name="startLocation.name" type="text" postField="keyword" lookupGroup="startLocation"  size="50"/>
				<a class="btnLook" href="admin/location!select.action" lookupGroup="startLocation">查找带回</a>	
				<span class="info">查找获得出发地经纬度</span>
			</dd>
		</dl>
		<dl class="nowrap">
			<dt>出发地址经纬度：</dt>
			<dd>
				<input class="readonly required" name="startLocation.longandlati" readonly="readonly" type="text" size="50"/>
			</dd>
		</dl>

		<dl class="nowrap">
			<dt>结束地址：</dt>
			<dd>
				<input class="required" name="endLocation.name" type="text" postField="keyword" lookupGroup="endLocation"  size="50"/>
				<a class="btnLook" href="admin/location!select.action" lookupGroup="endLocation">查找带回</a>
				<span class="info">查找获得结束地经纬度</span>
			</dd>
		</dl>
		<dl class="nowrap">
			<dt>结束地址经纬度：</dt>
			<dd>
				<input class="readonly required" name="endLocation.longandlati" readonly="readonly" type="text" size="50"/>
			</dd>
		</dl>	
		<dl>
			<dt>线路介绍：</dt>
			<dd>
				<textarea name="description" id="description" cols="83" rows="3" class="textInput" maxlength="1024"></textarea>
			</dd>
		</dl>	
		<dl>&nbsp;</dl>
		<dl>&nbsp;</dl>
		<dl>&nbsp;</dl>
		<dl>&nbsp;</dl>
		<div class="panel" defH="150">
			<h1>线路包括的景点</h1>
			<div>
				<table class="list nowrap itemDetail" addButton="增加景点" width="100%">
					<thead>
						<tr>
							<th type="text" name="items[#index#].order" defaultVal="#index#" size="10" fieldClass="digits required">次序</th>
							<th type="lookup" name="items[#index#].viewSpotForm.viewName" lookupGroup="items[#index#].viewSpotForm" lookupUrl="admin/view-spot!selectView.action" postField="keywords" size="40" fieldClass="required readonly">景点名称</th>
							<th type="text" name="items[#index#].numberOfDay" defaultVal="#index#" size="10" fieldClass="digits required">第几天</th>
							<th type="date" name="items[#index#].viewSpotForm.startTime" dateFmt="HH:mm" mmStep="15" readonly="true" size=20 fieldClass="required">开始时间</th>
							<th type="date" name="items[#index#].viewSpotForm.endTime" dateFmt="HH:mm" size=20 mmStep="15" readonly="true" fieldClass="required">结束时间</th>
							<th type="del" width="60">操作</th>
						</tr>
					</thead>
					<tbody></tbody>
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
