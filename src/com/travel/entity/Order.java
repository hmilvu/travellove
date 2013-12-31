package com.travel.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.travel.common.dto.OrderDTO;
import com.travel.utils.DateUtils;

/**
 * Order entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "order_inf", catalog = "travel_love_db")
public class Order extends AbstractOrder implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Order() {
	}

	/** full constructor */
	public Order(TeamInfo teamInfo, ItemInf itemInf, MemberInf memberInf,
			SysUser sysUser, Integer itemCount, Double totalPrice,
			Timestamp createDate, Timestamp updateDate) {
		super(teamInfo, itemInf, memberInf, sysUser, itemCount, totalPrice,
				createDate, updateDate);
	}
	
	@Transient
	private String orderName;
	@Transient
	public String getOrderName() {
		return orderName;
	}
	@Transient
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	/**
	 * @return
	 */
	public OrderDTO toDTO() {
		OrderDTO dto = new OrderDTO();
		dto.setCreateTime(DateUtils.toStr(getCreateDate()));
		dto.setItem(getItemInf().toDTO());
		dto.setItemCount(getItemCount());
		dto.setOrderId(getId());
		dto.setTotalPrice(getTotalPrice());
		return dto;
	}
	
}
