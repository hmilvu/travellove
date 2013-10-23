package com.travel.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

/**
 * AbstractRouteViewSpot entity provides the base persistence definition of the
 * RouteViewSpot entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractRouteViewSpot extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private ViewSpotInfo viewSpotInfo;
	private RouteInf routeInf;
	private SysUser sysUser;
	private Timestamp createDate;
	private Timestamp updateDate;

	// Constructors

	/** default constructor */
	public AbstractRouteViewSpot() {
	}

	/** full constructor */
	public AbstractRouteViewSpot(ViewSpotInfo viewSpotInfo, RouteInf routeInf,
			SysUser sysUser, Timestamp createDate, Timestamp updateDate) {
		this.viewSpotInfo = viewSpotInfo;
		this.routeInf = routeInf;
		this.sysUser = sysUser;
		this.createDate = createDate;
		this.updateDate = updateDate;
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
	@JoinColumn(name = "view_spot_id", nullable = false)
	public ViewSpotInfo getViewSpotInfo() {
		return this.viewSpotInfo;
	}

	public void setViewSpotInfo(ViewSpotInfo viewSpotInfo) {
		this.viewSpotInfo = viewSpotInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "route_id", nullable = false)
	public RouteInf getRouteInf() {
		return this.routeInf;
	}

	public void setRouteInf(RouteInf routeInf) {
		this.routeInf = routeInf;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id", nullable = false)
	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
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

}