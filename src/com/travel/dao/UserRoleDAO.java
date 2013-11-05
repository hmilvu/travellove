package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.travel.entity.UserRole;

/**
 * A data access object (DAO) providing persistence and search support for
 * UserRole entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.UserRole
 * @author MyEclipse Persistence Tools
 */
@Repository
public class UserRoleDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(UserRoleDAO.class);

	// property constants

	public void save(UserRole transientInstance) {
		log.debug("saving UserRole instance");
		try {
			getSession().save(transientInstance);
			getSession().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(UserRole persistentInstance) {
		log.debug("deleting UserRole instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserRole findById(java.lang.Long id) {
		log.debug("getting UserRole instance with id: " + id);
		try {
			UserRole instance = (UserRole) getSession().get(
					"com.travel.entity.UserRole", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<UserRole> findByExample(UserRole instance) {
		log.debug("finding UserRole instance by example");
		try {
			List<UserRole> results = (List<UserRole>) getSession()
					.createCriteria("com.travel.entity.UserRole").add(
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
		log.debug("finding UserRole instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from UserRole as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}