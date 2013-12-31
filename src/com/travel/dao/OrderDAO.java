package com.travel.dao;

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
import com.travel.entity.Order;

/**
 * A data access object (DAO) providing persistence and search support for Order
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.travel.entity.Order
 * @author MyEclipse Persistence Tools
 */
@Repository
public class OrderDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(OrderDAO.class);
	// property constants
	public static final String ITEM_COUNT = "itemCount";
	public static final String TOTAL_PRICE = "totalPrice";

	public void save(Order transientInstance) {
		log.debug("saving Order instance");
		try {
			getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Order persistentInstance) {
		log.debug("deleting Order instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Order findById(java.lang.Long id) {
		log.debug("getting Order instance with id: " + id);
		try {
			Order instance = (Order) getHibernateTemplate().get(
					"com.travel.entity.Order", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * @param idList
	 * @return
	 */
	public List<Order> findByItemIds(final List<Long> idList) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Order>>() {
			@Override
			public List<Order> doInHibernate(Session session) throws HibernateException,
					SQLException {
				String queryString = "from Order where itemInf.id in (:ids)";
				Query queryObject = session.createQuery(queryString);
				queryObject.setParameterList("ids", idList);
				return queryObject.list();
		}
	});	
	}
}