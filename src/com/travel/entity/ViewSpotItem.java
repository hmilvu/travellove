package com.travel.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ViewSpotInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "view_spot_item", catalog = "travel_love_db")
public class ViewSpotItem extends AbstractViewSpotItem implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public ViewSpotItem() {
	}
	
}
