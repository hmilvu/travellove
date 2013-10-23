package com.travel.entity;

import java.sql.Time;
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
import org.hibernate.annotations.GenericGenerator;

/**
 * AbstractLocationLog entity provides the base persistence definition of the
 * LocationLog entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractLocationLog extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private TeamInfo teamInfo;
	private MemberInf memberInf;
	private SysUser sysUser;
	private Date locateDate;
	private Time locateTime;
	private Double longitude;
	private Double latitude;
	private Timestamp createDate;
	private Timestamp updateDate;

	// Constructors

	/** default constructor */
	public AbstractLocationLog() {
	}

	/** full constructor */
	public AbstractLocationLog(TeamInfo teamInfo, MemberInf memberInf,
			SysUser sysUser, Date locateDate, Time locateTime,
			Double longitude, Double latitude, Timestamp createDate,
			Timestamp updateDate) {
		this.teamInfo = teamInfo;
		this.memberInf = memberInf;
		this.sysUser = sysUser;
		this.locateDate = locateDate;
		this.locateTime = locateTime;
		this.longitude = longitude;
		this.latitude = latitude;
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
	@JoinColumn(name = "team_id", nullable = false)
	public TeamInfo getTeamInfo() {
		return this.teamInfo;
	}

	public void setTeamInfo(TeamInfo teamInfo) {
		this.teamInfo = teamInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	public MemberInf getMemberInf() {
		return this.memberInf;
	}

	public void setMemberInf(MemberInf memberInf) {
		this.memberInf = memberInf;
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
	@Column(name = "locate_date", nullable = false, length = 10)
	public Date getLocateDate() {
		return this.locateDate;
	}

	public void setLocateDate(Date locateDate) {
		this.locateDate = locateDate;
	}

	@Column(name = "locate_time", nullable = false, length = 8)
	public Time getLocateTime() {
		return this.locateTime;
	}

	public void setLocateTime(Time locateTime) {
		this.locateTime = locateTime;
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