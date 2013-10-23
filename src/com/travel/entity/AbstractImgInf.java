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
 * AbstractImgInf entity provides the base persistence definition of the ImgInf
 * entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractImgInf extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private SysUser sysUser;
	private Integer type;
	private Long associateId;
	private String imgName;
	private String suffix;
	private Integer size;
	private String url;
	private Timestamp createDate;
	private Timestamp updateDate;

	// Constructors

	/** default constructor */
	public AbstractImgInf() {
	}

	/** full constructor */
	public AbstractImgInf(SysUser sysUser, Integer type, Long associateId,
			String imgName, String suffix, Integer size, String url,
			Timestamp createDate, Timestamp updateDate) {
		this.sysUser = sysUser;
		this.type = type;
		this.associateId = associateId;
		this.imgName = imgName;
		this.suffix = suffix;
		this.size = size;
		this.url = url;
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

	@Column(name = "associate_id", nullable = false)
	public Long getAssociateId() {
		return this.associateId;
	}

	public void setAssociateId(Long associateId) {
		this.associateId = associateId;
	}

	@Column(name = "img_name", nullable = false, length = 32)
	public String getImgName() {
		return this.imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	@Column(name = "suffix", nullable = false, length = 5)
	public String getSuffix() {
		return this.suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Column(name = "size", nullable = false)
	public Integer getSize() {
		return this.size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Column(name = "url", nullable = false, length = 256)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
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