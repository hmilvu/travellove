package com.travel.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.travel.common.Constants;
import com.travel.entity.AppVersion;

/**
 * A data access object (DAO) providing persistence and search support for
 * AppVersion entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.AppVersion
 * @author MyEclipse Persistence Tools
 */

@Repository
public class AppVersionDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(AppVersionDAO.class);
	// property constants
	public static final String TYPE = "type";
	public static final String OS_TYPE = "osType";
	public static final String LAST_VERSION_NO = "lastVersionNo";
	public static final String CURRENT_VERSION_NO = "currentVersionNo";
	public static final String MUST = "must";
	public static final String URL = "url";
	public static final String SIZE = "size";
	public static final String COVER_IMG_URL = "coverImgUrl";
	public static final String DESCRIPTION = "description";

	public void save(AppVersion transientInstance) {
		log.debug("saving AppVersion instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AppVersion persistentInstance) {
		log.debug("deleting AppVersion instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AppVersion findById(java.lang.Long id) {
		log.debug("getting AppVersion instance with id: " + id);
		try {
			AppVersion instance = (AppVersion) getSession().get(
					"com.travel.entity.AppVersion", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<AppVersion> findByVersionAndType(int appType, int osType) {
		log.debug("finding AppVersion instance by example");
		try {
			String queryString = "from AppVersion as model where model.type = ? and model.osType = ? ";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, appType);
			queryObject.setParameter(1, osType);
			List<AppVersion> results = queryObject.list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			return null;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding AppVersion instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from AppVersion as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<AppVersion> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<AppVersion> findByOsType(Object osType) {
		return findByProperty(OS_TYPE, osType);
	}

	public List<AppVersion> findByLastVersionNo(Object lastVersionNo) {
		return findByProperty(LAST_VERSION_NO, lastVersionNo);
	}

	public List<AppVersion> findByCurrentVersionNo(Object currentVersionNo) {
		return findByProperty(CURRENT_VERSION_NO, currentVersionNo);
	}

	public List<AppVersion> findByMust(Object must) {
		return findByProperty(MUST, must);
	}

	public List<AppVersion> findByUrl(Object url) {
		return findByProperty(URL, url);
	}

	public List<AppVersion> findBySize(Object size) {
		return findByProperty(SIZE, size);
	}

	public List<AppVersion> findByCoverImgUrl(Object coverImgUrl) {
		return findByProperty(COVER_IMG_URL, coverImgUrl);
	}

	public List<AppVersion> findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List findAll() {
		log.debug("finding all AppVersion instances");
		try {
			String queryString = "from AppVersion";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AppVersion merge(AppVersion detachedInstance) {
		log.debug("merging AppVersion instance");
		try {
			AppVersion result = (AppVersion) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AppVersion instance) {
		log.debug("attaching dirty AppVersion instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AppVersion instance) {
		log.debug("attaching clean AppVersion instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}