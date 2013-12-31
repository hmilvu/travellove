package com.travel.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.travel.common.Constants;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.MemberInf;
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
			getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().flush();
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
			getHibernateTemplate().update(transientInstance);
			getHibernateTemplate().flush();
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
			getHibernateTemplate().delete(persistentInstance);
			getHibernateTemplate().flush();
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
			RoleInf instance = (RoleInf) getHibernateTemplate().get(
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
			return getHibernateTemplate().find(queryString, value);
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
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	
	/**
	 * @return
	 */
	public int getTotalNum(final String roleName) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = buildSearchCriteria(session, roleName);
				Long total=(Long)cr.setProjection(Projections.rowCount()).uniqueResult(); 			
				return  total.intValue();
			}
		});	
	}

	/**
	 * @param roleName
	 * @return
	 */
	private Criteria buildSearchCriteria(Session session, String roleName) {
		Criteria cr = session.createCriteria(RoleInf.class);
		if (!StringUtils.isBlank(roleName)) {
			cr.add(Restrictions.like("name", StringUtils.trim(roleName) + "%").ignoreCase());
		}
		return cr;
	}

	/**
	 * @param roleName
	 * @param pageInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RoleInf> findRolesByName(final String roleName, final PageInfoDTO pageInfo) {
		return getHibernateTemplate().execute(new HibernateCallback<List<RoleInf>>() {
			@Override
			public List<RoleInf> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = buildSearchCriteria(session, roleName);
				int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.ADMIN_DEFAULT_PAGE_SIZE;
				cr.setMaxResults(maxResults);
				cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
				cr.addOrder(Order.desc("id"));
				return cr.list();
			}
		});	
	}

	/**
	 * @param idLong
	 * @return
	 */
	public int deleteById(final Long idLong) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				String sql = "delete from role_inf where id = ?";
				Query queryObject = session.createSQLQuery(sql);
				queryObject.setParameter(0, idLong);
				int result = queryObject.executeUpdate();
				return result;
			}
		});	
	}
}