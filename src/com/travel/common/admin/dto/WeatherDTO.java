/**
 * @author Zhang Zhipeng
 *
 * 2014-6-2
 */
package com.travel.common.admin.dto;

/**
 * @author Lenovo
 *
 */
public class WeatherDTO {
	private String cityName;
	private String []data;
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String[] getData() {
		return data;
	}
	public void setData(String[] data) {
		this.data = data;
	}
	
}
