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
 * AbstractTravelInf entity provides the base persistence definition of the
 * TravelInf entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractTravelInf extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private SysUser sysUser;
	private String name;
	private String address;
	private String phone;
	private String contact;
	private String linker;
	private String description;
	private Timestamp createDate;
	private Timestamp updateDate;
	private Set<TeamInfo> teamInfos = new HashSet<TeamInfo>(0);

	// Constructors

	/** default constructor */
	public AbstractTravelInf() {
	}

	/** minimal constructor */
	public AbstractTravelInf(String name, String address, String phone,
			String contact, String linker, String description,
			Timestamp createDate, Timestamp updateDate) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.contact = contact;
		this.linker = linker;
		this.description = description;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	/** full constructor */
	public AbstractTravelInf(SysUser sysUser, String name, String address,
			String phone, String contact, String linker, String description,
			Timestamp createDate, Timestamp updateDate, Set<TeamInfo> teamInfos) {
		this.sysUser = sysUser;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.contact = contact;
		this.linker = linker;
		this.description = description;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.teamInfos = teamInfos;
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
	@JoinColumn(name = "create_user_id")
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

	@Column(name = "address", nullable = false, length = 20)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "phone", nullable = false, length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "contact", nullable = false, length = 32)
	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "linker", nullable = false, length = 32)
	public String getLinker() {
		return this.linker;
	}

	public void setLinker(String linker) {
		this.linker = linker;
	}

	@Column(name = "description", nullable = false, length = 1024)
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "travelInf")
	public Set<TeamInfo> getTeamInfos() {
		return this.teamInfos;
	}

	public void setTeamInfos(Set<TeamInfo> teamInfos) {
		this.teamInfos = teamInfos;
	}

}