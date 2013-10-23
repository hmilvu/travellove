package com.travel.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.travel.common.dto.AppVersionDTO;

/**
 * AppVersion entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_version", catalog = "travel_love_db")
public class AppVersion extends AbstractAppVersion implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public AppVersion() {
	}

	/** full constructor */
	public AppVersion(SysUser sysUser, Integer type, Integer osType,
			String lastVersionNo, String currentVersionNo, Integer must,
			String url, Integer size, String coverImgUrl, String description,
			Date buildDate, Timestamp createDate, Timestamp updateDate) {
		super(sysUser, type, osType, lastVersionNo, currentVersionNo, must,
				url, size, coverImgUrl, description, buildDate, createDate,
				updateDate);
	}
	
	public AppVersionDTO toDTO(){
		AppVersionDTO dto = new AppVersionDTO();
		dto.setCoverImgUrl(this.getCoverImgUrl());
		dto.setMust(this.getMust());
		dto.setSize(this.getSize());
		dto.setUrl(this.getUrl());
		return dto;
	}

}
