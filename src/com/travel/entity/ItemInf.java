package com.travel.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.travel.common.dto.ItemInfDTO;
import com.travel.utils.DateUtils;

/**
 * ItemInf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "item_inf", catalog = "travel_love_db")
public class ItemInf extends AbstractItemInf implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public ItemInf() {
	}

	/** minimal constructor */
	public ItemInf(SysUser sysUser, String name, Double price,
			String contactPhone, String contactName, Timestamp createDate,
			Timestamp updateDate) {
		super(sysUser, name, price, contactPhone, contactName, createDate,
				updateDate);
	}

	/** full constructor */
	public ItemInf(SysUser sysUser, String name, String brands,
			String specification, Double price, String description,
			String contactPhone, String contactName, Timestamp createDate,
			Timestamp updateDate, Set<Order> orders) {
		super(sysUser, name, brands, specification, price, description,
				contactPhone, contactName, createDate, updateDate, orders);
	}
	@Transient
	private List<String>urls = new ArrayList<String>();	
	@Transient
	public List<String> getUrls() {
		return urls;
	}
	@Transient
	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	/**
	 * @return
	 */
	public ItemInfDTO toDTO() {
		ItemInfDTO dto = new ItemInfDTO();
		dto.setItemId(getId());
		dto.setItemName(getName());
		dto.setPrice(getPrice());
		dto.setCreateTime(DateUtils.toStr(getCreateDate()));
		dto.addImageUrl(urls);		
		return dto;
	}

}
