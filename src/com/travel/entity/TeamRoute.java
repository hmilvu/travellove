package com.travel.entity;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

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
	public TeamRoute(RouteInf routeInf, SysUser sysUser, Date date,
			Integer status, Timestamp createDate, Timestamp updateDate) {
		super(routeInf, sysUser, date, status, createDate, updateDate);
	}

}
