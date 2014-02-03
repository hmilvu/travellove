package com.travel.entity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

/**
 * AbstractTriggerViewSpot entity provides the base persistence definition of
 * the TriggerViewSpot entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractTriggerViewSpot extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private ViewSpotInfo viewSpotInfo;
	private TriggerConfig triggerConfig;

	// Constructors

	/** default constructor */
	public AbstractTriggerViewSpot() {
	}

	/** full constructor */
	public AbstractTriggerViewSpot(ViewSpotInfo viewSpotInfo,
			TriggerConfig triggerConfig) {
		this.viewSpotInfo = viewSpotInfo;
		this.triggerConfig = triggerConfig;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "view_spot_id", nullable = false)
	public ViewSpotInfo getViewSpotInfo() {
		return this.viewSpotInfo;
	}

	public void setViewSpotInfo(ViewSpotInfo viewSpotInfo) {
		this.viewSpotInfo = viewSpotInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trigger_id", nullable = false)
	public TriggerConfig getTriggerConfig() {
		return this.triggerConfig;
	}

	public void setTriggerConfig(TriggerConfig triggerConfig) {
		this.triggerConfig = triggerConfig;
	}

}