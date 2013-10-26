package com.travel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.common.dto.PageInfoDTO;
import com.travel.common.dto.TeamRouteDTO;
import com.travel.dao.TeamRouteDAO;
import com.travel.entity.TeamRoute;

@Service
public class TeamInfoService
{
	@Autowired
	private TeamRouteDAO teamRouteDao;
	
	
	public TeamRouteDTO getRouteInfByTeamId(Long id, PageInfoDTO pageInfo){
		 List <TeamRoute> list = teamRouteDao.findByTeamId(id, pageInfo);
		 TeamRouteDTO dto = new TeamRouteDTO();
		 for(TeamRoute teamRoute : list){
			dto.setTeamInfo(teamRoute.getTeamInfo().toDTO());
			dto.addRouteInfo(teamRoute.getRouteInf().toDTO());			 
		 }
		 return dto;
	}
	
}
