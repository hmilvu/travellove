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
import org.hibernate.annotations.GenericGenerator;

/**
 * AbstractAppVersion entity provides the base persistence definition of the
 * AppVersion entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractAppVersion extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private SysUser sysUser;
	private Integer type;
	private Integer osType;
	private String lastVersionNo;
	private String currentVersionNo;
	private Integer must;
	private String url;
	private Integer size;
	private String coverImgUrl;
	private String description;
	private Date buildDate;
	private Timestamp createDate;
	private Timestamp updateDate;

	// Constructors

	/** default constructor */
	public AbstractAppVersion() {
	}

	/** full constructor */
	public AbstractAppVersion(SysUser sysUser, Integer type, Integer osType,
			String lastVersionNo, String currentVersionNo, Integer must,
			String url, Integer size, String coverImgUrl, String description,
			Date buildDate, Timestamp createDate, Timestamp updateDate) {
		this.sysUser = sysUser;
		this.type = type;
		this.osType = osType;
		this.lastVersionNo = lastVersionNo;
		this.currentVersionNo = currentVersionNo;
		this.must = must;
		this.url = url;
		this.size = size;
		this.coverImgUrl = coverImgUrl;
		this.description = description;
		this.buildDate = buildDate;
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
	@JoinColumn(name = "update_user_id", nullable = false)
	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "os_type", nullable = false)
	public Integer getOsType() {
		return this.osType;
	}

	public void setOsType(Integer osType) {
		this.osType = osType;
	}

	@Column(name = "last_version_no", nullable = false, length = 512)
	public String getLastVersionNo() {
		return this.lastVersionNo;
	}

	public void setLastVersionNo(String lastVersionNo) {
		this.lastVersionNo = lastVersionNo;
	}

	@Column(name = "current_version_no", nullable = false, length = 32)
	public String getCurrentVersionNo() {
		return this.currentVersionNo;
	}

	public void setCurrentVersionNo(String currentVersionNo) {
		this.currentVersionNo = currentVersionNo;
	}

	@Column(name = "must", nullable = false)
	public Integer getMust() {
		return this.must;
	}

	public void setMust(Integer must) {
		this.must = must;
	}

	@Column(name = "url", nullable = false, length = 256)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "size", nullable = false)
	public Integer getSize() {
		return this.size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Column(name = "cover_img_url", nullable = false, length = 256)
	public String getCoverImgUrl() {
		return this.coverImgUrl;
	}

	public void setCoverImgUrl(String coverImgUrl) {
		this.coverImgUrl = coverImgUrl;
	}

	@Column(name = "description", nullable = false, length = 512)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "build_date", nullable = false, length = 10)
	public Date getBuildDate() {
		return this.buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
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