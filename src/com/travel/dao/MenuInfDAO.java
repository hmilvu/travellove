package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class MenuInfDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(MenuInfDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String URL = "url";
	public static final String MENU_ORDER = "menuOrder";

	public void save(MenuInf transientInstance) {
		log.debug("saving MenuInf instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MenuInf persistentInstance) {
		log.debug("deleting MenuInf instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
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

	public List<MenuInf> findByExample(MenuInf instance) {
		log.debug("finding MenuInf instance by example");
		try {
			List<MenuInf> results = (List<MenuInf>) getSession()
					.createCriteria("com.travel.entity.MenuInf").add(
							create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding MenuInf instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from MenuInf as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<MenuInf> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<MenuInf> findByUrl(Object url) {
		return findByProperty(URL, url);
	}

	public List<MenuInf> findByMenuOrder(Object menuOrder) {
		return findByProperty(MENU_ORDER, menuOrder);
	}

	public List findAll() {
		log.debug("finding all MenuInf instances");
		try {
			String queryString = "from MenuInf";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MenuInf merge(MenuInf detachedInstance) {
		log.debug("merging MenuInf instance");
		try {
			MenuInf result = (MenuInf) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MenuInf instance) {
		log.debug("attaching dirty MenuInf instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MenuInf instance) {
		log.debug("attaching clean MenuInf instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}