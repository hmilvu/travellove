package com.travel.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.travel.common.Constants;
import com.travel.entity.SysUser;

/**
 * 权限控制
 * 
 * @author deniro
 */
public class AuthorityInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({"unchecked" })
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		Map session = ctx.getSession();

		// 判断是否登录，若未登录，则返回登录页面
		SysUser currentUser = (SysUser) session
				.get(Constants.SYS_USER_INF_IN_SESSION);
		if (currentUser != null && currentUser.getId() != null && currentUser.getId().longValue() > 0) {
			return invocation.invoke();
		}
		return Action.LOGIN;
	}

}
