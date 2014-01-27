package com.travel.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.common.Constants.TRIGGER_STATUS;
import com.travel.common.Constants.TRIGGER_TYPE;
import com.travel.common.admin.dto.SearchTriggerConfigDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.common.dto.ViewSpotDTO;
import com.travel.dao.RouteInfDAO;
import com.travel.dao.RouteViewSpotDAO;
import com.travel.dao.TeamInfoDAO;
import com.travel.dao.TeamRouteDAO;
import com.travel.dao.TriggerConfigDAO;
import com.travel.dao.ViewSpotInfoDAO;
import com.travel.entity.MemberInf;
import com.travel.entity.TeamInfo;
import com.travel.entity.TeamRoute;
import com.travel.entity.TriggerConfig;
import com.travel.entity.ViewSpotInfo;

@Service
public class TriggerConfigService extends AbstractBaseService
{
	@Autowired
	private TriggerConfigDAO triggerDao;
	@Autowired
	private TeamInfoDAO teamDao;
	@Autowired
	private WeatherService weahterService;
	@Autowired
	private ViewSpotInfoDAO viewSpotDao;
	@Autowired
	private RouteInfDAO routeDao;
	@Autowired
	private RouteViewSpotDAO routeViewSpotDao;
	@Autowired
	private TeamRouteDAO teamRouteDao;
	/**
	 * @param id
	 * @param configList
	 */
	public void initTriggerConfig(Long travelId) {
		triggerDao.initTriggerConfig(travelId);		
	}

	/**
	 * @param dto
	 * @return
	 */
	public int getTotalTriggerConfigNum(SearchTriggerConfigDTO dto) {
		return triggerDao.getTotalNum(dto);
	}

	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	public List<TriggerConfig> findTriggerConfigs(SearchTriggerConfigDTO dto,
			PageInfoDTO pageInfo) {
		return triggerDao.findTriggerConfigs(dto, pageInfo);
	}

	/**
	 * @param valueOf
	 * @return
	 */
	public TriggerConfig getTriggerConfigById(Long triggerId) {
		return triggerDao.findById(triggerId);
	}

	/**
	 * @param valueOf
	 */
	public void updateTriggerStatus(Long triggerId) {
		TriggerConfig trigger = triggerDao.findById(triggerId);
		if(trigger.getTriggerStatus().intValue() == TRIGGER_STATUS.ACTIVE.getValue()){
			trigger.setTriggerStatus(TRIGGER_STATUS.INACTIVE.getValue());
		} else {
			trigger.setTriggerStatus(TRIGGER_STATUS.ACTIVE.getValue());
		}
		triggerDao.update(trigger);
	}

	/**
	 * @param trigger
	 */
	public void updateTrigger(TriggerConfig trigger) {
		triggerDao.update(trigger);
	}

	/**
	 * @return
	 */
	public List<TriggerConfig> getValidTriggerConfigs() {
		return triggerDao.getValidTriggerConfigs();
	}

	/**
	 * @param trigger
	 */
	public void trigger(TriggerConfig trigger) {
		if(trigger.getTriggerType().intValue() == TRIGGER_TYPE.WHEATHER.getValue()){
			triggerWheather(trigger);
		}		
	}

	/**
	 * 
	 */
	private void triggerWheather(TriggerConfig trigger) {
		Long travelId = trigger.getTravelId();
		List<TeamInfo> teamList =teamDao.getActiveTeamByTravelId(travelId);
		PageInfoDTO pageInfo = new PageInfoDTO();
		pageInfo.setPageNumber(1);
		pageInfo.setPageSize(1000);
		for(TeamInfo team : teamList){
			List<ViewSpotInfo> viewList = null;
			List <TeamRoute> list = teamRouteDao.findByTeamId(team.getId(), pageInfo);	
			for(TeamRoute teamRoute : list){			
				List<Object[]> viewSpotList = routeViewSpotDao.findViewSpotByRouteId(teamRoute.getRouteInf().getId());
				for(Object[] objArr : viewSpotList){
					ViewSpotInfo view = (ViewSpotInfo)objArr[0];
					Integer numberOfDay = (Integer)objArr[3] - 1;
					if(numberOfDay < 0){
						numberOfDay = 0;
					}
					Calendar cal = Calendar.getInstance();	
					cal.setTime(teamRoute.getDate()); 
					cal.add(Calendar.DAY_OF_YEAR, numberOfDay);
					cal.add(Calendar.HOUR_OF_DAY, 8);
					Long startDateLong = cal.getTime().getTime();
					Long endDateLong = cal.getTime().getTime();	
					Long currentLong = System.currentTimeMillis();
//					if(currentLong > startDateLong )
								
//					String []data = weahterService.getWheatherData(view.getLatitude(), view.getLongitude());
				}
			}
		}
	}

	/**
	 * 
	 */
	public void triggerVelocity(MemberInf member, Double velocity) {
		// TODO Auto-generated method stub
		
	}	
	
}
