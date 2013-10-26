package com.travel.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.travel.common.dto.TeamInfoDTO;
import com.travel.utils.DateUtils;

/**
 * TeamInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "team_info", catalog = "travel_love_db")
public class TeamInfo extends AbstractTeamInfo implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public TeamInfo() {
	}

	/** minimal constructor */
	public TeamInfo(TravelInf travelInf, SysUser sysUser, String name,
			Date beginDate, Integer peopleCount, Timestamp createDate,
			Timestamp updateDate) {
		super(travelInf, sysUser, name, beginDate, peopleCount, createDate,
				updateDate);
	}

	/** full constructor */
	public TeamInfo(TravelInf travelInf, SysUser sysUser, String name,
			Date beginDate, Date endDate, Integer status, Integer peopleCount,
			String description, Timestamp createDate, Timestamp updateDate,
			Set<Message> messages, Set<LocationLog> locationLogs,
			Set<MemberInf> memberInfs, Set<Order> orders) {
		super(travelInf, sysUser, name, beginDate, endDate, status,
				peopleCount, description, createDate, updateDate, messages,
				locationLogs, memberInfs, orders);
	}
	
	public TeamInfoDTO toDTO(){
		TeamInfoDTO dto = new TeamInfoDTO();
		dto.setName(getName());
		dto.setStatus(getStatus());
		dto.setDescription(getDescription());
		dto.setPeopleCount(getPeopleCount());
		dto.setBeginDate(DateUtils.toStr(getBeginDate()));
		dto.setEndDate(DateUtils.toStr(getEndDate()));
		return dto;		
	}

}
