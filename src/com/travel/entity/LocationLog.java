package com.travel.entity;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.travel.common.dto.LocationLogDTO;

/**
 * LocationLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "location_log", catalog = "travel_love_db")
public class LocationLog extends AbstractLocationLog implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public LocationLog() {
	}

	/** full constructor */
	public LocationLog(TeamInfo teamInfo, MemberInf memberInf,
			Timestamp locateTime, Double longitude,
			Double latitude, Timestamp createDate, Timestamp updateDate) {
		super(teamInfo, memberInf, locateTime, longitude,
				latitude, createDate, updateDate);
	}
	
	public LocationLogDTO toDTO(){
		LocationLogDTO dto = new LocationLogDTO();
		dto.setLatitude(getLatitude());
		dto.setLongitude(getLongitude());
		dto.setMemberName(getMemberInf().getMemberName());
		dto.setNickName(getMemberInf().getNickname());
		dto.setSex(getMemberInf().getSex());
		dto.setTravelerMobile(getMemberInf().getTravelerMobile());
		dto.setMemberId(getMemberInf().getId());
		dto.setMemberType(getMemberInf().getMemberType());
		return dto;
	}
}
