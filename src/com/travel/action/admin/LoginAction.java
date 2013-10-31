package com.travel.action.admin;

import org.apache.commons.lang3.StringUtils;

import com.opensymphony.xwork2.Action;
import com.travel.action.BaseAction;
import com.travel.action.ValificationCodeAction;
import com.travel.utils.JsonUtils;

/**
 * 包含权限判断
 * 
 * @author deniro
 */
public class LoginAction extends BaseAction {

	public String login() {
		String account = request.getParameter("account");
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
//		TBmAccount tBmAccount = accountDao.isPassword(account, password);
//		if (StringUtils.isNotBlank(tBmAccount.getAccount())) {
//			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));
//			session.put(ACCOUNT_SESSION_NAME, tBmAccount);
//			
//			TBmRole role=tBmAccount.getRole();
//			if(role!=null){
//				String menuInfor=getMenuInfor(role.getId());
//				session.put("menuInfor", menuInfor);
//			}
//			
//			return null;
//		}

		JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));
		return null;
	}
	
	public String logout(){
		session.clear();
		return Action.LOGIN;
	}


}
