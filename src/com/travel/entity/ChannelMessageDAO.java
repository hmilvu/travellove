package com.travel.entity;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travel.dao.BaseDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * ChannelMessage entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.travel.entity.ChannelMessage
 * @author MyEclipse Persistence Tools
 */

public class ChannelMessageDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ChannelMessageDAO.class);
	// property constants
	public static final String MESSAGE_ID = "messageId";
	public static final String CHANNEL_ID = "channelId";
	public static final String STATUS = "status";
	public static final String RESPONSE = "response";

	public void save(ChannelMessage transientInstance) {
		log.debug("saving ChannelMessage instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ChannelMessage persistentInstance) {
		log.debug("deleting ChannelMessage instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ChannelMessage findById(java.lang.Long id) {
		log.debug("getting ChannelMessage instance with id: " + id);
		try {
			ChannelMessage instance = (ChannelMessage) getSession().get(
					"com.travel.entity.ChannelMessage", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<ChannelMessage> findByExample(ChannelMessage instance) {
		log.debug("finding ChannelMessage instance by example");
		try {
			List<ChannelMessage> results = (List<ChannelMessage>) getSession()
					.createCriteria("com.travel.entity.ChannelMessage").add(
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
		log.debug("finding ChannelMessage instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ChannelMessage as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<ChannelMessage> findByMessageId(Object messageId) {
		return findByProperty(MESSAGE_ID, messageId);
	}

	public List<ChannelMessage> findByChannelId(Object channelId) {
		return findByProperty(CHANNEL_ID, channelId);
	}

	public List<ChannelMessage> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List<ChannelMessage> findByResponse(Object response) {
		return findByProperty(RESPONSE, response);
	}

	public List findAll() {
		log.debug("finding all ChannelMessage instances");
		try {
			String queryString = "from ChannelMessage";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ChannelMessage merge(ChannelMessage detachedInstance) {
		log.debug("merging ChannelMessage instance");
		try {
			ChannelMessage result = (ChannelMessage) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ChannelMessage instance) {
		log.debug("attaching dirty ChannelMessage instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ChannelMessage instance) {
		log.debug("attaching clean ChannelMessage instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}