package com.travel.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TriggerViewSpot entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "trigger_view_spot", catalog = "travel_love_db")
public class TriggerViewSpot extends AbstractTriggerViewSpot implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TriggerViewSpot() {
	}

	/** full constructor */
	public TriggerViewSpot(ViewSpotInfo viewSpotInfo,
			TriggerConfig triggerConfig) {
		super(viewSpotInfo, triggerConfig);
	}

}
