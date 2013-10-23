package com.travel.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;

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
	public Reply(MemberInf memberInf, Message message, SysUser sysUser,
			Timestamp createDate, Timestamp updateDate) {
		super(memberInf, message, sysUser, createDate, updateDate);
	}

	/** full constructor */
	public Reply(MemberInf memberInf, Message message, SysUser sysUser,
			String content, Timestamp createDate, Timestamp updateDate) {
		super(memberInf, message, sysUser, content, createDate, updateDate);
	}

}
