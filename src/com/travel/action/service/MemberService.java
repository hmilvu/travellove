package com.travel.action.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.MemberInfDAO;
import com.travel.entity.MemberInf;

@Service
public class MemberService
{
	@Autowired
	private MemberInfDAO memberInfDao;
	
	
	public MemberInf getMemberById(Long id){
		return memberInfDao.findById(id);
	}
	
}
