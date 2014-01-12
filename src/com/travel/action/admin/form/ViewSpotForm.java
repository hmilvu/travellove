/**
 * @author Zhang Zhipeng
 *
 * 2013-11-20
 */
package com.travel.action.admin.form;

/**
 * @author Lenovo
 *
 */
public class ViewSpotForm {
	private long id;
	private String viewName;
	private String startTime;
	private String endTime;
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	

}
