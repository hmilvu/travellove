/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.admin;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.travel.action.AuthorityAction;
import com.travel.common.Constants;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.MenuInf;
import com.travel.entity.RoleInf;
import com.travel.entity.UserRole;
import com.travel.service.MenuInfService;
import com.travel.service.RoleService;
import com.travel.utils.JsonUtils;

/**
 * @author Lenovo
 *
 */
public class LocationAction extends AuthorityAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String select(){
		return "select";
	}
}
