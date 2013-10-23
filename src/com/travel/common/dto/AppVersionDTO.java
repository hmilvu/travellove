/**
 * @author Zhang Zhipeng
 *
 * 2013-10-23
 */
package com.travel.common.dto;

/**
 * @author Lenovo
 *
 */
public class AppVersionDTO {
	private Integer must;
	private String url;
	private Integer size;
	private String coverImgUrl;
	public Integer getMust() {
		return must;
	}
	public void setMust(Integer must) {
		this.must = must;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getCoverImgUrl() {
		return coverImgUrl;
	}
	public void setCoverImgUrl(String coverImgUrl) {
		this.coverImgUrl = coverImgUrl;
	}
	
}
