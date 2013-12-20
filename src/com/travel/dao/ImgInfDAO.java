package com.travel.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	public void save(ImgInf transientInstance) {
		log.debug("saving ImgInf instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ImgInf persistentInstance) {
		log.debug("deleting ImgInf instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ImgInf findById(java.lang.Long id) {
		log.debug("getting ImgInf instance with id: " + id);
		try {
			ImgInf instance = (ImgInf) getSession().get(
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
			Query queryObject = getSession().createQuery(queryString);			
			queryObject.setParameter(0, viewspot.getValue());
			queryObject.setParameter(1, id);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}