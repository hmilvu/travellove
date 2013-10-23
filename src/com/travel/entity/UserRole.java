package com.travel.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * UserRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_role", catalog = "travel_love_db")
public class UserRole extends AbstractUserRole implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public UserRole() {
	}

	/** full constructor */
	public UserRole(SysUser sysUser, RoleInf roleInf) {
		super(sysUser, roleInf);
	}

}
