package com.travel.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.travel.entity.MemberInf;

/**
 * A data access object (DAO) providing persistence and search support for
 * MemberInf entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.MemberInf
 * @author MyEclipse Persistence Tools
 */
@Repository
public class MemberInfDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(MemberInfDAO.class);
	// property constants
	public static final String MEMBER_NAME = "memberName";
	public static final String NICKNAME = "nickname";
	public static final String TRAVELER_MOBILE = "travelerMobile";
	public static final String MEMBER_TYPE = "memberType";
	public static final String PASSWORD = "password";
	public static final String SEX = "sex";
	public static final String AGE = "age";
	public static final String ID_TYPE = "idType";
	public static final String ID_NO = "idNo";
	public static final String AVATAR_URL = "avatarUrl";
	public static final String PROFILE = "profile";
	public static final String INTEREST = "interest";

	public void save(MemberInf transientInstance) {
		log.debug("saving MemberInf instance");
		try {
			getSession().save(transientInstance);
			getSession().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MemberInf persistentInstance) {
		log.debug("deleting MemberInf instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MemberInf findById(java.lang.Long id) {
		log.debug("getting MemberInf instance with id: " + id);
		try {
			MemberInf instance = (MemberInf) getSession().get(
					"com.travel.entity.MemberInf", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * @param member
	 * @return
	 */
	public int updateMember(MemberInf member) {
		int result = 0;
		log.debug("update MemberInf instance");
		try {
			getSession().update(member);
			getSession().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			result = -1;
		}
		return result;
	}
}