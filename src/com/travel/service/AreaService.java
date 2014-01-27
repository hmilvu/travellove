package com.travel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.AreaDAO;
import com.travel.entity.AreaInf;

@Service
public class AreaService extends AbstractBaseService
{
	@Autowired
	private AreaDAO areaDao;

	/**
	 * @return
	 */
	public List<AreaInf> getAllProvinces() {
		return areaDao.findAllProvinces();
	}

	/**
	 * @param cityCode
	 * @return
	 */
	public List<AreaInf> getSubCitiesByCode(String cityCode) {
		return areaDao.findSubCitiesByCode(cityCode, cityCode.substring(0, 2));
	}	
	
	
}
