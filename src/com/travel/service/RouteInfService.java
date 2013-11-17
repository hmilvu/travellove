package com.travel.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.RouteInfDAO;
import com.travel.dao.RouteViewSpotDAO;
import com.travel.entity.RouteInf;
import com.travel.entity.RouteViewSpot;

@Service
public class RouteInfService
{
	@Autowired
	private RouteInfDAO routeInfDAO;
	@Autowired
	private RouteViewSpotDAO routViewSpotDAO;
	
	public RouteInf getRouteInfById(Long id){
		return routeInfDAO.findById(id);
	}


	/**
	 * @param ids
	 * @return
	 */
	public List<RouteViewSpot> findRouteViewSpotByViewSpotIds(String viewSpotIds) {
		String []ids = StringUtils.split(viewSpotIds, ",");
		List<Long> idList = new ArrayList<Long>();
		for(String id : ids){
			idList.add(Long.valueOf(id));
		}
		return routViewSpotDAO.findByViewSpotIds(idList);
	}
	
}
