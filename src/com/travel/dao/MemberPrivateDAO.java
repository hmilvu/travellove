package com.travel.dao;
// default package

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.travel.common.Constants.MEMBER_STATUS;
import com.travel.common.Constants.VISIBLE_TYPE;
import com.travel.common.Constants.VISIBLITY;
import com.travel.entity.MemberPrivate;

/**
 	* A data access object (DAO) providing persistence and search support for MemberPrivate entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .MemberPrivate
  * @author MyEclipse Persistence Tools 
 */
@Repository
public class MemberPrivateDAO extends BaseDAO  {
	     private static final Logger log = LoggerFactory.getLogger(MemberPrivateDAO.class);
		//property constants
	public static final String TYPE = "type";
	public static final String VISIBILITY = "visibility";
    
    public void save(MemberPrivate transientInstance) {
        log.debug("saving MemberPrivate instance");
        try {
            getHibernateTemplate().save(transientInstance);
            getHibernateTemplate().flush();
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MemberPrivate persistentInstance) {
        log.debug("deleting MemberPrivate instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
        	getHibernateTemplate().flush();
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

	/**
	 * @param memberId
	 */
	public void deleteByMemberId(final Long memberId, final int type) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				String sql = "delete from member_private where member_id = ? and type = ?";
				Query queryObject = session.createSQLQuery(sql);
				queryObject.setLong(0, memberId);
				queryObject.setInteger(1, type);
				queryObject.executeUpdate();
				return null;
			}
		});	
		
	}
	
	/**
	 * @param travelId
	 */
//	public void initMemberPrivate(final Long teamId, final Long memberId, final VISIBLE_TYPE type) {
//		getHibernateTemplate().execute(new HibernateCallback<Object>() {
//			@Override
//			public Object doInHibernate(Session session) throws HibernateException,
//					SQLException {
//				String sql = "insert into member_private (type, member_id, visible_member_id, visibility) " +
//							 "values(select ?, ?, a.id, ? from " +
//								     "(select m.* from member_inf m where m.team_id = ?)a " +
//									 "left join " +
//									 "(select p.* from member_private p where p.member_id = ?)b " +
//									 "on a.id = b.visible_member_id " +
//									 "where b.id is null" ;
//				SQLQuery query = session.createSQLQuery(sql);
//				query.setParameter(0, type.getValue());
//				query.setParameter(1, memberId);
//				query.setParameter(2, VISIBLITY.VISIBLE.getValue());
//				query.setParameter(3, teamId);
//				query.setParameter(4, memberId);
//				query.executeUpdate();
//				return null;
//			}
//		});	
//	}
	

	/**
	 * @param memberId
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> getVisibilityByType(Long memberId, int type) {
		List<Long> list = getHibernateTemplate().find("select m.memberInfByVisibleMemberId.id from MemberPrivate m where m.memberInfByMemberId.id = ? and m.type = ? and m.visibility = " + VISIBLITY.INVISIBLE.getValue(), memberId, type);
		return list;
	}
    
}