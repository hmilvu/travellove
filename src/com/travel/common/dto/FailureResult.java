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
public class FailureResult {
	private int code;
	private String msg;
	
	public FailureResult(String msg){
		this.code = -1;
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
