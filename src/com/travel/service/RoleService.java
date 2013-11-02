package com.travel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.common.dto.PageInfoDTO;
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
	
	public int getTotalRoleNum(String roleName) {
		int totalNum = roleDao.getTotalNum(roleName);
		return totalNum;
	}

	/**
	 * @param string
	 * @param pageInfo
	 * @return
	 */
	public List<RoleInf> findRolesByName(String roleName, PageInfoDTO pageInfo) {
		List<RoleInf> roleList = roleDao.findRolesByName(roleName, pageInfo);
		return roleList;
	}

	/**
	 * @param name
	 * @return
	 */
	public List<RoleInf> getRoleByName(String name) {
		List<RoleInf> roleList = roleDao.findByName(name);
		return roleList;
	}

	/**
	 * @param idLong
	 * @return
	 */
	public int deleteRoleById(Long idLong) {
		return roleDao.deleteById(idLong);
	}

	/**
	 * @param idLong
	 * @return
	 */
	public RoleInf getRoleById(Long idLong) {
		return roleDao.findById(idLong);
	}

	/**
	 * @param role
	 * @return
	 */
	public int updateRole(RoleInf role) {
		return roleDao.update(role);
	}
}
