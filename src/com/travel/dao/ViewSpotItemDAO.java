package com.travel.dao;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.travel.entity.ViewSpotItem;

/**
 * A data access object (DAO) providing persistence and search support for
 * ViewSpotInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.travel.entity.ViewSpotInfo
 * @author MyEclipse Persistence Tools
 */
@Repository
public class ViewSpotItemDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ViewSpotItemDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String LONGITUDE = "longitude";
	public static final String LATITUDE = "latitude";
	public static final String DESCRIPTION = "description";

	public int save(ViewSpotItem transientInstance) {
		log.debug("saving ViewSpotItem instance");
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
	
	/**
	 * @param travelId
	 * @param viewSpotId
	 */
	public void deleteViewSpotItems(final Long travelId, final Long viewSpotId) {
		getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
			String sql = "delete from view_spot_item where travel_id = " + travelId + " and view_spot_id = " + viewSpotId;
			Query queryObject = session.createSQLQuery(sql);
			return queryObject.executeUpdate();
		}
	});	
		
	}

}