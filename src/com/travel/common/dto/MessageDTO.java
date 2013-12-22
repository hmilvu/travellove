/**
 * @author Zhang Zhipeng
 *
 * 2013-10-23
 */
package com.travel.common.dto;

import java.sql.Timestamp;

import com.travel.entity.SysUser;
import com.travel.entity.TeamInfo;

/**
 * @author Lenovo
 *
 */
public class MessageDTO {
	private Long id;
	private Integer priority;
	private Integer status;
	private Integer type;
	private String topic;
	private String content;
	private String createDate;
	private String creatorName;
	private Integer createType;
	private Long createId;
	
	public Integer getCreateType() {
		return createType;
	}
	public void setCreateType(Integer createType) {
		this.createType = createType;
	}
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
	
	
}
