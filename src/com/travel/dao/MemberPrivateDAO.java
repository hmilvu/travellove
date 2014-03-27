package com.travel.dao;
// default package

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.travel.common.Constants.MEMBER_STATUS;
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
    
}