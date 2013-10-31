package com.travel.entity;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * MenuInf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "menu_inf", catalog = "travel_love_db")
public class MenuInf extends AbstractMenuInf implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public MenuInf() {
	}

	/** minimal constructor */
	public MenuInf(MenuInf menuInf, String name, String url, Integer menuOrder) {
		super(menuInf, name, url, menuOrder);
	}

	/** full constructor */
	public MenuInf(MenuInf menuInf, String name, String url, Integer menuOrder,
			Set<MenuInf> menuInfs, Set<RoleMenu> roleMenus) {
		super(menuInf, name, url, menuOrder, menuInfs, roleMenus);
	}

}
