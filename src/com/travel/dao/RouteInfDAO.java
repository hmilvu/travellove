package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
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
import com.travel.common.admin.dto.SearchRouteDTO;
import com.travel.common.admin.dto.SearchViewSpotDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.MemberInf;
import com.travel.entity.RouteInf;
import com.travel.entity.ViewSpotInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * RouteInf entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.RouteInf
 * @author MyEclipse Persistence Tools
 */
@Repository
public class RouteInfDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(RouteInfDAO.class);
	// property constants
	public static final String ROUTE_NAME = "routeName";
	public static final String START_ADDRESS = "startAddress";
	public static final String START_LONGTITUDE = "startLongtitude";
	public static final String START_LATITUDE = "startLatitude";
	public static final String END_ADDRESS = "endAddress";
	public static final String END_LONGITUDE = "endLongitude";
	public static final String END_LATITUDE = "endLatitude";
	public static final String DESCRIPTION = "description";

	public Long save(RouteInf transientInstance) {
		log.debug("saving RouteInf instance");
		Long id  = null;
		try {
			id = (Long)getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		return id;
	}

	public void delete(RouteInf persistentInstance) {
		log.debug("deleting RouteInf instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RouteInf findById(java.lang.Long id) {
		log.debug("getting RouteInf instance with id: " + id);
		try {
			RouteInf instance = (RouteInf) getHibernateTemplate().get(
					"com.travel.entity.RouteInf", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}


	/**
	 * @param dto
	 * @return
	 */
	public int getTotalNum(final SearchRouteDTO dto) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = buildSearchCriteria(session, dto);
				Long total=(Long)cr.setProjection(Projections.rowCount()).uniqueResult(); 			
				return  total.intValue();
			}
		});	
	}

	/**
	 * @param dto
	 * @return
	 */
	private Criteria buildSearchCriteria(Session session, SearchRouteDTO dto) {
		Criteria cr = session.createCriteria(RouteInf.class);
		cr.createAlias("travelInf", "t");
		if (dto.getTravelId() != null) {
			cr.add(Restrictions.eq("t.id", dto.getTravelId()));
		}
		if (StringUtils.isNotBlank(dto.getName())) {
			cr.add(Restrictions.like("routeName", StringUtils.trim(dto.getName()) + "%").ignoreCase());
		}
		return cr;
	}

	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RouteInf> findRoutes(final SearchRouteDTO dto, final PageInfoDTO pageInfo) {
		return getHibernateTemplate().execute(new HibernateCallback<List<RouteInf>>() {
			@Override
			public List<RouteInf> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = buildSearchCriteria(session, dto);
				int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.ADMIN_DEFAULT_PAGE_SIZE;
				cr.setMaxResults(maxResults);
				cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
	//			cr.addOrder(Order.asc("t.name"));
				cr.addOrder(Order.asc("routeName"));
				return cr.list();
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
				String sql = "delete from route_inf where id in ("+ids+")";
				Query queryObject = session.createSQLQuery(sql);
				queryObject.executeUpdate();
				return null;
			}
		});		
	}
	

	/**
	 * @param view
	 * @return
	 */
	public int update(RouteInf view) {
		log.debug("updating RouteInf instance");
		int result = 0;
		try {
			getHibernateTemplate().save(view);
			getHibernateTemplate().flush();
			log.debug("updating successful");
		} catch (RuntimeException re) {
			log.error("updating failed", re);
			result = -1;
			throw re;
		}
		return result;
	}

	/**
	 * @param teamId
	 * @return
	 */
	public List<Long> getRouteIdsByTeamId(Long teamId) {
		return getHibernateTemplate().find("select r.routeInf.id from TeamRoute r where r.teamInfo.id = ?", teamId);
	}

}