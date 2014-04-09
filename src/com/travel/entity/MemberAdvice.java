package com.travel.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * MemberAdvice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "member_advice", catalog = "travel_love_db")
public class MemberAdvice extends AbstractMemberAdvice implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public MemberAdvice() {
	}

	/** minimal constructor */
	public MemberAdvice(MemberInf memberInf, String content,
			Timestamp createTime) {
		super(memberInf, content, createTime);
	}

	/** full constructor */
	public MemberAdvice(MemberInf memberInf, String topic, String content,
			Timestamp createTime) {
		super(memberInf, topic, content, createTime);
	}

}
