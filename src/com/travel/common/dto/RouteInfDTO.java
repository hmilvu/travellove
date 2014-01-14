/**
 * @author Zhang Zhipeng
 *
 * 2013-10-24
 */
package com.travel.common.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Lenovo
 *
 */
public class RouteInfDTO {
	private Long id;
	private String routeName;
	private String startAddress;
	private Double startLongtitude;
	private Double startLatitude;
	private String endAddress;
	private Double endLongitude;
	private Double endLatitude;
	private String description;
	private String startDate;
	private String endDate;
	private int status;
	private String attachmentUrl;
	
	private List<ViewSpotDTO> viewSportList = new ArrayList<ViewSpotDTO>();
	
	public List<ViewSpotDTO> getViewSportList() {
		return Collections.unmodifiableList(viewSportList);
	}
	public void addViewSport(ViewSpotDTO viewSpotDTO) {
		this.viewSportList.add(viewSpotDTO);
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public String getStartAddress() {
		return startAddress;
	}
	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}
	public Double getStartLongtitude() {
		return startLongtitude;
	}
	public void setStartLongtitude(Double startLongtitude) {
		this.startLongtitude = startLongtitude;
	}
	public Double getStartLatitude() {
		return startLatitude;
	}
	public void setStartLatitude(Double startLatitude) {
		this.startLatitude = startLatitude;
	}
	public String getEndAddress() {
		return endAddress;
	}
	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}
	public Double getEndLongitude() {
		return endLongitude;
	}
	public void setEndLongitude(Double endLongitude) {
		this.endLongitude = endLongitude;
	}
	public Double getEndLatitude() {
		return endLatitude;
	}
	public void setEndLatitude(Double endLatitude) {
		this.endLatitude = endLatitude;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAttachmentUrl() {
		return attachmentUrl;
	}
	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}
	
	
}
