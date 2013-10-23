package com.travel.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ImgInf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "img_inf", catalog = "travel_love_db")
public class ImgInf extends AbstractImgInf implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public ImgInf() {
	}

	/** full constructor */
	public ImgInf(SysUser sysUser, Integer type, Long associateId,
			String imgName, String suffix, Integer size, String url,
			Timestamp createDate, Timestamp updateDate) {
		super(sysUser, type, associateId, imgName, suffix, size, url,
				createDate, updateDate);
	}

}
