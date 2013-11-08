package com.travel.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * RoleInf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "role_inf", catalog = "travel_love_db")
public class RoleInf extends AbstractRoleInf implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public RoleInf() {
	}

	/** minimal constructor */
	public RoleInf(RoleMenu roleMenu, String name, String description) {
		super(roleMenu, name, description);
	}

	/** full constructor */
	public RoleInf(RoleMenu roleMenu, String name, String description,
			Set<UserRole> userRoles, Set<RoleMenu> roleMenus) {
		super(roleMenu, name, description, userRoles, roleMenus);
	}
	
	@Transient
	private boolean checkRole;
	@Transient
	public boolean isCheckRole() {
		return checkRole;
	}
	@Transient
	public void setCheckRole(boolean checkRole) {
		this.checkRole = checkRole;
	}
	

}
