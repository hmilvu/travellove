package com.travel.entity;

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
 * AbstractRoleMenu entity provides the base persistence definition of the
 * RoleMenu entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractRoleMenu extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private MenuInf menuInf;
	private RoleInf roleInf;
	private Set<RoleInf> roleInfs = new HashSet<RoleInf>(0);

	// Constructors

	/** default constructor */
	public AbstractRoleMenu() {
	}

	/** minimal constructor */
	public AbstractRoleMenu(MenuInf menuInf, RoleInf roleInf) {
		this.menuInf = menuInf;
		this.roleInf = roleInf;
	}

	/** full constructor */
	public AbstractRoleMenu(MenuInf menuInf, RoleInf roleInf,
			Set<RoleInf> roleInfs) {
		this.menuInf = menuInf;
		this.roleInf = roleInf;
		this.roleInfs = roleInfs;
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
	@JoinColumn(name = "menu_id", nullable = false)
	public MenuInf getMenuInf() {
		return this.menuInf;
	}

	public void setMenuInf(MenuInf menuInf) {
		this.menuInf = menuInf;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	public RoleInf getRoleInf() {
		return this.roleInf;
	}

	public void setRoleInf(RoleInf roleInf) {
		this.roleInf = roleInf;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "roleMenu")
	public Set<RoleInf> getRoleInfs() {
		return this.roleInfs;
	}

	public void setRoleInfs(Set<RoleInf> roleInfs) {
		this.roleInfs = roleInfs;
	}

}