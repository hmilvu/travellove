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
 * AbstractViewSpotInfo entity provides the base persistence definition of the
 * ViewSpotInfo entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractViewSpotInfo extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private SysUser sysUser;
	private String name;
	private Double longitude;
	private Double latitude;
	private String description;
	private Integer type;
	private Timestamp createDate;
	private Timestamp updateDate;
	private Set<RouteViewSpot> routeViewSpots = new HashSet<RouteViewSpot>(0);
	private TravelInf travelInf;
	// Constructors

	/** default constructor */
	public AbstractViewSpotInfo() {
	}

	/** minimal constructor */
	public AbstractViewSpotInfo(SysUser sysUser, String name, Double longitude,
			Double latitude, Timestamp createDate, Timestamp updateDate) {
		this.sysUser = sysUser;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	/** full constructor */
	public AbstractViewSpotInfo(SysUser sysUser, String name, Double longitude,
			Double latitude, String description, Timestamp createDate,
			Timestamp updateDate, Set<RouteViewSpot> routeViewSpots) {
		this.sysUser = sysUser;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.description = description;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.routeViewSpots = routeViewSpots;
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

	@Column(name = "name", nullable = false, length = 64)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "longitude", nullable = false, precision = 20, scale = 8)
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude", nullable = false, precision = 20, scale = 8)
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "description")
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "viewSpotInfo")
	public Set<RouteViewSpot> getRouteViewSpots() {
		return this.routeViewSpots;
	}

	public void setRouteViewSpots(Set<RouteViewSpot> routeViewSpots) {
		this.routeViewSpots = routeViewSpots;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "travel_id")
	public TravelInf getTravelInf() {
		return this.travelInf;
	}

	public void setTravelInf(TravelInf travelInf) {
		this.travelInf = travelInf;
	}
}