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
public class RouteForm {
	private long id;
	private String routeName;
	private String date;
	private int status;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}	
	
}
