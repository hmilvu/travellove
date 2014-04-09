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
 * AbstractMemberAdvice entity provides the base persistence definition of the
 * MemberAdvice entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractMemberAdvice extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private MemberInf memberInf;
	private String topic;
	private String content;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public AbstractMemberAdvice() {
	}

	/** minimal constructor */
	public AbstractMemberAdvice(MemberInf memberInf, String content,
			Timestamp createTime) {
		this.memberInf = memberInf;
		this.content = content;
		this.createTime = createTime;
	}

	/** full constructor */
	public AbstractMemberAdvice(MemberInf memberInf, String topic,
			String content, Timestamp createTime) {
		this.memberInf = memberInf;
		this.topic = topic;
		this.content = content;
		this.createTime = createTime;
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

	@Column(name = "topic", length = 200)
	public String getTopic() {
		return this.topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Column(name = "content", nullable = false, length = 2000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "create_time", nullable = false, length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}