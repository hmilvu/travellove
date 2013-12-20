package com.travel.entity;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.travel.common.dto.ViewSpotDTO;

/**
 * ViewSpotInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "view_spot_info", catalog = "travel_love_db")
public class ViewSpotInfo extends AbstractViewSpotInfo implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public ViewSpotInfo() {
	}

	/** minimal constructor */
	public ViewSpotInfo(SysUser sysUser, String name, Double longitude,
			Double latitude, Timestamp createDate, Timestamp updateDate) {
		super(sysUser, name, longitude, latitude, createDate, updateDate);
	}

	/** full constructor */
	public ViewSpotInfo(SysUser sysUser, String name, Double longitude,
			Double latitude, String description, Timestamp createDate,
			Timestamp updateDate, Set<RouteViewSpot> routeViewSpots) {
		super(sysUser, name, longitude, latitude, description, createDate,
				updateDate, routeViewSpots);
	}

	/**
	 * @return
	 */
	public ViewSpotDTO toDTO() {
		ViewSpotDTO dto = new ViewSpotDTO();
		dto.setViewId(getId());
		dto.setName(getName());
		dto.setDescription(getDescription());
		dto.setLatitude(getLatitude());
		dto.setLongitude(getLongitude());
		return dto;
	}

}
