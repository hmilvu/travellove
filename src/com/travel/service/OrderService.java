package com.travel.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.OrderDAO;
import com.travel.entity.Order;

@Service
public class OrderService extends AbstractBaseService
{
	@Autowired
	private OrderDAO orderDao;

	/**
	 * @param ids
	 * @return
	 */
	public List<Order> getOrderByItemIds(String ids) {
		String []idArray = StringUtils.split(ids, ",");
		List <Long>idList = new ArrayList<Long>();
		for(String id : idArray){
			idList.add(Long.valueOf(id));
		}
		return orderDao.findByItemIds(idList);
		
	}	
	
	
}
