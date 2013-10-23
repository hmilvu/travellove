package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travel.entity.ImgInf;

/**
 * A data access object (DAO) providing persistence and search support for
 * ImgInf entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.ImgInf
 * @author MyEclipse Persistence Tools
 */

public class ImgInfDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(ImgInfDAO.class);
	// property constants
	public static final String TYPE = "type";
	public static final String ASSOCIATE_ID = "associateId";
	public static final String IMG_NAME = "imgName";
	public static final String SUFFIX = "suffix";
	public static final String SIZE = "size";
	public static final String URL = "url";

	public void save(ImgInf transientInstance) {
		log.debug("saving ImgInf instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ImgInf persistentInstance) {
		log.debug("deleting ImgInf instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ImgInf findById(java.lang.Long id) {
		log.debug("getting ImgInf instance with id: " + id);
		try {
			ImgInf instance = (ImgInf) getSession().get(
					"com.travel.entity.ImgInf", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<ImgInf> findByExample(ImgInf instance) {
		log.debug("finding ImgInf instance by example");
		try {
			List<ImgInf> results = (List<ImgInf>) getSession().createCriteria(
					"com.travel.entity.ImgInf").add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ImgInf instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ImgInf as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<ImgInf> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<ImgInf> findByAssociateId(Object associateId) {
		return findByProperty(ASSOCIATE_ID, associateId);
	}

	public List<ImgInf> findByImgName(Object imgName) {
		return findByProperty(IMG_NAME, imgName);
	}

	public List<ImgInf> findBySuffix(Object suffix) {
		return findByProperty(SUFFIX, suffix);
	}

	public List<ImgInf> findBySize(Object size) {
		return findByProperty(SIZE, size);
	}

	public List<ImgInf> findByUrl(Object url) {
		return findByProperty(URL, url);
	}

	public List findAll() {
		log.debug("finding all ImgInf instances");
		try {
			String queryString = "from ImgInf";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ImgInf merge(ImgInf detachedInstance) {
		log.debug("merging ImgInf instance");
		try {
			ImgInf result = (ImgInf) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ImgInf instance) {
		log.debug("attaching dirty ImgInf instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ImgInf instance) {
		log.debug("attaching clean ImgInf instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}