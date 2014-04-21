/**
 * @author Zhang Zhipeng
 *
 * 2013-10-26
 */
package com.travel.common.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lenovo
 *
 */
public class LocationLogDTO {
	private Double longitude;
	private Double latitude;
	private String memberName;
	private String nickName;
	private String travelerMobile;
	private Integer memberType;
	private Integer sex;
	private Long memberId;
	private String createDate;
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getTravelerMobile() {
		return travelerMobile;
	}
	public void setTravelerMobile(String travelerMobile) {
		this.travelerMobile = travelerMobile;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
}
