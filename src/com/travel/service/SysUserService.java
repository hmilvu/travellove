package com.travel.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.MenuInfDAO;
import com.travel.dao.SysUserDAO;
import com.travel.entity.MenuInf;
import com.travel.entity.RoleInf;
import com.travel.entity.RouteInf;
import com.travel.entity.SysUser;

@Service
public class SysUserService
{

	@Autowired
	private SysUserDAO sysUserDao;
	@Autowired
	private MenuInfService menuService;
	/**
	 * @param username
	 * @param password
	 * @return
	 */
	public SysUser getSysUserByCredentials(String username, String password) {
		SysUser user = sysUserDao.findByCredentials(username, password);
		return user;
	}	
	
	/**
	 * @param member
	 * @return
	 */
	public String generateMenuBySysUser(SysUser user) {
		List<RoleInf> roleList = sysUserDao.getRolesByUser(user);
		List<MenuInf> menuList = sysUserDao.findMenuRoles(roleList);
		String menuInfor = menuService.generateMenuInfor(menuList);
		System.out.println(menuInfor);
		return menuInfor;
	}
	
}
