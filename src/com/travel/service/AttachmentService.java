package com.travel.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.AttachmentInfDAO;
import com.travel.entity.AttachmentInf;
import com.travel.entity.SysUser;

@Service
public class AttachmentService extends AbstractBaseService
{
	@Autowired
	private AttachmentInfDAO attachmentDao;

	/**
	 * @param uploadFileName
	 * @param currentUser
	 * @return
	 */
	public Long createAttachment(String uploadFileName, SysUser currentUser) {
		AttachmentInf a = new AttachmentInf();
		a.setFileName(uploadFileName);
		a.setSysUser(currentUser);
		a.setCreateDate(new Timestamp(new Date().getTime()));
		return attachmentDao.save(a);
	}
	
	
}
