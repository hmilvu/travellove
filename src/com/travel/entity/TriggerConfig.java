package com.travel.entity;

import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TriggerConfig entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "trigger_config", catalog = "travel_love_db")
public class TriggerConfig extends AbstractTriggerConfig implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TriggerConfig() {
	}

	@Override
	public String toString() {
		return "TriggerConfig [getConditionValue()=" + getConditionValue()
				+ ", getContent()=" + getContent() + ", getEndTime()="
				+ getEndTime() + ", getId()=" + getId() + ", getStartTime()="
				+ getStartTime() + ", getSysTriggerConfigId()="
				+ getSysTriggerConfigId() + ", getTimes()=" + getTimes()
				+ ", getTravelId()=" + getTravelId()
				+ ", getTriggerCondition()=" + getTriggerCondition()
				+ ", getTriggerName()=" + getTriggerName()
				+ ", getTriggerStatus()=" + getTriggerStatus()
				+ ", getTriggerType()=" + getTriggerType()
				+ ", getTypeValue()=" + getTypeValue() + ", getUnitage()="
				+ getUnitage() +  "]";
	}


	
}
