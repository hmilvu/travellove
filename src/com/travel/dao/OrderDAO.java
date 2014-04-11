package com.travel.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.travel.common.Constants;
import com.travel.common.Constants.MESSAGE_RECEIVER_TYPE;
import com.travel.common.Constants.MESSAGE_STATUS;
import com.travel.common.Constants.ORDER_STATUS;
import com.travel.common.Constants.TEAM_STATUS;
import com.travel.common.admin.dto.SearchOrderDTO;
import com.travel.common.admin.dto.SearchTeamDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.MemberInf;
import com.travel.entity.Message;
import com.travel.entity.Order;
import com.travel.entity.TeamInfo;

/**
 * A data access object (DAO) providing persistence and search support for Order
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.travel.entity.Order
 * @author MyEclipse Persistence Tools
 */
@Repository
public class OrderDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(OrderDAO.class);
	// property constants
	public static final String ITEM_COUNT = "itemCount";
	public static final String TOTAL_PRICE = "totalPrice";

	public void save(Order transientInstance) {
		log.debug("saving Order instance");
		try {
			getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Order persistentInstance) {
		log.debug("deleting Order instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Order findById(java.lang.Long id) {
		log.debug("getting Order instance with id: " + id);
		try {
			Order instance = (Order) getHibernateTemplate().get(
					"com.travel.entity.Order", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * @param idList
	 * @return
	 */
	public List<Order> findByItemIds(final List<Long> idList) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Order>>() {
			@Override
			public List<Order> doInHibernate(Session session) throws HibernateException,
					SQLException {
				String queryString = "from Order where itemInf.id in (:ids)";
				Query queryObject = session.createQuery(queryString);
				queryObject.setParameterList("ids", idList);
				return queryObject.list();
		}
	});	
	}

	/**
	 * @param dto
	 * @return
	 */
	private Criteria buildSearchCriteria(Session session, SearchOrderDTO dto) {
		Criteria cr = session.createCriteria(Order.class);
		if (!StringUtils.isBlank(dto.getProductName())) {
			cr.createAlias("itemInf", "i");
			cr.add(Restrictions.like("i.name", StringUtils.trim(dto.getProductName()) + "%").ignoreCase());
		}
		cr.createAlias("memberInf", "m");
		if (!StringUtils.isBlank(dto.getName())) {
			cr.add(Restrictions.like("m.name", StringUtils.trim(dto.getName()) + "%").ignoreCase());
		}		
		cr.createAlias("teamInfo", "t");
		if(dto.getTravelId() != null){
			cr.add(Restrictions.eq("t.travelInf.id", dto.getTravelId()));
		}
		if(StringUtils.isNotBlank(dto.getTeamName())){
			cr.add(Restrictions.like("t.name", StringUtils.trim(dto.getProductName()) + "%").ignoreCase());
		}
		cr.add(Restrictions.ne("status", Integer.valueOf(ORDER_STATUS.DELETED.getValue())));
		return cr;
	}


	/**
	 * @param dto
	 * @return
	 */
	public int getTotalNum(final SearchOrderDTO dto) {
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
	 * @param pageInfo
	 * @return
	 */
	public List<Order> getOrders(final SearchOrderDTO dto, final PageInfoDTO pageInfo) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Order>>() {
			@Override
			public List<Order> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = buildSearchCriteria(session, dto);
				int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.ADMIN_DEFAULT_PAGE_SIZE;
				cr.setMaxResults(maxResults);
				cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
				cr.addOrder(org.hibernate.criterion.Order.desc("createDate"));
				return cr.list();
		}
	});
	}

	/**
	 * @param ids
	 * @return
	 */
	public void deleteByIds(final String ids) {
		getHibernateTemplate().execute(new HibernateCallback<Object>(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String sql = "update order_inf set status = "+ORDER_STATUS.DELETED.getValue()+" where id in (?)";
				Query queryObject = session.createSQLQuery(sql);
				queryObject.setParameter(0, ids);
				queryObject.executeUpdate();
				return null;
			}});	
	}

	/**
	 * @param memberId
	 * @param pageInfo
	 * @return
	 */
	public List<Order> getOrdersByMemberId(final Long memberId, final PageInfoDTO pageInfo) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Order>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Order> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = session.createCriteria(Order.class);
				cr.add(Restrictions.eq("memberInf.id", memberId));
				cr.add(Restrictions.ne("status", ORDER_STATUS.DELETED.getValue()));
				int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.DEFAULT_PAGE_SIZE;
				cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
				cr.setMaxResults(maxResults);
				cr.addOrder(org.hibernate.criterion.Order.desc("createDate"));
				return cr.list();
			}
		});
	}

	/**
	 * @param order
	 */
	public void update(Order order) {
		try {
			getHibernateTemplate().update(order);
			getHibernateTemplate().flush();
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
		
	}
}