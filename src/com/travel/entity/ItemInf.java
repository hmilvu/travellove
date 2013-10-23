package com.travel.entity;

import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ItemInf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "item_inf", catalog = "travel_love_db")
public class ItemInf extends AbstractItemInf implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public ItemInf() {
	}

	/** minimal constructor */
	public ItemInf(SysUser sysUser, String name, Double price,
			String contactPhone, String contactName, Timestamp createDate,
			Timestamp updateDate) {
		super(sysUser, name, price, contactPhone, contactName, createDate,
				updateDate);
	}

	/** full constructor */
	public ItemInf(SysUser sysUser, String name, String brands,
			String specification, Double price, String description,
			String contactPhone, String contactName, Timestamp createDate,
			Timestamp updateDate, Set<Order> orders) {
		super(sysUser, name, brands, specification, price, description,
				contactPhone, contactName, createDate, updateDate, orders);
	}

}
