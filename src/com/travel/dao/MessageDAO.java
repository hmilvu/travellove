package com.travel.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
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
import com.travel.common.Constants.MESSAGE_RECEIVER_TYPE;
import com.travel.common.Constants.MESSAGE_REMIND_MODE;
import com.travel.common.Constants.MESSAGE_STATUS;
import com.travel.common.Constants.PUSH_STATUS;
import com.travel.common.Constants.TRIGGER_TYPE;
import com.travel.common.admin.dto.SearchMessageDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.Message;
import com.travel.entity.Reply;

/**
 * A data access object (DAO) providing persistence and search support for
 * Message entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.Message
 * @author MyEclipse Persistence Tools
 */
@Repository
public class MessageDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(MessageDAO.class);
	// property constants
	public static final String AUTHOR_ID = "authorId";
	public static final String PRIORITY = "priority";
	public static final String STATUS = "status";
	public static final String TYPE = "type";
	public static final String TOPIC = "topic";
	public static final String CONTENT = "content";
	public static final String RECEIVER_ID = "receiverId";
	public static final String REMIND_MODE = "remindMode";

	public Long save(Message transientInstance) {
		log.debug("saving Message instance");
		try {
			Long id = (Long)getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().flush();
			log.debug("save successful");
			return id;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Message persistentInstance) {
		log.debug("deleting Message instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Message findById(java.lang.Long id) {
		log.debug("getting Message instance with id: " + id);
		try {
			Message instance = (Message) getHibernateTemplate().get(
					"com.travel.entity.Message", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Message instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Message as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Message> findByReceiverId(final Long memberId, final Long teamId, final PageInfoDTO pageInfo) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Message>>() {
			@Override
			public List<Message> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = session.createCriteria(Message.class);
				cr.add(Restrictions.le("remindTime", new Timestamp(new Date().getTime())));
				cr.add(Restrictions.ne("status", MESSAGE_STATUS.DELETED.getValue()));
				cr.add(Restrictions.or(Restrictions.and(Restrictions.eq("receiverId", memberId), Restrictions.eq("receiverType", MESSAGE_RECEIVER_TYPE.MEMBER.getValue())), 
						Restrictions.and(Restrictions.eq("receiverId", teamId), Restrictions.eq("receiverType", MESSAGE_RECEIVER_TYPE.TEAM.getValue()))));
				int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.DEFAULT_PAGE_SIZE;
				cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
				cr.setMaxResults(maxResults);
				cr.addOrder(Order.asc("priority"));
				cr.addOrder(Order.desc("remindTime"));
				return cr.list();
			}
		});	
	}

	/**
	 * @param messageId
	 * @param pageInfo
	 * @return
	 */
	public List<Reply> findRepliesByMessageId(final Long messageId,
			final PageInfoDTO pageInfo) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Reply>>() {
			@Override
			public List<Reply> doInHibernate(Session session) throws HibernateException,
					SQLException {
			Criteria cr = session.createCriteria(Reply.class).setFetchMode("memberInf",FetchMode.JOIN).setFetchMode("sysUser", FetchMode.JOIN);
			cr.add(Restrictions.eq("message.id", messageId));
			int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.DEFAULT_PAGE_SIZE;
			cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
			cr.setMaxResults(maxResults);
			cr.addOrder(Order.desc("createDate"));
			return cr.list();
			}
		});	
	}

	/**
	 * @param dto
	 * @return
	 */
	public int getTotalNum(final SearchMessageDTO dto) {
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
	private Criteria buildSearchCriteria(Session session, SearchMessageDTO dto) {
		Criteria cr = session.createCriteria(Message.class);
		cr.createAlias("travelInf", "t");
		if (dto.getTravelId() != null) {
			cr.add(Restrictions.eq("t.id", dto.getTravelId()));
		}
		if (!StringUtils.isBlank(dto.getContent())) {
			cr.add(Restrictions.like("content", StringUtils.trim("%" + dto.getContent()) + "%").ignoreCase());
		}
		if (dto.getTopic() != null){
			cr.add(Restrictions.like("topic", "%" + StringUtils.trim(dto.getTopic()) + "%").ignoreCase());
		}
		if (dto.getPriority() != null){
			cr.add(Restrictions.eq("priority", dto.getPriority()));
		}
		if(dto.getType() != null){
			cr.add(Restrictions.eq("type", dto.getType()));
		}
		if(dto.getStatus() != null){
			cr.add(Restrictions.eq("status", dto.getStatus()));
		}
		cr.add(Restrictions.ne("status", Integer.valueOf(MESSAGE_STATUS.DELETED.getValue())));
		cr.add(Restrictions.eq("triggerId", Long.valueOf(TRIGGER_TYPE.MANUAL.getValue())));
		cr.add(Restrictions.ne("receiverType", Integer.valueOf(MESSAGE_RECEIVER_TYPE.VIEW_SPOT.getValue())));
		cr.add(Restrictions.ne("receiverType", Integer.valueOf(MESSAGE_RECEIVER_TYPE.ITEM.getValue())));
		return cr;
	}

	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Message> findMessages(final SearchMessageDTO dto, final PageInfoDTO pageInfo) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Message>>() {
			@Override
			public List<Message> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = buildSearchCriteria(session, dto);
				int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.ADMIN_DEFAULT_PAGE_SIZE;
				cr.setMaxResults(maxResults);
				cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
				cr.addOrder(Order.asc("status"));
				cr.addOrder(Order.desc("t.id"));
				cr.addOrder(Order.desc("updateDate"));
				return cr.list();
			}
		});	
	}

	/**
	 * @param idList
	 */
	public void deleteByIds(final String ids) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				String sql = "update message set status = "+MESSAGE_STATUS.DELETED.getValue()+" where id in ("+ids+")";
				Query queryObject = session.createSQLQuery(sql);
				queryObject.executeUpdate();
				return null;
			}
		});	
	}

	/**
	 * @param id
	 * @return
	 */
	public int getTotalReplyNum(final Long id) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = buildReplyCriteria(session, id);
				Long total=(Long)cr.setProjection(Projections.rowCount()).uniqueResult(); 			
				return  total.intValue();
			}
		});	
	}
	
	/**
	 * @param messageId
	 * @param pageInfo
	 * @return
	 */
	public List<Reply> findAdminRepliesByMessageId(final Long messageId,
			final PageInfoDTO pageInfo) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Reply>>() {
			@Override
			public List<Reply> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = buildReplyCriteria(session, messageId);
				int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.DEFAULT_PAGE_SIZE;
				cr.setMaxResults(maxResults);
				cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
				cr.addOrder(Order.desc("createDate"));
				return cr.list();
			}
		});	
	}

	/**
	 * @param messageId
	 * @return
	 */
	private Criteria buildReplyCriteria(Session session, Long messageId) {
		Criteria cr = session.createCriteria(Reply.class).setFetchMode("memberInf",FetchMode.JOIN).setFetchMode("sysUser", FetchMode.JOIN);
		cr.add(Restrictions.eq("message.id", messageId));
		return cr;
	}

	/**
	 * @return
	 */
	public List<Message> getNeedToPush() {
		try {
			String queryString = "from Message where remindTime < ? and remindMode = ? and push_status != ? and push_status != ? and status = ? ";
			return getHibernateTemplate().find(queryString, new Timestamp(new Date().getTime()), MESSAGE_REMIND_MODE.LATER.getValue(), PUSH_STATUS.PUSHED.getValue(), PUSH_STATUS.PUSH_FAILED.getValue(), MESSAGE_STATUS.ACTIVE.getValue());
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * @param msg
	 */
	public void update(Message transientInstance) {
		log.debug("update Message instance");
		try {
			getHibernateTemplate().update(transientInstance);
			getHibernateTemplate().flush();
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	/**
	 * @param viewSpotId
	 * @return
	 */
	public int getTotalMessageNum(final MESSAGE_RECEIVER_TYPE type, final Long receiverId) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
			Criteria cr = buildSearchCriteria(session, type, receiverId);
			Long total=(Long)cr.setProjection(Projections.rowCount()).uniqueResult(); 			
			return  total.intValue();
			}
		});	
	}

	/**
	 * @param viewSpotId
	 * @param pageInfo
	 * @return
	 */
	public List<Message> findMessages(final MESSAGE_RECEIVER_TYPE type, final Long receiverId, final PageInfoDTO pageInfo) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Message>>() {
			@Override
			public List<Message> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = buildSearchCriteria(session, type, receiverId);
				int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.ADMIN_DEFAULT_PAGE_SIZE;
				cr.setMaxResults(maxResults);
				cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
				cr.addOrder(Order.desc("updateDate"));
				return cr.list();
			}
		});	
	}
	
	private Criteria buildSearchCriteria(Session session, MESSAGE_RECEIVER_TYPE type, final Long receiverId) {
		Criteria cr = session.createCriteria(Message.class);
		cr.add(Restrictions.eq("receiverId", receiverId));
		cr.add(Restrictions.ne("status", Integer.valueOf(MESSAGE_STATUS.DELETED.getValue())));
		cr.add(Restrictions.eq("receiverType", Integer.valueOf(type.getValue())));
		return cr;
	}

	/**
	 * @param id
	 * @param weather
	 * @return
	 */
	public List<Message> findTriggerMessage(Long teamId, Long triggerId) {
		Timestamp startTimestamp = new Timestamp(System.currentTimeMillis()/86400000*86400000-(23-Calendar.ZONE_OFFSET)*3600000); 
		Timestamp endTimestamp = new Timestamp(startTimestamp.getTime() + 3600 * 24 * 1000);
		String hql = "from Message where receiverType = " + MESSAGE_RECEIVER_TYPE.TEAM.getValue() + " and status <> " + MESSAGE_STATUS.DELETED.getValue()
		 + " and receiverId = ? and triggerId = ? and remindTime > ? and remindTime < ?";
		return getHibernateTemplate().find(hql, teamId, triggerId, startTimestamp, endTimestamp);
	}
	
	/**
	 * @param id
	 * @param weather
	 * @return
	 */
	public List<Message> findWaringTriggerMessage(Long teamId, Long triggerId) {
		String hql = "from Message where receiverType = " + MESSAGE_RECEIVER_TYPE.TEAM.getValue() + " and status <> " + MESSAGE_STATUS.DELETED.getValue()
		 + " and receiverId = ? and triggerId = ?";
		return getHibernateTemplate().find(hql, teamId, triggerId);
	}

	/**
	 * @param dto
	 * @return
	 */
	public int getTriggerTotalNum(final SearchMessageDTO dto) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
			Criteria cr = buildTriggerSearchCriteria(session, dto);
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
	public List<Message> findTriggerMessages(final SearchMessageDTO dto,
			final PageInfoDTO pageInfo) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Message>>() {
			@Override
			public List<Message> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = buildTriggerSearchCriteria(session, dto);
				int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.ADMIN_DEFAULT_PAGE_SIZE;
				cr.setMaxResults(maxResults);
				cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
				cr.addOrder(Order.asc("status"));
				cr.addOrder(Order.desc("remindTime"));
				return cr.list();
			}
		});	
	}
	
	private Criteria buildTriggerSearchCriteria(Session session, SearchMessageDTO dto) {
		Criteria cr = session.createCriteria(Message.class);
		cr.createAlias("travelInf", "t");
		if (dto.getTravelId() != null) {
			cr.add(Restrictions.eq("t.id", dto.getTravelId()));
		}
		cr.add(Restrictions.eq("triggerId", dto.getTriggerId()));
		cr.add(Restrictions.ne("status", Integer.valueOf(MESSAGE_STATUS.DELETED.getValue())));
		cr.add(Restrictions.ne("receiverType", Integer.valueOf(MESSAGE_RECEIVER_TYPE.VIEW_SPOT.getValue())));
		cr.add(Restrictions.ne("receiverType", Integer.valueOf(MESSAGE_RECEIVER_TYPE.ITEM.getValue())));
		return cr;
	}

	/**
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Message> getLastHourMessage(MESSAGE_RECEIVER_TYPE receiverType, Long receiverId, Long triggerId) {
		try {
			String query = "from Message m where m.receiverType = ? and m.receiverId = ? "
			 + " and m.triggerId = " + triggerId + " and m.status <>" + MESSAGE_STATUS.DELETED.getValue() 
			 + " and now() - m.remindTime > 0 and now() - m.remindTime < 3600";
			return getHibernateTemplate().find(query, receiverType.getValue(), receiverId);
		} catch (RuntimeException re) {
			log.error("getLastHourMessage failed", re);
			throw re;
		}
	}

	/**
	 * @param viewSpot
	 * @param id
	 * @return
	 */
	public Double caculateScore(final MESSAGE_RECEIVER_TYPE type, final Long receiverId) {
		return getHibernateTemplate().execute(new HibernateCallback<Double>() {
			@SuppressWarnings("unchecked")
			@Override
			public Double doInHibernate(Session session) throws HibernateException,
					SQLException {
				SQLQuery query = session.createSQLQuery("select avg(if(isnull(score),0,score)) as score from message where receiver_type = "+type.getValue()+" and receiver_id = ? and status <> " + MESSAGE_STATUS.DELETED.getValue());
				query.setLong(0, receiverId);
				List list = query.list();
				if(list.size() > 0){
					if(list.get(0) == null){
						return 0D;
					}
					return Double.valueOf(list.get(0).toString());
				} else {
					return 0D;
				}
			}
		});	
	}
}