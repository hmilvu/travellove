package com.travel.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.action.admin.form.ViewSpotItemForm;
import com.travel.common.Constants.MEMBER_TYPE;
import com.travel.common.Constants.MESSAGE_CREATE_TYPE;
import com.travel.common.Constants.MESSAGE_PRIORITY;
import com.travel.common.Constants.MESSAGE_RECEIVER_TYPE;
import com.travel.common.Constants.MESSAGE_REMIND_MODE;
import com.travel.common.Constants.MESSAGE_STATUS;
import com.travel.common.Constants.MESSAGE_TYPE;
import com.travel.common.Constants.SMS_STATUS;
import com.travel.common.Constants.SMS_TRIGGER;
import com.travel.common.Constants.TRIGGER_STATUS;
import com.travel.common.Constants.TRIGGER_TYPE;
import com.travel.common.admin.dto.SearchTriggerConfigDTO;
import com.travel.common.admin.dto.WeatherDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.dao.LocationLogDAO;
import com.travel.dao.MemberInfDAO;
import com.travel.dao.MessageDAO;
import com.travel.dao.RouteViewSpotDAO;
import com.travel.dao.TeamInfoDAO;
import com.travel.dao.TeamRouteDAO;
import com.travel.dao.TriggerConfigDAO;
import com.travel.dao.TriggerViewSpotDAO;
import com.travel.dao.ViewSpotInfoDAO;
import com.travel.entity.LocationLog;
import com.travel.entity.MemberInf;
import com.travel.entity.Message;
import com.travel.entity.TeamInfo;
import com.travel.entity.TeamRoute;
import com.travel.entity.TravelInf;
import com.travel.entity.TriggerConfig;
import com.travel.entity.TriggerViewSpot;
import com.travel.entity.ViewSpotInfo;
import com.travel.utils.DateUtils;

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
	private MemberInfDAO memberDao;
	@Autowired
	private RouteViewSpotDAO routeViewSpotDao;
	@Autowired
	private TeamRouteDAO teamRouteDao;
	@Autowired
	private MessageService messageService;
	@Autowired
	private MessageDAO messageDao;
	@Autowired
	private LocationLogDAO locationDao;	
	@Autowired
	private TriggerViewSpotDAO triggerViewDao;
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
	 * @param items 
	 */
	@Transactional
	public void updateTrigger(TriggerConfig trigger, List<ViewSpotItemForm> viewSpotItems) {
		triggerDao.update(trigger);
		triggerViewDao.deleteByTriggerIds(trigger.getId() + "");
		if(viewSpotItems != null){
			for(ViewSpotItemForm viewForm : viewSpotItems){
				TriggerViewSpot triggerView = new TriggerViewSpot();
				ViewSpotInfo view = new ViewSpotInfo();
				view.setId(viewForm.getViewSpotForm().getId());
				triggerView.setViewSpotInfo(view);
				triggerView.setTriggerConfig(trigger);
				triggerViewDao.save(triggerView);
			}
		}
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
		if(trigger.getTypeValue().intValue() == TRIGGER_TYPE.REMIND.getValue()){
			triggerRemind(trigger);
		} else if(trigger.getTypeValue().intValue() == TRIGGER_TYPE.INSTALL.getValue()){
			triggerInstall(trigger);
		}			
	}
	
	private void triggerInstall(TriggerConfig trigger) {
		Long travelId = trigger.getTravelId();
		List<TeamInfo> teamList =teamDao.getWillStartTeamByTravelId(travelId);
		for(TeamInfo team : teamList){
			List<Long> teamIdList = new ArrayList<Long>();
			teamIdList.add(team.getId());
			List<MemberInf> memberList = memberDao.findByTeamIds(teamIdList);
			for(MemberInf member : memberList){
				List<Message> existingMessages = messageDao.findTriggerMessage(MESSAGE_RECEIVER_TYPE.MEMBER, member.getId(), trigger.getId());
				if(existingMessages != null && existingMessages.size() > 0){
					continue;
				}
				Message msg1 = setupMessage(team.getTravelInf().getId(), trigger.getTriggerName(), trigger.getContent(), trigger.getId());
				msg1.setSmsTrigger(SMS_TRIGGER.ACTIVE.getValue());
				Timestamp startTimestamp = new Timestamp(System.currentTimeMillis()/86400000*86400000 + DateUtils.strToHHssTime(trigger.getStartTime()).getTime()); 
				msg1.setRemindTime(startTimestamp);
				if(trigger.getTimes() > 0){
					messageService.addMessageForReceiver(msg1, member.getId().toString(), MESSAGE_RECEIVER_TYPE.MEMBER);
				}
			}
		}
	}

	public void triggerTomorrowWheather(TriggerConfig trigger) {
		Long travelId = trigger.getTravelId();
		List<TeamInfo> teamList = teamDao.getActiveTeamByTravelId(travelId);
		PageInfoDTO pageInfo = new PageInfoDTO();
		pageInfo.setPageNumber(1);
		pageInfo.setPageSize(1000);
		for(TeamInfo team : teamList){
			WeatherDTO weatherDto = null;
			List<MemberInf> memberList = memberDao.findByTeamIds(team.getId()+"");
			boolean triggered = false;
			List <TeamRoute> list = teamRouteDao.findByTeamId(team.getId(), pageInfo);	
			log.info("线路总数 = " + list.size() + " teamId = " + team.getId());
			for(TeamRoute teamRoute : list){			
//				Timestamp tomorrow = new Timestamp(System.currentTimeMillis()/86400000*86400000 +  + 3600 * 24 * 1000);
				List<Object[]> viewSpotList = routeViewSpotDao.findViewSpotByRouteId(teamRoute.getRouteInf().getId());
				log.info("景点数量 = " + viewSpotList.size() + " routeId = " + teamRoute.getRouteInf().getId());
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
					Long currentLong = System.currentTimeMillis();
					log.info("景点 viewId = " + view.getId() + " viewName = " + view.getName());
					if(startDateLong - currentLong > 0 && startDateLong - currentLong < 24 * 3600 * 1000 ){ // 提前一天	
						try {
							log.info("查询天气线程休眠10秒");
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							log.error("查询天气线程休眠10秒错误", e);
						}
						if(weatherDto == null){
							weatherDto = weahterService.getWheatherData(view.getLatitude(), view.getLongitude());
						}
						String []data = null;
						if(weatherDto != null){
							data = weatherDto.getData();
						}
						if(data != null && data.length > 15){
							String weather = data[13] + " " + data[12] + " " + data[14];
							String content = trigger.getContent();
							content = StringUtils.replace(content, "{weatherData}", weather);
							content = StringUtils.replace(content, "{cityName}", weatherDto.getCityName());
							Message msg1 = setupMessage(team.getTravelInf().getId(), trigger.getTriggerName(), content, trigger.getId());
							msg1.setRemindTime(new Timestamp(System.currentTimeMillis()));
							msg1.setRemindMode(MESSAGE_REMIND_MODE.NOW.getValue());
							if(trigger.getTimes() > 0){
								for(MemberInf member : memberList){
									List<Message>msgList = messageService.addMessageForReceiver(msg1, member.getId().toString(), MESSAGE_RECEIVER_TYPE.MEMBER);
									if(msgList != null && msgList.size() > 0){									
										messageService.sendSMS(msgList.get(0), member);
										messageService.sendPushMsg(msgList.get(0), member);
										triggered = true;
									}
								}
							}
						}
						if(triggered){
							break; // break view
						}
					}
					if(triggered){
						break; // break route
					}
				}
			}
		}
	}
	
	public void triggerTodayWheather(TriggerConfig trigger) {
		Long travelId = trigger.getTravelId();
		List<TeamInfo> teamList = teamDao.getActiveTeamByTravelId(travelId);
		PageInfoDTO pageInfo = new PageInfoDTO();
		pageInfo.setPageNumber(1);
		pageInfo.setPageSize(1000);
		for(TeamInfo team : teamList){
			WeatherDTO weatherDto = null;
			List<MemberInf> memberList = memberDao.findByTeamIds(team.getId()+"");
			boolean triggered = false;
			List <TeamRoute> list = teamRouteDao.findByTeamId(team.getId(), pageInfo);	
			log.info("线路总数 = " + list.size() + " teamId = " + team.getId());
			for(TeamRoute teamRoute : list){			
//				Timestamp tomorrow = new Timestamp(System.currentTimeMillis()/86400000*86400000 +  + 3600 * 24 * 1000);
				List<Object[]> viewSpotList = routeViewSpotDao.findViewSpotByRouteId(teamRoute.getRouteInf().getId());
				log.info("景点数量 = " + viewSpotList.size() + " routeId = " + teamRoute.getRouteInf().getId());
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
					Long currentLong = System.currentTimeMillis();
					log.info("景点 viewId = " + view.getId() + " viewName = " + view.getName());
					if(startDateLong - currentLong > 0 && startDateLong - currentLong < 24 * 3600 * 1000 ){ // 提前一天	
						try {
							log.info("查询天气线程休眠10秒");
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							log.error("查询天气线程休眠10秒错误", e);
						}
						if(weatherDto == null){
							weatherDto = weahterService.getWheatherData(view.getLatitude(), view.getLongitude());
						}
						String []data = null;
						if(weatherDto != null){
							data = weatherDto.getData();
						}
						if(data != null && data.length > 15){
							String weather = data[13] + " " + data[12] + " " + data[14];
							String content = trigger.getContent();
							content = StringUtils.replace(content, "{weatherData}", weather);
							content = StringUtils.replace(content, "{cityName}", weatherDto.getCityName());
							Message msg1 = setupMessage(team.getTravelInf().getId(), trigger.getTriggerName(), content, trigger.getId());
							msg1.setRemindTime(new Timestamp(System.currentTimeMillis()));
							msg1.setRemindMode(MESSAGE_REMIND_MODE.NOW.getValue());
							if(trigger.getTimes() > 0){
								for(MemberInf member : memberList){
									List<Message>msgList = messageService.addMessageForReceiver(msg1, member.getId().toString(), MESSAGE_RECEIVER_TYPE.MEMBER);
									if(msgList != null && msgList.size() > 0){									
										messageService.sendSMS(msgList.get(0), member);
										messageService.sendPushMsg(msgList.get(0), member);
										triggered = true;
									}
								}
							}
						}
						if(triggered){
							break; // break view
						}
					}
					if(triggered){
						break; // break route
					}
				}
			}
		}
	}
	
	private void triggerRemind(TriggerConfig trigger) {
		Long travelId = trigger.getTravelId();
		List<TeamInfo> teamList =teamDao.getWillStartTeamByTravelId(travelId);
		for(TeamInfo team : teamList){
			List<Message> existingMessages = messageDao.findWaringTriggerMessage(team.getId(), trigger.getId());
			if(existingMessages != null && existingMessages.size() > 0){
				continue;
			}
			String content = trigger.getContent();
			if(StringUtils.contains(content, "{name}")){
				content = StringUtils.replace(content, "{name}", team.getName());
			}
			Message msg1 = setupMessage(team.getTravelInf().getId(), trigger.getTriggerName(), content, trigger.getId());
			Timestamp startTimestamp = new Timestamp(System.currentTimeMillis()/86400000*86400000 + DateUtils.strToHHssTime(trigger.getStartTime()).getTime()); 
			Timestamp endTimestamp = new Timestamp(System.currentTimeMillis()/86400000*86400000 + DateUtils.strToHHssTime(trigger.getEndTime()).getTime());
			Timestamp middleTimestamp = new Timestamp((startTimestamp.getTime() + endTimestamp.getTime())/2);
			msg1.setRemindTime(startTimestamp);
			List<MemberInf> memberList = memberDao.findByTeamIds(team.getId()+"");
			if(trigger.getTimes() > 0){
				messageService.addMessageForTeamMembers(memberList, msg1, team.getId()+"");
			}
			if(trigger.getTimes() > 1){
				Message msg2 =  msg1.clone();
				msg2.setRemindTime(endTimestamp);
				messageService.addMessageForTeamMembers(memberList, msg2, team.getId()+"");
			} 
			if(trigger.getTimes() > 2){
				Message msg3 = msg1.clone();
				msg3.setRemindTime(middleTimestamp);								
				messageService.addMessageForTeamMembers(memberList, msg3, team.getId()+"");
			}
		}
	}
	
	private Message setupMessage(Long travelId, String topic, String content, Long triggerId){
		Message msg = new Message();
		msg.setRemindMode(MESSAGE_REMIND_MODE.LATER.getValue());
		msg.setStatus(MESSAGE_STATUS.ACTIVE.getValue());
		msg.setPriority(MESSAGE_PRIORITY.NORMAL.getValue());
		msg.setType(MESSAGE_TYPE.NOTIFICATION.getValue());
		msg.setTopic(topic);
		msg.setContent(content);
		msg.setCreateType(MESSAGE_CREATE_TYPE.SYSUSER.getValue());
		msg.setCreateId(1L);
		TravelInf travelInf = new TravelInf();
		travelInf.setId(travelId);
		msg.setTravelInf(travelInf);
		msg.setTriggerId(triggerId);
		return msg;
	}
	
	public void triggerVelocity(MemberInf member, Double latitude, Double longitude) {
		LocationLog lastLocation = locationDao.getLocationByMember(member.getTeamInfo().getId(), member.getId());
		if(lastLocation != null){
			long current = System.currentTimeMillis();
			double distance = getDistance(latitude, longitude, lastLocation.getLatitude(), lastLocation.getLongitude());
			long duration = current - lastLocation.getCreateDate().getTime();
			Double velocity = Math.abs(distance*1.0 / (duration * 1.0 / 3600000));
			log.info("计算出的平均速度为：velocity = " + velocity + ";" + latitude +";"+ longitude +";"+ lastLocation.getLatitude() +";"+ lastLocation.getLongitude());
			log.info("当前时间（毫秒）：" + current);
			log.info("上次地理位置记录时间（毫秒）: " + lastLocation.getCreateDate().getTime());
			this.triggerVelocity(member, velocity);
		} else{
			log.info("no location. memberid = " + member.getId() + " phone = " + member.getTravelerMobile());
		}
	}

	/**
	 * 
	 */
	public void triggerVelocity(MemberInf member, Double velocity) {
		TeamInfo team = teamDao.findById(member.getTeamInfo().getId());
		TriggerConfig trigger = triggerDao.getByType(team.getTravelInf().getId(), TRIGGER_TYPE.VELOCITY);
		if(trigger != null){
			Double triggerValue = null;
			try{
				triggerValue = Double.valueOf(trigger.getConditionValue());
			} catch(Throwable ignore){
				triggerValue = Double.valueOf(999999999);
			}
			if(velocity > triggerValue){				
				Long todayTime = System.currentTimeMillis()/86400000*86400000;
				if(team.getBeginDate().getTime() <= todayTime && todayTime <= team.getEndDate().getTime()+3600 * 24 * 1000){
					Long startTime = team.getBeginDate().getTime() + DateUtils.strToHHssTime(trigger.getStartTime()).getTime();
					Long endTime = team.getEndDate().getTime() + DateUtils.strToHHssTime(trigger.getEndTime()).getTime();
					if(System.currentTimeMillis() >= startTime && System.currentTimeMillis() <= endTime){
						List<MemberInf> list = memberDao.getTeamMemberByType(team.getId(), MEMBER_TYPE.DRIVER, MEMBER_TYPE.GUIDE);
						if(list.size() > 0){
							for(MemberInf reminder : list){
								List<Message> triggeredMessages = messageDao.getLastHourMessage(MESSAGE_RECEIVER_TYPE.MEMBER, reminder.getId(), trigger.getId());
								if(triggeredMessages.size() < trigger.getTimes()){
									String content = trigger.getContent();
									if(StringUtils.contains(content, "{value}")){
										content = StringUtils.replace(content, "{value}", triggerValue+"");
									}
									Message msg = setupMessage(team.getTravelInf().getId(), trigger.getTriggerName(), content, trigger.getId());
									msg.setSmsTrigger(trigger.getSendSMS());
									Timestamp now = new Timestamp(System.currentTimeMillis());
									msg.setRemindTime(now);
									msg.setRemindMode(MESSAGE_REMIND_MODE.NOW.getValue());
									List<Message> msgList = messageService.addMessageForReceiver(msg, reminder.getId().toString(), MESSAGE_RECEIVER_TYPE.MEMBER);
									messageService.sendPushMsg(msgList.get(0), reminder);
								}
							}
						}
					}
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		TriggerConfigService s = new TriggerConfigService();
		double a = s.getDistance(31.349134, 121.488716, 31.275961, 121.483952);
		System.out.println("a = " + a);
	}

	/**
	 * @param member
	 * @param latitude
	 * @param longitude
	 */
	public void triggerDistance(MemberInf member, Double latitude,
			Double longitude) {
		TeamInfo team = teamDao.findById(member.getTeamInfo().getId());
		Long todayTime = System.currentTimeMillis()/86400000*86400000;
		if(team.getBeginDate().getTime() > todayTime || todayTime > team.getEndDate().getTime() + 3600 * 24 * 1000){
			log.info("旅行团已经失效, team = " + team.toString());
			return;
		}
		TriggerConfig trigger = triggerDao.getByType(team.getTravelInf().getId(), TRIGGER_TYPE.MEMBER_DISTANCE);
		MemberInf guider = memberDao.getMemberByType(member.getTeamInfo().getId(), MEMBER_TYPE.GUIDE);
		if(guider != null && trigger != null){
			LocationLog location = locationDao.getLocationByMember(member.getTeamInfo().getId(), guider.getId());
			log.info("计算距离 memberId = " + member.getId() + " guiderId = " + guider.getId());
			//导游的位置是最近一个小时的位置
//			boolean isNowLocation = (System.currentTimeMillis() - location.getLocateTime().getTime()) > 0 
//					&& (System.currentTimeMillis() - location.getLocateTime().getTime()) < 3600 * 1000;
//			if(location != null && isNowLocation){
			if(location != null){
				double distance = getDistance(latitude, longitude, location.getLatitude(), location.getLongitude());
				log.info("计算距离 memberId = " + member.getId() + " guiderId = " + guider.getId());
				log.info("latitude1="+latitude+", longitude1="+longitude+", latitude2="+location.getLatitude()+", longitude2="+location.getLongitude() + ", distance="+distance);
				Double triggerValue = null;
				try{
					triggerValue = Double.valueOf(trigger.getConditionValue());
				} catch(Throwable ignore){
					triggerValue = Double.valueOf(-1);
				}
				if(triggerValue > 0 && distance > triggerValue){
					List<Message> triggeredMessages = messageDao.getLastHourMessage(MESSAGE_RECEIVER_TYPE.MEMBER, member.getId(), trigger.getId());
					if(triggeredMessages.size() < trigger.getTimes()){
						String content = trigger.getContent();
						if(StringUtils.contains(content, "{value}")){
							content = StringUtils.replace(content, "{value}", triggerValue+"");
						}
						if(StringUtils.contains(content, "{name}")){
							content = StringUtils.replace(content, "{name}", "您");
						}
						Message msg = setupMessage(team.getTravelInf().getId(), trigger.getTriggerName(), content, trigger.getId());
						Timestamp now = new Timestamp(System.currentTimeMillis());
						msg.setRemindTime(now);
						msg.setRemindMode(MESSAGE_REMIND_MODE.NOW.getValue());
						List<Message> msgList = messageService.addMessageForReceiver(msg, member.getId().toString(), MESSAGE_RECEIVER_TYPE.MEMBER);
						messageService.sendPushMsg(msgList.get(0), member);
						messageService.sendSMS(msgList.get(0), member);
					} else {
						log.info("会员提醒超过提醒次数 memberId = " + member.getId());
					}
					triggeredMessages = messageDao.getLastHourMessage(MESSAGE_RECEIVER_TYPE.MEMBER, guider.getId(), trigger.getId());
					if(triggeredMessages.size() < trigger.getTimes()){
						String content = trigger.getContent();
						if(StringUtils.contains(content, "{value}")){
							content = StringUtils.replace(content, "{value}", triggerValue+"");
						}
						if(StringUtils.contains(content, "{name}")){
							content = StringUtils.replace(content, "{name}", member.getMemberName());
						}
						Message msg = setupMessage(team.getTravelInf().getId(), trigger.getTriggerName(), content, trigger.getId());
						Timestamp now = new Timestamp(System.currentTimeMillis());
						msg.setRemindTime(now);
						msg.setRemindMode(MESSAGE_REMIND_MODE.NOW.getValue());
						List<Message> msgList = messageService.addMessageForReceiver(msg, guider.getId().toString(), MESSAGE_RECEIVER_TYPE.MEMBER);
						messageService.sendPushMsg(msgList.get(0), guider);
						messageService.sendSMS(msgList.get(0), guider);
					} else {
						log.info("导游提醒超过提醒次数 memberId = " + guider.getId());
					}
				}
			} else{
				log.info("导游位置不存在或者导游位置没有更新（已经是1个小时前的位置，所以无法计算最新距离）。LocationLog = " + location);
			}
		}		
	}
	
	private double getDistance(double lat1, double lon1, double lat2, double lon2) { 
		if(lat1 < 1 || lon1 < 1 || lat2 < 1 || lon2 < 1){
			return 0;
		}
        double R = 6371; 
        double distance = 0.0; 
        double dLat = (lat2 - lat1) * Math.PI / 180; 
        double dLon = (lon2 - lon1) * Math.PI / 180; 
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) 
                + Math.cos(lat1 * Math.PI / 180) 
                * Math.cos(lat2 * Math.PI / 180) * Math.sin(dLon / 2) 
                * Math.sin(dLon / 2); 
        distance = (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))) * R; 
        return distance; 
    }

	/**
	 * @param id
	 * @return
	 */
	public List<TriggerViewSpot> getTriggerViewSpots(Long triggerId) {
		return triggerViewDao.getByTriggerId(triggerId); 
	}

	/**
	 * @param member
	 */
	public void triggerViewSpotWarning(MemberInf member, Double latitude,
			Double longitude) {
		TeamInfo team = teamDao.findById(member.getTeamInfo().getId());
		Long todayTime = System.currentTimeMillis()/86400000*86400000;
		if(team.getBeginDate().getTime() > todayTime || todayTime > team.getEndDate().getTime()+ 3600 * 24 * 1000){
			log.info("旅行团已经失效, team = " + team.toString());
			return;
		}
		List<TriggerConfig> triggerList = triggerDao.getViewSpotTriggerByType(team.getTravelInf().getId(), TRIGGER_TYPE.VIEW_SPOT_WARNING);
		for(TriggerConfig trigger : triggerList){
			List <Long> routeIdList = teamRouteDao.getRouteIdListByTeamId(team.getId());	
			if(routeIdList != null && routeIdList.size() > 0){
				List <Long> viewSpotIdList = viewSpotDao.getViewSpotIdListByRouteIdList(routeIdList);
				if(viewSpotIdList != null && viewSpotIdList.size() > 0){
					List <ViewSpotInfo> viewSpotList = viewSpotDao.getTriggeredViewSpot(trigger.getId(), viewSpotIdList);
					for(ViewSpotInfo view : viewSpotList){
						try{
							List<Message> existingMessages = messageDao.getLastHourMessage(MESSAGE_RECEIVER_TYPE.MEMBER, team.getId(), trigger.getId());
							if(existingMessages.size() < trigger.getTimes()){
								double distance = getDistance(latitude, longitude, view.getLatitude(), view.getLongitude());
								Double triggerValue = null;
								try{
									triggerValue = Double.valueOf(trigger.getConditionValue());
								} catch(Throwable ignore){
									triggerValue = Double.valueOf(-1);
								}
								if(triggerValue > 0 && distance <= triggerValue){
									String content = trigger.getContent();									
									if(StringUtils.contains(content, "{name}")){
										content = StringUtils.replace(content, "{name}", view.getName());
									}
									Message msg = setupMessage(team.getTravelInf().getId(), trigger.getTriggerName(), content, trigger.getId());
									Timestamp now = new Timestamp(System.currentTimeMillis());
									msg.setRemindTime(now);
									msg.setRemindMode(MESSAGE_REMIND_MODE.NOW.getValue());
									List<MemberInf> memberList = memberDao.findByTeamIds(team.getId()+"");
									List<Message> msgList = messageService.addMessageForTeamMembers(memberList, msg, team.getId()+"");
									for(int i = 0; i < msgList.size(); i++){
										messageService.sendSMS(msgList.get(i), memberList.get(i));
										messageService.sendPushMsg(msgList.get(i), memberList.get(i));
									}
								}
							}
						}catch(Throwable e){
							log.error("景点推送错误", e);
						}
					}
				}
			}
		}
	}
}
