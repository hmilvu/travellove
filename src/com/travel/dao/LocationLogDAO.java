package com.travel.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.travel.common.Constants;
import com.travel.common.Constants.IS_NEW;
import com.travel.common.Constants.MEMBER_STATUS;
import com.travel.entity.LocationLog;

/**
 * A data access object (DAO) providing persistence and search support for
 * LocationLog entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.travel.entity.LocationLog
 * @author MyEclipse Persistence Tools
 */
@Repository
public class LocationLogDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(LocationLogDAO.class);
	
	public void save(LocationLog transientInstance) {
		log.debug("saving LocationLog instance");
		try {
			getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public int update(LocationLog transientInstance) {
		log.debug("update LocationLog instance");
		int result = 0;
		try {
			getHibernateTemplate().update(transientInstance);
			getHibernateTemplate().flush();
			log.debug("upate successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			result = -1;
		}
		return result;
	}

	public void delete(LocationLog persistentInstance) {
		log.debug("deleting LocationLog instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public LocationLog findById(java.lang.Long id) {
		log.debug("getting LocationLog instance with id: " + id);
		try {
			LocationLog instance = (LocationLog) getHibernateTemplate().get(
					"com.travel.entity.LocationLog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	/**
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LocationLog> findByTeamId(Long teamId) {
		try {
			String queryString = "from LocationLog as lo where lo.memberInf.status <> ? and lo.teamInfo.id = ? and isNew = ? order by lo.memberInf.memberType";
			return getHibernateTemplate().find(queryString, MEMBER_STATUS.INACTIVE.getValue(), teamId, Constants.IS_NEW.TRUE.getValue());			
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * @param id
	 * @param id2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public LocationLog getLocationByMember(Long teamId, Long memberId) {
		try {
			String queryString = "from LocationLog as lo where lo.isNew = ? and lo.memberInf.status <> ? and lo.teamInfo.id = ? and lo.memberInf.id = ? order by lo.createDate desc";
			List <LocationLog> list = getHibernateTemplate().find(queryString, IS_NEW.TRUE.getValue(), MEMBER_STATUS.INACTIVE.getValue(), teamId, memberId);
			if(list != null && list.size() > 0){
				return list.get(0);
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	/**
	 * @param id
	 * @param id2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LocationLog> getLocationListByMember(Long teamId, Long memberId) {
		try {
			String queryString = "from LocationLog as lo where lo.memberInf.status <> ? and lo.teamInfo.id = ? and lo.memberInf.id = ? order by lo.createDate desc";
			List <LocationLog> list = getHibernateTemplate().find(queryString, MEMBER_STATUS.INACTIVE.getValue(), teamId, memberId);
			return list;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * @param id
	 * @param id2
	 * @param date
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LocationLog> getLocationListByMember(Long teamId, Long memberId,
			Date date) {
		try {
			Timestamp startTimestamp = new Timestamp(date.getTime()); 
			Timestamp endTimestamp = new Timestamp(startTimestamp.getTime() + 3600 * 24 * 1000);
			String queryString = "from LocationLog as lo where lo.memberInf.status <> ? and lo.teamInfo.id = ? and lo.memberInf.id = ? and lo.createDate >= ? and lo.createDate < ? order by lo.createDate desc";
			List <LocationLog> list = getHibernateTemplate().find(queryString, MEMBER_STATUS.INACTIVE.getValue(), teamId, memberId, startTimestamp, endTimestamp);
			return list;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * @param id
	 * @param id2
	 */
	public void updateLocationLog(final Long teamId, final Long memberId) {
		getHibernateTemplate().execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				SQLQuery query = session.createSQLQuery("update location_log set is_new = ? where team_id = ? and member_id = ?");
				query.setInteger(0, Constants.IS_NEW.FALSE.getValue());
				query.setLong(1, teamId);
				query.setLong(2, memberId);
				return query.executeUpdate();
			}
		});		
	}

	/**
	 * @param valueOf
	 * @return
	 */
	public List<LocationLog> getLocationByTeamId(final Long teamId) {
		return getHibernateTemplate().execute(new HibernateCallback<List<LocationLog>>() {

			@Override
			public List<LocationLog> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = session.createCriteria(LocationLog.class);
				cr.createAlias("teamInfo", "t");
				cr.createAlias("memberInf", "m");
				cr.add(Restrictions.ne("m.status", MEMBER_STATUS.INACTIVE.getValue()));
				cr.add(Restrictions.isNotNull("latitude"));
				cr.add(Restrictions.isNotNull("longitude"));
				cr.add(Restrictions.eq("isNew", Constants.IS_NEW.TRUE.getValue()));
				cr.add(Restrictions.eq("t.id", teamId));
				cr.addOrder(Order.desc("createDate"));
				return cr.list();
			}
		});	
	}
}