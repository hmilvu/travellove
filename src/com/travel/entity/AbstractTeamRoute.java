package com.travel.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.GenericGenerator;

/**
 * AbstractTeamRoute entity provides the base persistence definition of the
 * TeamRoute entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractTeamRoute extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private RouteInf routeInf;
	private TeamInfo teamInfo;
	private SysUser sysUser;
	private Date date;
	private Date endDate;
	private Integer status;
	private Timestamp createDate;
	private Timestamp updateDate;
	private Integer routeOrder;
	// Constructors

	/** default constructor */
	public AbstractTeamRoute() {
	}

	/** full constructor */
	public AbstractTeamRoute(RouteInf routeInf, TeamInfo teamInfo, SysUser sysUser, Date date,
			Integer status, Timestamp createDate, Timestamp updateDate) {
		this.routeInf = routeInf;
		this.sysUser = sysUser;
		this.date = date;
		this.status = status;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.teamInfo = teamInfo;
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
	@Fetch(FetchMode.JOIN) 
	@JoinColumn(name = "route_id", nullable = false)
	public RouteInf getRouteInf() {
		return this.routeInf;
	}

	public void setRouteInf(RouteInf routeInf) {
		this.routeInf = routeInf;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN) 
	@JoinColumn(name = "team_id", nullable = false)
	public TeamInfo getTeamInfo() {
		return this.teamInfo;
	}

	public void setTeamInfo(TeamInfo teamInfo) {
		this.teamInfo = teamInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id", nullable = false)
	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false, length = 10)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "end_date", nullable = false, length = 10)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
	
	@Column(name = "route_order", nullable = false)
	public Integer getRouteOrder() {
		return this.routeOrder;
	}

	public void setRouteOrder(Integer routeOrder) {
		this.routeOrder = routeOrder;
	}

}