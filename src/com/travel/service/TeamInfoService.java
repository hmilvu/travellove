package com.travel.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.action.admin.form.RouteItemForm;
import com.travel.common.admin.dto.SearchTeamDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.common.dto.TeamLocationDTO;
import com.travel.common.dto.TeamRouteDTO;
import com.travel.dao.LocationLogDAO;
import com.travel.dao.TeamInfoDAO;
import com.travel.dao.TeamRouteDAO;
import com.travel.entity.LocationLog;
import com.travel.entity.RouteInf;
import com.travel.entity.TeamInfo;
import com.travel.entity.TeamRoute;
import com.travel.utils.DateUtils;

@Service
public class TeamInfoService
{
	@Autowired
	private TeamRouteDAO teamRouteDao;
	@Autowired
	private LocationLogDAO locationDao;
	@Autowired
	private TeamInfoDAO teamDao;
	
	public TeamRouteDTO getRouteInfByTeamId(Long id, PageInfoDTO pageInfo){
		 List <TeamRoute> list = teamRouteDao.findByTeamId(id, pageInfo);
		 TeamRouteDTO dto = new TeamRouteDTO();
		 for(TeamRoute teamRoute : list){
			dto.setTeamInfo(teamRoute.getTeamInfo().toDTO());
			dto.addRouteInfo(teamRoute.getRouteInf().toDTO());			 
		 }
		 return dto;
	}


	/**
	 * @param id
	 * @return
	 */
	public TeamLocationDTO getTeamMemeberLocation(Long teamId, Long memberId) {
		List <LocationLog> list = locationDao.findByTeamId(teamId);
		TeamLocationDTO dto = new TeamLocationDTO();
		for(LocationLog location : list){
			if(location.getMemberInf().getId().longValue() != memberId.longValue()){
				dto.setTeamInfo(location.getTeamInfo().toDTO());
				dto.addLocation(location.toDTO());
			}
		}
		return dto;
	}


	/**
	 * @param dto
	 * @return
	 */
	public int getTotalTeamNum(SearchTeamDTO dto) {
		return teamDao.getTotalNum(dto);
	}


	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	public List<TeamInfo> findTeams(SearchTeamDTO dto, PageInfoDTO pageInfo) {
		return teamDao.findTeams(dto, pageInfo);
	}


	/**
	 * @param team
	 * @return
	 */
	public int addTeamInf(TeamInfo team, List<RouteItemForm>items) {
		team.setCreateDate(new Timestamp(new Date().getTime()));
		team.setUpdateDate(team.getCreateDate());
		Long id = teamDao.save(team);
		if(id != null && id > 0){
			if(items != null){
				team.setId(id);
				for(RouteItemForm routeItem : items){
					TeamRoute teamRoute = new TeamRoute();
					teamRoute.setTeamInfo(team);
					teamRoute.setRouteOrder(routeItem.getOrder());
					RouteInf route = new RouteInf();
					route.setId(routeItem.getRouteForm().getId());
					teamRoute.setRouteInf(route);
					teamRoute.setStatus(routeItem.getRouteForm().getStatus());
					teamRoute.setDate(DateUtils.toDate(routeItem.getRouteForm().getDate()));
					teamRoute.setCreateDate(team.getCreateDate());
					teamRoute.setUpdateDate(team.getCreateDate());
					teamRoute.setSysUser(team.getSysUser());
					teamRouteDao.save(teamRoute);
				}
			}
			return 0;
		} else {
			return -1;
		}
	}


	/**
	 * @param teamIdLong
	 * @return
	 */
	public TeamInfo getTeamById(Long teamIdLong) {
		return teamDao.findById(teamIdLong);
	}

	/**
	 * @param team
	 */
	public int updateTeam(TeamInfo team) {
		team.setUpdateDate(new Timestamp(new Date().getTime()));
		int result = teamDao.update(team);	
		return result;
	}

	/**
	 * @param team
	 */
	public int updateTeam(TeamInfo team, List<RouteItemForm>items) {
		int result = updateTeam(team);	
		if(result == 0){
			teamRouteDao.deleteByTeamIds(team.getId()+"");
			if(items != null){
				for(RouteItemForm item : items){
					TeamRoute teamRoute = new TeamRoute();
					teamRoute.setTeamInfo(team);
					teamRoute.setRouteOrder(item.getOrder());
					RouteInf route = new RouteInf();
					route.setId(item.getRouteForm().getId());
					teamRoute.setRouteInf(route);
					teamRoute.setStatus(item.getRouteForm().getStatus());
					teamRoute.setDate(DateUtils.toDate(item.getRouteForm().getDate()));
					teamRoute.setCreateDate(team.getCreateDate());
					teamRoute.setUpdateDate(team.getCreateDate());
					teamRoute.setSysUser(team.getSysUser());
					teamRouteDao.save(teamRoute);
				}
			}
			return 0;
		} else {
			return -1;
		}
	}


	/**
	 * @return
	 */
	public List<TeamInfo> findAllTeams(Long travelid) {
		return teamDao.findAll(travelid);
	}


	/**
	 * @param ids
	 * @return
	 */
	public List<TeamRoute> findRouteByIds(String routeIds) {
		String []ids = StringUtils.split(routeIds, ",");
		List<Long> idList = new ArrayList<Long>();
		for(String id : ids){
			idList.add(Long.valueOf(id));
		}
		List <TeamRoute> list = teamRouteDao.findByRouteIds(idList);
		return list;
	}


	/**
	 * @param id
	 * @return
	 */
	public List<TeamRoute> getRouteInfByTeamId(Long id) {
		List <TeamRoute> list = teamRouteDao.findByTeamId(id);
		return list;
	}
	
}
