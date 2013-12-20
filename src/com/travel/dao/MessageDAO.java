package com.travel.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.travel.common.Constants;
import com.travel.common.Constants.MESSAGE_RECEIVER_TYPE;
import com.travel.common.Constants.MESSAGE_STATUS;
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
			Long id = (Long)getSession().save(transientInstance);
			getSession().flush();
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
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Message findById(java.lang.Long id) {
		log.debug("getting Message instance with id: " + id);
		try {
			Message instance = (Message) getSession().get(
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
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Message> findByReceiverId(Long memberId, Long teamId, PageInfoDTO pageInfo) {
		try {
			String queryString = "from Message as m where (m.receiverId = ? and m.receiverType = "+MESSAGE_RECEIVER_TYPE.MEMBER.getValue()+") " +
					" or (m.receiverId = ?  and m.receiverType = "+MESSAGE_RECEIVER_TYPE.TEAM.getValue()+") order by m.priority asc, m.remindTime desc";
			Query queryObject = getSession().createQuery(queryString);
			int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.DEFAULT_PAGE_SIZE;
			queryObject.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
			queryObject.setParameter(0, memberId);
			queryObject.setParameter(1, teamId);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by receiverId failed", re);
			throw re;
		}
	}

	/**
	 * @param messageId
	 * @param pageInfo
	 * @return
	 */
	public List<Reply> findRepliesByMessageId(Long messageId,
			PageInfoDTO pageInfo) {
		try {
			Criteria cr = getSession().createCriteria(Reply.class).setFetchMode("memberInf",FetchMode.JOIN).setFetchMode("sysUser", FetchMode.JOIN);
			cr.add(Restrictions.eq("message.id", messageId));
			int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.DEFAULT_PAGE_SIZE;
			cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
			cr.setMaxResults(maxResults);
			cr.addOrder(Order.desc("createDate"));
			return cr.list();
		} catch (RuntimeException re) {
			log.error("find by receiverId failed", re);
			throw re;
		}
	}

	/**
	 * @param dto
	 * @return
	 */
	public int getTotalNum(SearchMessageDTO dto) {
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
	private Criteria buildSearchCriteria(SearchMessageDTO dto) {
		Criteria cr = getSession().createCriteria(Message.class);
		cr.createAlias("travelInf", "t");
		if (dto.getTravelId() != null) {
			cr.add(Restrictions.eq("t.id", dto.getTravelId()));
		}
		if (!StringUtils.isBlank(dto.getTeamName())) {
			cr.add(Restrictions.like("name", StringUtils.trim(dto.getTeamName()) + "%").ignoreCase());
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
		return cr;
	}

	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Message> findMessages(SearchMessageDTO dto, PageInfoDTO pageInfo) {
		try {
			Criteria cr = buildSearchCriteria(dto);
			int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.ADMIN_DEFAULT_PAGE_SIZE;
			cr.setMaxResults(maxResults);
			cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
			cr.addOrder(Order.asc("status"));
			cr.addOrder(Order.desc("t.id"));
			cr.addOrder(Order.desc("updateDate"));
			return cr.list();
		} catch (RuntimeException re) {
			log.error("find teams failed", re);
			throw re;
		}
	}

	/**
	 * @param idList
	 */
	public void deleteByIds(String ids) {
		try {
			String sql = "update message set status = "+MESSAGE_STATUS.DELETED.getValue()+" where id in ("+ids+")";
			Query queryObject = getSession().createSQLQuery(sql);
			queryObject.executeUpdate();
		} catch (RuntimeException re) {
			log.error("find by credentials failed", re);
			throw re;
		}
		
	}

	/**
	 * @param id
	 * @return
	 */
	public int getTotalReplyNum(Long id) {
		try {
			Criteria cr = buildReplyCriteria(id);
			Long total=(Long)cr.setProjection(Projections.rowCount()).uniqueResult(); 			
			return  total.intValue();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	/**
	 * @param messageId
	 * @param pageInfo
	 * @return
	 */
	public List<Reply> findAdminRepliesByMessageId(Long messageId,
			PageInfoDTO pageInfo) {
		try {
			Criteria cr = buildReplyCriteria(messageId);
			int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.DEFAULT_PAGE_SIZE;
			cr.setMaxResults(maxResults);
			cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
			cr.addOrder(Order.desc("createDate"));
			return cr.list();
		} catch (RuntimeException re) {
			log.error("find by receiverId failed", re);
			throw re;
		}
	}

	/**
	 * @param messageId
	 * @return
	 */
	private Criteria buildReplyCriteria(Long messageId) {
		Criteria cr = getSession().createCriteria(Reply.class).setFetchMode("memberInf",FetchMode.JOIN).setFetchMode("sysUser", FetchMode.JOIN);
		cr.add(Restrictions.eq("message.id", messageId));
		return cr;
	}

}