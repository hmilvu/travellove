/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.admin;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.travel.action.AuthorityAction;
import com.travel.common.Constants;
import com.travel.common.admin.dto.SearchViewSpotDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.MemberInf;
import com.travel.entity.RouteViewSpot;
import com.travel.entity.TeamInfo;
import com.travel.entity.ViewSpotInfo;
import com.travel.service.RouteInfService;
import com.travel.service.ViewSpotService;
import com.travel.utils.JsonUtils;

/**
 * @author Lenovo
 *
 */
public class ViewSpotAction extends AuthorityAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ViewSpotService viewSpotService;
	@Autowired
	private RouteInfService routeService;

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
		SearchViewSpotDTO dto = new SearchViewSpotDTO();		
		dto.setName(name);
		if(isTravelUser()){
			dto.setTravelId(getCurrentUser().getTravelInf().getId());
		}
		int totalNum = viewSpotService.getTotalViewSpotNum(dto);
		List<ViewSpotInfo> list = viewSpotService.findViewSpots(dto, pageInfo);
		request.setAttribute("viewSpotList", list);
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
		String name = request.getParameter("name");
		String longitudeStr = request.getParameter("longitudeInput");
		String latitudeStr = request.getParameter("latitudeInput");
		String description = request.getParameter("description");
		Double longitude = null;
		Double latitude = null;
		try{
			longitude = Double.valueOf(longitudeStr);
			latitude = Double.valueOf(latitudeStr);
		} catch(Throwable e){
			log.error("景点经纬度错误 name=" + name + " lng=" + longitudeStr + " lat=" + latitudeStr, e);
			JsonUtils.write(response, binder.toJson("result", Action.INPUT));
			return;
		}
		
		ViewSpotInfo view = new ViewSpotInfo();
		view.setName(name);
		view.setDescription(description);
		view.setLatitude(latitude);
		view.setLongitude(longitude);
		view.setSysUser(getCurrentUser());
		view.setTravelInf(getCurrentUser().getTravelInf());
		if(viewSpotService.addViewSpot(view) == 0){
			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));			
		} else {
			JsonUtils.write(response, binder.toJson("result", Action.ERROR));		
		}
	}
	
	public void delete(){
		String ids = request.getParameter("ids");
		List<RouteViewSpot> list = routeService.findRouteViewSpotByViewSpotIds(ids);
		if(list != null && list.size() > 0){
			JsonUtils.write(response, "{\"statusCode\":\"300\", \"message\":\"该景点已被规划到旅游线路中，不能删除\"}");
		}
		viewSpotService.deleteViewSpotByIds(ids);
		JsonUtils.write(response, "{\"statusCode\":\"200\", \"message\":\"删除成功\", \"navTabId\":\"景点管理\", \"forwardUrl\":\"\", \"callbackType\":\"\", \"rel\":\"\"}");
	}
	
	public String edit(){
		String id = request.getParameter("uid");
		Long idLong = 0L;
		try{
			idLong = Long.valueOf(id);
		}catch(Throwable ignore){	
			return "edit";
		}
		ViewSpotInfo view = viewSpotService.getViewSpotById(idLong);	
		if(view != null && view.getId() > 0){
			request.setAttribute("editView", view);
		}
		return "edit";
	}
	
	@SuppressWarnings("static-access")
	public void update(){
		String id = request.getParameter("viewSpotId");		
		String name = request.getParameter("name");
		String longitudeStr = request.getParameter("longitudeInput");
		String latitudeStr = request.getParameter("latitudeInput");
		String description = request.getParameter("description");	
		Double longitude = null;
		Double latitude = null;
		try{
			longitude = Double.valueOf(longitudeStr);
			latitude = Double.valueOf(latitudeStr);
		} catch(Throwable e){
			log.error("景点经纬度错误 name=" + name + " lng=" + longitudeStr + " lat=" + latitudeStr, e);
			JsonUtils.write(response, binder.toJson("result", Action.INPUT));
			return;
		}
		ViewSpotInfo view = viewSpotService.getViewSpotById(Long.valueOf(id));
		view.setName(name);
		view.setDescription(description);
		view.setLatitude(latitude);
		view.setLongitude(longitude);

		if(viewSpotService.updateViewSpot(view) == 0){
			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));			
		} else {
			JsonUtils.write(response, binder.toJson("result", Action.ERROR));
		}		
	}	
	
	public String selectView(){
		list();
		return "select";
	}
	
	public String upload(){
		edit();
		return "upload";
	}
}
