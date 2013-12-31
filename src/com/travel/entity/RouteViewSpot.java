package com.travel.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.travel.utils.DateUtils;

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
	
	@Transient
	private String startDateStr;
	@Transient
	private String endDateStr;
	@Transient
	public String getStartDateStr() {
		if(getStartDate()!=null){
			startDateStr = DateUtils.toTimeStr2(new Date(getStartDate().getTime()));
			return startDateStr;
		} else {
			return "";
		}
	}
	@Transient
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	@Transient
	public String getEndDateStr() {
		if(getEndDate()!=null){
			endDateStr = DateUtils.toTimeStr2(new Date(getEndDate().getTime()));
			return endDateStr;
		} else {
			return "";
		}
	}
	@Transient
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	

}
