package com.travel.entity;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

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
	public LocationLog(TeamInfo teamInfo, MemberInf memberInf, SysUser sysUser,
			Date locateDate, Time locateTime, Double longitude,
			Double latitude, Timestamp createDate, Timestamp updateDate) {
		super(teamInfo, memberInf, sysUser, locateDate, locateTime, longitude,
				latitude, createDate, updateDate);
	}

}
