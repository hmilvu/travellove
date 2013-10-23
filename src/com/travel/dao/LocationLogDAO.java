package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travel.entity.LocationLog;

/**
 * A data access object (DAO) providing persistence and search support for
 * LocationLog entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.travel.entity.LocationLog
 * @author MyEclipse Persistence Tools
 */

public class LocationLogDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(LocationLogDAO.class);
	// property constants
	public static final String LONGITUDE = "longitude";
	public static final String LATITUDE = "latitude";

	public void save(LocationLog transientInstance) {
		log.debug("saving LocationLog instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(LocationLog persistentInstance) {
		log.debug("deleting LocationLog instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public LocationLog findById(java.lang.Long id) {
		log.debug("getting LocationLog instance with id: " + id);
		try {
			LocationLog instance = (LocationLog) getSession().get(
					"com.travel.entity.LocationLog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<LocationLog> findByExample(LocationLog instance) {
		log.debug("finding LocationLog instance by example");
		try {
			List<LocationLog> results = (List<LocationLog>) getSession()
					.createCriteria("com.travel.entity.LocationLog").add(
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
		log.debug("finding LocationLog instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from LocationLog as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<LocationLog> findByLongitude(Object longitude) {
		return findByProperty(LONGITUDE, longitude);
	}

	public List<LocationLog> findByLatitude(Object latitude) {
		return findByProperty(LATITUDE, latitude);
	}

	public List findAll() {
		log.debug("finding all LocationLog instances");
		try {
			String queryString = "from LocationLog";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public LocationLog merge(LocationLog detachedInstance) {
		log.debug("merging LocationLog instance");
		try {
			LocationLog result = (LocationLog) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(LocationLog instance) {
		log.debug("attaching dirty LocationLog instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(LocationLog instance) {
		log.debug("attaching clean LocationLog instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}