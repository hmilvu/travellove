package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travel.entity.ItemInf;

/**
 * A data access object (DAO) providing persistence and search support for
 * ItemInf entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.ItemInf
 * @author MyEclipse Persistence Tools
 */

public class ItemInfDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(ItemInfDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String BRANDS = "brands";
	public static final String SPECIFICATION = "specification";
	public static final String PRICE = "price";
	public static final String DESCRIPTION = "description";
	public static final String CONTACT_PHONE = "contactPhone";
	public static final String CONTACT_NAME = "contactName";

	public void save(ItemInf transientInstance) {
		log.debug("saving ItemInf instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ItemInf persistentInstance) {
		log.debug("deleting ItemInf instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ItemInf findById(java.lang.Long id) {
		log.debug("getting ItemInf instance with id: " + id);
		try {
			ItemInf instance = (ItemInf) getSession().get(
					"com.travel.entity.ItemInf", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<ItemInf> findByExample(ItemInf instance) {
		log.debug("finding ItemInf instance by example");
		try {
			List<ItemInf> results = (List<ItemInf>) getSession()
					.createCriteria("com.travel.entity.ItemInf").add(
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
		log.debug("finding ItemInf instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ItemInf as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<ItemInf> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<ItemInf> findByBrands(Object brands) {
		return findByProperty(BRANDS, brands);
	}

	public List<ItemInf> findBySpecification(Object specification) {
		return findByProperty(SPECIFICATION, specification);
	}

	public List<ItemInf> findByPrice(Object price) {
		return findByProperty(PRICE, price);
	}

	public List<ItemInf> findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List<ItemInf> findByContactPhone(Object contactPhone) {
		return findByProperty(CONTACT_PHONE, contactPhone);
	}

	public List<ItemInf> findByContactName(Object contactName) {
		return findByProperty(CONTACT_NAME, contactName);
	}

	public List findAll() {
		log.debug("finding all ItemInf instances");
		try {
			String queryString = "from ItemInf";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ItemInf merge(ItemInf detachedInstance) {
		log.debug("merging ItemInf instance");
		try {
			ItemInf result = (ItemInf) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ItemInf instance) {
		log.debug("attaching dirty ItemInf instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ItemInf instance) {
		log.debug("attaching clean ItemInf instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}