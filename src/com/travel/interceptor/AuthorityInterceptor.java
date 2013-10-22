package com.travel.interceptor;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

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

	@SuppressWarnings("rawtypes")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		Map session = ctx.getSession();

		// 判断是否登录，若未登录，则返回登录页面
//		TBmAccount account = (TBmAccount) session
//				.get(LoginAction.ACCOUNT_SESSION_NAME);
//		if (account != null && StringUtils.isNotBlank(account.getAccount())) {
//			return invocation.invoke();
//		}
		return Action.LOGIN;
	}

}
