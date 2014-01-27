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
public class SearchViewSpotDTO extends BaseAdminDTO{
	private String name;
	private Long teamId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String province;
	private String city;

	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

}
