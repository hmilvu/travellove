package com.travel.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

/**
 * AbstractRoleInf entity provides the base persistence definition of the
 * RoleInf entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractRoleInf extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private String description;
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);

	// Constructors

	/** default constructor */
	public AbstractRoleInf() {
	}

	/** minimal constructor */
	public AbstractRoleInf(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/** full constructor */
	public AbstractRoleInf(String name, String description,
			Set<UserRole> userRoles) {
		this.name = name;
		this.description = description;
		this.userRoles = userRoles;
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

	@Column(name = "name", unique = true, nullable = false, length = 64)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", nullable = false, length = 128)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "roleInf")
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

}