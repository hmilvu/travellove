package com.travel.entity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

/**
 * AbstractUserRole entity provides the base persistence definition of the
 * UserRole entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractUserRole extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private SysUser sysUser;
	private RoleInf roleInf;

	// Constructors

	/** default constructor */
	public AbstractUserRole() {
	}

	/** full constructor */
	public AbstractUserRole(SysUser sysUser, RoleInf roleInf) {
		this.sysUser = sysUser;
		this.roleInf = roleInf;
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
	@JoinColumn(name = "user_id", nullable = false)
	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	public RoleInf getRoleInf() {
		return this.roleInf;
	}

	public void setRoleInf(RoleInf roleInf) {
		this.roleInf = roleInf;
	}

}