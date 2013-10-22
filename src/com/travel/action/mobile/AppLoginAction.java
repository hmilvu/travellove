/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.mobile;

import com.travel.action.BaseAction;
import com.travel.utils.JsonUtils;

/**
 * @author Lenovo
 *
 */
public class AppLoginAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String login(){
		JsonUtils.write(response, binder.toJson(true));
		return null;
	}
}
