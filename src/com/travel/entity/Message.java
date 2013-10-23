package com.travel.entity;

import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Message entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "message", catalog = "travel_love_db")
public class Message extends AbstractMessage implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Message() {
	}

	/** minimal constructor */
	public Message(TeamInfo teamInfo, SysUser sysUser, Long authorId,
			Integer priority, Integer status, Integer type, String topic,
			String content, Long receiverId, Timestamp remindTime,
			Integer remindMode, Timestamp createDate, Timestamp updateDate) {
		super(teamInfo, sysUser, authorId, priority, status, type, topic,
				content, receiverId, remindTime, remindMode, createDate,
				updateDate);
	}

	/** full constructor */
	public Message(TeamInfo teamInfo, SysUser sysUser, Long authorId,
			Integer priority, Integer status, Integer type, String topic,
			String content, Long receiverId, Timestamp remindTime,
			Integer remindMode, Timestamp createDate, Timestamp updateDate,
			Set<Reply> replies) {
		super(teamInfo, sysUser, authorId, priority, status, type, topic,
				content, receiverId, remindTime, remindMode, createDate,
				updateDate, replies);
	}

}
