package com.travel.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.travel.common.dto.ItemInfDTO;
import com.travel.common.dto.ViewSpotDTO;

/**
 * ViewSpotInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "view_spot_info", catalog = "travel_love_db")
public class ViewSpotInfo extends AbstractViewSpotInfo implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public ViewSpotInfo() {
	}

	/** minimal constructor */
	public ViewSpotInfo(SysUser sysUser, String name, Double longitude,
			Double latitude, Timestamp createDate, Timestamp updateDate) {
		super(sysUser, name, longitude, latitude, createDate, updateDate);
	}

	/** full constructor */
	public ViewSpotInfo(SysUser sysUser, String name, Double longitude,
			Double latitude, String description, Timestamp createDate,
			Timestamp updateDate, Set<RouteViewSpot> routeViewSpots) {
		super(sysUser, name, longitude, latitude, description, createDate,
				updateDate, routeViewSpots);
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
	
	private List<ItemInfDTO> itemList = new ArrayList<ItemInfDTO>();
	
	@Transient
	public List<ItemInfDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemInfDTO> itemList) {
		this.itemList = itemList;
	}
	@Transient
	private String provinceName;
	@Transient
	private String cityName;	
	@Transient
	private int commentCount;
	@Transient
	public String getProvinceName() {
		return provinceName;
	}
	@Transient
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	@Transient
	public String getCityName() {
		return cityName;
	}
	@Transient
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	@Transient
	public int getCommentCount() {
		return commentCount;
	}
	@Transient
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	@Transient
	private double score;		
	@Transient
	public double getScore() {
		return score;
	}
	@Transient
	public void setScore(double score) {
		this.score = score;
	}

	/**
	 * @return
	 */
	public ViewSpotDTO toDTO() {
		ViewSpotDTO dto = new ViewSpotDTO();
		dto.setViewId(getId());
		dto.setName(getName());
		dto.setDescription(getDescription());
		dto.setLatitude(getLatitude());
		dto.setLongitude(getLongitude());
		if(getAddress() == null){
			dto.setAddress("");
		} else {
			dto.setAddress(getAddress());
		}
		dto.addImageUrl(urls);
		dto.setItemList(itemList);
		dto.setProvince(getProvinceName());
		dto.setCity(getCityName());
		dto.setCommentCount(commentCount);
		dto.setScore(score);
		return dto;
	}

}
