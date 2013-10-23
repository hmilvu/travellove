package com.travel.entity;

import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * SysUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_user", catalog = "travel_love_db")
public class SysUser extends AbstractSysUser implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public SysUser() {
	}

	/** minimal constructor */
	public SysUser(String username, String password, Integer superAdmin,
			Long travelId, String travelName, Integer status, String name,
			String mobile, String email, String telNumber,
			Timestamp createDate, Timestamp updateDate, Long updateUserId) {
		super(username, password, superAdmin, travelId, travelName, status,
				name, mobile, email, telNumber, createDate, updateDate,
				updateUserId);
	}

	/** full constructor */
	public SysUser(String username, String password, Integer superAdmin,
			Long travelId, String travelName, Integer status, String name,
			String mobile, String email, String telNumber,
			Timestamp createDate, Timestamp updateDate, Long updateUserId,
			Set<ItemInf> itemInfs, Set<ImgInf> imgInfs,
			Set<RouteViewSpot> routeViewSpots, Set<TravelInf> travelInfs,
			Set<TeamInfo> teamInfos, Set<ViewSpotInfo> viewSpotInfos,
			Set<Order> orders, Set<UserRole> userRoles,
			Set<AppVersion> appVersions, Set<TeamRoute> teamRoutes,
			Set<LocationLog> locationLogs, Set<MemberInf> memberInfs,
			Set<Message> messages, Set<Reply> replies, Set<RouteInf> routeInfs) {
		super(username, password, superAdmin, travelId, travelName, status,
				name, mobile, email, telNumber, createDate, updateDate,
				updateUserId, itemInfs, imgInfs, routeViewSpots, travelInfs,
				teamInfos, viewSpotInfos, orders, userRoles, appVersions,
				teamRoutes, locationLogs, memberInfs, messages, replies,
				routeInfs);
	}

}
