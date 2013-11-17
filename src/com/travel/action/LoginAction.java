package com.travel.action;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.travel.common.Constants;
import com.travel.common.Constants.SYS_USER_STATUS;
import com.travel.entity.SysUser;
import com.travel.service.MenuInfService;
import com.travel.service.SysUserService;
import com.travel.utils.JsonUtils;

/**
 * 包含权限判断
 * 
 * @author deniro
 */
public class LoginAction extends BaseAction {
	
	@Autowired
	private MenuInfService menuService;
	
	@Autowired
	private SysUserService sysUserService;

	public String login() {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String valificationCode = request.getParameter("valificationCode");

		// 判断验证码

		String valificationCodeSessionValue = (String) session
				.get(ValificationCodeAction.VALIFICATION_CODE_SESSION_NAME);
		if (!StringUtils.equals(valificationCode, valificationCodeSessionValue)) {
			JsonUtils.write(response, binder.toJson("result", Action.ERROR));
			return null;
		}

		// 判断账号、密码；
		SysUser user = sysUserService.getSysUserByCredentials(username, password);
		if (user != null && user.getId() > 0) {
			user.setPassword(null);
			if(user.getStatus() == SYS_USER_STATUS.ACTIVE.getValue()){
//				Hibernate.initialize(user.getTravelInf());
				JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));
				session.put(Constants.SYS_USER_INF_IN_SESSION, user);
	
				String menuInfor = sysUserService.generateMenuBySysUser(user);
				session.put(Constants.MENU_INF_IN_SESSION, menuInfor);
				session.put(Constants.BADU_MAP_AK_KEY, Constants.BAIDU_MAP_AK);
			} else if(user.getStatus() == SYS_USER_STATUS.IN_ACTIVE.getValue()){
				JsonUtils.write(response, binder.toJson("result", "INACTIVE"));			
			} else if(user.getStatus() == SYS_USER_STATUS.INVALID.getValue()){
				JsonUtils.write(response, binder.toJson("result", "INVALID"));			
			}
			return null;
		} else {
			JsonUtils.write(response, binder.toJson("result", Action.INPUT));
			return null;
		}
	}
	
	public String logout(){
		session.clear();
		return Action.LOGIN;
	}

}
