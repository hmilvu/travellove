<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.travel.common.dto.LocationLogDTO"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=BCb9aa9f6e09df9416488b5ac74a20b7"></script>
	<div class="pageFormContent" layoutH="26">			
		<dl>
			<dt>名称：</dt>
			<dd><s:property value='%{#request.member.memberName}'/> / <s:property value='%{#request.member.nickname}'/>&nbsp;	
			</dd>
		</dl>
		<dl>
			<dt>电话：</dt>
			<dd><s:property value='%{#request.member.travelerMobile}'/>
			</dd>
		</dl>
		<dl>
			<dt><div id=memberallmap style="width:1080px;height:390px;border:solid 1px gray"></div></dt>
			<dd></dd>
		</dl>
	</div>
	<%
	Double centerLati = (Double)request.getAttribute("centerLati");
	Double centerLongi = (Double)request.getAttribute("centerLongi");
	if(centerLati == null || centerLongi == null){
		centerLongi = 121.48;
		centerLati = 31.22;
	}
 %>
<script type="text/javascript">
// 百度地图API功能
var map = new BMap.Map("memberallmap");
var point = new BMap.Point(<%=centerLongi%>, <%=centerLati%>);
map.centerAndZoom(point, 11);
map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
// 编写自定义函数,创建标注
function addMarker(point, name, timeStr, longi, lati){
  	var marker = new BMap.Marker(point);
  	map.addOverlay(marker);
  	var infoWindow1 = new BMap.InfoWindow("<b>序号：" + name + "</b><br><br>记录时间："+timeStr + "<br><br>经纬度：" + longi + ", " + lati);
	marker.addEventListener("click", function(){this.openInfoWindow(infoWindow1);});
}
<%
List<LocationLogDTO> list = (List<LocationLogDTO>)request.getAttribute("locationList");
if(list == null || list.size() == 0){
%>
alertMsg.info('该游客暂无轨迹记录！');
<%
}
%>
var latiArray=new Array();
var longiArray=new Array();
var pointArray=new Array(<%=list.size()%>);
<%
for (int i = 0; i < list.size(); i++) {
	LocationLogDTO dto = list.get(i);
%>
	latiArray[<%=i%>] = <%=dto.getLatitude()%>;
	longiArray[<%=i%>] = <%=dto.getLongitude()%>;
	var point = new BMap.Point(longiArray[<%=i%>], latiArray[<%=i%>]);
	pointArray[<%=i%>] = point;
 	addMarker(point, "<%=i+1%>", "<%=dto.getCreateDate()%>", longiArray[<%=i%>], latiArray[<%=i%>]);
<%
}
%>
var polyline = new BMap.Polyline(pointArray, {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5}); 
map.addOverlay(polyline);
</script>
