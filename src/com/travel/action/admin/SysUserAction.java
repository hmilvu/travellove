/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.admin;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.travel.action.AuthorityAction;
import com.travel.common.Constants;
import com.travel.common.Constants.SYS_USER_TYPE;
import com.travel.common.admin.dto.SearchSysUserDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.RoleInf;
import com.travel.entity.SysUser;
import com.travel.entity.TeamInfo;
import com.travel.entity.TravelInf;
import com.travel.entity.UserRole;
import com.travel.service.RoleService;
import com.travel.service.SysUserService;
import com.travel.service.TravelInfService;
import com.travel.utils.JsonUtils;

/**
 * @author Lenovo
 *
 */
public class SysUserAction extends AuthorityAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private TravelInfService travelService;
	@Autowired
	private SysUserService userService;
	@Autowired
	private RoleService roleService;

	public String list(){
		String travelName = request.getParameter("travelName");
		String username = request.getParameter("username");
		String userType = request.getParameter("userType");
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
		int userTypeNum = -1;
		try{
			userTypeNum = Integer.valueOf(userType);
		}catch(Throwable ignore){
			
		}
		SearchSysUserDTO dto = new SearchSysUserDTO();
		dto.setTravelName(travelName);
		dto.setName(name);
		dto.setUsername(username);
		dto.setUserType(userTypeNum);
		int totalNum = userService.getTotalUserNum(dto);
		List<SysUser> list = userService.findUsers(dto, pageInfo);
		request.setAttribute("userList", list);
		request.setAttribute("userTotalCount", totalNum+"");
		request.setAttribute("travelName", travelName);
		request.setAttribute("username", username);
		request.setAttribute("userType", userType);
		request.setAttribute("name", name);
		request.setAttribute("pageNumber", pageNumber == null ? 1 : pageNumber);
		request.setAttribute("startNum", (pageInfo.getPageNumber()-1)*pageInfo.getPageSize());
		return "list";
	}
	
	public String addAdmin(){
		return "addAdmin";
	}
	public String addSysUser(){
		setupRolesInRequest();
		return "addSysUser";
	}

	/**
	 * setup roles to display for add user page
	 */
	private void setupRolesInRequest() {
		PageInfoDTO pageInfo = new PageInfoDTO();
		pageInfo.setPageNumber(1);
		pageInfo.setPageSize(100);		
		int totalNum = roleService.getTotalRoleNum(null);
		List<RoleInf> list = roleService.findRolesByName(null, pageInfo);
		request.setAttribute("roleList", list);
		request.setAttribute("roleTotalCount", totalNum+"");
	}
	
	public String addTravelUser(){
		return "addTravelUser";
	}
	
	public void createAdmin(){
		if(create(SYS_USER_TYPE.SUPER_ADMIN) == 0){
			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));		
		}
		return;
	}
	
	public void createSysUser(){
		int reslut = create(SYS_USER_TYPE.SYSTEM_USER);
		if(reslut == 0){
			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));		
		} else if(reslut == 1) {
			JsonUtils.write(response, binder.toJson("result", Action.INPUT));
		} else {
			JsonUtils.write(response, binder.toJson("result", Action.ERROR));	
		}		
	}
	
	private int create(SYS_USER_TYPE userType){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String status = request.getParameter("status");
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");
		String telNumber = request.getParameter("telNumber");
		String email = request.getParameter("email");
		
		List<SysUser> list = userService.findUserByUsername(name);
		if(list != null && list.size() > 0){
			return 1;
		}
		SysUser user = new SysUser();
		user.setUsername(username);
		user.setPassword(password);
		user.setStatus(Integer.valueOf(status));
		user.setName(name);
		user.setMobile(mobile);
		user.setTelNumber(telNumber);
		user.setEmail(email);
		user.setUserType(userType.getValue());
		user.setUpdateUserId(getCurrentUser().getId());
		List<Long> roleIdList = new ArrayList<Long>();
		if(userType != SYS_USER_TYPE.SUPER_ADMIN){
			Enumeration <?>enu = request.getParameterNames();
			while(enu.hasMoreElements()){
				String key = enu.nextElement().toString();
				if(StringUtils.startsWith(key, "roleIds")){
					String roleId = StringUtils.substring(key, "roleIds".length());
					roleIdList.add(Long.valueOf(roleId));
				}
			}
		}
		
		int result = userService.saveNewUser(user, roleIdList);
		return result;
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
