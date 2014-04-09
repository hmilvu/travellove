/**
 * @author Zhang Zhipeng
 *
 * 2013-10-23
 */
package com.travel.common.dto;

import java.util.List;

/**
 * @author Lenovo
 *
 */
public class MemberPrivateDTO {
	private List<Long>geoVisibleMemberIdList;
	private List<Long>phoneVisibleMemberIdList;
	private List<MemberDTO>memberList;	

	
	public List<Long> getGeoVisibleMemberIdList() {
		return geoVisibleMemberIdList;
	}
	public void setGeoVisibleMemberIdList(List<Long> geoVisibleMemberIdList) {
		this.geoVisibleMemberIdList = geoVisibleMemberIdList;
	}
	public List<Long> getPhoneVisibleMemberIdList() {
		return phoneVisibleMemberIdList;
	}
	public void setPhoneVisibleMemberIdList(List<Long> phoneVisibleMemberIdList) {
		this.phoneVisibleMemberIdList = phoneVisibleMemberIdList;
	}
	public List<MemberDTO> getMemberList() {
		return memberList;
	}
	public void setMemberList(List<MemberDTO> memberList) {
		this.memberList = memberList;
	}
	
	
}
