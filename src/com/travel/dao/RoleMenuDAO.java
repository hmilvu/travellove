package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.travel.entity.MemberInf;
import com.travel.entity.RoleMenu;

/**
 * A data access object (DAO) providing persistence and search support for
 * RoleMenu entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.RoleMenu
 * @author MyEclipse Persistence Tools
 */
@Repository
public class RoleMenuDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(RoleMenuDAO.class);

	// property constants

	public void save(RoleMenu transientInstance) {
		log.debug("saving RoleMenu instance");
		try {
			getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(RoleMenu persistentInstance) {
		log.debug("deleting RoleMenu instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RoleMenu findById(java.lang.Long id) {
		log.debug("getting RoleMenu instance with id: " + id);
		try {
			RoleMenu instance = (RoleMenu) getHibernateTemplate().get(
					"com.travel.entity.RoleMenu", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}	

	public List<RoleMenu> findByProperty(String propertyName, Object value) {
		log.debug("finding RoleMenu instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from RoleMenu as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * @param id
	 */
	public void deleteByRoleId(final Long roleId) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				String sql = "delete from role_menu where role_id = ?";
				Query queryObject = session.createSQLQuery(sql);
				queryObject.setParameter(0, roleId);
				queryObject.executeUpdate();
				return null;
			}
		});			
	}
}