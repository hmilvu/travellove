package com.travel.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
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
	private SysUser sysUser;
	private String content;
	private Timestamp createDate;
	private Timestamp updateDate;

	// Constructors

	/** default constructor */
	public AbstractReply() {
	}

	/** minimal constructor */
	public AbstractReply(MemberInf memberInf, Message message, SysUser sysUser,
			Timestamp createDate, Timestamp updateDate) {
		this.memberInf = memberInf;
		this.message = message;
		this.sysUser = sysUser;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	/** full constructor */
	public AbstractReply(MemberInf memberInf, Message message, SysUser sysUser,
			String content, Timestamp createDate, Timestamp updateDate) {
		this.memberInf = memberInf;
		this.message = message;
		this.sysUser = sysUser;
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
	@JoinColumn(name = "member_id", nullable = false)
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id", nullable = false)
	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
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

}