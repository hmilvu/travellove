/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.admin;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.travel.action.AuthorityAction;
import com.travel.common.Constants;
import com.travel.common.admin.dto.SearchTeamDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.TeamInfo;
import com.travel.entity.TravelInf;
import com.travel.service.TeamInfoService;
import com.travel.utils.DateUtils;
import com.travel.utils.JsonUtils;

/**
 * @author Lenovo
 *
 */
public class TeamInfAction extends AuthorityAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private TeamInfoService teamService;

	public String list(){
		String travelName = request.getParameter("travelName");
		String teamName = request.getParameter("teamName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String pageSize = request.getParameter("numPerPage");
		String pageNumber = request.getParameter("pageNum");
		PageInfoDTO pageInfo = new PageInfoDTO();
		try{
			pageInfo.setPageNumber(Integer.valueOf(pageNumber.toString()));
		}catch(Throwable ignore){	
			pageInfo.setPageNumber(1);
		}
		try{			
			pageInfo.setPageSize(Integer.valueOf(pageSize.toString()));
		}catch(Throwable ignore){	
			pageInfo.setPageSize(Constants.ADMIN_DEFAULT_PAGE_SIZE);
		}
		SearchTeamDTO dto = new SearchTeamDTO();
		if(isTravelUser()){
			dto.setTravelId(getCurrentUser().getTravelInf().getId());
		}
		dto.setTravelName(travelName);
		dto.setTeamName(teamName);
		dto.setStartDate(DateUtils.toDate(startDate));
		dto.setEndDate(DateUtils.toDate(endDate));
		int totalNum = teamService.getTotalTeamNum(dto);
		List<TeamInfo> list = teamService.findTeams(dto, pageInfo);
		request.setAttribute("teamList", list);
		request.setAttribute("teamTotalCount", totalNum+"");
		request.setAttribute("travelName", travelName);
		request.setAttribute("teamName", teamName);
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("pageNumber", pageNumber == null ? 1 : pageNumber);
		request.setAttribute("startNum", (pageInfo.getPageNumber()-1)*pageInfo.getPageSize());
		return "list";
	}
	
	public String add(){
		return "add";
	}
	
	public void create(){
		String travelId = request.getParameter("travelLookup.id");
		String name = request.getParameter("name");
		String peopleCount = request.getParameter("peopleCount");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String description = request.getParameter("description");		
		
		TeamInfo team = new TeamInfo();
		team.setName(name);
		team.setPeopleCount(Integer.valueOf(peopleCount));
		team.setBeginDate(DateUtils.toDate(startDate));
		team.setEndDate(DateUtils.toDate(endDate));
		team.setDescription(description);
		team.setSysUser(getCurrentUser());
		TravelInf travelInf = new TravelInf();
		if(StringUtils.isBlank(travelId)){
			travelInf.setId(getCurrentUser().getTravelInf().getId());
		} else{
			travelInf.setId(Long.valueOf(travelId));
		}
		team.setTravelInf(travelInf);
		if(teamService.addTeamInf(team) == 0){
			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));			
		} else {
			JsonUtils.write(response, binder.toJson("result", Action.ERROR));		
		}
	}
	
}
