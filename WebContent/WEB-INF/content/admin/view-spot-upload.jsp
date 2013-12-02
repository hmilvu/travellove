<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
function previewImage(file, imageDiv, imageId)  
{  
  var MAXWIDTH  = 200;  
  var MAXHEIGHT = 200;  
  var div = document.getElementById(imageDiv);  
  if (file.files && file.files[0])  
  {  
    div.innerHTML = '<img id='+imageId+'>';  
    var img = document.getElementById(imageId);  
    img.onload = function(){  
      var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);  
      img.width = rect.width;  
      img.height = rect.height;  
      img.style.marginLeft = rect.left+'px';  
      img.style.marginTop = rect.top+'px';  
    }  
    var reader = new FileReader();  
    reader.onload = function(evt){img.src = evt.target.result;}  
    reader.readAsDataURL(file.files[0]);  
  }  
  else  
  {  
    var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="'; 
    file.select();  
    var src = document.selection.createRange().text;  
    div.innerHTML = '<img id='+imageId+'>';  
    var img = document.getElementById(imageId);  
    img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;  
    var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);  
    status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);  
    div.innerHTML = "<div id=divhead style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;margin-left:"+rect.left+"px;"+sFilter+src+"\"'></div>";  
  }  
}  
function clacImgZoomParam( maxWidth, maxHeight, width, height ){  
    var param = {top:0, left:0, width:width, height:height};  
    if( width>maxWidth || height>maxHeight )  
    {  
        rateWidth = width / maxWidth;  
        rateHeight = height / maxHeight;  
          
        if( rateWidth > rateHeight )  
        {  
            param.width =  maxWidth;  
            param.height = Math.round(height / rateWidth);  
        }else  
        {  
            param.width = Math.round(width / rateHeight);  
            param.height = maxHeight;  
        }  
    }  
      
    param.left = Math.round((maxWidth - param.width) / 2);  
    param.top = Math.round((maxHeight - param.height) / 2);  
    return param;  
}   
   
function deleteImg(num){
	if(num == 1){
		$("#imghead1").attr("src","a");
		$("#upload1").val("");
	} else if(num == 2){
		$("#upload2").val("");
		$("#imghead2").attr("src","a");
	} else if(num == 3){
		$("#upload3").val("");
		$("#imghead3").attr("src","a");
	}

}
</script>
<div class="pageContent">
	<form name="viewForm" id="viewForm" method="post" action="" class="pageForm required-validate">
		<input type="hidden" id="viewSpotId" name="viewSpotId" value="<s:property value='%{#request.editView.id}'/>"/>
		<div class="panel" defH="370">
			<h1><s:property value='%{#request.editView.name}'/></h1>
			<div>
				<table border="0" width="100%">
					<tr height="50">
						<td>
							图片：<input type="file" name="upload1" id="upload1" size="30" onchange="javascript:previewImage(this, 'preview1', 'imghead1');"/>	
						</td>
						<td>
							图片：<input type="file" name="upload2" id="upload2" size="30" onchange="javascript:previewImage(this, 'preview2', 'imghead2');"/>
						</td>
						<td>
							图片：<input type="file" name="upload3" id="upload3" size="30" onchange="javascript:previewImage(this, 'preview3', 'imghead3');"/>
						</td>
					</tr>
					<tr height="250">
						<td>
							<div id="preview1">  
							    <img id="imghead1" name="imghead1" width="200" height="200" border="0" src="a"/>  
							</div> 
						</td>
						<td>
							<div id="preview2">  
							    <img id="imghead2" name="imghead2" width="200" height="200" border="0" src="a"/>  
							</div> 
						</td>
						<td>
							<div id="preview3">  
							    <img id="imghead3" name="imghead3" width="200" height="200" border="0" src="a"/>  
							</div> 
						</td>
					</tr>
					<tr height="40">
						<td>
						<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="deleteImg(1);">删除</button></div></div>
						</td>
						<td>
						<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="deleteImg(2);">删除</button></div></div>
						</td>
						<td>
						<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="deleteImg(3);">删除</button></div></div>
						</td>
					</tr>
				</table>
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
