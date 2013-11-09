package com.travel.action;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.travel.common.Constants;
import com.travel.common.Constants.SYS_USER_TYPE;
import com.travel.entity.SysUser;

/**
 * 包含权限判断
 * 
 * @author Zhang Zhipeng
 */
@ParentPackage("authority")
public class AuthorityAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 获取登陆账号信息
	 * 
	 * @return
	 */
	protected SysUser getCurrentUser() {
		return (SysUser) session.get(Constants.SYS_USER_INF_IN_SESSION);

	}
	
	protected boolean isSessionUser(SysUser user){
		return user.getId().longValue() != getCurrentUser().getId().longValue();
	}
	
	protected boolean isTravelUser(){
		SysUser user = getCurrentUser();
		if(user.getUserType().intValue() == SYS_USER_TYPE.TRAVEL_USER.getValue()){
			return true;
		} else {
			return false;
		}
	}

}
