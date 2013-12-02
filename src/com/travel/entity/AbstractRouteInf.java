package com.travel.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

/**
 * AbstractRouteInf entity provides the base persistence definition of the
 * RouteInf entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractRouteInf extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private SysUser sysUser;
	private String routeName;
	private String startAddress;
	private Double startLongtitude;
	private Double startLatitude;
	private String endAddress;
	private Double endLongitude;
	private Double endLatitude;
	private String description;
	private Timestamp createDate;
	private Timestamp updateDate;
	private Set<RouteViewSpot> routeViewSpots = new HashSet<RouteViewSpot>(0);
	private Set<TeamRoute> teamRoutes = new HashSet<TeamRoute>(0);
	private TravelInf travelInf;
	// Constructors

	/** default constructor */
	public AbstractRouteInf() {
	}

	/** minimal constructor */
	public AbstractRouteInf(SysUser sysUser, String routeName,
			String startAddress, Double startLongtitude, Double startLatitude,
			String endAddress, Double endLongitude, Double endLatitude,
			Timestamp createDate, Timestamp updateDate) {
		this.sysUser = sysUser;
		this.routeName = routeName;
		this.startAddress = startAddress;
		this.startLongtitude = startLongtitude;
		this.startLatitude = startLatitude;
		this.endAddress = endAddress;
		this.endLongitude = endLongitude;
		this.endLatitude = endLatitude;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	/** full constructor */
	public AbstractRouteInf(SysUser sysUser, String routeName,
			String startAddress, Double startLongtitude, Double startLatitude,
			String endAddress, Double endLongitude, Double endLatitude,
			String description, Timestamp createDate, Timestamp updateDate,
			Set<RouteViewSpot> routeViewSpots, Set<TeamRoute> teamRoutes) {
		this.sysUser = sysUser;
		this.routeName = routeName;
		this.startAddress = startAddress;
		this.startLongtitude = startLongtitude;
		this.startLatitude = startLatitude;
		this.endAddress = endAddress;
		this.endLongitude = endLongitude;
		this.endLatitude = endLatitude;
		this.description = description;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.routeViewSpots = routeViewSpots;
		this.teamRoutes = teamRoutes;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id", nullable = false)
	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	@Column(name = "route_name", nullable = false, length = 64)
	public String getRouteName() {
		return this.routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	@Column(name = "start_address", nullable = false, length = 64)
	public String getStartAddress() {
		return this.startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	@Column(name = "start_longtitude", nullable = false, precision = 20, scale = 8)
	public Double getStartLongtitude() {
		return this.startLongtitude;
	}

	public void setStartLongtitude(Double startLongtitude) {
		this.startLongtitude = startLongtitude;
	}

	@Column(name = "start_latitude", nullable = false, precision = 20, scale = 8)
	public Double getStartLatitude() {
		return this.startLatitude;
	}

	public void setStartLatitude(Double startLatitude) {
		this.startLatitude = startLatitude;
	}

	@Column(name = "end_address", nullable = false, length = 64)
	public String getEndAddress() {
		return this.endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}

	@Column(name = "end_longitude", nullable = false, precision = 20, scale = 8)
	public Double getEndLongitude() {
		return this.endLongitude;
	}

	public void setEndLongitude(Double endLongitude) {
		this.endLongitude = endLongitude;
	}

	@Column(name = "end_latitude", nullable = false, precision = 20, scale = 8)
	public Double getEndLatitude() {
		return this.endLatitude;
	}

	public void setEndLatitude(Double endLatitude) {
		this.endLatitude = endLatitude;
	}

	@Column(name = "description", length = 1024)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "routeInf")
	public Set<RouteViewSpot> getRouteViewSpots() {
		return this.routeViewSpots;
	}

	public void setRouteViewSpots(Set<RouteViewSpot> routeViewSpots) {
		this.routeViewSpots = routeViewSpots;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "routeInf")
	public Set<TeamRoute> getTeamRoutes() {
		return this.teamRoutes;
	}

	public void setTeamRoutes(Set<TeamRoute> teamRoutes) {
		this.teamRoutes = teamRoutes;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "travel_id", nullable = false)
	public TravelInf getTravelInf() {
		return this.travelInf;
	}

	public void setTravelInf(TravelInf travelInf) {
		this.travelInf = travelInf;
	}
}