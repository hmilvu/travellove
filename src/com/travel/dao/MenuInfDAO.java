package com.travel.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.travel.entity.MenuInf;
import com.travel.entity.RoleMenu;

/**
 * A data access object (DAO) providing persistence and search support for
 * MenuInf entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.MenuInf
 * @author MyEclipse Persistence Tools
 */
@Repository
public class MenuInfDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(MenuInfDAO.class);

	public List<MenuInf> findAll() {
		try {
			String queryString = "from MenuInf order by menuOrder";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public MenuInf findById(java.lang.Long id) {
		log.debug("getting MenuInf instance with id: " + id);
		try {
			MenuInf instance = (MenuInf) getSession().get(
					"com.travel.entity.MenuInf", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * @param id
	 * @return
	 */
	public List<MenuInf> findMenuByRoleId(Long roleId) {
		try {
			String queryString = "select r.menuInf from RoleMenu r where r.roleInf.id = ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, roleId);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("findMenuByRoleId failed", re);
			throw re;
		}
	}
}