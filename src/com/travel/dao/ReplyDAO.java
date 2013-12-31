package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.travel.common.Constants.MESSAGE_STATUS;
import com.travel.entity.MemberInf;
import com.travel.entity.Reply;

/**
 * A data access object (DAO) providing persistence and search support for Reply
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.travel.entity.Reply
 * @author MyEclipse Persistence Tools
 */
@Repository
public class ReplyDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(ReplyDAO.class);
	// property constants
	public static final String CONTENT = "content";

	public int save(Reply transientInstance) {
		log.debug("saving Reply instance");
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

	public void delete(Reply persistentInstance) {
		log.debug("deleting Reply instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Reply findById(java.lang.Long id) {
		log.debug("getting Reply instance with id: " + id);
		try {
			Reply instance = (Reply) getHibernateTemplate().get(
					"com.travel.entity.Reply", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * @param idList
	 */
	public void deleteByIds(final String ids) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				String sql = "delete from reply where id in ("+ids+")";
				Query queryObject = session.createSQLQuery(sql);
				queryObject.executeUpdate();
				return null;
			}
		});	
	}
}