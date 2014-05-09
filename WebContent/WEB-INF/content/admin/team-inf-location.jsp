<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.travel.common.dto.LocationLogDTO"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=BCb9aa9f6e09df9416488b5ac74a20b7"></script>
	<div class="pageFormContent" layoutH="26">			
		<dl>
			<dt>旅行团：</dt>
			<dd><s:property value='%{#request.editTeam.name}'/>		
			</dd>
		</dl>
		<dl>
			<dt>定位到团员到人数：</dt>
			<dd><select class="combox" id="memberMap" name="memberMap" onChange="moveMap();">
					<option value="-1">---------所有-------</option>
					<s:iterator var="location" value="%{#request.locationList}" status="statu">
						<option value="<s:property value='%{#location.memberId}'/>" >
							<s:property value='%{#location.memberName}'/>
						</option>
					</s:iterator>
				</select>
				<a class="button" href="admin/member-inf!routeView.action" target="navTab" title="查看轨迹" rel="dlg_member_route"><span>查看轨迹</span></a>
			</dd>
		</dl>
		<dl>
			<dt><div id="allmap" style="width:1080px;height:390px;border:solid 1px gray"></div></dt>
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
var map = new BMap.Map("allmap");
var centerPoint = new BMap.Point(<%=centerLongi%>, <%=centerLati%>);
map.centerAndZoom(centerPoint, 11);
map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
// 编写自定义函数,创建标注
function addMarker(num, point, name, tele, timeStr){
  	var marker = new BMap.Marker(point);
  	map.addOverlay(marker);
  	var infoWindow1 = new BMap.InfoWindow("<b>名称：" + name + "</b><br><br>电话：" + tele + "<br><br>记录时间："+timeStr);
	marker.addEventListener("click", function(){this.openInfoWindow(infoWindow1);});
	if(num == 0){
		map.openInfoWindow(infoWindow1,point);
	}
}
var latiArray=new Array();
var longiArray=new Array();
<%
List<LocationLogDTO> list = (List<LocationLogDTO>)request.getAttribute("locationList");
for (int i = 0; i < list.size(); i++) {
	LocationLogDTO dto = list.get(i);
%>
	latiArray[<%=i%>] = <%=dto.getLatitude()%>;
	longiArray[<%=i%>] = <%=dto.getLongitude()%>;
	var point = new BMap.Point(longiArray[<%=i%>], latiArray[<%=i%>]);
 	addMarker("<%=i%>", point, "<%=dto.getMemberName()%>" + " / " + "<%=dto.getNickName()%>", "<%=dto.getTravelerMobile()%>", "<%=dto.getCreateDate()%>");
<%
}
%>

function moveMap(){
	var memberSeq = $("#memberMap")[0].selectedIndex - 1;
	if(memberSeq == -1){
		return;
	}
	var memberPoint = new BMap.Point(longiArray[memberSeq], latiArray[memberSeq]);
	map.centerAndZoom(memberPoint, 15); 
	$.ajax({
			type:'POST',
			url:'admin/member-inf!saveMemberId.action?memberId=' + $("#memberMap").val(),
			data:null,//序列化表单里所有的内容
			success: null
			});
}
</script>
