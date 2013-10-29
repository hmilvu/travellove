package com.travel.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

/**
 * AbstractSysUser entity provides the base persistence definition of the
 * SysUser entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractSysUser extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private String username;
	private String password;
	private Integer superAdmin;
	private Long travelId;
	private String travelName;
	private Integer status;
	private String name;
	private String mobile;
	private String email;
	private String telNumber;
	private Timestamp createDate;
	private Timestamp updateDate;
	private Long updateUserId;
	private Set<ItemInf> itemInfs = new HashSet<ItemInf>(0);
	private Set<ImgInf> imgInfs = new HashSet<ImgInf>(0);
	private Set<RouteViewSpot> routeViewSpots = new HashSet<RouteViewSpot>(0);
	private Set<TravelInf> travelInfs = new HashSet<TravelInf>(0);
	private Set<TeamInfo> teamInfos = new HashSet<TeamInfo>(0);
	private Set<ViewSpotInfo> viewSpotInfos = new HashSet<ViewSpotInfo>(0);
	private Set<Order> orders = new HashSet<Order>(0);
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);
	private Set<AppVersion> appVersions = new HashSet<AppVersion>(0);
	private Set<TeamRoute> teamRoutes = new HashSet<TeamRoute>(0);
	private Set<MemberInf> memberInfs = new HashSet<MemberInf>(0);
	private Set<Message> messages = new HashSet<Message>(0);
	private Set<RouteInf> routeInfs = new HashSet<RouteInf>(0);

	// Constructors

	/** default constructor */
	public AbstractSysUser() {
	}

	/** minimal constructor */
	public AbstractSysUser(String username, String password,
			Integer superAdmin, Long travelId, String travelName,
			Integer status, String name, String mobile, String email,
			String telNumber, Timestamp createDate, Timestamp updateDate,
			Long updateUserId) {
		this.username = username;
		this.password = password;
		this.superAdmin = superAdmin;
		this.travelId = travelId;
		this.travelName = travelName;
		this.status = status;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.telNumber = telNumber;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.updateUserId = updateUserId;
	}

	/** full constructor */
	public AbstractSysUser(String username, String password,
			Integer superAdmin, Long travelId, String travelName,
			Integer status, String name, String mobile, String email,
			String telNumber, Timestamp createDate, Timestamp updateDate,
			Long updateUserId, Set<ItemInf> itemInfs, Set<ImgInf> imgInfs,
			Set<RouteViewSpot> routeViewSpots, Set<TravelInf> travelInfs,
			Set<TeamInfo> teamInfos, Set<ViewSpotInfo> viewSpotInfos,
			Set<Order> orders, Set<UserRole> userRoles,
			Set<AppVersion> appVersions, Set<TeamRoute> teamRoutes,
			Set<MemberInf> memberInfs,
			Set<Message> messages, Set<RouteInf> routeInfs) {
		this.username = username;
		this.password = password;
		this.superAdmin = superAdmin;
		this.travelId = travelId;
		this.travelName = travelName;
		this.status = status;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.telNumber = telNumber;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.updateUserId = updateUserId;
		this.itemInfs = itemInfs;
		this.imgInfs = imgInfs;
		this.routeViewSpots = routeViewSpots;
		this.travelInfs = travelInfs;
		this.teamInfos = teamInfos;
		this.viewSpotInfos = viewSpotInfos;
		this.orders = orders;
		this.userRoles = userRoles;
		this.appVersions = appVersions;
		this.teamRoutes = teamRoutes;
		this.memberInfs = memberInfs;
		this.messages = messages;
		this.routeInfs = routeInfs;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "username", unique = true, nullable = false, length = 32)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 128)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "super_admin", nullable = false)
	public Integer getSuperAdmin() {
		return this.superAdmin;
	}

	public void setSuperAdmin(Integer superAdmin) {
		this.superAdmin = superAdmin;
	}

	@Column(name = "travel_id", nullable = false)
	public Long getTravelId() {
		return this.travelId;
	}

	public void setTravelId(Long travelId) {
		this.travelId = travelId;
	}

	@Column(name = "travel_name", nullable = false, length = 64)
	public String getTravelName() {
		return this.travelName;
	}

	public void setTravelName(String travelName) {
		this.travelName = travelName;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "name", nullable = false, length = 32)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "mobile", nullable = false, length = 20)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "email", nullable = false, length = 64)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "tel_number", nullable = false, length = 16)
	public String getTelNumber() {
		return this.telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	@Column(name = "create_date", nullable = false, length = 19)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "update_date", nullable = false, length = 19)
	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "update_user_id", nullable = false)
	public Long getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysUser")
	public Set<ItemInf> getItemInfs() {
		return this.itemInfs;
	}

	public void setItemInfs(Set<ItemInf> itemInfs) {
		this.itemInfs = itemInfs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysUser")
	public Set<ImgInf> getImgInfs() {
		return this.imgInfs;
	}

	public void setImgInfs(Set<ImgInf> imgInfs) {
		this.imgInfs = imgInfs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysUser")
	public Set<RouteViewSpot> getRouteViewSpots() {
		return this.routeViewSpots;
	}

	public void setRouteViewSpots(Set<RouteViewSpot> routeViewSpots) {
		this.routeViewSpots = routeViewSpots;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysUser")
	public Set<TravelInf> getTravelInfs() {
		return this.travelInfs;
	}

	public void setTravelInfs(Set<TravelInf> travelInfs) {
		this.travelInfs = travelInfs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysUser")
	public Set<TeamInfo> getTeamInfos() {
		return this.teamInfos;
	}

	public void setTeamInfos(Set<TeamInfo> teamInfos) {
		this.teamInfos = teamInfos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysUser")
	public Set<ViewSpotInfo> getViewSpotInfos() {
		return this.viewSpotInfos;
	}

	public void setViewSpotInfos(Set<ViewSpotInfo> viewSpotInfos) {
		this.viewSpotInfos = viewSpotInfos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysUser")
	public Set<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysUser")
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysUser")
	public Set<AppVersion> getAppVersions() {
		return this.appVersions;
	}

	public void setAppVersions(Set<AppVersion> appVersions) {
		this.appVersions = appVersions;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysUser")
	public Set<TeamRoute> getTeamRoutes() {
		return this.teamRoutes;
	}

	public void setTeamRoutes(Set<TeamRoute> teamRoutes) {
		this.teamRoutes = teamRoutes;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysUser")
	public Set<MemberInf> getMemberInfs() {
		return this.memberInfs;
	}

	public void setMemberInfs(Set<MemberInf> memberInfs) {
		this.memberInfs = memberInfs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysUser")
	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}	

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysUser")
	public Set<RouteInf> getRouteInfs() {
		return this.routeInfs;
	}

	public void setRouteInfs(Set<RouteInf> routeInfs) {
		this.routeInfs = routeInfs;
	}

}