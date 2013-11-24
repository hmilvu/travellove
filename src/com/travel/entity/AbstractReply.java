package com.travel.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 * AbstractReply entity provides the base persistence definition of the Reply
 * entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractReply extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private MemberInf memberInf;
	private Message message;
	private String content;
	private Timestamp createDate;
	private Timestamp updateDate;
	private SysUser sysUser;
	// Constructors

	/** default constructor */
	public AbstractReply() {
	}

	/** minimal constructor */
	public AbstractReply(MemberInf memberInf, Message message,
			Timestamp createDate, Timestamp updateDate) {
		this.memberInf = memberInf;
		this.message = message;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	/** full constructor */
	public AbstractReply(MemberInf memberInf, Message message,
			String content, Timestamp createDate, Timestamp updateDate) {
		this.memberInf = memberInf;
		this.message = message;
		this.content = content;
		this.createDate = createDate;
		this.updateDate = updateDate;
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
	@JoinColumn(name = "member_id", nullable = true)
	public MemberInf getMemberInf() {
		return this.memberInf;
	}

	public void setMemberInf(MemberInf memberInf) {
		this.memberInf = memberInf;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "msg_id", nullable = false)
	public Message getMessage() {
		return this.message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@Column(name = "content", length = 1024)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "create_date", nullable = false, length = 19)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "update_date", nullable = false, length = 19)
	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_user_id", nullable = true)
	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
}