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
import com.travel.common.Constants.TEAM_STATUS;
import com.travel.common.admin.dto.SearchTeamDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.TeamInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * TeamInfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.TeamInfo
 * @author MyEclipse Persistence Tools
 */
@Repository
public class TeamInfoDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TeamInfoDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String STATUS = "status";
	public static final String PEOPLE_COUNT = "peopleCount";
	public static final String DESCRIPTION = "description";

	public int save(TeamInfo transientInstance) {
		log.debug("saving TeamInfo instance");
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

	public void delete(TeamInfo persistentInstance) {
		log.debug("deleting TeamInfo instance");
		try {
			getSession().delete(persistentInstance);
			getSession().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TeamInfo findById(java.lang.Long id) {
		log.debug("getting TeamInfo instance with id: " + id);
		try {
			TeamInfo instance = (TeamInfo) getSession().get(
					"com.travel.entity.TeamInfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * @param idLong
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TeamInfo> findByTravelId(Long travelId) {
		try {
			String queryString = "from TeamInfo as t where t.travelInf.id = ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, travelId);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * @param dto
	 * @return
	 */
	public int getTotalNum(SearchTeamDTO dto) {
		try {
			Criteria cr = buildSearchCriteria(dto);
			Long total=(Long)cr.setProjection(Projections.rowCount()).uniqueResult(); 			
			return  total.intValue();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/**
	 * @param dto
	 * @return
	 */
	private Criteria buildSearchCriteria(SearchTeamDTO dto) {
		Criteria cr = getSession().createCriteria(TeamInfo.class);
		cr.createAlias("travelInf", "t");
		if (!StringUtils.isBlank(dto.getTravelName())) {
			cr.add(Restrictions.like("t.name", StringUtils.trim(dto.getTravelName()) + "%").ignoreCase());
		}
		if (!StringUtils.isBlank(dto.getTeamName())) {
			cr.add(Restrictions.like("name", StringUtils.trim(dto.getTeamName()) + "%").ignoreCase());
		}
		if (dto.getStartDate() != null){
			cr.add(Restrictions.ge("beginDate", dto.getStartDate()));
		}
		if (dto.getEndDate() != null){
			cr.add(Restrictions.ge("endDate", dto.getEndDate()));
		}
		if(dto.getTravelId() != null){
			cr.add(Restrictions.eq("t.id", dto.getTravelId()));
		}
		cr.add(Restrictions.eq("status", Integer.valueOf(TEAM_STATUS.ACTIVE.getValue())));
		return cr;
	}

	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TeamInfo> findTeams(SearchTeamDTO dto, PageInfoDTO pageInfo) {
		try {
			Criteria cr = buildSearchCriteria(dto);
			int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.ADMIN_DEFAULT_PAGE_SIZE;
			cr.setMaxResults(maxResults);
			cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
			cr.addOrder(Order.desc("beginDate"));
			return cr.list();
		} catch (RuntimeException re) {
			log.error("find teams failed", re);
			throw re;
		}
	}

	/**
	 * @param team
	 */
	public int update(TeamInfo team) {
		log.debug("update TeamInfo instance");
		int result = 0;
		try {
			getSession().update(team);
			getSession().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			result = -1;
			throw re;
		}
		return result;
		
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TeamInfo> findAll(Long travelId) {
		log.debug("finding all TeamInfo instances");
		try {
			String queryString = "from TeamInfo where travelInf.id = ? and status=0 order by createDate desc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, travelId);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}