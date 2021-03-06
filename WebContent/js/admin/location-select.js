function bringBackLocation(){
	var longandlatiInput = $("#longitudeInput").val() + ", " + $("#latitudeInput").val();
	$.bringBack({longandlati:longandlatiInput});
}

var map = new BMap.Map("map_canvas");
var opts = {anchor: BMAP_ANCHOR_TOP_RIGHT, offset: new BMap.Size(2, 2)};
map.enableScrollWheelZoom();
map.addControl(new BMap.NavigationControl(opts));			
map.centerAndZoom(new BMap.Point(121.48, 31.22), 15);

window.openInfoWinFuns = null;
var options = {
  //renderOptions:{map: map},
  onSearchComplete: function(results){
    // 判断状态是否正确
    if (local.getStatus() == BMAP_STATUS_SUCCESS){
        var s = [];
        s.push('<div>');
        s.push('<div style="background: none repeat scroll 0% 0% rgb(255, 255, 255);">');
        s.push('<ol style="list-style: none outside none; padding: 0pt; margin: 0pt;">');
        openInfoWinFuns = [];
        for (var i = 0; i < results.getCurrentNumPois(); i ++){
            var marker = addMarker(results.getPoi(i).point,i);
            var openInfoWinFun = addInfoWindow(marker,results.getPoi(i),i);
            openInfoWinFuns.push(openInfoWinFun);
            // 默认打开第一标注的信息窗口
            var selected = "";
            if(i == 0){
                selected = "background-color:#f0f0f0;";
                openInfoWinFun();
            }
            s.push('<li id="list' + i + '" style="margin: 2px 0pt; padding: 0pt 5px 0pt 3px; cursor: pointer; overflow: hidden; line-height: 17px;' + selected + '" onclick="openInfoWinFuns[' + i + ']()">');
            s.push('<span style="width:1px;background:url(http://partner.d.edaijia.cn/sto/classic/www/images/red_labels.gif) 0 ' + ( 2 - i*20 ) + 'px no-repeat;padding-left:10px;margin-right:3px"> </span>');
            s.push('<span style="color:#00c;text-decoration:underline">' + results.getPoi(i).title.replace(new RegExp(results.keyword,"g"),'<b>' + results.keyword + '</b>') + '</span>');
            s.push('<span style="color:#666;"> - ' + results.getPoi(i).address + '</span>');
            s.push('</li>');
            s.push('');
        }
        s.push('</ol></div></div>');
        document.getElementById("map_poilist").innerHTML = s.join("");
    }
  }
};

//添加标注
function addMarker(point, index){
  var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
    offset: new BMap.Size(10, 25),
    imageOffset: new BMap.Size(0, 0 - index * 25)
  });
  var marker = new BMap.Marker(point, {icon: myIcon});
  map.addOverlay(marker);
  return marker;
}
// 添加信息窗口
function addInfoWindow(marker,poi,index){
    var maxLen = 10;
    var name = null;
    if(poi.type == BMAP_POI_TYPE_NORMAL){
        name = "地址：  "
    }else if(poi.type == BMAP_POI_TYPE_BUSSTOP){
        name = "公交：  "
    }else if(poi.type == BMAP_POI_TYPE_SUBSTOP){
        name = "地铁：  "
    }else {
    	name="";
    }
    
    // infowindow的标题
    var infoWindowTitle = '<div style="font-weight:bold;color:#CE5521;font-size:14px">'+poi.title+'</div>';
    // infowindow的显示信息
    var infoWindowHtml = [];
    infoWindowHtml.push('<table cellspacing="0" style="table-layout:fixed;width:100%;font:12px arial,simsun,sans-serif"><tbody>');
    infoWindowHtml.push('<tr>');
    infoWindowHtml.push('<td style="vertical-align:top;line-height:16px;width:38px;white-space:nowrap;word-break:keep-all">' + name + '</td>');
    infoWindowHtml.push('<td style="vertical-align:top;line-height:16px">' + poi.address + ' </td>');
    infoWindowHtml.push('</tr>');
    infoWindowHtml.push('</tbody></table>');
    var infoWindow = new BMap.InfoWindow(infoWindowHtml.join(""),{title:infoWindowTitle,width:200}); 
    var openInfoWinFun = function(){    	  
        marker.openInfoWindow(infoWindow);
        for(var cnt = 0; cnt < maxLen; cnt++){
            if(!document.getElementById("list" + cnt)){continue;}
            if(cnt == index){
                document.getElementById("list" + cnt).style.backgroundColor = "#f0f0f0";
                $("#longitudeInput").val(marker.point.lng);
                $("#latitudeInput").val(marker.point.lat);
            }else{
                document.getElementById("list" + cnt).style.backgroundColor = "#fff";
            }
        }
    }
    marker.addEventListener("click", openInfoWinFun);
    return openInfoWinFun;
}
	
var local = new BMap.LocalSearch(map, options);
local.disableFirstResultSelection();

$(document).ready(function(){
    $('input#OrderQueue_address').keydown(function(e){
    	if (e.keyCode == 13) {
        	map.clearOverlays();
			local.search($(this).val());
    	}			
	});

    $('input#OrderQueue_address').blur(function(e){
    	map.clearOverlays();
		local.search($(this).val());
	});

    $('input#OrderQueue_address').focus();
	//autocomplete();
	local_search();
})

function local_search(){

}

function autocomplete(){
	var location = "";
    var ac = new BMap.Autocomplete(
        	{"input" : "OrderQueue_address",
        	 "location" : location
        });

    ac.addEventListener("onhighlight", function(e) {
    	var str = "";
    	if(e.fromitem.value){
        	var _value = e.fromitem.value;
    	}
        var value = "";
        if (e.fromitem.index > -1) {
            value = _value.business;
        }    
        
        value = "";
        if (e.toitem.index > -1) {
            _value = e.toitem.value;
            value = _value.business;
        }
        //$('input#OrderQueue_address').val(_value.business);
    });

    var myValue;
    //鼠标点击下拉列表后的事件
    ac.addEventListener("onconfirm", function(e) {
    	var _value = e.item.value;
    	//  _value.province +  _value.city +  _value.district +  _value.street + 
    	myValue = _value.business;
    	$('input#OrderQueue_address').val(_value.business);
    });   	
}