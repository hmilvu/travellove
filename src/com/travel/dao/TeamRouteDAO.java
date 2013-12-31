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
			getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TeamRoute persistentInstance) {
		log.debug("deleting TeamRoute instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TeamRoute findById(java.lang.Long id) {
		log.debug("getting TeamRoute instance with id: " + id);
		try {
			TeamRoute instance = (TeamRoute) getHibernateTemplate().get(
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
	public List<TeamRoute> findByTeamId(final Long teamId, final PageInfoDTO pageInfo) {
		return getHibernateTemplate().execute(new HibernateCallback<List<TeamRoute>>() {
			@Override
			public List<TeamRoute> doInHibernate(Session session) throws HibernateException,
					SQLException {
					String queryString = "from TeamRoute as model where model.teamInfo.id = ? order by model.routeOrder";
					Query queryObject = session.createQuery(queryString);
					int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.DEFAULT_PAGE_SIZE;
					queryObject.setFirstResult(pageInfo.getPageNumber() * maxResults);
					queryObject.setMaxResults(maxResults);
					queryObject.setParameter(0, teamId);
					return queryObject.list();
			}
		});	
	}

	/**
	 * @param idList
	 * @return
	 */
	public List<TeamRoute> findByRouteIds(final List<Long> idList) {
		return getHibernateTemplate().execute(new HibernateCallback<List<TeamRoute>>() {
			@Override
			public List<TeamRoute> doInHibernate(Session session) throws HibernateException,
					SQLException {
				String queryString = "from TeamRoute as r where r.routeInf.id in (:ids)";
				Query queryObject = session.createQuery(queryString);
				queryObject.setParameterList("ids", idList);
				return queryObject.list();
			}
		});	
	}

	/**
	 * @param string
	 */
	public void deleteByTeamIds(final String ids) {
		 getHibernateTemplate().execute(new HibernateCallback<Integer>() {
				@Override
				public Integer doInHibernate(Session session) throws HibernateException,
						SQLException {
				String sql = "delete from team_route where team_id in ("+ids+")";
				Query queryObject = session.createSQLQuery(sql);
				return queryObject.executeUpdate();
			}
		});	
		
	}
	
	/**
	 * @param id
	 * @return
	 */
	public List<TeamRoute> findByTeamId(Long teamId) {
		try {
			String queryString = "from TeamRoute as model where model.teamInfo.id = ? order by model.routeOrder";
			return getHibernateTemplate().find(queryString, teamId);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}