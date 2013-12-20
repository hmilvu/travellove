package com.travel.entity;

import java.sql.Timestamp;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 * AbstractTeamInfo entity provides the base persistence definition of the
 * TeamInfo entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractTeamInfo extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private TravelInf travelInf;
	private SysUser sysUser;
	private String name;
	private Date beginDate;
	private Date endDate;
	private Integer status;
	private Integer peopleCount;
	private String description;
	private Timestamp createDate;
	private Timestamp updateDate;
	private Set<LocationLog> locationLogs = new HashSet<LocationLog>(0);
	private Set<MemberInf> memberInfs = new HashSet<MemberInf>(0);
	private Set<Order> orders = new HashSet<Order>(0);

	// Constructors

	/** default constructor */
	public AbstractTeamInfo() {
	}

	/** minimal constructor */
	public AbstractTeamInfo(TravelInf travelInf, SysUser sysUser, String name,
			Date beginDate, Integer peopleCount, Timestamp createDate,
			Timestamp updateDate) {
		this.travelInf = travelInf;
		this.sysUser = sysUser;
		this.name = name;
		this.beginDate = beginDate;
		this.peopleCount = peopleCount;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	/** full constructor */
	public AbstractTeamInfo(TravelInf travelInf, SysUser sysUser, String name,
			Date beginDate, Date endDate, Integer status, Integer peopleCount,
			String description, Timestamp createDate, Timestamp updateDate,
			Set<Message> messages, Set<LocationLog> locationLogs,
			Set<MemberInf> memberInfs, Set<Order> orders) {
		this.travelInf = travelInf;
		this.sysUser = sysUser;
		this.name = name;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.status = status;
		this.peopleCount = peopleCount;
		this.description = description;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.locationLogs = locationLogs;
		this.memberInfs = memberInfs;
		this.orders = orders;
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
	@JoinColumn(name = "travel_id", nullable = false)
	public TravelInf getTravelInf() {
		return this.travelInf;
	}

	public void setTravelInf(TravelInf travelInf) {
		this.travelInf = travelInf;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id", nullable = false)
	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	@Column(name = "name", nullable = false, length = 128)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "begin_date", nullable = false, length = 10)
	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "end_date", length = 10)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "people_count", nullable = false)
	public Integer getPeopleCount() {
		return this.peopleCount;
	}

	public void setPeopleCount(Integer peopleCount) {
		this.peopleCount = peopleCount;
	}

	@Column(name = "description", length = 128)
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teamInfo")
	public Set<LocationLog> getLocationLogs() {
		return this.locationLogs;
	}

	public void setLocationLogs(Set<LocationLog> locationLogs) {
		this.locationLogs = locationLogs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teamInfo")
	public Set<MemberInf> getMemberInfs() {
		return this.memberInfs;
	}

	public void setMemberInfs(Set<MemberInf> memberInfs) {
		this.memberInfs = memberInfs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teamInfo")
	public Set<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

}