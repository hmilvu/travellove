package com.travel.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.travel.common.dto.WeatherDataDTO;
import com.travel.utils.DateUtils;

/**
 * WeatherData entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "weather_data", catalog = "travel_love_db")
public class WeatherData extends AbstractWeatherData implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public WeatherData() {
	}

	/** minimal constructor */
	public WeatherData(String cityName) {
		super(cityName);
	}

	/** full constructor */
	public WeatherData(String cityName, String data1, String data2) {
		super(cityName, data1, data2);
	}

	/**
	 * @return
	 */
	public WeatherDataDTO toDTO() {
		WeatherDataDTO dto = new WeatherDataDTO();
		dto.setId(getId());
		dto.setCityName(getCityName());
		dto.setData1(getData1());
		dto.setData2(getData2());
		dto.setData3(getData3());
		dto.setCreateDate(DateUtils.toSimpleStr(getCreateDate()));
		return dto;
	}

}
