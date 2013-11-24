<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
function save(){
	var $form = $($("#objectForm"));

	if (!$form.valid()) {
		return false;
	}
	$.ajax({
			type:'POST',
			url:'admin/item-inf!create.action',
			data:$("#objectForm").formSerialize(),//序列化表单里所有的内容
			success: function(data){				
				switch(data.result){
						case "success":	
							navTabAjaxDone({"statusCode":"200", "message":"保存成功", "navTabId":"销售品管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});	
							break;
						default:
							navTabAjaxDone({"statusCode":"300", "message":"保存失败，请重试", "navTabId":"销售品管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});							
							break;	
					}
			}
			});
}
</script>

<div class="pageContent">
	<form name="objectForm" id="objectForm" idmethod="post" action="" class="pageForm required-validate">
		<div class="pageFormContent nowrap" layoutH="56">			
			<dl>
				<dt>名称：</dt>
				<dd>
					<input type="text" name="name" maxlength="64" size="40" class="required" />
				</dd>
			</dl>
			<dl>
				<dt>品牌：</dt>
				<dd>
					<input type="text" name="brands" maxlength="64" size="40"/>
				</dd>
			</dl>
			<dl>
				<dt>规格：</dt>
				<dd>
					<input type="text" name="specification" maxlength="64" size="40"/>
				</dd>
			</dl>
			<dl>
				<dt>单价（元）：</dt>
				<dd>
					<input type="text" name="price" class="number required" maxlength="20" size="40"/>
				</dd>
			</dl>
			<dl>
				<dt>姓名：</dt>
				<dd>
					<input type="text" name="contactName" maxlength="64" class="required" alt="请输入负责人姓名" size="40"/>
				</dd>
			</dl>
			<dl>
				<dt>手机号：</dt>
				<dd>
					<input type="text" name="contactPhone" maxlength="20" class="required phone" alt="请输入负责人的手机号" size="40"/>
				</dd>
			</dl>
			<dl>
				<dt>产品介绍：</dt>
				<dd>
					<textarea name="description" cols="80" rows="4" maxlength="1000"></textarea>
				</dd>
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
