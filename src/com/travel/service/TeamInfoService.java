package com.travel.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.common.admin.dto.SearchTeamDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.common.dto.TeamLocationDTO;
import com.travel.common.dto.TeamRouteDTO;
import com.travel.dao.LocationLogDAO;
import com.travel.dao.TeamInfoDAO;
import com.travel.dao.TeamRouteDAO;
import com.travel.entity.LocationLog;
import com.travel.entity.TeamInfo;
import com.travel.entity.TeamRoute;

@Service
public class TeamInfoService
{
	@Autowired
	private TeamRouteDAO teamRouteDao;
	@Autowired
	private LocationLogDAO locationDAO;
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
		List <LocationLog> list = locationDAO.findByTeamId(teamId);
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
	public int addTeamInf(TeamInfo team) {
		team.setCreateDate(new Timestamp(new Date().getTime()));
		team.setUpdateDate(team.getCreateDate());
		int result = teamDao.save(team);
		return result;
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
		return teamDao.update(team);
		
	}
	
}
