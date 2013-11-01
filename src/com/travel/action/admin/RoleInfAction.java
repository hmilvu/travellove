/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.travel.action.AuthorityAction;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.RoleInf;
import com.travel.service.RoleService;

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
	
	public String list(){
		String roleName = request.getParameter("roleName");
		String pageSize = request.getParameter("pageSize");
		String pageNumber = request.getParameter("pageNumber");
		PageInfoDTO pageInfo = new PageInfoDTO();
		try{
			pageInfo.setPageNumber(Integer.valueOf(pageNumber.toString()));
			pageInfo.setPageSize(Integer.valueOf(pageSize.toString()));
		}catch(Throwable ignore){			
		}
		int totalNum = roleService.getTotalRoleNum(roleName);
		List<RoleInf> list = roleService.findRolesByName(roleName, pageInfo);
		request.setAttribute("roleList", list);
		request.setAttribute("roleTotalCount", totalNum+"");
		return "list";
	}
	
	public String add(){
		return "add";
	}
	
	public String create(){
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		RoleInf role = new RoleInf();
		role.setName(name);
		role.setDescription(description);
		if(roleService.addRole(role) == 0){
			
		} else {
			
		}
		
		return "";
	}
}
