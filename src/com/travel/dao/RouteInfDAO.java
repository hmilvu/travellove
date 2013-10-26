package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.travel.entity.RouteInf;

/**
 * A data access object (DAO) providing persistence and search support for
 * RouteInf entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.RouteInf
 * @author MyEclipse Persistence Tools
 */
@Repository
public class RouteInfDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(RouteInfDAO.class);
	// property constants
	public static final String ROUTE_NAME = "routeName";
	public static final String START_ADDRESS = "startAddress";
	public static final String START_LONGTITUDE = "startLongtitude";
	public static final String START_LATITUDE = "startLatitude";
	public static final String END_ADDRESS = "endAddress";
	public static final String END_LONGITUDE = "endLongitude";
	public static final String END_LATITUDE = "endLatitude";
	public static final String DESCRIPTION = "description";

	public void save(RouteInf transientInstance) {
		log.debug("saving RouteInf instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(RouteInf persistentInstance) {
		log.debug("deleting RouteInf instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RouteInf findById(java.lang.Long id) {
		log.debug("getting RouteInf instance with id: " + id);
		try {
			RouteInf instance = (RouteInf) getSession().get(
					"com.travel.entity.RouteInf", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<RouteInf> findByExample(RouteInf instance) {
		log.debug("finding RouteInf instance by example");
		try {
			List<RouteInf> results = (List<RouteInf>) getSession()
					.createCriteria("com.travel.entity.RouteInf").add(
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
		log.debug("finding RouteInf instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from RouteInf as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<RouteInf> findByRouteName(Object routeName) {
		return findByProperty(ROUTE_NAME, routeName);
	}

	public List<RouteInf> findByStartAddress(Object startAddress) {
		return findByProperty(START_ADDRESS, startAddress);
	}

	public List<RouteInf> findByStartLongtitude(Object startLongtitude) {
		return findByProperty(START_LONGTITUDE, startLongtitude);
	}

	public List<RouteInf> findByStartLatitude(Object startLatitude) {
		return findByProperty(START_LATITUDE, startLatitude);
	}

	public List<RouteInf> findByEndAddress(Object endAddress) {
		return findByProperty(END_ADDRESS, endAddress);
	}

	public List<RouteInf> findByEndLongitude(Object endLongitude) {
		return findByProperty(END_LONGITUDE, endLongitude);
	}

	public List<RouteInf> findByEndLatitude(Object endLatitude) {
		return findByProperty(END_LATITUDE, endLatitude);
	}

	public List<RouteInf> findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List findAll() {
		log.debug("finding all RouteInf instances");
		try {
			String queryString = "from RouteInf";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public RouteInf merge(RouteInf detachedInstance) {
		log.debug("merging RouteInf instance");
		try {
			RouteInf result = (RouteInf) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(RouteInf instance) {
		log.debug("attaching dirty RouteInf instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RouteInf instance) {
		log.debug("attaching clean RouteInf instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}