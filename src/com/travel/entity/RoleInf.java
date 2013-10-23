package com.travel.entity;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

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
	public RoleInf(String name, String description) {
		super(name, description);
	}

	/** full constructor */
	public RoleInf(String name, String description, Set<UserRole> userRoles) {
		super(name, description, userRoles);
	}

}
