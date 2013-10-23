package com.travel.action.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.AppVersionDAO;
import com.travel.entity.AppVersion;

@Service
public class AppVersionService
{
	@Autowired
	private AppVersionDAO appVersionDao;
	
	
	public List<AppVersion> findByVersionAndType(int appType, int osType){
		return appVersionDao.findByVersionAndType(appType, osType);
	}
	
}
