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
 * AbstractOrder entity provides the base persistence definition of the Order
 * entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractOrder extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private TeamInfo teamInfo;
	private ItemInf itemInf;
	private MemberInf memberInf;
	private SysUser sysUser;
	private Integer itemCount;
	private Integer status;
	private Double totalPrice;
	private Timestamp createDate;
	private Timestamp updateDate;
	private MemberInf createMemberInf;
	private String remark;
	private String contactTel;
	private String contactName;
	// Constructors

	/** default constructor */
	public AbstractOrder() {
	}

	/** full constructor */
	public AbstractOrder(TeamInfo teamInfo, ItemInf itemInf,
			MemberInf memberInf, SysUser sysUser, Integer itemCount,
			Double totalPrice, Timestamp createDate, Timestamp updateDate) {
		this.teamInfo = teamInfo;
		this.itemInf = itemInf;
		this.memberInf = memberInf;
		this.sysUser = sysUser;
		this.itemCount = itemCount;
		this.totalPrice = totalPrice;
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
	@JoinColumn(name = "item_id", nullable = false)
	public ItemInf getItemInf() {
		return this.itemInf;
	}

	public void setItemInf(ItemInf itemInf) {
		this.itemInf = itemInf;
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
	@JoinColumn(name = "create_member_id", nullable = true)
	public MemberInf getCreateMemberInf() {
		return this.createMemberInf;
	}

	public void setCreateMemberInf(MemberInf createMemberInf) {
		this.createMemberInf = createMemberInf;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id")
	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	@Column(name = "item_count", nullable = false)
	public Integer getItemCount() {
		return this.itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "total_price", nullable = false, precision = 15, scale = 3)
	public Double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
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
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "contact_tel", nullable = false, length = 20)
	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	@Column(name = "contact_name", length = 20)	
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

}