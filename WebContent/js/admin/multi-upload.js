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
		$("#upload1").val("");
		$("#imghead1").attr("src","a");
		$("#imgId1").val("");
	} else if(num == 2){
		$("#upload2").val("");
		$("#imghead2").attr("src","a");
		$("#imgId2").val("");
	} else if(num == 3){
		$("#upload3").val("");
		$("#imghead3").attr("src","a");
		$("#imgId3").val("");
	}

}

function checkupload(){
	var iframe = $("#callbackframe");
	var doc = iframe[0].contentDocument || iframe[0].content;
	var response = $(doc).find("body").text();;
	response = jQuery.parseJSON(response);
	if(response.code == "0"){
		navTabAjaxDone({"statusCode":"200", "message":"上传成功", "navTabId":"销售品管理", "forwardUrl":"", "callbackType":"", "rel":""});	
	} else {
		navTabAjaxDone({"statusCode":"300", "message":"上传失败，请重试", "navTabId":"销售品管理", "forwardUrl":"", "callbackType":"closeCurrent", "rel":""});							
	}
}