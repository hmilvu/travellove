<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="js/admin/location-select.js" type="text/javascript"></script>
<div class="pageContent">
	<form name="viewForm" id="viewForm" method="post" action="" class="pageForm required-validate">
		<div class="pageFormContent" layoutH="56" style="height: 125px; overflow: auto;">						
			<dl>
				<dt>位置：</dt>
				<dd>
					<input type="text" name="OrderQueue[address]" id="OrderQueue_address" size=35 class="required" alt="请输入关键字查询景点"/>
				</dd>
			</dl>
			<dl></dl>	
			<dl>
				<dt>经度：</dt>
				<dd>
					<input type="text" name="longitudeInput" id="longitudeInput" size="35" class="required disabled"/>
				</dd>
			</dl>
			<dl>
				<dt>维度：</dt>
				<dd>
					<input type="text" name="latitudeInput" id="latitudeInput" size="35" class="required disabled"/>
				</dd>
			</dl>	
			<dl>
				<dt></dt>
				<dd>
					<div id="map_canvas" style="width:500px;height:400px;border:solid 1px gray"></div>
				</dd>
			</dl>	
			<dl>
				<dt>&nbsp;</dt>
				<dd><div id="map_poilist" style="height:400px;border:solid 1px gray"></div></dd>
			</dl>	
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="javascript:bringBackLocation();">选择带回</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>		
