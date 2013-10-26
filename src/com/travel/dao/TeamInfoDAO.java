package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travel.entity.TeamInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * TeamInfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.TeamInfo
 * @author MyEclipse Persistence Tools
 */

public class TeamInfoDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TeamInfoDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String STATUS = "status";
	public static final String PEOPLE_COUNT = "peopleCount";
	public static final String DESCRIPTION = "description";

	public void save(TeamInfo transientInstance) {
		log.debug("saving TeamInfo instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TeamInfo persistentInstance) {
		log.debug("deleting TeamInfo instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TeamInfo findById(java.lang.Long id) {
		log.debug("getting TeamInfo instance with id: " + id);
		try {
			TeamInfo instance = (TeamInfo) getSession().get(
					"com.travel.entity.TeamInfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}	
}