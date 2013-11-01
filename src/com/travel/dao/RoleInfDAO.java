package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.travel.common.Constants;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.RoleInf;

/**
 * A data access object (DAO) providing persistence and search support for
 * RoleInf entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.RoleInf
 * @author MyEclipse Persistence Tools
 */
@Repository
public class RoleInfDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(RoleInfDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";

	public int save(RoleInf transientInstance) {
		int result = 0;
		log.debug("saving RoleInf instance");
		try {
			getSession().save(transientInstance);
			getSession().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			result = -1;
		}
		return result;
	}
	
	public int update(RoleInf transientInstance) {
		int result = 0;
		log.debug("saving RoleInf instance");
		try {
			getSession().update(transientInstance);
			getSession().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			result = -1;
		}
		return result;
	}

	public int delete(RoleInf persistentInstance) {
		int result = 0;
		log.debug("deleting RoleInf instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			result = -1;
		}
		return result;
	}

	public RoleInf findById(java.lang.Long id) {
		log.debug("getting RoleInf instance with id: " + id);
		try {
			RoleInf instance = (RoleInf) getSession().get(
					"com.travel.entity.RoleInf", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding RoleInf instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from RoleInf as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<RoleInf> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findAll() {
		log.debug("finding all RoleInf instances");
		try {
			String queryString = "from RoleInf";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	
	/**
	 * @return
	 */
	public int getTotalNum(String roleName) {
		try {
			String queryString = null;
			if(StringUtils.isBlank(roleName)){
				queryString = "select count(r.id) from RoleInf as r order by r.name";
			} else {
				queryString = "select count(r.id) from RoleInf as r where r.name like '%?%' order by r.name";
			}
			Query queryObject = getSession().createQuery(queryString);
			if(!StringUtils.isBlank(roleName)){
				queryObject.setParameter(0, StringUtils.trim(roleName));
			}
			return  ((Number) queryObject.iterate().next()).intValue();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/**
	 * @param roleName
	 * @param pageInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RoleInf> findRolesByName(String roleName, PageInfoDTO pageInfo) {
		try {
			String queryString = null;
			if(StringUtils.isBlank(roleName)){
				queryString = "from RoleInf as r order by r.name";
			} else {
				queryString = "from RoleInf as r where r.name like '%?%' order by r.name";
			}
			Query queryObject = getSession().createQuery(queryString);
			int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.DEFAULT_PAGE_SIZE;
			queryObject.setFirstResult(pageInfo.getPageNumber() * maxResults);
			if(!StringUtils.isBlank(roleName)){
				queryObject.setParameter(0, StringUtils.trim(roleName));
			}
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by receiverId failed", re);
			throw re;
		}
	}
}