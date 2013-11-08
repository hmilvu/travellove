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
public class SearchSysUserDTO {
	private String travelName;
	private String username;
	private String name;
	private int userType;
	private int currentUserType;
	private Long travelId;
	public String getTravelName() {
		return travelName;
	}
	public void setTravelName(String travelName) {
		this.travelName = travelName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public int getCurrentUserType() {
		return currentUserType;
	}
	public void setCurrentUserType(int currentUserType) {
		this.currentUserType = currentUserType;
	}
	public Long getTravelId() {
		return travelId;
	}
	public void setTravelId(Long travelId) {
		this.travelId = travelId;
	}
	

}
