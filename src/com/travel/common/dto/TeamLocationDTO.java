/**
 * @author Zhang Zhipeng
 *
 * 2013-10-24
 */
package com.travel.common.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Lenovo
 *
 */
public class TeamLocationDTO {
	private TeamInfoDTO teamInfo;
	private List<LocationLogDTO> locationList = new ArrayList<LocationLogDTO>();
	private List<Long>geoVisibleMemberIdList;
	private List<Long>phoneVisibleMemberIdList;
	public TeamInfoDTO getTeamInfo() {
		return teamInfo;
	}
	public void setTeamInfo(TeamInfoDTO teamInfo) {
		this.teamInfo = teamInfo;
	}
	public List<LocationLogDTO> getLocationList() {
		return Collections.unmodifiableList(locationList);
	}
	public void addLocation(LocationLogDTO locationLogDTO) {
		this.locationList.add(locationLogDTO);
	}
	public List<Long> getGeoVisibleMemberIdList() {
		return geoVisibleMemberIdList;
	}
	public void setGeoVisibleMemberIdList(List<Long> geoVisibleMemberIdList) {
		this.geoVisibleMemberIdList = geoVisibleMemberIdList;
	}
	public List<Long> getPhoneVisibleMemberIdList() {
		return phoneVisibleMemberIdList;
	}
	public void setPhoneVisibleMemberIdList(List<Long> phoneVisibleMemberIdList) {
		this.phoneVisibleMemberIdList = phoneVisibleMemberIdList;
	}	

}
