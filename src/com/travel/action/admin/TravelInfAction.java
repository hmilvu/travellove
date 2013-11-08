/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.travel.action.AuthorityAction;
import com.travel.common.Constants;
import com.travel.common.admin.dto.SearchTravelDTO;
import com.travel.common.admin.dto.SelectListTravelDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.TeamInfo;
import com.travel.entity.TravelInf;
import com.travel.service.TravelInfService;
import com.travel.utils.JsonUtils;

/**
 * @author Lenovo
 *
 */
public class TravelInfAction extends AuthorityAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private TravelInfService travelService;

	public String list(){
		String travelName = request.getParameter("travelName");
		String personName = request.getParameter("personName");
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
		SearchTravelDTO dto = new SearchTravelDTO();
		dto.setTravelName(travelName);
		dto.setPersonName(personName);
		int totalNum = travelService.getTotalRoleNum(dto);
		List<TravelInf> list = travelService.findTravels(dto, pageInfo);
		request.setAttribute("travelList", list);
		request.setAttribute("travelTotalCount", totalNum+"");
		request.setAttribute("travelName", travelName);
		request.setAttribute("personName", personName);
		request.setAttribute("pageNumber", pageNumber == null ? 1 : pageNumber);
		request.setAttribute("startNum", (pageInfo.getPageNumber()-1)*pageInfo.getPageSize());
		return "list";
	}
	
	public void selectList(){
		List<TravelInf> allList = travelService.findAllTravels();
		List<SelectListTravelDTO> list = new ArrayList<SelectListTravelDTO>();
		for(TravelInf travelInf : allList){
			SelectListTravelDTO dto = new SelectListTravelDTO();
			dto.setId(travelInf.getId().toString());
			dto.setTravelName(travelInf.getName());
			list.add(dto);
		}
		JsonUtils.write(response, binder.toJson(list));	
	}
	
	public String add(){
		return "add";
	}
	
	public void create(){
		String name = request.getParameter("name");
		List<TravelInf> roleInf = travelService.getTravelInfByName(name);
		if(roleInf != null && roleInf.size() > 0){
			JsonUtils.write(response, binder.toJson("result", Action.INPUT));	
			return;
		}
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		String contact = request.getParameter("contact");
		String linker = request.getParameter("linker");
		String description = request.getParameter("description");		
		
		TravelInf travel = new TravelInf();
		travel.setName(name);
		travel.setPhone(phone);
		travel.setAddress(address);
		travel.setContact(contact);
		travel.setLinker(linker);
		travel.setDescription(description);
		travel.setSysUser(getCurrentUser());
		if(travelService.addTravelInf(travel) == 0){
			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));			
		} else {
			JsonUtils.write(response, binder.toJson("result", Action.ERROR));		
		}
		return;
	}
	
	public void delete(){
		String id = request.getParameter("uid");
		Long idLong = 0L;
		try{
			idLong = Long.valueOf(id);
		}catch(Throwable ignore){	
			JsonUtils.write(response, "{\"statusCode\":\"300\",\"message\":\"删除失败，请选择旅行社后重试\"}");
			return;
		}
		List<TeamInfo> list = travelService.getTeamByTravelId(idLong);
		if(list != null && list.size() > 0){
			JsonUtils.write(response, "{\"statusCode\":\"300\",\"message\":\"该旅行社正在被使用，不能删除\"}");
			return;
		}
		travelService.deleteTravelById(idLong);		
		JsonUtils.write(response, "{\"statusCode\":\"200\", \"message\":\"删除成功\", \"navTabId\":\"旅行社管理\", \"forwardUrl\":\"\", \"callbackType\":\"\", \"rel\":\"\"}");
	}
	
	public String edit(){
		String id = request.getParameter("uid");
		Long idLong = 0L;
		try{
			idLong = Long.valueOf(id);
		}catch(Throwable ignore){	
			return "edit";
		}
		TravelInf travel = travelService.getTravelById(idLong);
		if(travel != null && travel.getId() > 0){
			request.setAttribute("editTravel", travel);
		}
		return "edit";
	}
	
	public void update(){
		String id = request.getParameter("travelId");
		Long idLong = 0L;
		try{
			idLong = Long.valueOf(id);
		}catch(Throwable ignore){	
			JsonUtils.write(response, binder.toJson("result", Action.INPUT));	
			return;
		}
		String name = request.getParameter("name");
		List<TravelInf> roleInf = travelService.getTravelInfByName(name);
		if(roleInf != null && roleInf.size() > 0 && roleInf.get(0).getId().longValue() != idLong){
			JsonUtils.write(response, binder.toJson("result", Action.INPUT));	
			return;
		}
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		String contact = request.getParameter("contact");
		String linker = request.getParameter("linker");
		String description = request.getParameter("description");		
		
		TravelInf travel = travelService.getTravelById(idLong);
		if(travel != null && travel.getId().longValue() > 0){
			travel.setName(name);
			travel.setPhone(phone);
			travel.setAddress(address);
			travel.setContact(contact);
			travel.setLinker(linker);
			travel.setDescription(description);		
			travel.setSysUser(getCurrentUser());
			if(travelService.updateTravel(travel) == 0){
				JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));			
			} else {
				JsonUtils.write(response, binder.toJson("result", Action.ERROR));
			}
		} else {
			JsonUtils.write(response, binder.toJson("result", Action.ERROR));
		}
		return;
	}
}
