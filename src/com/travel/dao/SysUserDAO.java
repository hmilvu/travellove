package com.travel.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.travel.common.Constants;
import com.travel.common.Constants.SYS_USER_STATUS;
import com.travel.common.Constants.SYS_USER_TYPE;
import com.travel.common.admin.dto.SearchSysUserDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.MemberInf;
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

	public Long save(SysUser transientInstance) {
		log.debug("saving SysUser instance");
		try {
			Long id = (Long) getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().flush();
			log.debug("save successful");
			return id;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SysUser persistentInstance) {
		log.debug("deleting SysUser instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysUser findById(java.lang.Long id) {
		log.debug("getting SysUser instance with id: " + id);
		try {
			SysUser instance = (SysUser) getHibernateTemplate().get(
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
			return getHibernateTemplate().find(queryString, value);
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
		try {
			String queryString = "from SysUser as model where model.travelInf.id = ?";
			return getHibernateTemplate().find(queryString, travelId);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
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
			return getHibernateTemplate().find(queryString);
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
			List<SysUser> list = getHibernateTemplate().find(queryString, username, password);
			if(list != null && list.size() > 0){
				return list.get(0);
			} else {
				return null;
			}
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
	public List<MenuInf> findMenuRoles(final List<RoleInf> roleList) {
		if(roleList == null || roleList.size() == 0){
			return new ArrayList<MenuInf>();
		}
		return getHibernateTemplate().execute(new HibernateCallback<List<MenuInf>>() {
			@Override
			public List<MenuInf> doInHibernate(Session session) throws HibernateException,
					SQLException {
				List<Long>roleIds = new ArrayList<Long>();
				for(RoleInf role : roleList){
					roleIds.add(role.getId());
				}
				String queryString = "select distinct r.menuInf from RoleMenu as r where r.roleInf.id in (:roleIds)";
				Query queryObject = session.createQuery(queryString);
				queryObject.setParameterList("roleIds", roleIds);
				return (List<MenuInf>)queryObject.list();
			}
		});	
	}

	/**
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RoleInf> getRolesByUser(SysUser user) {
		try {
			String queryString = "select r.roleInf from UserRole as r where r.sysUser.id = ?";
			return getHibernateTemplate().find(queryString, user.getId());
		} catch (RuntimeException re) {
			log.error("find by credentials failed", re);
			throw re;
		}
	}

	/**
	 * @param dto
	 * @return
	 */
	public int getTotalNum(final SearchSysUserDTO dto) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = buildSearchCriteria(session, dto);
				Long total=(Long)cr.setProjection(Projections.rowCount()).uniqueResult(); 			
				return  total.intValue();
			}
		});	
	}

	/**
	 * @param dto
	 * @return
	 */
	private Criteria buildSearchCriteria(Session session, SearchSysUserDTO dto) {
		Criteria cr = session.createCriteria(SysUser.class);
		if (!StringUtils.isBlank(dto.getTravelName())) {
			cr.createAlias("travelInf", "c");
			cr.add(Restrictions.like("c.name", StringUtils.trim(dto.getTravelName()) + "%").ignoreCase());
		}
		if (!StringUtils.isBlank(dto.getName())) {
			cr.add(Restrictions.like("name", StringUtils.trim(dto.getName()) + "%").ignoreCase());
		}
		if (dto.getUserType() >= 0){
			cr.add(Restrictions.eq("userType", Integer.valueOf(dto.getUserType())));
		}
		if (!StringUtils.isBlank(dto.getUsername())) {
			cr.add(Restrictions.like("username", StringUtils.trim(dto.getUsername()) + "%").ignoreCase());
		}
		if(dto.getTravelId() != null){
			cr.createAlias("travelInf", "c");
			cr.add(Restrictions.eq("c.id", dto.getTravelId())); 
		}
//		if(dto.getCurrentUserType() == SYS_USER_TYPE.SYSTEM_USER.getValue()){
//			cr.add(Restrictions.gt("userType", SYS_USER_TYPE.SUPER_ADMIN.getValue()));
//		} else if(dto.getCurrentUserType() == SYS_USER_TYPE.TRAVEL_USER.getValue()){
//			cr.add(Restrictions.eq("userType", SYS_USER_TYPE.TRAVEL_USER.getValue()));
//			cr.add(Restrictions.eq("travelInf.id", dto.getTravelId()));
//		}
		cr.add(Restrictions.ne("status", Integer.valueOf(SYS_USER_STATUS.INVALID.getValue())));
		return cr;
	}

	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	public List<SysUser> findBySearchCriteria(final SearchSysUserDTO dto,
			final PageInfoDTO pageInfo) {
		return getHibernateTemplate().execute(new HibernateCallback< List<SysUser>>() {
			@Override
			public  List<SysUser> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cr = buildSearchCriteria(session, dto);
				int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.ADMIN_DEFAULT_PAGE_SIZE;
				cr.setMaxResults(maxResults);
				cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
				cr.addOrder(Order.desc("userType"));
				cr.addOrder(Order.desc("travelInf.id"));
				cr.addOrder(Order.desc("username"));
				return cr.list();
			}
		});	
	}

	/**
	 * @param user
	 * @return
	 */
	public int update(SysUser user) {
		int result = 0;
		try {
			getHibernateTemplate().update(user);
			getHibernateTemplate().flush();
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			result = -1;
			throw re;
		}
		return result;
	}
}