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

import javax.persistence.Transient;

/**
 * @author Lenovo
 *
 */
public class ItemInfDTO {
	private Long itemId;
	private String itemName;
	private Double price;
	private String createTime;
	private String description;
	private String brands;
	private String specification;
	private Double score;
	private List<String> imageUrls = new ArrayList<String>();	
	private int commentCount;
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBrands() {
		return brands;
	}
	public void setBrands(String brands) {
		this.brands = brands;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	
}
