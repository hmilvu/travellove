package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travel.entity.RoleInf;

/**
 * A data access object (DAO) providing persistence and search support for
 * RoleInf entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.RoleInf
 * @author MyEclipse Persistence Tools
 */

public class RoleInfDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(RoleInfDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";

	public void save(RoleInf transientInstance) {
		log.debug("saving RoleInf instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(RoleInf persistentInstance) {
		log.debug("deleting RoleInf instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RoleInf findById(java.lang.Long id) {
		log.debug("getting RoleInf instance with id: " + id);
		try {
			RoleInf instance = (RoleInf) getSession().get(
					"com.travel.entity.RoleInf", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<RoleInf> findByExample(RoleInf instance) {
		log.debug("finding RoleInf instance by example");
		try {
			List<RoleInf> results = (List<RoleInf>) getSession()
					.createCriteria("com.travel.entity.RoleInf").add(
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
		log.debug("finding RoleInf instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from RoleInf as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<RoleInf> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<RoleInf> findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List findAll() {
		log.debug("finding all RoleInf instances");
		try {
			String queryString = "from RoleInf";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public RoleInf merge(RoleInf detachedInstance) {
		log.debug("merging RoleInf instance");
		try {
			RoleInf result = (RoleInf) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(RoleInf instance) {
		log.debug("attaching dirty RoleInf instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RoleInf instance) {
		log.debug("attaching clean RoleInf instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}