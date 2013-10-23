/**
 * @author Zhang Zhipeng
 *
 * 2013-10-23
 */
package com.travel.common.dto;

/**
 * @author Lenovo
 *
 */
public class SuccessResult <T> {
	private int code;
	private T msg;
	
	public SuccessResult(T result){
		this.msg = result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getMsg() {
		return msg;
	}

	public void setMsg(T msg) {
		this.msg = msg;
	}
	
	
}
