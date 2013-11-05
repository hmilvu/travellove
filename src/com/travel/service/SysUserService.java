package com.travel.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.common.Constants.SYS_USER_TYPE;
import com.travel.common.admin.dto.SearchSysUserDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.dao.SysUserDAO;
import com.travel.dao.UserRoleDAO;
import com.travel.entity.MenuInf;
import com.travel.entity.RoleInf;
import com.travel.entity.SysUser;
import com.travel.entity.UserRole;

@Service
public class SysUserService
{

	@Autowired
	private SysUserDAO sysUserDao;
	@Autowired
	private MenuInfService menuService;
	@Autowired
	private UserRoleDAO userRoleDao;
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
		List<MenuInf> menuList = null;
		if(user.getUserType().intValue() == SYS_USER_TYPE.SUPER_ADMIN.getValue()){
			menuList = menuService.getAllMenuItem();
		} else {
			List<RoleInf> roleList = sysUserDao.getRolesByUser(user);
			menuList = sysUserDao.findMenuRoles(roleList);
		}
		String menuInfor = menuService.generateMenuInfor(menuList);
		return menuInfor;
	}

	/**
	 * @param dto
	 * @return
	 */
	public int getTotalUserNum(SearchSysUserDTO dto) {
		return sysUserDao.getTotalNum(dto);
	}

	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	public List<SysUser> findUsers(SearchSysUserDTO dto, PageInfoDTO pageInfo) {
		return sysUserDao.findBySearchCriteria(dto, pageInfo);
	}

	/**
	 * @param user
	 * @return
	 */
	public int saveNewUser(SysUser user, List<Long>roleIdList) {
		user.setCreateDate(new Timestamp(new Date().getTime()));
		user.setUpdateDate(user.getCreateDate());
		Long userId = sysUserDao.save(user);
		if(userId != null && userId.longValue() > 0){
			SysUser savedUser = sysUserDao.findById(userId);
			for(Long roleId : roleIdList){
				UserRole userRole = new UserRole();
				userRole.setSysUser(savedUser);
				RoleInf role = new RoleInf();
				role.setId(roleId);
				userRole.setRoleInf(role);
				userRoleDao.save(userRole);
			}
			return 0;
		} else {
			return -1;
		}
		
	}

	/**
	 * @param name
	 * @return
	 */
	public List<SysUser> findUserByUsername(String name) {		
		return sysUserDao.findByUsername(name);
	}
	
	
	
}
