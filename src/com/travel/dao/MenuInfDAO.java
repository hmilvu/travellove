package com.travel.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.travel.entity.MenuInf;

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
}