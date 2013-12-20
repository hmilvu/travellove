package com.travel.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ChannelMessage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "channel_message", catalog = "travel_love_db")
public class ChannelMessage extends AbstractChannelMessage implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public ChannelMessage() {
	}

	/** minimal constructor */
	public ChannelMessage(Long messageId, Long channelId, Integer status,
			Timestamp createDate) {
		super(messageId, channelId, status, createDate);
	}

	/** full constructor */
	public ChannelMessage(Long messageId, Long channelId, Integer status,
			String response, Timestamp createDate) {
		super(messageId, channelId, status, response, createDate);
	}

}
