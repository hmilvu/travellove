package com.travel.entity;

import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TravelInf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "travel_inf", catalog = "travel_love_db")
public class TravelInf extends AbstractTravelInf implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TravelInf() {
	}

	/** minimal constructor */
	public TravelInf(String name, String address, String phone, String contact,
			String linker, String description, Timestamp createDate,
			Timestamp updateDate) {
		super(name, address, phone, contact, linker, description, createDate,
				updateDate);
	}

	/** full constructor */
	public TravelInf(SysUser sysUser, String name, String address,
			String phone, String contact, String linker, String description,
			Timestamp createDate, Timestamp updateDate, Set<TeamInfo> teamInfos) {
		super(sysUser, name, address, phone, contact, linker, description,
				createDate, updateDate, teamInfos);
	}

}
