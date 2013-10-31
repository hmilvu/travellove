/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.admin;

import org.springframework.beans.factory.annotation.Autowired;

import com.travel.action.AuthorityAction;
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
