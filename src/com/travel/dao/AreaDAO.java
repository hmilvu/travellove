package com.travel.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.travel.entity.AreaInf;

/**
 * A data access object (DAO) providing persistence and search support for Order
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.travel.entity.Order
 * @author MyEclipse Persistence Tools
 */
@Repository
public class AreaDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(AreaDAO.class);
	// property constants
	
	/**
	 * @param idList
	 * @return
	 */
	public List<AreaInf> findAllProvinces() {
		return getHibernateTemplate().execute(new HibernateCallback<List<AreaInf>>() {
			@Override
			public List<AreaInf> doInHibernate(Session session) throws HibernateException,
					SQLException {
				String queryString = "from AreaInf where cityCode like '%0000' order by cityCode";
				Query queryObject = session.createQuery(queryString);
				return queryObject.list();
		}
		});	
	}

	/**
	 * @param code
	 * @return
	 */
	public List<AreaInf> findSubCitiesByCode(final String selfCode, final String code) {
		return getHibernateTemplate().execute(new HibernateCallback<List<AreaInf>>() {
			@Override
			public List<AreaInf> doInHibernate(Session session) throws HibernateException,
					SQLException {
				String queryString = "from AreaInf where cityCode <> "+selfCode+" and cityCode like '"+code+"%' order by cityCode";
				Query queryObject = session.createQuery(queryString);
				return queryObject.list();
		}
		});	
	}

	/**
	 * @param city
	 * @return
	 */
	public AreaInf getByCode(final String cityCode) {
		return getHibernateTemplate().execute(new HibernateCallback<AreaInf>() {
			@Override
			public AreaInf doInHibernate(Session session) throws HibernateException,
					SQLException {
				String queryString = "from AreaInf where cityCode = ?";
				Query queryObject = session.createQuery(queryString);
				queryObject.setParameter(0, cityCode);
				List<AreaInf> list = queryObject.list();
				if(list.size() > 0){
					return list.get(0);
				} else {
					return new AreaInf();
				}
		}
		});	
	}

}