/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.admin;

import java.util.Enumeration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.travel.action.AuthorityAction;
import com.travel.common.Constants;
import com.travel.common.admin.dto.SearchRouteDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.RouteInf;
import com.travel.entity.TeamRoute;
import com.travel.service.RouteInfService;
import com.travel.service.TeamInfoService;
import com.travel.utils.JsonUtils;

/**
 * @author Lenovo
 *
 */
public class RouteInfAction extends AuthorityAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private RouteInfService routeService;
	@Autowired
	private TeamInfoService teamService;

	public String list(){
		String name = request.getParameter("name");		
		String pageSize = request.getParameter("numPerPage");
		String pageNumber = request.getParameter("pageNum");
		PageInfoDTO pageInfo = new PageInfoDTO();
		try{
			pageInfo.setPageNumber(Integer.valueOf(pageNumber.toString()));
		}catch(Throwable ignore){	
			pageInfo.setPageNumber(1);
		}
		try{			
			pageInfo.setPageSize(Integer.valueOf(pageSize.toString()));
		}catch(Throwable ignore){	
			pageInfo.setPageSize(Constants.ADMIN_DEFAULT_PAGE_SIZE);
		}
		SearchRouteDTO dto = new SearchRouteDTO();		
		dto.setName(name);
		dto.setTravelId(getCurrentUser().getTravelInf().getId());
		int totalNum = routeService.getTotalRouteNum(dto);
		List<RouteInf> list = routeService.findRoute(dto, pageInfo);
		request.setAttribute("routeList", list);
		request.setAttribute("totalCount", totalNum+"");
		request.setAttribute("name", name);
		request.setAttribute("pageNumber", pageNumber == null ? 1 : pageNumber);
		request.setAttribute("startNum", (pageInfo.getPageNumber()-1)*pageInfo.getPageSize());
		return "list";
	}
	
	public String add(){
		return "add";
	}
	
	@SuppressWarnings("static-access")
	public void create(){
		Enumeration ee = request.getParameterNames();
		while(ee.hasMoreElements()){
			String key = ee.nextElement().toString();
			System.out.println(key + " = " + request.getParameter(key));
		}
		String name = request.getParameter("name");
		String startAddress = request.getParameter("startAddress");
		String startLngStr = request.getParameter("startLng");
		String startLatStr = request.getParameter("startLat");
		String endAddress = request.getParameter("endAddress");
		String endLngStr = request.getParameter("endLng");
		String endLatStr = request.getParameter("endLat");
		String description = request.getParameter("description");
		Double startLng = null;
		Double startLat = null;
		Double endLng = null;
		Double endLat = null;
		try{
			startLng = Double.valueOf(startLngStr);
			startLat = Double.valueOf(startLatStr);
			endLng = Double.valueOf(endLngStr);
			endLat = Double.valueOf(endLatStr);
		} catch(Throwable e){
			log.error("线路经纬度错误 ", e);
			JsonUtils.write(response, binder.toJson("result", Action.INPUT));
			return;
		}
		
		RouteInf route = new RouteInf();
		route.setRouteName(name);
		route.setDescription(description);
		route.setStartAddress(startAddress);
		route.setEndAddress(endAddress);
		route.setStartLongtitude(startLng);
		route.setEndLongitude(endLng);
		route.setStartLatitude(startLat);
		route.setEndLatitude(endLat);
		route.setSysUser(getCurrentUser());
		if(routeService.addRoute(route) == 0){
			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));			
		} else {
			JsonUtils.write(response, binder.toJson("result", Action.ERROR));		
		}
	}
	
	public void delete(){
		String ids = request.getParameter("ids");
		List<TeamRoute> list = teamService.findRouteByIds(ids);
		if(list != null && list.size() > 0){
			JsonUtils.write(response, "{\"statusCode\":\"300\", \"message\":\"该线路已被规划到旅行团中，不能删除\"}");
		}
		routeService.deleteRouteByIds(ids);
		JsonUtils.write(response, "{\"statusCode\":\"200\", \"message\":\"删除成功\", \"navTabId\":\"线路管理\", \"forwardUrl\":\"\", \"callbackType\":\"\", \"rel\":\"\"}");
	}
	
	public String edit(){
		String id = request.getParameter("uid");
		Long idLong = 0L;
		try{
			idLong = Long.valueOf(id);
		}catch(Throwable ignore){	
			return "edit";
		}
		RouteInf route = routeService.getRouteInfById(idLong);	
		if(route != null && route.getId() > 0){
			request.setAttribute("editRoute", route);
		}
		return "edit";
	}
	
	@SuppressWarnings("static-access")
	public void update(){
		String id = request.getParameter("viewSpotId");		
		String name = request.getParameter("name");
		String startAddress = request.getParameter("startAddress");
		String startLngStr = request.getParameter("startLng");
		String startLatStr = request.getParameter("startLat");
		String endAddress = request.getParameter("endAddress");
		String endLngStr = request.getParameter("endLng");
		String endLatStr = request.getParameter("endLat");
		String description = request.getParameter("description");
		Double startLng = null;
		Double startLat = null;
		Double endLng = null;
		Double endLat = null;
		try{
			startLng = Double.valueOf(startLngStr);
			startLat = Double.valueOf(startLatStr);
			endLng = Double.valueOf(endLngStr);
			endLat = Double.valueOf(endLatStr);
		} catch(Throwable e){
			log.error("线路经纬度错误 ", e);
			JsonUtils.write(response, binder.toJson("result", Action.INPUT));
			return;
		}
		
		RouteInf route = routeService.getRouteInfById(Long.valueOf(id));
		route.setRouteName(name);
		route.setDescription(description);
		route.setStartAddress(startAddress);
		route.setEndAddress(endAddress);
		route.setStartLongtitude(startLng);
		route.setEndLongitude(endLng);
		route.setStartLatitude(startLat);
		route.setEndLatitude(endLat);

		if(routeService.updateRoute(route) == 0){
			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));			
		} else {
			JsonUtils.write(response, binder.toJson("result", Action.ERROR));
		}
		
	}	
}
