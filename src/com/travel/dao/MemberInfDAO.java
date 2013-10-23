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

	public List<MemberInf> findByExample(MemberInf instance) {
		log.debug("finding MemberInf instance by example");
		try {
			List<MemberInf> results = (List<MemberInf>) getSession()
					.createCriteria("com.travel.entity.MemberInf").add(
							create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding MemberInf instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from MemberInf as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<MemberInf> findByMemberName(Object memberName) {
		return findByProperty(MEMBER_NAME, memberName);
	}

	public List<MemberInf> findByNickname(Object nickname) {
		return findByProperty(NICKNAME, nickname);
	}

	public List<MemberInf> findByTravelerMobile(Object travelerMobile) {
		return findByProperty(TRAVELER_MOBILE, travelerMobile);
	}

	public List<MemberInf> findByMemberType(Object memberType) {
		return findByProperty(MEMBER_TYPE, memberType);
	}

	public List<MemberInf> findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List<MemberInf> findBySex(Object sex) {
		return findByProperty(SEX, sex);
	}

	public List<MemberInf> findByAge(Object age) {
		return findByProperty(AGE, age);
	}

	public List<MemberInf> findByIdType(Object idType) {
		return findByProperty(ID_TYPE, idType);
	}

	public List<MemberInf> findByIdNo(Object idNo) {
		return findByProperty(ID_NO, idNo);
	}

	public List<MemberInf> findByAvatarUrl(Object avatarUrl) {
		return findByProperty(AVATAR_URL, avatarUrl);
	}

	public List<MemberInf> findByProfile(Object profile) {
		return findByProperty(PROFILE, profile);
	}

	public List<MemberInf> findByInterest(Object interest) {
		return findByProperty(INTEREST, interest);
	}

	public List findAll() {
		log.debug("finding all MemberInf instances");
		try {
			String queryString = "from MemberInf";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MemberInf merge(MemberInf detachedInstance) {
		log.debug("merging MemberInf instance");
		try {
			MemberInf result = (MemberInf) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MemberInf instance) {
		log.debug("attaching dirty MemberInf instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MemberInf instance) {
		log.debug("attaching clean MemberInf instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}