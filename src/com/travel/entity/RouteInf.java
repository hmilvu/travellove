package com.travel.entity;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.travel.common.dto.RouteInfDTO;

/**
 * RouteInf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "route_inf", catalog = "travel_love_db")
public class RouteInf extends AbstractRouteInf implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public RouteInf() {
	}

	/** minimal constructor */
	public RouteInf(SysUser sysUser, String routeName, String startAddress,
			Double startLongtitude, Double startLatitude, String endAddress,
			Double endLongitude, Double endLatitude, Timestamp createDate,
			Timestamp updateDate) {
		super(sysUser, routeName, startAddress, startLongtitude, startLatitude,
				endAddress, endLongitude, endLatitude, createDate, updateDate);
	}

	/** full constructor */
	public RouteInf(SysUser sysUser, String routeName, String startAddress,
			Double startLongtitude, Double startLatitude, String endAddress,
			Double endLongitude, Double endLatitude, String description,
			Timestamp createDate, Timestamp updateDate,
			Set<RouteViewSpot> routeViewSpots, Set<TeamRoute> teamRoutes) {
		super(sysUser, routeName, startAddress, startLongtitude, startLatitude,
				endAddress, endLongitude, endLatitude, description, createDate,
				updateDate, routeViewSpots, teamRoutes);
	}

	/**
	 * @return
	 */
	public RouteInfDTO toDTO() {
		RouteInfDTO dto = new RouteInfDTO();
		dto.setId(getId());
		dto.setRouteName(getRouteName());
		dto.setDescription(getDescription());
		dto.setStartAddress(getStartAddress());
		dto.setEndAddress(getEndAddress());
		dto.setStartLongtitude(getStartLongtitude());
		dto.setStartLatitude(getStartLatitude());
		dto.setEndLongitude(getEndLongitude());
		dto.setEndLatitude(getEndLatitude());
		return dto;
	}

}
