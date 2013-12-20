package com.travel.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.travel.common.dto.ReplyDTO;
import com.travel.utils.DateUtils;

/**
 * Reply entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "reply", catalog = "travel_love_db")
public class Reply extends AbstractReply implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Reply() {
	}

	/** minimal constructor */
	public Reply(MemberInf memberInf, Message message,
			Timestamp createDate, Timestamp updateDate) {
		super(memberInf, message, createDate, updateDate);
	}

	/** full constructor */
	public Reply(MemberInf memberInf, Message message,
			String content, Timestamp createDate, Timestamp updateDate) {
		super(memberInf, message, content, createDate, updateDate);
	}

	/**
	 * @return
	 */
	public ReplyDTO toDTO() {
		ReplyDTO dto = new ReplyDTO();
		dto.setContent(getContent());
		dto.setId(getId());
		dto.setCreateTime(DateUtils.toStr(getCreateDate()));
		if(getMemberInf() != null){
			dto.setMemberId(getMemberInf().getId());
			dto.setMemberName(getMemberInf().getMemberName());
		} else {
			dto.setMemberId(getSysUser().getId());
			dto.setMemberName("系统管理员");
		}
		dto.setMessageId(getMessage().getId());
		return dto;
	}

}
