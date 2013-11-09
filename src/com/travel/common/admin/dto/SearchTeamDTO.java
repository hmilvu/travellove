/**
 * @author Zhang Zhipeng
 *
 * 2013-11-4
 */
package com.travel.common.admin.dto;

import java.util.Date;

/**
 * @author Lenovo
 *
 */
public class SearchTeamDTO extends BaseAdminDTO{
	private String travelName;
	private String teamName;
	private Date startDate;
	private Date endDate;
	public String getTravelName() {
		return travelName;
	}
	public void setTravelName(String travelName) {
		this.travelName = travelName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	

}
