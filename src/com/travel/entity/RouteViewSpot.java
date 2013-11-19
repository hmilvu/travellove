package com.travel.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * RouteViewSpot entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "route_view_spot", catalog = "travel_love_db")
public class RouteViewSpot extends AbstractRouteViewSpot implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public RouteViewSpot() {
	}

	/** full constructor */
	public RouteViewSpot(ViewSpotInfo viewSpotInfo, RouteInf routeInf,
			SysUser sysUser, Timestamp createDate, Timestamp updateDate, Integer order) {
		super(viewSpotInfo, routeInf, sysUser, createDate, updateDate, order);
	}

}
