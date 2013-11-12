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
public class SearchMemberDTO extends BaseAdminDTO{
	private String name;
	private String teamName;
	private String idNumber;
	private String phoneNumber;
	private Integer memberType;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	
	

}
