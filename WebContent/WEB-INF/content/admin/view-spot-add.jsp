<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="js/admin/view-spot-add.js" type="text/javascript"></script>
<div class="pageContent">
	<form name="viewForm" id="viewForm" method="post" action="" class="pageForm required-validate">
		<div class="pageFormContent" layoutH="56" style="height: 125px; overflow: auto;">			
			<dl>
				<dt>景点名称：</dt>
				<dd>
					<input type="text" name="name" maxlength="64" class="required" size="35"/>
				</dd>
			</dl>
			<dl></dl>	
			<dl>
				<dt>景点介绍：</dt>
				<dd>
					<textarea name="description" id="description" cols="83" rows="4" class="required textInput" maxlength="5000"></textarea>
				</dd>
			</dl>	
			<dl></dl>
			<dl></dl>
			<dl></dl>
			<dl></dl>
			<dl></dl>
			<dl>
				<dt>景点地址：</dt>
				<dd>
					<input type="text" name="address" maxlength="120" size="100"/>
				</dd>
			</dl>
			<dl></dl>
			<dl>
				<dt>查找景点：</dt>
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
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="save();">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>		
