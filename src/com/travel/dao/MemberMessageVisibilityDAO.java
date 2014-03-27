package com.travel.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.travel.entity.MemberMessageVisibility;

/**
 * A data access object (DAO) providing persistence and search support for
 * MemberMessageVisibility entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.travel.entity.MemberMessageVisibility
 * @author MyEclipse Persistence Tools
 */
@Repository
public class MemberMessageVisibilityDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(MemberMessageVisibilityDAO.class);

	// property constants

	public void save(MemberMessageVisibility transientInstance) {
		log.debug("saving MemberMessageVisibility instance");
		try {
			getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/**
	 * @param memberId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> getVisibleIdList(Long memberId) {
		List<Long> list = getHibernateTemplate().find("select m.message.id from MemberMessageVisibility m where m.memberInf.id = ?", memberId);
		return list;
	}

	/**
	 * @param memberId
	 * @param msgId
	 */
	public void deleteVisible(final Long memberId, final Long msgId) {
		getHibernateTemplate().execute(new HibernateCallback<Object>(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String sql = "delete from member_message_visibility where member_id = ? and message_id = ?";
				Query queryObject = session.createSQLQuery(sql);
				queryObject.setLong(0, memberId);
				queryObject.setLong(1, msgId);
				queryObject.executeUpdate();
				return null;
			}});	
		
	}

	
}