package com.travel.entity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

/**
 * AbstractMemberMessageVisibility entity provides the base persistence
 * definition of the MemberMessageVisibility entity. @author MyEclipse
 * Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractMemberMessageVisibility extends BaseEntity
		implements java.io.Serializable {

	// Fields

	private Long id;
	private Message message;
	private MemberInf memberInf;

	// Constructors

	/** default constructor */
	public AbstractMemberMessageVisibility() {
	}

	/** full constructor */
	public AbstractMemberMessageVisibility(Message message, MemberInf memberInf) {
		this.message = message;
		this.memberInf = memberInf;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "message_id", nullable = false)
	public Message getMessage() {
		return this.message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	public MemberInf getMemberInf() {
		return this.memberInf;
	}

	public void setMemberInf(MemberInf memberInf) {
		this.memberInf = memberInf;
	}

}