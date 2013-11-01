package com.travel.action.admin;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.travel.action.BaseAction;
import com.travel.common.Constants;
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

//		String valificationCodeSessionValue = (String) session
//				.get(ValificationCodeAction.VALIFICATION_CODE_SESSION_NAME);
//		if (!StringUtils.equals(valificationCode, valificationCodeSessionValue)) {
//			JsonUtils.write(response, binder.toJson("result", Action.ERROR));
//			return null;
//		}

		// 判断账号、密码；
		SysUser user = sysUserService.getSysUserByCredentials(username, password);
		if (user != null && user.getId() > 0) {
			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));
			session.put(Constants.SYS_USER_INF_IN_SESSION, user);

			String menuInfor = sysUserService.generateMenuBySysUser(user);
			session.put(Constants.MENU_INF_IN_SESSION, menuInfor);

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
