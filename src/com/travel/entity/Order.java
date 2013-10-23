package com.travel.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Order entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "order", catalog = "travel_love_db")
public class Order extends AbstractOrder implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Order() {
	}

	/** full constructor */
	public Order(TeamInfo teamInfo, ItemInf itemInf, MemberInf memberInf,
			SysUser sysUser, Integer itemCount, Double totalPrice,
			Timestamp createDate, Timestamp updateDate) {
		super(teamInfo, itemInf, memberInf, sysUser, itemCount, totalPrice,
				createDate, updateDate);
	}

}
