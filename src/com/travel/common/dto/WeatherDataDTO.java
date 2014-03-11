/**
 * @author Zhang Zhipeng
 *
 * 2013-10-26
 */
package com.travel.common.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lenovo
 *
 */
public class WeatherDataDTO {
	private Long id;
	private String cityName;
	private String data1;
	private String data2;
	private String data3;
	private String createDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getData1() {
		return data1;
	}
	public void setData1(String data1) {
		this.data1 = data1;
	}
	public String getData2() {
		return data2;
	}
	public void setData2(String data2) {
		this.data2 = data2;
	}
	public String getData3() {
		return data3;
	}
	public void setData3(String data3) {
		this.data3 = data3;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
}
