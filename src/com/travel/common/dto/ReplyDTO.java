/**
 * @author Zhang Zhipeng
 *
 * 2013-10-23
 */
package com.travel.common.dto;

import java.sql.Timestamp;

import com.travel.entity.MemberInf;
import com.travel.entity.Message;
import com.travel.entity.SysUser;
import com.travel.entity.TeamInfo;

/**
 * @author Lenovo
 *
 */
public class ReplyDTO {
	private Long id;
	private Long memberId;
	private String memberName;
	private Long messageId;
	private String content;
	private String createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
		
	
}
