package com.travel.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.travel.common.Constants;
import com.travel.common.Constants.VIEW_SPOT_TYPE;
import com.travel.common.admin.dto.SearchViewSpotDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.MemberInf;
import com.travel.entity.ViewSpotInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * ViewSpotInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.travel.entity.ViewSpotInfo
 * @author MyEclipse Persistence Tools
 */
@Repository
public class ViewSpotInfoDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ViewSpotInfoDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String LONGITUDE = "longitude";
	public static final String LATITUDE = "latitude";
	public static final String DESCRIPTION = "description";

	public int save(ViewSpotInfo transientInstance) {
		log.debug("saving ViewSpotInfo instance");
		int result = 0;
		try {
			getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			result = -1;
			throw re;
		}
		return result;
	}

	public ViewSpotInfo findById(java.lang.Long id) {
		log.debug("getting ViewSpotInfo instance with id: " + id);
		try {
			ViewSpotInfo instance = (ViewSpotInfo) getHibernateTemplate().get(
					"com.travel.entity.ViewSpotInfo", id);
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
	public int getTotalNum(final SearchViewSpotDTO dto) {
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
	 * @param username
	 * @param password2
	 * @return
	 */
	public MemberInf findByCredentials(Long teamId, String mobile, String password) {
		try {
			String queryString = "from MemberInf as m where m.teamInfo.id = ? and m.travelerMobile = ? and m.password = ?";
			List<MemberInf> list = getHibernateTemplate().find(queryString, teamId, mobile, password);
			if(list != null && list.size() > 0){
				return list.get(0);
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			log.error("find by credentials failed", re);
			throw re;
		}
	}

	/**
	 * @param dto
	 * @return
	 */
	private Criteria buildSearchCriteria(Session session, SearchViewSpotDTO dto) {
		Criteria cr = session.createCriteria(ViewSpotInfo.class);
		cr.createAlias("travelInf", "t", CriteriaSpecification.LEFT_JOIN);
		if (dto.getTravelId() != null) {
			cr.add(Restrictions.or(Restrictions.eq("t.id", dto.getTravelId()), Restrictions.eq("type", VIEW_SPOT_TYPE.PUBLIC.getValue())));
		}
		if (StringUtils.isNotBlank(dto.getName())) {
			cr.add(Restrictions.like("name", StringUtils.trim("%" + dto.getName()) + "%").ignoreCase());
		}
		if(StringUtils.isNotBlank(dto.getProvince())){
			cr.add(Restrictions.eq("province", dto.getProvince()));
		}
		if(StringUtils.isNotBlank(dto.getCity())){
			cr.add(Restrictions.eq("city", dto.getCity()));
		}
		return cr;
	}

	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewSpotInfo> findViewSpots(final SearchViewSpotDTO dto, final PageInfoDTO pageInfo) {
		return getHibernateTemplate().execute(new HibernateCallback<List<ViewSpotInfo>>() {
			@Override
			public List<ViewSpotInfo> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = buildSearchCriteria(session, dto);
				int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.ADMIN_DEFAULT_PAGE_SIZE;
				cr.setMaxResults(maxResults);
				cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
				cr.addOrder(Order.desc("type"));
				cr.addOrder(Order.asc("province"));
				cr.addOrder(Order.asc("city"));
				return cr.list();
			}
		});	
	}

	/**
	 * @param ids
	 */
	public void deleteByIds(final String ids) {
		getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				String sql = "delete from view_spot_info where id in ("+ids+")";
				Query queryObject = session.createSQLQuery(sql);
				return queryObject.executeUpdate();
			}
		});			
	}

	/**
	 * @param view
	 * @return
	 */
	public int update(ViewSpotInfo view) {
		log.debug("updating ViewSpotInfo instance");
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
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	public List<ViewSpotInfo> findTeamViewSpots(final List<Long> routeIdList,
			final PageInfoDTO pageInfo) {
		return getHibernateTemplate().execute(new HibernateCallback<List<ViewSpotInfo>>() {
			@Override
			public List<ViewSpotInfo> doInHibernate(Session session) throws HibernateException,
					SQLException {
				String queryString = "select rv.viewSpotInfo from RouteViewSpot rv where rv.routeInf.id in (:ids) order by rv.order";
				Query query = session.createQuery(queryString);
				query.setParameterList("ids", routeIdList);
				int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.DEFAULT_PAGE_SIZE;
				query.setMaxResults(maxResults);
				query.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
				return query.list();
			}
		});	
	}
}