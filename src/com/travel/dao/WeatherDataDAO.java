package com.travel.dao;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.travel.entity.WeatherData;

/**
 * A data access object (DAO) providing persistence and search support for
 * WeatherData entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.travel.entity.WeatherData
 * @author MyEclipse Persistence Tools
 */
@Repository
public class WeatherDataDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(WeatherDataDAO.class);
	// property constants
	public static final String CITY_NAME = "cityName";
	public static final String DATA1 = "data1";
	public static final String DATA2 = "data2";

	public void saveOrUpdate(WeatherData transientInstance) {
		log.debug("saving WeatherData instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			getHibernateTemplate().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public WeatherData seachByCityName(String cityName){
		try {
			List<WeatherData> list = getHibernateTemplate().find("from WeatherData where cityName = ? order by createDate desc", cityName);
			if(list != null && list.size() > 0){
				return list.get(0);
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			log.error("seachByCityName", re);
			throw re;
		}
	}

}