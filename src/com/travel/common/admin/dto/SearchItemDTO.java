/**
 * @author Zhang Zhipeng
 *
 * 2013-11-4
 */
package com.travel.common.admin.dto;


/**
 * @author Lenovo
 *
 */
public class SearchItemDTO extends BaseAdminDTO{
	private String name;
	private String brands;
	private Long teamId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrands() {
		return brands;
	}
	public void setBrands(String brands) {
		this.brands = brands;
	}
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	
}
