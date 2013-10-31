package com.travel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.RoleInfDAO;
import com.travel.entity.RoleInf;

@Service
public class RoleService
{
	@Autowired
	private RoleInfDAO roleDao;

	public int addRole(RoleInf role){
		return roleDao.save(role);
	}
	
}
