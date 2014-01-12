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
import com.travel.common.Constants.ITEM_TYPE;
import com.travel.common.Constants.VIEW_SPOT_TYPE;
import com.travel.common.admin.dto.SearchItemDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.ItemInf;

/**
 * A data access object (DAO) providing persistence and search support for
 * ItemInf entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.ItemInf
 * @author MyEclipse Persistence Tools
 */
@Repository
public class ItemInfDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(ItemInfDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String BRANDS = "brands";
	public static final String SPECIFICATION = "specification";
	public static final String PRICE = "price";
	public static final String DESCRIPTION = "description";
	public static final String CONTACT_PHONE = "contactPhone";
	public static final String CONTACT_NAME = "contactName";

	public int save(ItemInf transientInstance) {
		log.debug("saving ItemInf instance");
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

	public void delete(ItemInf persistentInstance) {
		log.debug("deleting ItemInf instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public ItemInf findById(java.lang.Long id) {
		log.debug("getting ItemInf instance with id: " + id);
		try {
			ItemInf instance = (ItemInf) getHibernateTemplate().get(
					"com.travel.entity.ItemInf", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * @param member
	 * @return
	 */
	public int update(ItemInf item) {
		int result = 0;
		log.debug("update ItemInf instance");
		try {
			getHibernateTemplate().update(item);
			getHibernateTemplate().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			result = -1;
		}
		return result;
	}
	
	/**
	 * @param dto
	 * @return
	 */
	public int getTotalNum(final SearchItemDTO dto) {
		Integer num  = getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = buildSearchCriteria(session, dto);
				Long total=(Long)cr.setProjection(Projections.rowCount()).uniqueResult(); 			
				return  total.intValue();
			}
		});	
		return num;
	}

	/**
	 * @param dto
	 * @return
	 */
	private Criteria buildSearchCriteria(Session session, SearchItemDTO dto) {
		Criteria cr = session.createCriteria(ItemInf.class);
		cr.createAlias("sysUser", "s");
		cr.createAlias("travelInf", "t", CriteriaSpecification.LEFT_JOIN);
		if (dto.getTravelId() != null) {
			cr.add(Restrictions.or(Restrictions.eq("t.id", dto.getTravelId()), Restrictions.eq("type", ITEM_TYPE.PUBLIC.getValue())));
		}
		if (StringUtils.isNotBlank(dto.getName())) {
			cr.add(Restrictions.like("name", StringUtils.trim(dto.getName()) + "%").ignoreCase());
		}
		if (StringUtils.isNotBlank(dto.getBrands())){
			cr.add(Restrictions.like("brands", StringUtils.trim(dto.getBrands()) + "%").ignoreCase());
		}
		return cr;
	}

	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	public List<ItemInf> findItems(final SearchItemDTO dto, final PageInfoDTO pageInfo) {
		return getHibernateTemplate().execute(new HibernateCallback<List<ItemInf>>() {
			@Override
			public List<ItemInf> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = buildSearchCriteria(session, dto);
				int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.ADMIN_DEFAULT_PAGE_SIZE;
				cr.setMaxResults(maxResults);
				cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
				cr.addOrder(Order.asc("brands"));
				cr.addOrder(Order.asc("name"));
				return cr.list();
			}
		});	
	}

	/**
	 * @param ids
	 */
	public void deleteByIds(final String ids) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				String sql = "delete from item_inf where id in ("+ids+")";
				Query queryObject = session.createSQLQuery(sql);
				queryObject.executeUpdate();
				return null;
			}
		});	
		
	}

	/**
	 * @param pageInfo
	 * @return
	 */
	public List<ItemInf> findItems(final Long travelId, final PageInfoDTO pageInfo) {
		return getHibernateTemplate().execute(new HibernateCallback<List<ItemInf>>() {
			@Override
			public List<ItemInf> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = session.createCriteria(ItemInf.class);
				cr.createAlias("travelInf", "t", CriteriaSpecification.LEFT_JOIN);
				cr.add(Restrictions.or(Restrictions.eq("t.id", travelId), Restrictions.eq("type", VIEW_SPOT_TYPE.PUBLIC.getValue())));
				int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.DEFAULT_PAGE_SIZE;
				cr.setMaxResults(maxResults);
				cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
				cr.addOrder(Order.desc("createDate"));
				return cr.list();
			}
		});	
	}

}