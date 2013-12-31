package com.travel.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.travel.common.dto.ViewSpotDTO;
import com.travel.utils.DateUtils;

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
	@Transient
	private List<String>urls = new ArrayList<String>();	
	@Transient
	public List<String> getUrls() {
		return urls;
	}
	@Transient
	public void setUrls(List<String> urls) {
		this.urls = urls;
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
		dto.addImageUrl(urls);
		return dto;
	}

}
