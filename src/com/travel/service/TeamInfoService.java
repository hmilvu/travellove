package com.travel.service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.action.admin.form.RouteItemForm;
import com.travel.common.Constants.IMAGE_TYPE;
import com.travel.common.Constants.MEMBER_TYPE;
import com.travel.common.Constants.ROUTE_STATUS;
import com.travel.common.Constants.VISIBLE_TYPE;
import com.travel.common.admin.dto.SearchTeamDTO;
import com.travel.common.dto.LocationLogDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.common.dto.RouteInfDTO;
import com.travel.common.dto.TeamLocationDTO;
import com.travel.common.dto.TeamRouteDTO;
import com.travel.common.dto.ViewSpotDTO;
import com.travel.dao.AttachmentInfDAO;
import com.travel.dao.ImgInfDAO;
import com.travel.dao.LocationLogDAO;
import com.travel.dao.MemberInfDAO;
import com.travel.dao.MemberPrivateDAO;
import com.travel.dao.RouteViewSpotDAO;
import com.travel.dao.TeamInfoDAO;
import com.travel.dao.TeamRouteDAO;
import com.travel.entity.AttachmentInf;
import com.travel.entity.LocationLog;
import com.travel.entity.MemberInf;
import com.travel.entity.RouteInf;
import com.travel.entity.TeamInfo;
import com.travel.entity.TeamRoute;
import com.travel.entity.ViewSpotInfo;
import com.travel.utils.DateUtils;

@Service
public class TeamInfoService extends AbstractBaseService
{
	@Autowired
	private TeamRouteDAO teamRouteDao;
	@Autowired
	private LocationLogDAO locationDao;
	@Autowired
	private TeamInfoDAO teamDao;
	@Autowired
	private RouteViewSpotDAO routeViewSpotDao;
	@Autowired
	private ImgInfDAO imageDao;
	@Autowired
	private AttachmentInfDAO attachmentDAO;
	@Autowired
	private MemberInfDAO memberInfDao;
	@Autowired
	private MemberPrivateDAO privateDao;
	public TeamRouteDTO getRouteInfByTeamId(Long id, PageInfoDTO pageInfo){
		TeamRouteDTO dto = new TeamRouteDTO();
		 List <TeamRoute> list = teamRouteDao.findByTeamId(id, pageInfo);
		 if(list == null || list.size() == 0){
			 TeamInfo teamInf = teamDao.findById(id);
			 dto.setTeamInfo(teamInf.toDTO());
		 } else {
			 for(TeamRoute teamRoute : list){
				dto.setTeamInfo(teamRoute.getTeamInfo().toDTO());
				RouteInfDTO routeDTO = teamRoute.getRouteInf().toDTO();
				if(teamRoute.getAttachmentId() != null){
					AttachmentInf a = attachmentDAO.findById(teamRoute.getAttachmentId());
					if(a != null){
						String extFileName = a.getFileName().substring(a.getFileName().lastIndexOf("."));	
						routeDTO.setAttachmentUrl("/attachment/"+a.getId()+extFileName);
						routeDTO.setAttachmentFileName(a.getFileName());
					}
				}

				routeDTO.setStartDate(teamRoute.getDateStr());
				routeDTO.setEndDate(teamRoute.getEndDateStr());
				if(teamRoute.getEndDate().before(new Date())){
					routeDTO.setStatus(ROUTE_STATUS.NOT_FINISH.getValue());
				} else {
					routeDTO.setStatus(ROUTE_STATUS.FINISHED.getValue());
				}
				List<Object[]> viewSpotList = routeViewSpotDao.findViewSpotByRouteId(teamRoute.getRouteInf().getId());
				for(Object[] objArr : viewSpotList){
					ViewSpotInfo view = (ViewSpotInfo)objArr[0];
					ViewSpotDTO viewDto = view.toDTO();
					Time startTime = (Time)objArr[1];
					Time endTime = (Time)objArr[2];
					Integer numberOfDay = (Integer)objArr[3] - 1;
					if(numberOfDay < 0){
						numberOfDay = 0;
					}
					viewDto.setNumberOfDay(numberOfDay);
					Calendar cal = Calendar.getInstance();	
					cal.setTime(teamRoute.getDate()); 
					cal.add(Calendar.DAY_OF_YEAR, numberOfDay);
					cal.add(Calendar.HOUR_OF_DAY, 8);
					Long startDateLong = cal.getTime().getTime() + startTime.getTime();
					Long endDateLong = cal.getTime().getTime() + endTime.getTime();					
					viewDto.setStartDate(DateUtils.toTimeStr(new Date(startDateLong)));
					viewDto.setEndDate(DateUtils.toTimeStr(new Date(endDateLong)));
					List<String>urls = imageDao.findUrls(IMAGE_TYPE.VIEWSPOT, view.getId());
					if(urls != null && urls.size() > 0){
						viewDto.addImageUrl(urls);
					}
					routeDTO.addViewSport(viewDto);
				}
				dto.addRouteInfo(routeDTO);
			 }
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
		RouteInf firstRoute = null;
		boolean searchedRoute = false;
		Set<Long> memberIdSet = new HashSet<Long>();
		for(LocationLog location : list){
			if(location.getMemberInf().getId().longValue() != memberId.longValue()){
				if(!memberIdSet.contains(location.getMemberInf().getId())){
					dto.setTeamInfo(location.getTeamInfo().toDTO());
					LocationLogDTO locationDto = location.toDTO();
					// If the member never signs in the app, the location is null. So we assign the route start point as the member's location. 
					if(locationDto.getLatitude() == null || locationDto.getLongitude() == null
							|| locationDto.getLatitude() < 1 || locationDto.getLongitude() < 1){
						if(!searchedRoute){
							firstRoute = teamRouteDao.getFirstRouteByTeamId(teamId);
							searchedRoute = true;
						}
						if(firstRoute != null){
							locationDto.setLatitude(firstRoute.getStartLatitude());
							locationDto.setLongitude(firstRoute.getStartLongtitude());
						}
					}
					dto.addLocation(locationDto);
					memberIdSet.add(location.getMemberInf().getId());
				}
			}
		}
		List<Long> geoVisibleMemberIdList = privateDao.getInVisibilityByType(memberId, VISIBLE_TYPE.GEO.getValue());
		List<Long> phoneVisibleMemberIdList = privateDao.getInVisibilityByType(memberId, VISIBLE_TYPE.PHONE.getValue());
		dto.setGeoVisibleMemberIdList(geoVisibleMemberIdList);
		dto.setPhoneVisibleMemberIdList(phoneVisibleMemberIdList);
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
					teamRoute.setStatus(0);
					teamRoute.setDate(DateUtils.toDate(routeItem.getRouteForm().getDate()));
					teamRoute.setEndDate(DateUtils.toDate(routeItem.getRouteForm().getEndDate()));
					teamRoute.setCreateDate(team.getCreateDate());
					teamRoute.setUpdateDate(team.getCreateDate());
					teamRoute.setSysUser(team.getSysUser());
					teamRoute.setAttachmentId(routeItem.getAttachmentForm().getId());
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
		teamDao.updateMemberNum(team.getId());
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
					teamRoute.setStatus(0);
					teamRoute.setDate(DateUtils.toDate(item.getRouteForm().getDate()));
					teamRoute.setEndDate(DateUtils.toDate(item.getRouteForm().getEndDate()));
					teamRoute.setCreateDate(team.getCreateDate());
					teamRoute.setUpdateDate(team.getCreateDate());
					teamRoute.setSysUser(team.getSysUser());
					teamRoute.setEndDate(team.getEndDate());
					teamRoute.setAttachmentId(item.getAttachmentForm().getId());
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
		for(TeamRoute teamRoute : list){
			if(teamRoute.getAttachmentId() != null && teamRoute.getAttachmentId() > 0){
				AttachmentInf a = attachmentDAO.findById(teamRoute.getAttachmentId());
				if(a != null){
					teamRoute.setAttachmentFileName(a.getFileName());
				}
			}
		}
		return list;
	}


	/**
	 * @param teamId
	 * @return
	 */
	public List<LocationLogDTO> getTeamMemeberLocation(String teamId, List<Double> centerPoint) {
		List <LocationLog> list = locationDao.getLocationByTeamId(Long.valueOf(teamId));
		List <LocationLogDTO> result = new ArrayList<LocationLogDTO>();
		Double centerLati = null;
		Double centerLongi = null;
		Set<Long> memberIdSet = new HashSet<Long>();
		for(LocationLog log : list){
			MemberInf m = log.getMemberInf();
			if(!memberIdSet.contains(log.getMemberInf().getId())){
				if(log.getLatitude() != null && log.getLongitude() != null){
					if(centerLati == null && centerLongi == null){
						centerLati = log.getLatitude();
						centerLongi = log.getLongitude();
					}
					if(m.getMemberType().intValue() == MEMBER_TYPE.GUIDE.getValue()){
						centerLati = log.getLatitude();
						centerLongi = log.getLongitude();
					}
				}
				result.add(log.toDTO());
				memberIdSet.add(log.getMemberInf().getId());
			}
		}
		centerPoint.add(centerLongi);
		centerPoint.add(centerLati);
		return result;
	}
	
}
