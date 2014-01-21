/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.admin.form;


/**
 * @author Lenovo
 *
 */
public class RouteItemForm{
	private int order;
	private RouteForm routeForm;
	private AttachmentForm attachmentForm;
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public RouteForm getRouteForm() {
		return routeForm;
	}
	public void setRouteForm(RouteForm routeForm) {
		this.routeForm = routeForm;
	}
	public AttachmentForm getAttachmentForm() {
		return attachmentForm;
	}
	public void setAttachmentForm(AttachmentForm attachmentForm) {
		this.attachmentForm = attachmentForm;
	}

}
