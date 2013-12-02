package com.travel.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.travel.common.Constants;
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
			getSession().save(transientInstance);
			getSession().flush();
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
			ViewSpotInfo instance = (ViewSpotInfo) getSession().get(
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
	public int getTotalNum(SearchViewSpotDTO dto) {
		try {
			Criteria cr = buildSearchCriteria(dto);
			Long total=(Long)cr.setProjection(Projections.rowCount()).uniqueResult(); 			
			return  total.intValue();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	/**
	 * @param username
	 * @param password2
	 * @return
	 */
	public MemberInf findByCredentials(Long teamId, String mobile, String password) {
		try {
			String queryString = "from MemberInf as m where m.teamInfo.id = ? and m.travelerMobile = ? and m.password = ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, teamId);
			queryObject.setParameter(1, mobile);
			queryObject.setParameter(2, password);
			return (MemberInf)queryObject.uniqueResult();
		} catch (RuntimeException re) {
			log.error("find by credentials failed", re);
			throw re;
		}
	}

	/**
	 * @param dto
	 * @return
	 */
	private Criteria buildSearchCriteria(SearchViewSpotDTO dto) {
		Criteria cr = getSession().createCriteria(ViewSpotInfo.class);
		cr.createAlias("travelInf", "t");
		if (dto.getTravelId() != null) {
			cr.add(Restrictions.eq("t.id", dto.getTravelId()));
		}
		if (StringUtils.isNotBlank(dto.getName())) {
			cr.add(Restrictions.like("name", StringUtils.trim(dto.getName()) + "%").ignoreCase());
		}
		return cr;
	}

	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewSpotInfo> findViewSpots(SearchViewSpotDTO dto, PageInfoDTO pageInfo) {
		try {
			Criteria cr = buildSearchCriteria(dto);
			int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.ADMIN_DEFAULT_PAGE_SIZE;
			cr.setMaxResults(maxResults);
			cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
//			cr.addOrder(Order.asc("t.name"));
			cr.addOrder(Order.asc("name"));
			return cr.list();
		} catch (RuntimeException re) {
			log.error("find view spots failed", re);
			throw re;
		}
	}

	/**
	 * @param ids
	 */
	public void deleteByIds(String ids) {
		try {
			String sql = "delete from view_spot_info where id in ("+ids+")";
			Query queryObject = getSession().createSQLQuery(sql);
			queryObject.executeUpdate();
		} catch (RuntimeException re) {
			log.error("find by credentials failed", re);
			throw re;
		}
		
	}

	/**
	 * @param view
	 * @return
	 */
	public int update(ViewSpotInfo view) {
		log.debug("updating ViewSpotInfo instance");
		int result = 0;
		try {
			getSession().save(view);
			getSession().flush();
			log.debug("updating successful");
		} catch (RuntimeException re) {
			log.error("updating failed", re);
			result = -1;
			throw re;
		}
		return result;
	}
}