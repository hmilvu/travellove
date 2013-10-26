/**
 *
 * Copyright (C) 2009-2011 Fsti Inc.
 *
 */

package com.travel.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.bcel.internal.generic.GETSTATIC;
import com.travel.utils.JsonBinder;
import com.travel.utils.JsonUtils;

/**
 * 
 * @author Zhang Zhipeng
 *
 */

@ParentPackage("base")
// 继承基本配置
public class BaseAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware, SessionAware {
	protected static final Logger log = LoggerFactory.getLogger(BaseAction.class);
	private static final long serialVersionUID = 1L;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, Object> session;
	protected static JsonBinder binder = JsonBinder.buildNormalBinder();
	protected static JsonBinder notNullBinder = JsonBinder.buildNotNullBinder();

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/**
	 * 获取绝对路径
	 * 
	 * @return
	 */
	public String getRealPath() {
		return request.getSession().getServletContext().getRealPath("");
	}

	/**
	 * 获取相对路径
	 * 
	 * @return
	 */
	public String getRelativePath() {
		//return request.getServletContext().getContextPath();
		return request.getSession().getServletContext().getContextPath();
	}
	
	/**
	 * 绕过Template,直接输出内容的简便函数.
	 */
	protected String render(String text, String contentType) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(contentType);
			response.getWriter().write(text);
			response.getWriter().flush();
//			response.getOutputStream().print(text);
//			response.getOutputStream().flush();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	protected String getMobileData(){
		String data = request.getParameter("data");
		log.info("客户端发送数据：  data = " + data);
		return data;
	}
	
	protected Object getMobileParameter(String data, String key){
		Object value = binder.getValue(data, key);
		return value;
	}	
	
	protected <T> void sendToMobile(T result){
		String jsonStr = binder.toJson(result);		
		log.info("返回客户端数据 ：" + jsonStr);
		JsonUtils.write(response, jsonStr);			
	}
}
