/**
 * @author Zhang Zhipeng
 *
 * 2013-10-24
 */
package com.travel.common.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author Lenovo
 *
 */
public class TeamRouteDTO {
	private TeamInfoDTO teamInfo;
	private List<RouteInfDTO> routeInfoList = new ArrayList<RouteInfDTO>();
	public TeamInfoDTO getTeamInfo() {
		return teamInfo;
	}
	public void setTeamInfo(TeamInfoDTO teamInfo) {
		this.teamInfo = teamInfo;
	}
	public List<RouteInfDTO> getRouteInfoList() {
		return Collections.unmodifiableList(routeInfoList);
	}
	public void addRouteInfo(RouteInfDTO routeInfoDTO) {
		this.routeInfoList.add(routeInfoDTO);
	}
	
	public static void main(String[] args) {
		UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString().toUpperCase());
	}
}
