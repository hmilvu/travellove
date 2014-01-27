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
 * AbstractRoleMenu entity provides the base persistence definition of the
 * RoleMenu entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractViewSpotItem extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private ViewSpotInfo viewSpotInf;
	private ItemInf itemInf;
	private TravelInf travelInf;
	private Integer displayOrder;
	// Constructors

	/** default constructor */
	public AbstractViewSpotItem() {
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
	public ViewSpotInfo getViewSpotInf() {
		return viewSpotInf;
	}

	public void setViewSpotInf(ViewSpotInfo viewSpotInf) {
		this.viewSpotInf = viewSpotInf;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", nullable = false)
	public ItemInf getItemInf() {
		return itemInf;
	}

	public void setItemInf(ItemInf itemInf) {
		this.itemInf = itemInf;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "travel_id", nullable = false)
	public TravelInf getTravelInf() {
		return travelInf;
	}

	public void setTravelInf(TravelInf travelInf) {
		this.travelInf = travelInf;
	}
	@Column(name = "display_order", nullable = false)
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	

}