package com.travel.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.travel.entity.AttachmentInf;

/**
 * A data access object (DAO) providing persistence and search support for
 * AttachmentInf entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.travel.entity.AttachmentInf
 * @author MyEclipse Persistence Tools
 */
@Repository
public class AttachmentInfDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(AttachmentInfDAO.class);
	// property constants
	public static final String FILE_NAME = "fileName";
	public static final String FILE_URL = "fileUrl";

	public Long save(AttachmentInf transientInstance) {
		log.debug("saving AttachmentInf instance");
		Long id = 0L;
		try {
			id = (Long)getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().flush();
			log.debug("save successful");
			return id;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AttachmentInf persistentInstance) {
		log.debug("deleting AttachmentInf instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public void update(AttachmentInf persistentInstance) {
		log.debug("update AttachmentInf instance");
		try {
			getHibernateTemplate().update(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}
	
	public AttachmentInf findById(java.lang.Long id) {
		log.debug("getting AttachmentInf instance with id: " + id);
		try {
			AttachmentInf instance = (AttachmentInf) getSession().get(
					"com.travel.entity.AttachmentInf", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}