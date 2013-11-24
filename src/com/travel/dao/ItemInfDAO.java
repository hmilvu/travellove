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
import com.travel.common.Constants.MEMBER_STATUS;
import com.travel.common.admin.dto.SearchItemDTO;
import com.travel.common.admin.dto.SearchMemberDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.ItemInf;
import com.travel.entity.MemberInf;

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

	public void delete(ItemInf persistentInstance) {
		log.debug("deleting ItemInf instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public ItemInf findById(java.lang.Long id) {
		log.debug("getting ItemInf instance with id: " + id);
		try {
			ItemInf instance = (ItemInf) getSession().get(
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
			getSession().update(item);
			getSession().flush();
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
	public int getTotalNum(SearchItemDTO dto) {
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
	private Criteria buildSearchCriteria(SearchItemDTO dto) {
		Criteria cr = getSession().createCriteria(ItemInf.class);
		cr.createAlias("sysUser", "s");
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
	public List<ItemInf> findItems(SearchItemDTO dto, PageInfoDTO pageInfo) {
		try {
			Criteria cr = buildSearchCriteria(dto);
			int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.ADMIN_DEFAULT_PAGE_SIZE;
			cr.setMaxResults(maxResults);
			cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
			cr.addOrder(Order.asc("brands"));
			cr.addOrder(Order.asc("name"));
			return cr.list();
		} catch (RuntimeException re) {
			log.error("find teams failed", re);
			throw re;
		}
	}

	/**
	 * @param ids
	 */
	public void deleteByIds(String ids) {
		try {
			String sql = "delete from item_inf where id in ("+ids+")";
			Query queryObject = getSession().createSQLQuery(sql);
			queryObject.executeUpdate();
		} catch (RuntimeException re) {
			log.error("find by credentials failed", re);
			throw re;
		}
		
	}

}