/**
 * @author Zhang Zhipeng
 *
 * 2013-10-25
 */
package com.travel.common.dto;


/**
 * @author Lenovo
 *
 */
public class OrderDTO {
	private Long orderId;
	private Integer itemCount;
	private Double totalPrice;
	private String createTime;
	private ItemInfDTO item;
	private String contactTel;
	private String remark;
	private String contactName;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Integer getItemCount() {
		return itemCount;
	}
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public ItemInfDTO getItem() {
		return item;
	}
	public void setItem(ItemInfDTO item) {
		this.item = item;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
}
