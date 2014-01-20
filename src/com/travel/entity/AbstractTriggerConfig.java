package com.travel.entity;

import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

/**
 * AbstractTriggerConfig entity provides the base persistence definition of the
 * TriggerConfig entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractTriggerConfig extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private Integer typeValue;
	private String content;
	private Time startTime;
	private Integer times;
	private Integer triggerType;
	private Double conditionValue;
	private String triggerCondition;
	private Long travelId;
	private Long sysTriggerConfigId;
	private String triggerName;
	private Integer triggerStatus;

	// Constructors

	/** default constructor */
	public AbstractTriggerConfig() {
	}

	/** minimal constructor */
	public AbstractTriggerConfig(String content, Integer times,
			Integer triggerType, String triggerCondition, String triggerName,
			Integer triggerStatus) {
		this.content = content;
		this.times = times;
		this.triggerType = triggerType;
		this.triggerCondition = triggerCondition;
		this.triggerName = triggerName;
		this.triggerStatus = triggerStatus;
	}

	/** full constructor */
	public AbstractTriggerConfig(Integer typeValue, String content,
			Time startTime, Integer times, Integer triggerType,
			Double conditionValue, String triggerCondition, Long travelId,
			Long sysTriggerConfigId, String triggerName, Integer triggerStatus) {
		this.typeValue = typeValue;
		this.content = content;
		this.startTime = startTime;
		this.times = times;
		this.triggerType = triggerType;
		this.conditionValue = conditionValue;
		this.triggerCondition = triggerCondition;
		this.travelId = travelId;
		this.sysTriggerConfigId = sysTriggerConfigId;
		this.triggerName = triggerName;
		this.triggerStatus = triggerStatus;
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

	@Column(name = "type_value")
	public Integer getTypeValue() {
		return this.typeValue;
	}

	public void setTypeValue(Integer typeValue) {
		this.typeValue = typeValue;
	}

	@Column(name = "content", nullable = false, length = 512)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "start_time", length = 8)
	public Time getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	@Column(name = "times", nullable = false)
	public Integer getTimes() {
		return this.times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	@Column(name = "trigger_type", nullable = false)
	public Integer getTriggerType() {
		return this.triggerType;
	}

	public void setTriggerType(Integer triggerType) {
		this.triggerType = triggerType;
	}

	@Column(name = "condition_value", precision = 15, scale = 3)
	public Double getConditionValue() {
		return this.conditionValue;
	}

	public void setConditionValue(Double conditionValue) {
		this.conditionValue = conditionValue;
	}

	@Column(name = "trigger_condition", nullable = false, length = 128)
	public String getTriggerCondition() {
		return this.triggerCondition;
	}

	public void setTriggerCondition(String triggerCondition) {
		this.triggerCondition = triggerCondition;
	}

	@Column(name = "travel_id")
	public Long getTravelId() {
		return this.travelId;
	}

	public void setTravelId(Long travelId) {
		this.travelId = travelId;
	}

	@Column(name = "sys_trigger_config_id")
	public Long getSysTriggerConfigId() {
		return this.sysTriggerConfigId;
	}

	public void setSysTriggerConfigId(Long sysTriggerConfigId) {
		this.sysTriggerConfigId = sysTriggerConfigId;
	}

	@Column(name = "trigger_name", nullable = false, length = 20)
	public String getTriggerName() {
		return this.triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	@Column(name = "trigger_status", nullable = false)
	public Integer getTriggerStatus() {
		return this.triggerStatus;
	}

	public void setTriggerStatus(Integer triggerStatus) {
		this.triggerStatus = triggerStatus;
	}

}