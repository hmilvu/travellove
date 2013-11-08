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
import com.travel.common.Constants.SYS_USER_STATUS;
import com.travel.common.Constants.SYS_USER_TYPE;
import com.travel.common.admin.dto.SearchSysUserDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.RoleInf;
import com.travel.entity.SysUser;
import com.travel.entity.TeamInfo;
import com.travel.entity.TravelInf;
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
		dto.setCurrentUserType(getCurrentUser().getUserType());
		if(getCurrentUser().getTravelInf() != null){
			dto.setTravelId(getCurrentUser().getTravelInf().getId());
		}
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
	/**
	 * setup roles to display for add user page
	 */
	private List<RoleInf> setupRolesInRequest() {
		PageInfoDTO pageInfo = new PageInfoDTO();
		pageInfo.setPageNumber(1);
		pageInfo.setPageSize(100);		
		int totalNum = roleService.getTotalRoleNum(null);
		List<RoleInf> list = roleService.findRolesByName(null, pageInfo);
		request.setAttribute("roleList", list);
		request.setAttribute("roleTotalCount", totalNum+"");
		return list;
	}
	
	public String addAdmin(){
		return "addAdmin";
	}
	public String addSysUser(){
		setupRolesInRequest();
		return "addSysUser";
	}

	
	public String addTravelUser(){
		setupRolesInRequest();
		return "addTravelUser";
	}
	
	public void createAdmin(){
		createUser(SYS_USER_TYPE.SUPER_ADMIN);	
	}
	
	public void createSysUser(){
		createUser(SYS_USER_TYPE.SYSTEM_USER);		
	}
	
	public void createTravelUser(){
		createUser(SYS_USER_TYPE.TRAVEL_USER);		
	}

	/**
	 * 
	 */
	@SuppressWarnings("static-access")
	private void createUser(SYS_USER_TYPE userType) {
		int reslut = create(userType);
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
		
		List<SysUser> list = userService.findUserByUsername(username);
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
			if(userType == SYS_USER_TYPE.TRAVEL_USER){
				String travelId = request.getParameter("travelLookup.id");
				TravelInf travelInf = new TravelInf();
				travelInf.setId(Long.valueOf(travelId));
				user.setTravelInf(travelInf);
			}
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
			JsonUtils.write(response, "{\"statusCode\":\"300\",\"message\":\"删除失败，请选择用户后重试\"}");
			return;
		}
		SysUser user = userService.getSysUserById(idLong);
		user.setStatus(SYS_USER_STATUS.INVALID.getValue());
		user.setUpdateUserId(getCurrentUser().getId());
		userService.updateUser(user);	
		JsonUtils.write(response, "{\"statusCode\":\"200\", \"message\":\"删除成功\", \"navTabId\":\"用户管理\", \"forwardUrl\":\"\", \"callbackType\":\"\", \"rel\":\"\"}");
	}
	
	public String edit(){
		String id = request.getParameter("uid");
		Long idLong = 0L;
		try{
			idLong = Long.valueOf(id);
		}catch(Throwable ignore){	
			return "edit";
		}
		SysUser user = userService.getSysUserById(idLong);
		if(user != null && user.getId() > 0){
			request.setAttribute("editUser", user);
			if(user.getUserType() == SYS_USER_TYPE.SUPER_ADMIN.getValue()){
				return "editAdmin";
			} else if (user.getUserType() == SYS_USER_TYPE.SYSTEM_USER.getValue()){
				if(isSessionUser(user)){
					setupUserRolesInRequest(user);
				}
				return "editSysUser";
			} else if (user.getUserType() == SYS_USER_TYPE.TRAVEL_USER.getValue()){
				if(isSessionUser(user)){
					setupUserRolesInRequest(user);
				}
				return "editTravelUser";
			}
		}
		return null;
	}
	
	public String editSessionUser(){
		SysUser user = getCurrentUser();
		if(user != null && user.getId() > 0){
			request.setAttribute("editUser", user);
			if(user.getUserType() == SYS_USER_TYPE.SUPER_ADMIN.getValue()){
				return "editAdmin";
			} else if (user.getUserType() == SYS_USER_TYPE.SYSTEM_USER.getValue()){
				if(isSessionUser(user)){
					setupUserRolesInRequest(user);
				}
				return "editSysUser";
			} else if (user.getUserType() == SYS_USER_TYPE.TRAVEL_USER.getValue()){
				if(isSessionUser(user)){
					setupUserRolesInRequest(user);
				}
				return "editTravelUser";
			}
		}
		return null;
	}
	
	/**
	 * 
	 */
	private void setupUserRolesInRequest(SysUser user) {
		List<RoleInf> allRoleList = setupRolesInRequest();
		List<RoleInf> userRoleList = userService.getUserRoleByUserId(user.getId());
		for(RoleInf userRole : userRoleList){
			for(RoleInf role : allRoleList){
				if(role.getId().longValue() == userRole.getId().longValue()){
					role.setCheckRole(true);
					break;
				}
			}			
		}
		request.setAttribute("roleList", allRoleList);
	}
	
	public void updateAdmin(){
		updateUser(SYS_USER_TYPE.SUPER_ADMIN);
	}
	
	public void updateSysUser(){
		updateUser(SYS_USER_TYPE.SYSTEM_USER);
	}
	
	public void updateTravelUser(){
		updateUser(SYS_USER_TYPE.TRAVEL_USER);
	}
	
	@SuppressWarnings("static-access")
	public void updateUser(SYS_USER_TYPE userType){
		int reslut = update(userType);
		if(reslut == 0){
			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));		
		} else if(reslut == 1) {
			JsonUtils.write(response, binder.toJson("result", Action.INPUT));
		} else {
			JsonUtils.write(response, binder.toJson("result", Action.ERROR));	
		}
	}
	
	public int update(SYS_USER_TYPE userType){
		String id = request.getParameter("userId");
		String username = request.getParameter("username");
		String status = request.getParameter("status");
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");
		String telNumber = request.getParameter("telNumber");
		String email = request.getParameter("email");
		
		List<SysUser> list = userService.findUserByUsername(username);
		if(list != null && list.size() > 0 && list.get(0).getId().longValue() != Long.valueOf(id)){
			return 1;
		}
		SysUser user = userService.getSysUserById(Long.valueOf(id));
		user.setUsername(username);
		user.setStatus(Integer.valueOf(status));
		user.setName(name);
		user.setMobile(mobile);
		user.setTelNumber(telNumber);
		user.setEmail(email);
		user.setUserType(userType.getValue());
		user.setUpdateUserId(getCurrentUser().getId());
		List<Long> roleIdList = new ArrayList<Long>();
		if(userType != SYS_USER_TYPE.SUPER_ADMIN){
			if(userType == SYS_USER_TYPE.TRAVEL_USER){
				String travelId = request.getParameter("travelLookup.id");
				if(!StringUtils.isBlank(travelId)){					
					TravelInf travelInf = new TravelInf();
					travelInf.setId(Long.valueOf(travelId));
					user.setTravelInf(travelInf);
				}
			}
			Enumeration <?>enu = request.getParameterNames();
			while(enu.hasMoreElements()){
				String key = enu.nextElement().toString();
				if(StringUtils.startsWith(key, "roleIds")){
					String roleId = StringUtils.substring(key, "roleIds".length());
					roleIdList.add(Long.valueOf(roleId));
				}
			}
		}
		int result = 0;
		if(isSessionUser(user)){
			result = userService.updateUser(user, roleIdList, true);
		} else {
			result = userService.updateUser(user, roleIdList, false);
		}
		return result;
	}
	
	public String changePassword(){
		String userId = request.getParameter("uid");
		String username = null;
		if(StringUtils.isBlank(userId)){
			username = getCurrentUser().getUsername();			
		} else {
			SysUser user = userService.getSysUserById(Long.valueOf(userId));
			username = user.getUsername();
		}
		request.setAttribute("username", username);
		return "changePassword";
	}
	
	public void updatePassword(){
		String username = request.getParameter("username");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		SysUser user = userService.getSysUserByCredentials(username, oldPassword);
		if (user != null && user.getId() > 0) {
			user.setPassword(newPassword);
			user.setUpdateUserId(getCurrentUser().getId());
			userService.updateUser(user);
			JsonUtils.write(response, "{\"statusCode\":\"200\", \"message\":\"修改密码成功\"}");
		} else {
			JsonUtils.write(response, "{\"statusCode\":\"300\", \"message\":\"旧密码输入错误，请重新输入\"}");
		}
	}
	
	
}
