/**
 * @author Zhang Zhipeng
 *
 * 2013-10-24
 */
package com.travel.common.dto;

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
	
	
}
