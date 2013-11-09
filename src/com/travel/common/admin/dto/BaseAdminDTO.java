/**
 * @author Zhang Zhipeng
 *
 * 2013-11-10
 */
package com.travel.common.admin.dto;

/**
 * @author Lenovo
 *
 */
public class BaseAdminDTO {
	private Integer userType = -1;
	private Long travelId;
	
	public Long getTravelId() {
		return travelId;
	}

	public void setTravelId(Long travelId) {
		this.travelId = travelId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
}
