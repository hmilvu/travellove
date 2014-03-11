<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var longEdit = '${requestScope.editView.longitude}';
var latiEdit = '${requestScope.editView.latitude}';
</script>
<script src="js/admin/view-spot-edit.js" type="text/javascript"></script>
<div class="pageContent">
	<form name="viewForm" id="viewForm" method="post" action="" class="pageForm required-validate">
		<input type="hidden" id="viewSpotId" name="viewSpotId" value="<s:property value='%{#request.editView.id}'/>"/>
		<div class="pageFormContent" layoutH="56" style="height: 125px; overflow: auto;">			
			<dl>
				<dt>景点名称：</dt>
				<dd>
					<s:property value='%{#request.editView.name}'/>
				</dd>
			</dl>
			<dl></dl>	
			<dl>
				<dt>景点介绍：</dt>
				<dd>
					<textarea name="description" id="description" cols="83" rows="4" readonly class="disabled" maxlength="5000"><s:property value='%{#request.editView.description}'/></textarea>
				</dd>
			</dl>	
			<dl></dl>
			<dl></dl>
			<dl></dl>
			<dl></dl>
			<dl></dl>
			<dl>
				<dt>所在城市：</dt>
				<s:iterator value="#request.areaList" id="c" status="st">
						<s:if test="%{#request.editView.province == #c.cityCode}"><s:property value="#c.cityName"/></s:if>
				</s:iterator>
				<s:iterator value="#request.subareaList" id="c" status="st">
						<s:if test="%{#request.editView.city == #c.cityCode}"><s:property value="#c.cityName"/></s:if>
				</s:iterator>	
			</dl>
			<dl></dl>
			<dl>
				<dt>景点地址：</dt>
				<dd>
					<s:property value='%{#request.editView.address}'/>
				</dd>
			</dl>
			<dl></dl>			
			<dl>
				<dt>经度：</dt>
				<dd>
					<s:property value='%{#request.editView.longitude}'/>
				</dd>
			</dl>
			<dl>
				<dt>维度：</dt>
				<dd>
					<s:property value='%{#request.editView.latitude}'/>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><a class="buttonActive" href="admin/view-spot!edit.action?uid=<s:property value='%{#request.editView.id}'/>" target="navTab" title="修改景点"><span>修改景点</span></a></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>		
