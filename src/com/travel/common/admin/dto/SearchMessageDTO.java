/**
 * @author Zhang Zhipeng
 *
 * 2013-11-4
 */
package com.travel.common.admin.dto;

import java.util.Date;

/**
 * @author Lenovo
 *
 */
public class SearchMessageDTO extends BaseAdminDTO{
	private String topic;
	private String teamName;
	private Integer type;
	private Integer priority;
	private Integer status;
	private Long triggerId;
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public Long getTriggerId() {
		return triggerId;
	}
	public void setTriggerId(Long triggerId) {
		this.triggerId = triggerId;
	}
	

}
