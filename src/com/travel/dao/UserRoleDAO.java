package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.travel.entity.RoleInf;
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
			getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(UserRole persistentInstance) {
		log.debug("deleting UserRole instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserRole findById(java.lang.Long id) {
		log.debug("getting UserRole instance with id: " + id);
		try {
			UserRole instance = (UserRole) getHibernateTemplate().get(
					"com.travel.entity.UserRole", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding UserRole instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from UserRole as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RoleInf> findRoleByUserId(Long id) {
		try {
			String queryString = "select r.roleInf from UserRole as r where r.sysUser.id = ?";
			return getHibernateTemplate().find(queryString, id);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * @param id
	 * @return
	 */
	public int deleteByUserId(final Long id) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				int result = 0;
				String sql = "delete from user_role where user_id = ?";
				Query queryObject = session.createSQLQuery(sql);
				queryObject.setParameter(0, id);
				queryObject.executeUpdate();
				return result;
			}
		});	
	}
}