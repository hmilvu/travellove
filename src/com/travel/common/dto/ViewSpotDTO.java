/**
 * @author Zhang Zhipeng
 *
 * 2013-10-23
 */
package com.travel.common.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Lenovo
 *
 */
public class ViewSpotDTO {
	private Long viewId;
	private String name;
	private String address;
	private Double longitude;
	private Double latitude;
	private String description;
	private String startDate;
	private String endDate;
	private Integer numberOfDay;
	private List<String> imageUrls = new ArrayList<String>();	
	private String province;
	private String city;
	private List<ItemInfDTO> itemList = new ArrayList<ItemInfDTO>();
	

	public List<ItemInfDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemInfDTO> itemList) {
		this.itemList = itemList;
	}
	
	public List<String> getImageUrls() {
		return Collections.unmodifiableList(imageUrls);
	}
	public void addImageUrl(List<String> imageUrls) {
		this.imageUrls.addAll(imageUrls);
	}
	
	
	public Long getViewId() {
		return viewId;
	}
	public void setViewId(Long viewId) {
		this.viewId = viewId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Integer getNumberOfDay() {
		return numberOfDay;
	}
	public void setNumberOfDay(Integer numberOfDay) {
		this.numberOfDay = numberOfDay;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getProvince() {
		if(province == null){
			return "";
		} else {
			return province;
		}
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		if(city == null){
			return "";
		} else {
			return city;
		}
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}
