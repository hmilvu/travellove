package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.travel.common.Constants;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.TeamInfo;
import com.travel.entity.TeamRoute;

/**
 * A data access object (DAO) providing persistence and search support for
 * TeamRoute entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.TeamRoute
 * @author MyEclipse Persistence Tools
 */
@Repository
public class TeamRouteDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TeamRouteDAO.class);
	// property constants
	public static final String STATUS = "status";

	public void save(TeamRoute transientInstance) {
		log.debug("saving TeamRoute instance");
		try {
			getSession().save(transientInstance);
			getSession().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TeamRoute persistentInstance) {
		log.debug("deleting TeamRoute instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TeamRoute findById(java.lang.Long id) {
		log.debug("getting TeamRoute instance with id: " + id);
		try {
			TeamRoute instance = (TeamRoute) getSession().get(
					"com.travel.entity.TeamRoute", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * @param id
	 * @return
	 */
	public List<TeamRoute> findByTeamId(Long teamId, PageInfoDTO pageInfo) {
		try {
			String queryString = "from TeamRoute as model where model.teamInfo.id = ? order by model.routeOrder";
			Query queryObject = getSession().createQuery(queryString);
			int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.DEFAULT_PAGE_SIZE;
			queryObject.setFirstResult(pageInfo.getPageNumber() * maxResults);
			queryObject.setMaxResults(maxResults);
			queryObject.setParameter(0, teamId);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * @param idList
	 * @return
	 */
	public List<TeamRoute> findByRouteIds(List<Long> idList) {
		log.debug("findByRouteIds");
		try {
			String queryString = "from TeamRoute as r where r.routeInf.id in (:ids)";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameterList("ids", idList);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("findByRouteIds failed", re);
			throw re;
		}
	}

	/**
	 * @param string
	 */
	public void deleteByTeamIds(String ids) {
		try {
			String sql = "delete from team_route where team_id in ("+ids+")";
			Query queryObject = getSession().createSQLQuery(sql);
			queryObject.executeUpdate();
		} catch (RuntimeException re) {
			log.error("find by credentials failed", re);
			throw re;
		}
		
	}
	
	/**
	 * @param id
	 * @return
	 */
	public List<TeamRoute> findByTeamId(Long teamId) {
		try {
			String queryString = "from TeamRoute as model where model.teamInfo.id = ? order by model.routeOrder";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, teamId);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}