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
 * AbstractItemInf entity provides the base persistence definition of the
 * ItemInf entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractItemInf extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private SysUser sysUser;
	private String name;
	private String brands;
	private String specification;
	private Double price;
	private String description;
	private String contactPhone;
	private String contactName;
	private Timestamp createDate;
	private Timestamp updateDate;
	private Set<Order> orders = new HashSet<Order>(0);

	// Constructors

	/** default constructor */
	public AbstractItemInf() {
	}

	/** minimal constructor */
	public AbstractItemInf(SysUser sysUser, String name, Double price,
			String contactPhone, String contactName, Timestamp createDate,
			Timestamp updateDate) {
		this.sysUser = sysUser;
		this.name = name;
		this.price = price;
		this.contactPhone = contactPhone;
		this.contactName = contactName;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	/** full constructor */
	public AbstractItemInf(SysUser sysUser, String name, String brands,
			String specification, Double price, String description,
			String contactPhone, String contactName, Timestamp createDate,
			Timestamp updateDate, Set<Order> orders) {
		this.sysUser = sysUser;
		this.name = name;
		this.brands = brands;
		this.specification = specification;
		this.price = price;
		this.description = description;
		this.contactPhone = contactPhone;
		this.contactName = contactName;
		this.createDate = createDate;
		this.updateDate = updateDate;
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

	@Column(name = "brands", length = 64)
	public String getBrands() {
		return this.brands;
	}

	public void setBrands(String brands) {
		this.brands = brands;
	}

	@Column(name = "specification", length = 64)
	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	@Column(name = "price", nullable = false, precision = 15, scale = 3)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "contact_phone", nullable = false, length = 20)
	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@Column(name = "contact_name", nullable = false, length = 64)
	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "itemInf")
	public Set<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

}