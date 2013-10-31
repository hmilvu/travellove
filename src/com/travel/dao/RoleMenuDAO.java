package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travel.entity.RoleMenu;

/**
 * A data access object (DAO) providing persistence and search support for
 * RoleMenu entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.RoleMenu
 * @author MyEclipse Persistence Tools
 */

public class RoleMenuDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(RoleMenuDAO.class);

	// property constants

	public void save(RoleMenu transientInstance) {
		log.debug("saving RoleMenu instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(RoleMenu persistentInstance) {
		log.debug("deleting RoleMenu instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RoleMenu findById(java.lang.Long id) {
		log.debug("getting RoleMenu instance with id: " + id);
		try {
			RoleMenu instance = (RoleMenu) getSession().get(
					"com.travel.entity.RoleMenu", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<RoleMenu> findByExample(RoleMenu instance) {
		log.debug("finding RoleMenu instance by example");
		try {
			List<RoleMenu> results = (List<RoleMenu>) getSession()
					.createCriteria("com.travel.entity.RoleMenu").add(
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
		log.debug("finding RoleMenu instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from RoleMenu as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all RoleMenu instances");
		try {
			String queryString = "from RoleMenu";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public RoleMenu merge(RoleMenu detachedInstance) {
		log.debug("merging RoleMenu instance");
		try {
			RoleMenu result = (RoleMenu) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(RoleMenu instance) {
		log.debug("attaching dirty RoleMenu instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RoleMenu instance) {
		log.debug("attaching clean RoleMenu instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}