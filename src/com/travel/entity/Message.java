package com.travel.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	public Message(Long authorId,
			Integer priority, Integer status, Integer type, String topic,
			String content, Long receiverId, Timestamp remindTime,
			Integer remindMode, Timestamp createDate, Timestamp updateDate) {
		super(authorId, priority, status, type, topic,
				content, receiverId, remindTime, remindMode, createDate,
				updateDate);
	}

	/** full constructor */
	public Message(Long authorId,
			Integer priority, Integer status, Integer type, String topic,
			String content, Long receiverId, Timestamp remindTime,
			Integer remindMode, Timestamp createDate, Timestamp updateDate,
			Set<Reply> replies) {
		super(authorId, priority, status, type, topic,
				content, receiverId, remindTime, remindMode, createDate,
				updateDate, replies);
	}

	/**
	 * @return
	 */
	public MessageDTO toDTO() {
		MessageDTO dto = new MessageDTO();
		dto.setId(getId());
		dto.setType(getType());
		dto.setPriority(getPriority());;
		dto.setStatus(getStatus());
		dto.setCreateDate(DateUtils.toStr(getCreateDate()));
		dto.setTopic(getTopic());
		dto.setContent(getContent());
		dto.setCreateId(getCreateId());
		dto.setCreateType(getCreateType());
		return dto;
	}
	
	@Override
	public Message clone(){
		Message msg = new Message();
		msg.setTravelInf(getTravelInf());
		msg.setCreateId(getCreateId());
		msg.setCreateType(getCreateType());
		msg.setAuthorId(getAuthorId());
		msg.setPriority(getPriority());
		msg.setStatus(getStatus());
		msg.setTopic(getTopic());
		msg.setType(getType());
		msg.setContent(getContent());
		msg.setReceiverId(getReceiverId());
		msg.setReceiverType(getReceiverType());
		msg.setRemindTime(getRemindTime());
		msg.setRemindMode(getRemindMode());
		msg.setCreateDate(getCreateDate());
		msg.setUpdateDate(getUpdateDate());
		msg.setPushStatus(getPushStatus());
		return msg;
		
	}
	
	@Transient
	private String receiverName;
	@Transient
	public String getReceiverName() {
		return receiverName;
	}
	@Transient
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
	@Transient
	private String remindTimeStr;
	@Transient
	public String getRemindTimeStr() {
		remindTimeStr = DateUtils.toStr(new Date(getRemindTime().getTime()));
		return remindTimeStr.substring(0, remindTimeStr.length() - 3);
	}
	@Transient
	public void setRemindTimeStr(String remindTimeStr) {
		this.remindTimeStr = remindTimeStr;
	}
	
	
}
