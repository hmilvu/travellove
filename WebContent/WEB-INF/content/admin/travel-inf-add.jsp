<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
function save(){
	var $form = $($("#travelForm"));

	if (!$form.valid()) {
		return false;
	}
	$.ajax({
			type:'POST',
			url:'admin/travel-inf!create.action',
			data:$("#travelForm").formSerialize(),//序列化表单里所有的内容
			success: function(data){				
				switch(data.result){
						case "success":	
							navTabAjaxDone({"statusCode":"200", "message":"保存成功", "navTabId":"旅行社管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});	
							break;
						case "input":	
							navTabAjaxDone({"statusCode":"300", "message":"旅行社已存在，请修改后重试"});	
							break;
						default:
							navTabAjaxDone({"statusCode":"300", "message":"保存失败，请重试", "navTabId":"旅行社管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});							
							break;	
					}
			}
			});
}
</script>

<div class="pageContent">
	<form name="travelForm" id="travelForm" method="post" action="" class="pageForm required-validate">
		<div class="pageFormContent" layoutH="56">			
			<p>
				<label>旅行社名称：</label>
				<input type="text" id="name" name="name" size="30" class="required" maxlength="128"/>
			</p>
			<p>
				<label>地址：</label>
				<input type="text" id="address" name="address" size="30" class="required" maxlength="128"/>
			</p>
			<p>
				<label>电话：</label>
				<input type="text" id="phone" name="phone" size="30" class="required" class="phone" maxlength="20"/>
			</p>
			<p>
				<label>联系人：</label>
				<input type="text" id="contact" name="contact" size="30" class="required" maxlength="20"/>
			</p>
			<p>
				<label>负责人：</label>
				<input type="text" id="linker" name="linker" size="30" class="required" maxlength="20"/>
			</p>
			<p></p>
			<p>
				<label>备注：</label>
				<textarea name="description" id="description" cols="112" rows="4" maxlength="1024"></textarea>
			</p>			
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
