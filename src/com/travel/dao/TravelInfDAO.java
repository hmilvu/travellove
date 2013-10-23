package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travel.entity.TravelInf;

/**
 * A data access object (DAO) providing persistence and search support for
 * TravelInf entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.TravelInf
 * @author MyEclipse Persistence Tools
 */

public class TravelInfDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TravelInfDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String ADDRESS = "address";
	public static final String PHONE = "phone";
	public static final String CONTACT = "contact";
	public static final String LINKER = "linker";
	public static final String DESCRIPTION = "description";

	public void save(TravelInf transientInstance) {
		log.debug("saving TravelInf instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TravelInf persistentInstance) {
		log.debug("deleting TravelInf instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TravelInf findById(java.lang.Long id) {
		log.debug("getting TravelInf instance with id: " + id);
		try {
			TravelInf instance = (TravelInf) getSession().get(
					"com.travel.entity.TravelInf", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TravelInf> findByExample(TravelInf instance) {
		log.debug("finding TravelInf instance by example");
		try {
			List<TravelInf> results = (List<TravelInf>) getSession()
					.createCriteria("com.travel.entity.TravelInf").add(
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
		log.debug("finding TravelInf instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TravelInf as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TravelInf> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<TravelInf> findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List<TravelInf> findByPhone(Object phone) {
		return findByProperty(PHONE, phone);
	}

	public List<TravelInf> findByContact(Object contact) {
		return findByProperty(CONTACT, contact);
	}

	public List<TravelInf> findByLinker(Object linker) {
		return findByProperty(LINKER, linker);
	}

	public List<TravelInf> findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List findAll() {
		log.debug("finding all TravelInf instances");
		try {
			String queryString = "from TravelInf";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TravelInf merge(TravelInf detachedInstance) {
		log.debug("merging TravelInf instance");
		try {
			TravelInf result = (TravelInf) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TravelInf instance) {
		log.debug("attaching dirty TravelInf instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TravelInf instance) {
		log.debug("attaching clean TravelInf instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}