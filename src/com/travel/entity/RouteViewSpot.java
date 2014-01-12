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
	private String startTimeStr;
	@Transient
	private String endTimeStr;
	@Transient
	public String getStartTimeStr() {
		if(getStartTime()!=null){
			startTimeStr = DateUtils.timeToHHssStr(getStartTime());
			return startTimeStr;
		} else {
			return "";
		}
	}
	@Transient
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	@Transient
	public String getEndTimeStr() {
		if(getEndTime()!=null){
			endTimeStr = DateUtils.timeToHHssStr(getEndTime());
			return endTimeStr;
		} else {
			return "";
		}
	}
	@Transient
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	

}
