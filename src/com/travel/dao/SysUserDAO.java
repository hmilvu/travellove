package com.travel.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.travel.entity.MenuInf;
import com.travel.entity.RoleInf;
import com.travel.entity.SysUser;

/**
 * A data access object (DAO) providing persistence and search support for
 * SysUser entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.SysUser
 * @author MyEclipse Persistence Tools
 */

@Repository
public class SysUserDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(SysUserDAO.class);
	// property constants
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String SUPER_ADMIN = "superAdmin";
	public static final String TRAVEL_ID = "travelId";
	public static final String TRAVEL_NAME = "travelName";
	public static final String STATUS = "status";
	public static final String NAME = "name";
	public static final String MOBILE = "mobile";
	public static final String EMAIL = "email";
	public static final String TEL_NUMBER = "telNumber";
	public static final String UPDATE_USER_ID = "updateUserId";

	public void save(SysUser transientInstance) {
		log.debug("saving SysUser instance");
		try {
			getSession().save(transientInstance);
			getSession().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SysUser persistentInstance) {
		log.debug("deleting SysUser instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysUser findById(java.lang.Long id) {
		log.debug("getting SysUser instance with id: " + id);
		try {
			SysUser instance = (SysUser) getSession().get(
					"com.travel.entity.SysUser", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}	

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding SysUser instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SysUser as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SysUser> findByUsername(Object username) {
		return findByProperty(USERNAME, username);
	}

	public List<SysUser> findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List<SysUser> findBySuperAdmin(Object superAdmin) {
		return findByProperty(SUPER_ADMIN, superAdmin);
	}

	public List<SysUser> findByTravelId(Object travelId) {
		return findByProperty(TRAVEL_ID, travelId);
	}

	public List<SysUser> findByTravelName(Object travelName) {
		return findByProperty(TRAVEL_NAME, travelName);
	}

	public List<SysUser> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List<SysUser> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<SysUser> findByMobile(Object mobile) {
		return findByProperty(MOBILE, mobile);
	}

	public List<SysUser> findByEmail(Object email) {
		return findByProperty(EMAIL, email);
	}

	public List<SysUser> findByTelNumber(Object telNumber) {
		return findByProperty(TEL_NUMBER, telNumber);
	}

	public List<SysUser> findByUpdateUserId(Object updateUserId) {
		return findByProperty(UPDATE_USER_ID, updateUserId);
	}

	public List findAll() {
		log.debug("finding all SysUser instances");
		try {
			String queryString = "from SysUser";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/**
	 * @param username2
	 * @param password2
	 * @return
	 */
	public SysUser findByCredentials(String username, String password) {
		try {
			String queryString = "from SysUser as m where m.username = ? and m.password = ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, username);
			queryObject.setParameter(1, password);
			return (SysUser)queryObject.uniqueResult();
		} catch (RuntimeException re) {
			log.error("find by credentials failed", re);
			throw re;
		}
	}

	/**
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MenuInf> findMenuRoles(List<RoleInf> roleList) {
		if(roleList == null || roleList.size() == 0){
			return new ArrayList<MenuInf>();
		}
		try {
			List<Long>roleIds = new ArrayList<Long>();
			for(RoleInf role : roleList){
				roleIds.add(role.getId());
			}
			String queryString = "select r.menuInf from RoleMenu as r where r.roleInf.id in (:roleIds)";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameterList("roleIds", roleIds);
			return (List<MenuInf>)queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by credentials failed", re);
			throw re;
		}
	}

	/**
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RoleInf> getRolesByUser(SysUser user) {
		try {
			String queryString = "select r.roleInf from UserRole as r where r.sysUser.id = ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, user.getId());
			return (List<RoleInf>)queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by credentials failed", re);
			throw re;
		}
	}
}