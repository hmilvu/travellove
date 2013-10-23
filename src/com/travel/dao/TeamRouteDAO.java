package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travel.entity.TeamRoute;

/**
 * A data access object (DAO) providing persistence and search support for
 * TeamRoute entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.TeamRoute
 * @author MyEclipse Persistence Tools
 */

public class TeamRouteDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TeamRouteDAO.class);
	// property constants
	public static final String STATUS = "status";

	public void save(TeamRoute transientInstance) {
		log.debug("saving TeamRoute instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TeamRoute persistentInstance) {
		log.debug("deleting TeamRoute instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TeamRoute findById(java.lang.Long id) {
		log.debug("getting TeamRoute instance with id: " + id);
		try {
			TeamRoute instance = (TeamRoute) getSession().get(
					"com.travel.entity.TeamRoute", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TeamRoute> findByExample(TeamRoute instance) {
		log.debug("finding TeamRoute instance by example");
		try {
			List<TeamRoute> results = (List<TeamRoute>) getSession()
					.createCriteria("com.travel.entity.TeamRoute").add(
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
		log.debug("finding TeamRoute instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TeamRoute as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TeamRoute> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all TeamRoute instances");
		try {
			String queryString = "from TeamRoute";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TeamRoute merge(TeamRoute detachedInstance) {
		log.debug("merging TeamRoute instance");
		try {
			TeamRoute result = (TeamRoute) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TeamRoute instance) {
		log.debug("attaching dirty TeamRoute instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TeamRoute instance) {
		log.debug("attaching clean TeamRoute instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}