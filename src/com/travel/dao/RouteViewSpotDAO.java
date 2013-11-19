package com.travel.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.travel.entity.RouteViewSpot;

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
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(RouteViewSpot persistentInstance) {
		log.debug("deleting RouteViewSpot instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RouteViewSpot findById(java.lang.Long id) {
		log.debug("getting RouteViewSpot instance with id: " + id);
		try {
			RouteViewSpot instance = (RouteViewSpot) getSession().get(
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
	public List<RouteViewSpot> findByViewSpotIds(List<Long> idList) {
		log.debug("findByViewSpotIds");
		try {
			String queryString = "from RouteViewSpot as r where r.viewSpotInfo.id in (:ids)";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameterList("ids", idList);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("findByViewSpotIds failed", re);
			throw re;
		}
	}

	/**
	 * @param ids
	 */
	public void deleteByRouteIds(String ids) {
		try {
			String sql = "delete from route_view_spot where id in ("+ids+")";
			Query queryObject = getSession().createSQLQuery(sql);
			queryObject.executeUpdate();
		} catch (RuntimeException re) {
			log.error("find by credentials failed", re);
			throw re;
		}
		
	}
}