package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.travel.entity.ViewSpotInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * ViewSpotInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.travel.entity.ViewSpotInfo
 * @author MyEclipse Persistence Tools
 */
@Repository
public class ViewSpotInfoDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ViewSpotInfoDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String LONGITUDE = "longitude";
	public static final String LATITUDE = "latitude";
	public static final String DESCRIPTION = "description";

	public void save(ViewSpotInfo transientInstance) {
		log.debug("saving ViewSpotInfo instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ViewSpotInfo persistentInstance) {
		log.debug("deleting ViewSpotInfo instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ViewSpotInfo findById(java.lang.Long id) {
		log.debug("getting ViewSpotInfo instance with id: " + id);
		try {
			ViewSpotInfo instance = (ViewSpotInfo) getSession().get(
					"com.travel.entity.ViewSpotInfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<ViewSpotInfo> findByExample(ViewSpotInfo instance) {
		log.debug("finding ViewSpotInfo instance by example");
		try {
			List<ViewSpotInfo> results = (List<ViewSpotInfo>) getSession()
					.createCriteria("com.travel.entity.ViewSpotInfo").add(
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
		log.debug("finding ViewSpotInfo instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ViewSpotInfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<ViewSpotInfo> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<ViewSpotInfo> findByLongitude(Object longitude) {
		return findByProperty(LONGITUDE, longitude);
	}

	public List<ViewSpotInfo> findByLatitude(Object latitude) {
		return findByProperty(LATITUDE, latitude);
	}

	public List<ViewSpotInfo> findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List findAll() {
		log.debug("finding all ViewSpotInfo instances");
		try {
			String queryString = "from ViewSpotInfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ViewSpotInfo merge(ViewSpotInfo detachedInstance) {
		log.debug("merging ViewSpotInfo instance");
		try {
			ViewSpotInfo result = (ViewSpotInfo) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ViewSpotInfo instance) {
		log.debug("attaching dirty ViewSpotInfo instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ViewSpotInfo instance) {
		log.debug("attaching clean ViewSpotInfo instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}