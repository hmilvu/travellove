<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<script type="text/javascript">
$(document).ready(function(){
	var allMenuItemsStr ='${requestScope.ALL_MENU_INF_STR}';
	$('#allMenuItem').html(allMenuItemsStr);
});

function save(){
	var $form = $($("#roleForm"));

	if (!$form.valid()) {
		return false;
	}
	$.ajax({
			type:'POST',
			url:'admin/role-inf!create.action',
			data:$("#roleForm").formSerialize(),//序列化表单里所有的内容
			success: function(data){				
				switch(data.result){
						case "success":	
							navTabAjaxDone({"statusCode":"200", "message":"保存成功", "navTabId":"角色管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});	
							break;
						case "input":	
							navTabAjaxDone({"statusCode":"300", "message":"角色名已存在，请修改后重试"});	
							break;
						default:
							navTabAjaxDone({"statusCode":"300", "message":"保存失败，请重试", "navTabId":"角色管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});							
							break;	
					}
			}
			});
}
</script>

<div class="pageContent">
	<form name="roleForm" id="roleForm" method="post" action="" class="pageForm required-validate">
		<div class="pageFormContent">
			<p>
				<label>角色名称：</label>
				<input name="name" id="name" class="required" type="text" size="30" value="" alt="请输入角色名称" maxlength="64"/>
			</p>
		</div>
		<div class="pageFormContent">	
				<label>角色描述：</label>
				<textarea name="description" id="description" cols="80" rows="4" maxlength="128"></textarea>
				<!--<textarea name="description" id="description" style="width:40%;height:120px"></textarea>		
		--></div>
		
		<div class="pageFormContent">	
			<label>可访问页面：</label>				
			<div style="float:left; display:block; margin:0px; overflow:auto; width:200px; height:264px; overflow:auto; border:solid 1px #CCC; line-height:21px; background:#FFF;">
				<ul id="allMenuItem" name="allMenuItem" class="tree treeFolder treeCheck expand" oncheck="kkk">
							
				</ul>		
			</div>
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
			