/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.admin;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.travel.action.AuthorityAction;
import com.travel.action.admin.form.ItemInfForm;
import com.travel.common.Constants;
import com.travel.common.Constants.IMAGE_TYPE;
import com.travel.common.Constants.MESSAGE_RECEIVER_TYPE;
import com.travel.common.Constants.VIEW_SPOT_TYPE;
import com.travel.common.admin.dto.SearchViewSpotDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.AreaInf;
import com.travel.entity.ImgInf;
import com.travel.entity.ItemInf;
import com.travel.entity.Message;
import com.travel.entity.RouteViewSpot;
import com.travel.entity.ViewSpotInfo;
import com.travel.service.AreaService;
import com.travel.service.ImgService;
import com.travel.service.MessageService;
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
	@Autowired
	private ImgService imageServcie;
	@Autowired
	private MessageService messageService;
	@Autowired
	private AreaService areaService;
	private List<AreaInf> areaList;
	private List<ItemInfForm> items;
	
	public List<ItemInfForm> getItems() {
		return items;
	}

	public void setItems(List<ItemInfForm> items) {
		this.items = items;
	}

	public String list(){
		String name = request.getParameter("name");		
		String pageSize = request.getParameter("numPerPage");
		String pageNumber = request.getParameter("pageNum");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		areaList = areaService.getAllProvinces();
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
		dto.setProvince(province);
		dto.setCity(city);
		if(isTravelUser()){
			dto.setTravelId(getCurrentUser().getTravelInf().getId());
		}
		int totalNum = viewSpotService.getTotalViewSpotNum(dto);
		List<ViewSpotInfo> list = viewSpotService.findViewSpots(dto, pageInfo);
		request.setAttribute("areaList", areaList);		
		request.setAttribute("viewSpotList", list);
		request.setAttribute("totalCount", totalNum+"");
		request.setAttribute("name", name);
		request.setAttribute("province", province);
		request.setAttribute("city", city);
		if(StringUtils.isNotBlank(province)){
			List<AreaInf> subareaList = areaService.getSubCitiesByCode(province);
			request.setAttribute("subareaList", subareaList);
		}
		request.setAttribute("pageNumber", pageNumber == null ? 1 : pageNumber);
		request.setAttribute("startNum", (pageInfo.getPageNumber()-1)*pageInfo.getPageSize());
		return "list";
	}
	
	public String add(){
		areaList = areaService.getAllProvinces();
		request.setAttribute("areaList", areaList);		
		return "add";
	}
	
	@SuppressWarnings("static-access")
	public void create(){
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String longitudeStr = request.getParameter("longitudeInput");
		String latitudeStr = request.getParameter("latitudeInput");
		String description = request.getParameter("description");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
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
		view.setAddress(address);
		view.setLongitude(longitude);
		view.setSysUser(getCurrentUser());
		view.setProvince(province);
		view.setCity(city);
		if(isTravelUser()){
			view.setTravelInf(getCurrentUser().getTravelInf());
			view.setType(VIEW_SPOT_TYPE.PRIVATE.getValue());
		} else {
			view.setType(VIEW_SPOT_TYPE.PUBLIC.getValue());
		}
		try {
			if(viewSpotService.addViewSpot(view) == 0){
				JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));			
			} else {
				JsonUtils.write(response, binder.toJson("result", Action.ERROR));		
			}
		} catch (Exception e) {
			log.error("创建景点错误", e);
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
		areaList = areaService.getAllProvinces();
		ViewSpotInfo view = viewSpotService.getViewSpotById(idLong);	
		if(view != null && view.getId() > 0){
			if(StringUtils.isNotBlank(view.getProvince())){
				List<AreaInf> subareaList = areaService.getSubCitiesByCode(view.getProvince());
				request.setAttribute("subareaList", subareaList);
			}
			request.setAttribute("areaList", areaList);
			request.setAttribute("editView", view);
		}
		return "edit";
	}
	
	public String view(){
		edit();
		return "view";
	}
	
	@SuppressWarnings("static-access")
	public void update(){
		String id = request.getParameter("viewSpotId");		
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String longitudeStr = request.getParameter("longitudeInput");
		String latitudeStr = request.getParameter("latitudeInput");
		String description = request.getParameter("description");	
		String province = request.getParameter("province");
		String city = request.getParameter("city");
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
		if(view.getType().intValue() == VIEW_SPOT_TYPE.PUBLIC.getValue() && isTravelUser()){
			// if the view spot is public and the user is traveler, update is create.
			ViewSpotInfo cloneView = new ViewSpotInfo();
			cloneView.setName(name);
			cloneView.setAddress(address);
			cloneView.setDescription(description);
			cloneView.setLatitude(latitude);
			cloneView.setLongitude(longitude);
			cloneView.setProvince(province);
			cloneView.setCity(city);
			cloneView.setSysUser(getCurrentUser());
			cloneView.setTravelInf(getCurrentUser().getTravelInf());
			cloneView.setType(VIEW_SPOT_TYPE.PRIVATE.getValue());	
			try {
				if(viewSpotService.addViewSpot(cloneView) == 0){
					JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));			
				} else {
					JsonUtils.write(response, binder.toJson("result", Action.ERROR));
				}	
			} catch (Exception e) {
				log.error("创建私有景点错误", e);
			}
		} else {// if the view spot is private, update is modify.		
			view.setName(name);
			view.setAddress(address);
			view.setDescription(description);
			view.setLatitude(latitude);
			view.setLongitude(longitude);
			view.setProvince(province);
			view.setCity(city);
			try {
				if(viewSpotService.updateViewSpot(view) == 0){
					JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));			
				} else {
					JsonUtils.write(response, binder.toJson("result", Action.ERROR));
				}
			} catch (Exception e) {
				log.error("更新公共景点错误", e);
			}
		}
	}	
	
	public String selectView(){
		list();
		return "select";
	}
	
	public String upload(){
		edit();
		ViewSpotInfo view = (ViewSpotInfo)request.getAttribute("editView");
		List<ImgInf> list = imageServcie.getImageList(IMAGE_TYPE.VIEWSPOT, view.getId());
		for(ImgInf img : list){
			request.setAttribute("imgId" + img.getImgName(), img.getId());
			request.setAttribute("imageName" + img.getImgName(), "images/viewspot/" + view.getId() + "/" +img.getImgName() + img.getSuffix());
		}
		return "upload";
	}
	
	public String message(){
		edit();
		String viewSpotId = request.getParameter("uid");
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
		int totalNum = messageService.getTotalMessageNumByReceiverId(MESSAGE_RECEIVER_TYPE.VIEW_SPOT, Long.valueOf(viewSpotId));
		List<Message> list = messageService.findMessageByReceiverId(MESSAGE_RECEIVER_TYPE.VIEW_SPOT, Long.valueOf(viewSpotId), pageInfo);
		request.setAttribute("messageList", list);
		request.setAttribute("totalCount", totalNum+"");
		request.setAttribute("pageNumber", pageNumber == null ? 1 : pageNumber);
		request.setAttribute("startNum", (pageInfo.getPageNumber()-1)*pageInfo.getPageSize());
		return "message";
	}
	
	public void deleteMessage(){
		String id = request.getParameter("id");
		Message msg = messageService.getMessageById(Long.valueOf(id));
		messageService.deleteMessage(msg);
		JsonUtils.write(response, "{\"statusCode\":\"200\", \"message\":\"删除成功\", \"navTabId\":\"查看评论\", \"forwardUrl\":\"\", \"callbackType\":\"closeCurrent\", \"rel\":\"\"}");
	
	}
	
	public String editItem(){
		if(!isTravelUser()){
			return "editItem";
		}
		String id = request.getParameter("uid");
		ViewSpotInfo view = viewSpotService.getViewSpotById(Long.valueOf(id));	
		List<ItemInf> list = viewSpotService.getViewSpotItems(getCurrentUser().getTravelInf().getId(), view.getId());
		if(view != null && view.getId() > 0){
			request.setAttribute("editView", view);
			request.setAttribute("itemInfList", list);
		}
		return "editItem";
	}
	
	public String updateItem(){
		if(!isTravelUser()){
			return "editItem";
		}
		String id = request.getParameter("viewSpotId");
		int result = viewSpotService.updateItems(getCurrentUser().getTravelInf().getId(), Long.valueOf(id), items);
		if(result == 0){
			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));			
		} else {
			JsonUtils.write(response, binder.toJson("result", Action.ERROR));
		}
		return null;
	}
}
