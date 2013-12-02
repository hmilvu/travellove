package com.travel.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.travel.common.dto.MessageDTO;
import com.travel.utils.DateUtils;

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

	/**
	 * @return
	 */
	public MessageDTO toDTO() {
		MessageDTO dto = new MessageDTO();
		dto.setId(getId());
		dto.setPriority(getPriority());;
		dto.setStatus(getStatus());
		dto.setCreateDate(DateUtils.toStr(getCreateDate()));
		dto.setTopic(getTopic());
		dto.setContent(getContent());
		return dto;
	}
	
	@Override
	public Message clone(){
		Message msg = new Message();
		msg.setTeamInfo(getTeamInfo());
		msg.setSysUser(getSysUser());
		msg.setAuthorId(getAuthorId());
		msg.setPriority(getPriority());
		msg.setStatus(getStatus());
		msg.setTopic(getTopic());
		msg.setType(getType());
		msg.setContent(getContent());
		msg.setReceiverId(getReceiverId());
		msg.setRemindTime(getRemindTime());
		msg.setRemindMode(getRemindMode());
		msg.setCreateDate(getCreateDate());
		msg.setUpdateDate(getUpdateDate());
		return msg;
		
	}

}
