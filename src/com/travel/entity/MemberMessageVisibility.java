package com.travel.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * MemberMessageVisibility entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "member_message_visibility", catalog = "travel_love_db")
public class MemberMessageVisibility extends AbstractMemberMessageVisibility
		implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public MemberMessageVisibility() {
	}

	/** full constructor */
	public MemberMessageVisibility(Message message, MemberInf memberInf) {
		super(message, memberInf);
	}

}
