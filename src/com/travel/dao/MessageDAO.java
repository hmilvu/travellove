package com.travel.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.travel.common.Constants;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.MemberInf;
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

	public void save(Message transientInstance) {
		log.debug("saving Message instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
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

	public List<Message> findByReceiverId(Object receiverId, PageInfoDTO pageInfo) {
		try {
			String queryString = "from Message as model where model.receiverId = ? order by model.remindTime desc";
			Query queryObject = getSession().createQuery(queryString);
			int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.DEFAULT_PAGE_SIZE;
			queryObject.setFirstResult(pageInfo.getPageNumber() * maxResults);
			queryObject.setParameter(0, receiverId);
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
	public List<Object[]> findRepliesByMessageId(Long messageId,
			PageInfoDTO pageInfo) {
		try {
			String queryString = "select r, r.memberInf, r.message from Reply r where r.message.id = ? order by r.createDate desc";
			Query queryObject = getSession().createQuery(queryString);
			int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.DEFAULT_PAGE_SIZE;
			queryObject.setFirstResult(pageInfo.getPageNumber() * maxResults);
			queryObject.setParameter(0, messageId);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by receiverId failed", re);
			throw re;
		}
	}

}