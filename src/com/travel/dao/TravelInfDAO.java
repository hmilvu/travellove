package com.travel.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
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
import com.travel.common.admin.dto.SearchTravelDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.TravelInf;

/**
 * A data access object (DAO) providing persistence and search support for
 * TravelInf entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.TravelInf
 * @author MyEclipse Persistence Tools
 */
@Repository
public class TravelInfDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TravelInfDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String ADDRESS = "address";
	public static final String PHONE = "phone";
	public static final String CONTACT = "contact";
	public static final String LINKER = "linker";
	public static final String DESCRIPTION = "description";

	public int save(TravelInf transientInstance) {
		log.debug("saving TravelInf instance");
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

	public void delete(TravelInf persistentInstance) {
		log.debug("deleting TravelInf instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public int update(TravelInf persistentInstance) {
		int result = 0;
		try {
			getHibernateTemplate().update(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			result = -1;
			throw re;
		}
		return result;
	}

	public TravelInf findById(java.lang.Long id) {
		log.debug("getting TravelInf instance with id: " + id);
		try {
			TravelInf instance = (TravelInf) getHibernateTemplate().get(
					"com.travel.entity.TravelInf", id);
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
	public int getTotalNum(final SearchTravelDTO dto) {
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
	private Criteria buildSearchCriteria(Session session, SearchTravelDTO dto) {
		Criteria cr = session.createCriteria(TravelInf.class);
		if (!StringUtils.isBlank(dto.getTravelName())) {
			cr.add(Restrictions.like("name", StringUtils.trim(dto.getTravelName()) + "%").ignoreCase());
		}
		if (!StringUtils.isBlank(dto.getPersonName())) {
			cr.add(Restrictions.or(Restrictions.like("contact", StringUtils.trim(dto.getPersonName()) + "%").ignoreCase(), Restrictions.like("linker", StringUtils.trim(dto.getPersonName()) + "%").ignoreCase()));
		}
		return cr;
	}

	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TravelInf> findTravels(final SearchTravelDTO dto, final PageInfoDTO pageInfo) {
		return getHibernateTemplate().execute(new HibernateCallback<List<TravelInf>>() {
			@Override
			public List<TravelInf> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = buildSearchCriteria(session, dto);
				int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.ADMIN_DEFAULT_PAGE_SIZE;
				cr.setMaxResults(maxResults);
				cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
				cr.addOrder(Order.desc("id"));
				return cr.list();
			}
		});	
	}
	
	@SuppressWarnings("unchecked")
	public List<TravelInf> findByName(String name) {
		try {
			String queryString = "from TravelInf as m where m.name = ?";
			return getHibernateTemplate().find(queryString, name);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * @param travelId
	 * @return
	 */
	public Integer deleteById(final Long travelId) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				String sql = "delete from travel_inf where id = ?";
				Query queryObject = session.createSQLQuery(sql);
				queryObject.setParameter(0, travelId);
				int result = queryObject.executeUpdate();
				return result;
			}
		});	
	}

	/**
	 * @return
	 */
	public List<TravelInf> findAll() {
		log.debug("finding all TravelInf instances");
		try {
			String queryString = "from TravelInf order by id desc";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}