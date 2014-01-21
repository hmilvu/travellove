package com.travel.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.travel.common.Constants;
import com.travel.common.admin.dto.SearchTriggerConfigDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.TriggerConfig;

/**
 * A data access object (DAO) providing persistence and search support for
 * TriggerConfig entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.travel.entity.TriggerConfig
 * @author MyEclipse Persistence Tools
 */
@Repository
public class TriggerConfigDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TriggerConfigDAO.class);

	public void save(TriggerConfig transientInstance) {
		log.debug("saving TriggerConfig instance");
		try {
			getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TriggerConfig persistentInstance) {
		log.debug("deleting TriggerConfig instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public void update(TriggerConfig persistentInstance) {
		log.debug("update TriggerConfig instance");
		try {
			getHibernateTemplate().update(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	public TriggerConfig findById(java.lang.Long id) {
		log.debug("getting TriggerConfig instance with id: " + id);
		try {
			TriggerConfig instance = (TriggerConfig) getHibernateTemplate().get(
					"com.travel.entity.TriggerConfig", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * @param travelId
	 * @return
	 */
//	public List<TriggerConfig> getNewSysTriggerConfig(final Long travelId) {
//		return getHibernateTemplate().execute(new HibernateCallback<List<TriggerConfig>>() {
//			@Override
//			public List<TriggerConfig> doInHibernate(Session session) throws HibernateException,
//					SQLException {
//					String queryString = "from TriggerConfig as m where m.sysTriggerConfigId is null" +
//							" and m.id not in (select b.sysTriggerConfigId from TriggerConfig b where b.travelId = ?)";
//					Query queryObject = session.createQuery(queryString);
//					queryObject.setParameter(0, travelId);
//					return queryObject.list();
//			}
//		});
//	}

	/**
	 * @param dto
	 * @return
	 */
	public int getTotalNum(final SearchTriggerConfigDTO dto) {
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
	private Criteria buildSearchCriteria(Session session,
			SearchTriggerConfigDTO dto) {
		Criteria cr = session.createCriteria(TriggerConfig.class);
		if (!StringUtils.isBlank(dto.getName())) {
			cr.add(Restrictions.like("name", StringUtils.trim(dto.getName()) + "%").ignoreCase());
		}
		if(dto.getTravelId() != null){
			cr.add(Restrictions.eq("travelId", dto.getTravelId()));
		} else {
			cr.add(Restrictions.isNull("sysTriggerConfigId"));
		}
		return cr;
	}

	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	public List<TriggerConfig> findTriggerConfigs(final SearchTriggerConfigDTO dto,
			final PageInfoDTO pageInfo) {
		return getHibernateTemplate().execute(new HibernateCallback<List<TriggerConfig>>() {
			@Override
			public List<TriggerConfig> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = buildSearchCriteria(session, dto);
				int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.ADMIN_DEFAULT_PAGE_SIZE;
				cr.setMaxResults(maxResults);
				cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
				cr.addOrder(Order.asc("id"));
				return cr.list();
			}
		});	
	}

	/**
	 * @param travelId
	 */
	public void initTriggerConfig(final Long travelId) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				String sql = "INSERT INTO trigger_config (type_value, content, start_time,times, " + 
							" trigger_type, condition_value, trigger_condition, " + 
							" trigger_status, travel_id, sys_trigger_config_id," + 
							" trigger_name, unitage)  " + 
							"  select b.type_value, b.content, b.start_time, b.times, " + 
							"  b.trigger_type, b.condition_value, b.trigger_condition, " + 
							"  b.trigger_status, ?, b.id," + 
							"  b.trigger_name, b.unitage from trigger_config b " + 
							"  where b.sys_trigger_config_id is NULL" + 
							"  and b.id not in (select c.sys_trigger_config_id from trigger_config c where c.travel_id = ?)" ;
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameter(0, travelId);
				query.setParameter(1, travelId);
				query.executeUpdate();
				return null;
			}
		});	
	}
}