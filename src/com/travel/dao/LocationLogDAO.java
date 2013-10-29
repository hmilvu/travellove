package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.travel.common.Constants;
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
			getSession().save(transientInstance);
			getSession().flush();
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
			getSession().update(transientInstance);
			getSession().flush();
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
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public LocationLog findById(java.lang.Long id) {
		log.debug("getting LocationLog instance with id: " + id);
		try {
			LocationLog instance = (LocationLog) getSession().get(
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
	public List<LocationLog> findByTeamId(Long teamId) {
		try {
			String queryString = "from LocationLog as lo where lo.teamInfo.id = ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, teamId);
			return queryObject.list();
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
	public LocationLog getLocationByMember(Long teamId, Long memberId) {
		try {
			String queryString = "from LocationLog as lo where lo.teamInfo.id = ? and lo.memberInf.id = ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, teamId);
			queryObject.setParameter(1, memberId);
			List <LocationLog> list = queryObject.list();
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
}