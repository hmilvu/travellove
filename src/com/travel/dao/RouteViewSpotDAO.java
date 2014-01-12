package com.travel.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.travel.entity.MemberInf;
import com.travel.entity.RouteViewSpot;
import com.travel.entity.ViewSpotInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * RouteViewSpot entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.travel.entity.RouteViewSpot
 * @author MyEclipse Persistence Tools
 */
@Repository
public class RouteViewSpotDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(RouteViewSpotDAO.class);

	// property constants

	public void save(RouteViewSpot transientInstance) {
		log.debug("saving RouteViewSpot instance");
		try {
			getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(RouteViewSpot persistentInstance) {
		log.debug("deleting RouteViewSpot instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RouteViewSpot findById(java.lang.Long id) {
		log.debug("getting RouteViewSpot instance with id: " + id);
		try {
			RouteViewSpot instance = (RouteViewSpot) getHibernateTemplate().get(
					"com.travel.entity.RouteViewSpot", id);
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
	@SuppressWarnings("unchecked")
	public List<RouteViewSpot> findByViewSpotIds(final List<Long> idList) {
		return getHibernateTemplate().execute(new HibernateCallback<List<RouteViewSpot>>() {
			@Override
			public List<RouteViewSpot> doInHibernate(Session session) throws HibernateException,
					SQLException {
				String queryString = "from RouteViewSpot as r where r.viewSpotInfo.id in (:ids)";
				Query queryObject = session.createQuery(queryString);
				queryObject.setParameterList("ids", idList);
				return queryObject.list();
			}
		});	
	}

	/**
	 * @param ids
	 */
	public void deleteByRouteIds(final String ids) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				String sql = "delete from route_view_spot where route_id in ("+ids+")";
				Query queryObject = session.createSQLQuery(sql);
				queryObject.executeUpdate();
				return null;
			}
		});	
		
	}

	/**
	 * @param id
	 * @return
	 */
	public List<Object[]> findByRouteId(Long id) {
		log.debug("findByViewSpotIds");
		try {
			String queryString = "select r, r.viewSpotInfo from RouteViewSpot r inner join r.viewSpotInfo where r.routeInf.id = ? order by r.order";
			return getHibernateTemplate().find(queryString, id);
		} catch (RuntimeException re) {
			log.error("findByViewSpotIds failed", re);
			throw re;
		}
	}

	/**
	 * @param id
	 * @return
	 */
	public List<Object[]> findViewSpotByRouteId(Long routeId) {
		log.debug("findViewSpotByRouteId");
		try {
			String queryString = "select r.viewSpotInfo, r.startTime, r.endTime, r.numberOfDay from RouteViewSpot r where r.routeInf.id = ? order by r.order";
			return getHibernateTemplate().find(queryString, routeId);
		} catch (RuntimeException re) {
			log.error("findViewSpotByRouteId failed", re);
			throw re;
		}
	}
}