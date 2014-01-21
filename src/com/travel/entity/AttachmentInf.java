package com.travel.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * AttachmentInf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "attachment_inf", catalog = "travel_love_db")
public class AttachmentInf extends AbstractAttachmentInf implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public AttachmentInf() {
	}

	/** full constructor */
	public AttachmentInf(SysUser sysUser, String fileName, String fileUrl,
			Timestamp createDate) {
		super(sysUser, fileName, fileUrl, createDate);
	}

}
