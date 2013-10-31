package com.travel.entity;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * RoleMenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "role_menu", catalog = "travel_love_db")
public class RoleMenu extends AbstractRoleMenu implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public RoleMenu() {
	}

	/** minimal constructor */
	public RoleMenu(MenuInf menuInf, RoleInf roleInf) {
		super(menuInf, roleInf);
	}

	/** full constructor */
	public RoleMenu(MenuInf menuInf, RoleInf roleInf, Set<RoleInf> roleInfs) {
		super(menuInf, roleInf, roleInfs);
	}

}
