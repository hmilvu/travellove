package com.travel.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ImgInf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "area_inf", catalog = "travel_love_db")
public class AreaInf extends AbstractAreaInf implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public AreaInf() {
	}
}
