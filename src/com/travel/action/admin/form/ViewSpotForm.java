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
	private String startDate;
	private String endDate;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
