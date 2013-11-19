package com.travel.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.common.admin.dto.SearchRouteDTO;
import com.travel.common.dto.PageInfoDTO;
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


	/**
	 * @param dto
	 * @return
	 */
	public int getTotalRouteNum(SearchRouteDTO dto) {
		return routeInfDAO.getTotalNum(dto);
	}


	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	public List<RouteInf> findRoute(SearchRouteDTO dto, PageInfoDTO pageInfo) {
		return routeInfDAO.findRoutes(dto, pageInfo);
	}


	/**
	 * @param route
	 * @return
	 */
	public int addRoute(RouteInf route) {
		return routeInfDAO.save(route);
	}


	/**
	 * @param ids
	 */
	public void deleteRouteByIds(String ids) {
		routViewSpotDAO.deleteByRouteIds(ids);
		routeInfDAO.deleteByRouteIds(ids);
		
	}


	/**
	 * @param route
	 * @return
	 */
	public int updateRoute(RouteInf route) {
		return routeInfDAO.update(route);
	}
	
}
