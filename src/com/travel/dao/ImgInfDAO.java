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

import com.travel.common.Constants.IMAGE_TYPE;
import com.travel.entity.ImgInf;

/**
 * A data access object (DAO) providing persistence and search support for
 * ImgInf entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.travel.entity.ImgInf
 * @author MyEclipse Persistence Tools
 */
@Repository
public class ImgInfDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(ImgInfDAO.class);
	// property constants
	public static final String TYPE = "type";
	public static final String ASSOCIATE_ID = "associateId";
	public static final String IMG_NAME = "imgName";
	public static final String SUFFIX = "suffix";
	public static final String SIZE = "size";
	public static final String URL = "url";

	public int save(ImgInf transientInstance) {
		log.debug("saving ImgInf instance");
		try {
			getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		return 0;
	}
	
	public int saveOrUpdate(ImgInf transientInstance) {
		log.debug("saving ImgInf instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		return 0;
	}

	public void delete(ImgInf persistentInstance) {
		log.debug("deleting ImgInf instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			getHibernateTemplate().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ImgInf findById(java.lang.Long id) {
		log.debug("getting ImgInf instance with id: " + id);
		try {
			ImgInf instance = (ImgInf) getHibernateTemplate().get(
					"com.travel.entity.ImgInf", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * @param viewspot
	 * @param id
	 * @return
	 */
	public List<String> findUrls(IMAGE_TYPE viewspot, Long id) {
		try {
			String queryString = "select url from ImgInf as m where m.type = ? and m.associateId = ?";
			return getHibernateTemplate().find(queryString, viewspot.getValue(), id);			
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	/**
	 * @param viewspot
	 * @param id
	 * @return
	 */
	public List<ImgInf> findImgInf(IMAGE_TYPE type, Long associateId) {
		try {
			String queryString = "from ImgInf as m where m.type = ? and m.associateId = ?";
			return getHibernateTemplate().find(queryString, type.getValue(), associateId);			
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * @param type2
	 * @param associateId
	 * @param imgName
	 * @return
	 */
	public ImgInf findByName(IMAGE_TYPE type, Long associateId,
			String imgName) {
		try {
			String queryString = "from ImgInf as m where m.type = ? and m.associateId = ? and m.imgName = ?";
			List<ImgInf> list = getHibernateTemplate().find(queryString, type.getValue(), associateId, imgName);			
			if(list != null && list.size() > 0){
				return list.get(0);
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * @param type2
	 * @param associateId
	 * @param name
	 */
	public void deleteByName(final IMAGE_TYPE type, final String associateId, final String name) {
		getHibernateTemplate().execute(new HibernateCallback<Object>(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String sql = "delete from img_inf where type = "+type.getValue()+" and associate_id = "+associateId+" and img_name = " + name;
				Query queryObject = session.createSQLQuery(sql);
				queryObject.executeUpdate();
				return null;
			}});		
	}
}