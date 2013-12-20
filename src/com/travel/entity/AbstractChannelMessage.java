package com.travel.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

/**
 * AbstractChannelMessage entity provides the base persistence definition of the
 * ChannelMessage entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractChannelMessage extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private Long messageId;
	private Long channelId;
	private Integer status;
	private String response;
	private Timestamp createDate;

	// Constructors

	/** default constructor */
	public AbstractChannelMessage() {
	}

	/** minimal constructor */
	public AbstractChannelMessage(Long messageId, Long channelId,
			Integer status, Timestamp createDate) {
		this.messageId = messageId;
		this.channelId = channelId;
		this.status = status;
		this.createDate = createDate;
	}

	/** full constructor */
	public AbstractChannelMessage(Long messageId, Long channelId,
			Integer status, String response, Timestamp createDate) {
		this.messageId = messageId;
		this.channelId = channelId;
		this.status = status;
		this.response = response;
		this.createDate = createDate;
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

	@Column(name = "message_id", nullable = false)
	public Long getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	@Column(name = "channel_id", nullable = false)
	public Long getChannelId() {
		return this.channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "response", length = 1024)
	public String getResponse() {
		return this.response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Column(name = "create_date", nullable = false, length = 19)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

}