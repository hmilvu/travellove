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

import com.travel.entity.TriggerViewSpot;

/**
 * A data access object (DAO) providing persistence and search support for
 * TriggerViewSpot entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.travel.entity.TriggerViewSpot
 * @author MyEclipse Persistence Tools
 */
@Repository
public class TriggerViewSpotDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TriggerViewSpotDAO.class);

	// property constants

	public void save(TriggerViewSpot transientInstance) {
		log.debug("saving TriggerViewSpot instance");
		try {
			getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public TriggerViewSpot findById(java.lang.Long id) {
		log.debug("getting TriggerViewSpot instance with id: " + id);
		try {
			TriggerViewSpot instance = (TriggerViewSpot) getHibernateTemplate().get(
					"com.travel.entity.TriggerViewSpot", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * @param string
	 */
	public void deleteByTriggerIds(final String triggerIds) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				String sql = "delete from trigger_view_spot where trigger_id in ("+triggerIds+")";
				Query queryObject = session.createSQLQuery(sql);
				queryObject.executeUpdate();
				return null;
			}
		});	
		
	}

	/**
	 * @param triggerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TriggerViewSpot> getByTriggerId(Long triggerId) {
		return getHibernateTemplate().find("from TriggerViewSpot where triggerConfig.id = ?", triggerId);
	}
}