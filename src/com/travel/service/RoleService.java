package com.travel.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.common.dto.PageInfoDTO;
import com.travel.dao.MenuInfDAO;
import com.travel.dao.RoleInfDAO;
import com.travel.dao.RoleMenuDAO;
import com.travel.dao.UserRoleDAO;
import com.travel.entity.MenuInf;
import com.travel.entity.RoleInf;
import com.travel.entity.RoleMenu;
import com.travel.entity.UserRole;

@Service
public class RoleService extends AbstractBaseService
{
	@Autowired
	private RoleInfDAO roleDao;	
	@Autowired
	private RoleMenuDAO roleMenuDao;
	@Autowired
	private MenuInfDAO menuDao;
	@Autowired
	private UserRoleDAO userRoleDao;

	public int addRole(RoleInf role, List <String> menuIdList){
		Set<RoleMenu> list = new HashSet<RoleMenu>();
		for(String menuId : menuIdList){
			Long id = Long.valueOf(menuId);
			MenuInf menuInf = menuDao.findById(id);
			MenuInf parentMenu = menuInf.getMenuInf();
			if(parentMenu != null){
				boolean exists = false;
				for(RoleMenu pMenu : list){
					if(pMenu.getMenuInf().getId().longValue() == parentMenu.getId().longValue()){
						exists = true;
						continue;
					}
				}
				if(!exists){
					RoleMenu roleMenu = new RoleMenu();
					roleMenu.setMenuInf(parentMenu);
					roleMenu.setRoleInf(role);			
					list.add(roleMenu);
				}
			}
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setMenuInf(menuInf);
			roleMenu.setRoleInf(role);			
			list.add(roleMenu);
		}
		role.setRoleMenus(list);
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
	public int updateRole(RoleInf role, List<String> menuIdList) {
		roleMenuDao.deleteByRoleId(role.getId());
		Set<RoleMenu> list = new HashSet<RoleMenu>();
		for(String menuId : menuIdList){
			Long id = Long.valueOf(menuId);
			MenuInf menuInf = menuDao.findById(id);
			MenuInf parentMenu = menuInf.getMenuInf();
			if(parentMenu != null){
				boolean exists = false;
				for(RoleMenu pMenu : list){
					if(pMenu.getMenuInf().getId().longValue() == parentMenu.getId().longValue()){
						exists = true;
						continue;
					}
				}
				if(!exists){
					RoleMenu roleMenu = new RoleMenu();
					roleMenu.setMenuInf(parentMenu);
					roleMenu.setRoleInf(role);			
					list.add(roleMenu);
				}
			}
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setMenuInf(menuInf);
			roleMenu.setRoleInf(role);			
			list.add(roleMenu);
		}
		role.setRoleMenus(list);
		return roleDao.update(role);
	}

	/**
	 * @param idLong
	 * @return
	 */
	public List<UserRole> getUserByRoleId(Long roleId) {
		List<UserRole> list = userRoleDao.findByProperty("roleInf.id", roleId);
		return list;
	}
}
