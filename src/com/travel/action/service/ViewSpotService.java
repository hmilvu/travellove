package com.travel.action.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.MemberInfDAO;
import com.travel.dao.ViewSpotInfoDAO;
import com.travel.entity.MemberInf;
import com.travel.entity.ViewSpotInfo;

@Service
public class ViewSpotService
{
	@Autowired
	private ViewSpotInfoDAO viewSpotDao;
	
	
	public ViewSpotInfo getViewSpotById(Long id){
		return viewSpotDao.findById(id);
	}
	
}
