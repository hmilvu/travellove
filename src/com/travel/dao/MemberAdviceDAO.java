package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.travel.entity.MemberAdvice;

/**
 * A data access object (DAO) providing persistence and search support for
 * MemberAdvice entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.travel.entity.MemberAdvice
 * @author MyEclipse Persistence Tools
 */
@Repository
public class MemberAdviceDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(MemberAdviceDAO.class);
	// property constants
	public static final String TOPIC = "topic";
	public static final String CONTENT = "content";

	public void save(MemberAdvice transientInstance) {
		log.debug("saving MemberAdvice instance");
		try {
			getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
}