package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travel.entity.TeamInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * TeamInfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.TeamInfo
 * @author MyEclipse Persistence Tools
 */

public class TeamInfoDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TeamInfoDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String STATUS = "status";
	public static final String PEOPLE_COUNT = "peopleCount";
	public static final String DESCRIPTION = "description";

	public void save(TeamInfo transientInstance) {
		log.debug("saving TeamInfo instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TeamInfo persistentInstance) {
		log.debug("deleting TeamInfo instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TeamInfo findById(java.lang.Long id) {
		log.debug("getting TeamInfo instance with id: " + id);
		try {
			TeamInfo instance = (TeamInfo) getSession().get(
					"com.travel.entity.TeamInfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TeamInfo> findByExample(TeamInfo instance) {
		log.debug("finding TeamInfo instance by example");
		try {
			List<TeamInfo> results = (List<TeamInfo>) getSession()
					.createCriteria("com.travel.entity.TeamInfo").add(
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
		log.debug("finding TeamInfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TeamInfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TeamInfo> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<TeamInfo> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List<TeamInfo> findByPeopleCount(Object peopleCount) {
		return findByProperty(PEOPLE_COUNT, peopleCount);
	}

	public List<TeamInfo> findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List findAll() {
		log.debug("finding all TeamInfo instances");
		try {
			String queryString = "from TeamInfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TeamInfo merge(TeamInfo detachedInstance) {
		log.debug("merging TeamInfo instance");
		try {
			TeamInfo result = (TeamInfo) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TeamInfo instance) {
		log.debug("attaching dirty TeamInfo instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TeamInfo instance) {
		log.debug("attaching clean TeamInfo instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}