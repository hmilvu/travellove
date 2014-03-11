package com.travel.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

/**
 * AbstractWeatherData entity provides the base persistence definition of the
 * WeatherData entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractWeatherData extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private String cityName;
	private String data1;
	private String data2;
	private String data3;
	private Date createDate;
	// Constructors

	/** default constructor */
	public AbstractWeatherData() {
	}

	/** minimal constructor */
	public AbstractWeatherData(String cityName) {
		this.cityName = cityName;
	}

	/** full constructor */
	public AbstractWeatherData(String cityName, String data1, String data2) {
		this.cityName = cityName;
		this.data1 = data1;
		this.data2 = data2;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "city_name", unique = true, nullable = false, length = 40)
	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name = "data1", length = 100)
	public String getData1() {
		return this.data1;
	}

	public void setData1(String data1) {
		this.data1 = data1;
	}

	@Column(name = "data2", length = 100)
	public String getData2() {
		return this.data2;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}
	@Column(name = "data3", length = 100)
	public String getData3() {
		return this.data3;
	}

	public void setData3(String data3) {
		this.data3 = data3;
	}
	@Column(name = "create_date", nullable = false)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}