/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.travel.action.AuthorityAction;
import com.travel.common.Constants;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.MenuInf;
import com.travel.entity.RoleInf;
import com.travel.service.MenuInfService;
import com.travel.service.RoleService;
import com.travel.utils.JsonUtils;

/**
 * @author Lenovo
 *
 */
public class RoleInfAction extends AuthorityAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuInfService menuService;
	
	public String list(){
		String roleName = request.getParameter("roleName");
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
		int totalNum = roleService.getTotalRoleNum(roleName);
		List<RoleInf> list = roleService.findRolesByName(roleName, pageInfo);
		request.setAttribute("roleList", list);
		request.setAttribute("roleTotalCount", totalNum+"");
		request.setAttribute("roleName", roleName);
		request.setAttribute("pageNumber", pageNumber == null ? 1 : pageNumber);
		request.setAttribute("startNum", (pageInfo.getPageNumber()-1)*pageInfo.getPageSize());
		return "list";
	}
	
	public String add(){
		List<MenuInf> menuList = menuService.getAllMenuItem();
		String allMenuInfStr = menuService.generateMenuInfor(menuList);
		request.setAttribute(Constants.ALL_MENU_INF_STR, allMenuInfStr);
		return "add";
	}
	
	public void create(){
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		List<RoleInf> roleInf = roleService.getRoleByName(name);
		if(roleInf != null && roleInf.size() > 0){
			JsonUtils.write(response, binder.toJson("result", Action.INPUT));	
			return;
		}
		RoleInf role = new RoleInf();
		role.setName(name);
		role.setDescription(description);
		if(roleService.addRole(role) == 0){
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
			JsonUtils.write(response, "{\"statusCode\":\"300\",\"message\":\"删除失败，请选择角色后重试\"}");
			return;
		}
		int result = roleService.deleteRoleById(idLong);
		JsonUtils.write(response, "{\"statusCode\":\"200\", \"message\":\"删除成功\", \"navTabId\":\"角色管理\", \"forwardUrl\":\"\", \"callbackType\":\"\", \"rel\":\"\"}");
	}
	
	public String edit(){
		String id = request.getParameter("uid");
		Long idLong = 0L;
		try{
			idLong = Long.valueOf(id);
		}catch(Throwable ignore){	
			return "edit";
		}
		RoleInf role = roleService.getRoleById(idLong);
		if(role != null && role.getId() > 0){
			request.setAttribute("editRole", role);
		}
		return "edit";
	}
	
	public void update(){
		String id = request.getParameter("roleId");
		Long idLong = 0L;
		try{
			idLong = Long.valueOf(id);
		}catch(Throwable ignore){	
			JsonUtils.write(response, binder.toJson("result", Action.INPUT));	
			return;
		}
		String name = request.getParameter("name");
		List<RoleInf> roleInf = roleService.getRoleByName(name);
		if(roleInf != null && roleInf.size() > 0 && roleInf.get(0).getId().longValue() != idLong.longValue()){
			JsonUtils.write(response, binder.toJson("result", Action.INPUT));	
			return;
		}
		String description = request.getParameter("description");
		RoleInf role = roleService.getRoleById(idLong);
		if(role != null && role.getId() > 0){		
			role.setName(name);
			role.setDescription(description);
		}
		if(roleService.updateRole(role) == 0){
			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));			
		} else {
			JsonUtils.write(response, binder.toJson("result", Action.ERROR));		
		}
		return;
	}
}
