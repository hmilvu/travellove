package com.travel.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.travel.common.dto.TeamRouteDTO;
import com.travel.utils.DateUtils;

/**
 * TeamRoute entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "team_route", catalog = "travel_love_db")
public class TeamRoute extends AbstractTeamRoute implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TeamRoute() {
	}

	/** full constructor */
	public TeamRoute(RouteInf routeInf, TeamInfo teamInfo, SysUser sysUser, Date date,
			Integer status, Timestamp createDate, Timestamp updateDate) {
		super(routeInf, teamInfo, sysUser, date, status, createDate, updateDate);
	}
	
	
	@Transient
	private String dateStr;
	@Transient
	public String getDateStr() {
		dateStr = DateUtils.toSimpleStr(getDate());
		return dateStr;
	}
	@Transient
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
}
