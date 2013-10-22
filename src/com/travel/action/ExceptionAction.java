package com.travel.action;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;

/**
 * 异常处理
 *
 * @author deniro
 */
public class ExceptionAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static Logger log = Logger.getLogger(ExceptionAction.class);
	
	public String execute(){
		return Action.ERROR;
	}
}
