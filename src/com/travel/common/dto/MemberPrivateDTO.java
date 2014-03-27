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
	private List<Long>visibleMemberIdList;
	private List<MemberDTO>memberList;
	public List<Long> getVisibleMemberIdList() {
		return visibleMemberIdList;
	}
	public void setVisibleMemberIdList(List<Long> visibleMemberIdList) {
		this.visibleMemberIdList = visibleMemberIdList;
	}
	public List<MemberDTO> getMemberList() {
		return memberList;
	}
	public void setMemberList(List<MemberDTO> memberList) {
		this.memberList = memberList;
	}
	
	
}
