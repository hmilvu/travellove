package com.travel.dao;

import java.sql.SQLException;
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
import com.travel.common.Constants.MEMBER_STATUS;
import com.travel.common.admin.dto.SearchMemberDTO;
import com.travel.common.dto.PageInfoDTO;
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

	public int save(MemberInf transientInstance) {
		log.debug("saving MemberInf instance");
		int result = 0;
		try {
			getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			result = -1;
			throw re;
		}
		return result;
	}

	public void delete(MemberInf persistentInstance) {
		log.debug("deleting MemberInf instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MemberInf findById(java.lang.Long id) {
		log.debug("getting MemberInf instance with id: " + id);
		try {
			MemberInf instance = (MemberInf) getHibernateTemplate().get(
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
			getHibernateTemplate().update(member);
			getHibernateTemplate().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			result = -1;
		}
		return result;
	}

	/**
	 * @param username
	 * @param password2
	 * @return
	 */
	public MemberInf findByCredentials(Long teamId, String mobile, String password) {
		try {
			String queryString = "from MemberInf as m where m.teamInfo.id = ? and m.travelerMobile = ? and m.password = ?";
			List<MemberInf> list = getHibernateTemplate().find(queryString, teamId, mobile, password);
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
	 * @param dto
	 * @return
	 */
	public int getTotalNum(final SearchMemberDTO dto) {
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
	private Criteria buildSearchCriteria(Session session, SearchMemberDTO dto) {
		Criteria cr = session.createCriteria(MemberInf.class);
		cr.createAlias("teamInfo", "t");
		cr.createAlias("sysUser", "s");
		if (StringUtils.isNotBlank(dto.getTeamName())) {
			cr.add(Restrictions.like("t.name", StringUtils.trim(dto.getTeamName()) + "%").ignoreCase());
		}
		if (StringUtils.isNotBlank(dto.getName())) {
			cr.add(Restrictions.like("memberName", StringUtils.trim(dto.getName()) + "%").ignoreCase());
		}
		if (StringUtils.isNotBlank(dto.getPhoneNumber())){
			cr.add(Restrictions.like("travelerMobile", StringUtils.trim(dto.getPhoneNumber()) + "%").ignoreCase());
		}
		if (StringUtils.isNotBlank(dto.getIdNumber())){
			cr.add(Restrictions.like("idNo", StringUtils.trim(dto.getIdNumber()) + "%").ignoreCase());
		}
		if(dto.getMemberType() != null){
			cr.add(Restrictions.eq("memberType", dto.getMemberType()));
		}
		if(dto.getTeamId() != null){
			cr.add(Restrictions.eq("t.id", dto.getTeamId()));
		}
		cr.add(Restrictions.eq("status", Integer.valueOf(MEMBER_STATUS.ACTIVE.getValue())));
		return cr;
	}

	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MemberInf> findMembers(final SearchMemberDTO dto, final PageInfoDTO pageInfo) {
		return getHibernateTemplate().execute(new HibernateCallback<List<MemberInf>>() {
			@Override
			public List<MemberInf> doInHibernate(Session session) throws HibernateException,
					SQLException {
			Criteria cr = buildSearchCriteria(session, dto);
			int maxResults = pageInfo.getPageSize() > 0 ? pageInfo.getPageSize() : Constants.ADMIN_DEFAULT_PAGE_SIZE;
			cr.setMaxResults(maxResults);
			cr.setFirstResult((pageInfo.getPageNumber()-1) * maxResults);
			cr.addOrder(Order.asc("t.name"));
			cr.addOrder(Order.asc("memberType"));
			cr.addOrder(Order.asc("memberName"));
			return cr.list();
			}
		});	
	}
	/**
	 * @param ids
	 */
	public void deleteByIds(final String ids) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				String sql = "update member_inf set status = " + MEMBER_STATUS.INACTIVE.getValue() + " where id in ("+ids+")";
				Query queryObject = session.createSQLQuery(sql);
				queryObject.executeUpdate();
				return null;
			}
		});	
	}

	/**
	 * @param idArray
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> findForQrCode(final List<Long> idArray) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException,
					SQLException {
			String queryString = "select id, memberName from MemberInf as m where id in (:ids)";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameterList("ids", idArray);
			return (List<Object[]>)queryObject.list();
			}
		});	
	}

	/**
	 * @param idList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MemberInf> findByTeamIds(final List<Long> idList) {
		return getHibernateTemplate().execute(new HibernateCallback<List<MemberInf>>() {
			@Override
			public List<MemberInf> doInHibernate(Session session) throws HibernateException,
					SQLException {
				String queryString = "from MemberInf as m where m.teamInfo.id in (:ids)";
				Query queryObject = session.createQuery(queryString);
				queryObject.setParameterList("ids", idList);
				return queryObject.list();
			}
		});	
	}

	/**
	 * @param idList
	 * @return
	 */
	public List<MemberInf> findByIds(final List<Long> idList) {
		return getHibernateTemplate().execute(new HibernateCallback<List<MemberInf>>() {
			@Override
			public List<MemberInf> doInHibernate(Session session) throws HibernateException,
					SQLException {
				String queryString = "from MemberInf as m where m.id in (:ids)";
				Query queryObject = session.createQuery(queryString);
				queryObject.setParameterList("ids", idList);
				return queryObject.list();
			}
		});	
	}
}