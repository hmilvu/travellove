package com.travel.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Channel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "channel", catalog = "travel_love_db")
public class Channel extends AbstractChannel implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Channel() {
	}

	/** minimal constructor */
	public Channel(String name, Timestamp createDate) {
		super(name, createDate);
	}

	/** full constructor */
	public Channel(SysUser sysUser, String name, Timestamp createDate) {
		super(sysUser, name, createDate);
	}

}
