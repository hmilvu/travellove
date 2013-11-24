package com.travel.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.action.admin.form.ViewSpotItemForm;
import com.travel.common.admin.dto.SearchRouteDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.dao.RouteInfDAO;
import com.travel.dao.RouteViewSpotDAO;
import com.travel.entity.RouteInf;
import com.travel.entity.RouteViewSpot;
import com.travel.entity.ViewSpotInfo;

@Service
public class RouteInfService
{
	@Autowired
	private RouteInfDAO routeInfDAO;
	@Autowired
	private RouteViewSpotDAO routeViewSpotDAO;
	
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
		return routeViewSpotDAO.findByViewSpotIds(idList);
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
	public int addRoute(RouteInf route, List<ViewSpotItemForm> viewSpotItems) {
		route.setCreateDate(new Timestamp(new Date().getTime()));
		route.setUpdateDate(route.getCreateDate());
		Long id = routeInfDAO.save(route);
		if(id != null && id > 0){
			route.setId(id);
			if(viewSpotItems != null){
				for(ViewSpotItemForm viewForm : viewSpotItems){
					RouteViewSpot rView = new RouteViewSpot();
					rView.setRouteInf(route);
					ViewSpotInfo view = new ViewSpotInfo();
					view.setId(viewForm.getViewSpotForm().getId());
					rView.setViewSpotInfo(view);
					rView.setOrder(viewForm.getOrder());
					rView.setCreateDate(route.getCreateDate());
					rView.setUpdateDate(route.getCreateDate());
					rView.setSysUser(route.getSysUser());
					routeViewSpotDAO.save(rView);
				}
			}
			return 0;
		} else {
			return -1;
		}
	}


	/**
	 * @param ids
	 */
	public void deleteRouteByIds(String ids) {
		routeViewSpotDAO.deleteByRouteIds(ids);
		routeInfDAO.deleteByRouteIds(ids);
	}


	/**
	 * @param route
	 * @return
	 */
	public int updateRoute(RouteInf route, List<ViewSpotItemForm> viewSpotItems) {
		route.setUpdateDate(new Timestamp(new Date().getTime()));
		if(routeInfDAO.update(route) == 0){
			routeViewSpotDAO.deleteByRouteIds(route.getId() + "");
			if(viewSpotItems != null){
				for(ViewSpotItemForm viewForm : viewSpotItems){
					RouteViewSpot rView = new RouteViewSpot();
					rView.setRouteInf(route);
					ViewSpotInfo view = new ViewSpotInfo();
					view.setId(viewForm.getViewSpotForm().getId());
					rView.setViewSpotInfo(view);
					rView.setOrder(viewForm.getOrder());
					rView.setCreateDate(route.getCreateDate());
					rView.setUpdateDate(route.getCreateDate());
					rView.setSysUser(route.getSysUser());
					routeViewSpotDAO.save(rView);
				}
			}
			return 0;
		} else {
			return -1;
		}
	}


	/**
	 * @param id
	 * @return
	 */
	public List<RouteViewSpot> getRouteViewSpots(Long id) {
		List<Object[]> list = routeViewSpotDAO.findByRouteId(id);
		List<RouteViewSpot> realList = new ArrayList<RouteViewSpot>();
		for(Object[] obj : list){
			RouteViewSpot route = (RouteViewSpot)obj[0];
			ViewSpotInfo view = (ViewSpotInfo)obj[1];
			route.setViewSpotInfo(view);
			realList.add(route);
		}
		return realList;
	}
	
}
