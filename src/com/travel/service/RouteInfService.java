package com.travel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.RouteInfDAO;
import com.travel.entity.RouteInf;

@Service
public class RouteInfService
{
	@Autowired
	private RouteInfDAO routeInfDAO;
	
	
	public RouteInf getRouteInfById(Long id){
		return routeInfDAO.findById(id);
	}
	
}
