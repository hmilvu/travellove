/**
 * @author Zhang Zhipeng
 *
 * 2013-10-25
 */
package com.travel.common.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Lenovo
 *
 */
public class ItemInfDTO {
	private Long itemId;
	private String itemName;
	private Double price;
	private String createTime;
	private List<String> imageUrls = new ArrayList<String>();	
	
	public List<String> getImageUrls() {
		return Collections.unmodifiableList(imageUrls);
	}
	public void addImageUrl(List<String> imageUrls) {
		this.imageUrls.addAll(imageUrls);
	}
	
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
