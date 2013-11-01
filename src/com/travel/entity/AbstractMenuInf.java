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
 * AbstractMenuInf entity provides the base persistence definition of the
 * MenuInf entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractMenuInf extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private MenuInf menuInf;
	private String name;
	private String url;
	private Integer menuOrder;
	private Set<MenuInf> menuInfs = new HashSet<MenuInf>(0);
	private Set<RoleMenu> roleMenus = new HashSet<RoleMenu>(0);

	// Constructors

	/** default constructor */
	public AbstractMenuInf() {
	}

	/** minimal constructor */
	public AbstractMenuInf(MenuInf menuInf, String name, String url,
			Integer menuOrder) {
		this.menuInf = menuInf;
		this.name = name;
		this.url = url;
		this.menuOrder = menuOrder;
	}

	/** full constructor */
	public AbstractMenuInf(MenuInf menuInf, String name, String url,
			Integer menuOrder, Set<MenuInf> menuInfs, Set<RoleMenu> roleMenus) {
		this.menuInf = menuInf;
		this.name = name;
		this.url = url;
		this.menuOrder = menuOrder;
		this.menuInfs = menuInfs;
		this.roleMenus = roleMenus;
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
	@JoinColumn(name = "parent_id", nullable = false)
	public MenuInf getMenuInf() {
		return this.menuInf;
	}

	public void setMenuInf(MenuInf menuInf) {
		this.menuInf = menuInf;
	}

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "url", nullable = false, length = 256)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "menu_order", nullable = false)
	public Integer getMenuOrder() {
		return this.menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menuInf")
	public Set<MenuInf> getMenuInfs() {
		return this.menuInfs;
	}

	public void setMenuInfs(Set<MenuInf> menuInfs) {
		this.menuInfs = menuInfs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menuInf")
	public Set<RoleMenu> getRoleMenus() {
		return this.roleMenus;
	}

	public void setRoleMenus(Set<RoleMenu> roleMenus) {
		this.roleMenus = roleMenus;
	}

}