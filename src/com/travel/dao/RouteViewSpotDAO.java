package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travel.entity.RouteViewSpot;

/**
 * A data access object (DAO) providing persistence and search support for
 * RouteViewSpot entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.travel.entity.RouteViewSpot
 * @author MyEclipse Persistence Tools
 */

public class RouteViewSpotDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(RouteViewSpotDAO.class);

	// property constants

	public void save(RouteViewSpot transientInstance) {
		log.debug("saving RouteViewSpot instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(RouteViewSpot persistentInstance) {
		log.debug("deleting RouteViewSpot instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RouteViewSpot findById(java.lang.Long id) {
		log.debug("getting RouteViewSpot instance with id: " + id);
		try {
			RouteViewSpot instance = (RouteViewSpot) getSession().get(
					"com.travel.entity.RouteViewSpot", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<RouteViewSpot> findByExample(RouteViewSpot instance) {
		log.debug("finding RouteViewSpot instance by example");
		try {
			List<RouteViewSpot> results = (List<RouteViewSpot>) getSession()
					.createCriteria("com.travel.entity.RouteViewSpot").add(
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
		log.debug("finding RouteViewSpot instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from RouteViewSpot as model where model."
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
		log.debug("finding all RouteViewSpot instances");
		try {
			String queryString = "from RouteViewSpot";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public RouteViewSpot merge(RouteViewSpot detachedInstance) {
		log.debug("merging RouteViewSpot instance");
		try {
			RouteViewSpot result = (RouteViewSpot) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(RouteViewSpot instance) {
		log.debug("attaching dirty RouteViewSpot instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RouteViewSpot instance) {
		log.debug("attaching clean RouteViewSpot instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}