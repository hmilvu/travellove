/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.mobile;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.travel.action.BaseAction;
import com.travel.common.Constants.MEMBER_STATUS;
import com.travel.common.Constants.MEMBER_TYPE;
import com.travel.common.Constants.OS_TYPE;
import com.travel.common.Constants.VISIBLE_TYPE;
import com.travel.common.dto.FailureResult;
import com.travel.common.dto.MemberDTO;
import com.travel.common.dto.MemberPrivateDTO;
import com.travel.common.dto.SuccessResult;
import com.travel.entity.MemberInf;
import com.travel.entity.SysUser;
import com.travel.entity.TeamInfo;
import com.travel.service.MemberService;
import com.travel.service.SysUserService;
import com.travel.service.TeamInfoService;
import com.travel.service.TriggerConfigService;

/**
 * @author Lenovo
 * 
 */
public class MemberAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private MemberService memberService;
	@Autowired
	private TeamInfoService teamService;
	@Autowired
	private TriggerConfigService triggerService;
	@Autowired
	private SysUserService sysUserService;
	public void search() {
		String data = getMobileData();
		Object id = getMobileParameter(data, "id");
		Long idLong = Long.valueOf(0);
		try {
			idLong = Long.valueOf(id.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("id类型错误");
			sendToMobile(result);
			return;
		}
		MemberInf member = memberService.getMemberById(idLong);
		if (member != null && member.getId() > 0) {
			SuccessResult<MemberDTO> result = new SuccessResult<MemberDTO>(
					member.toDTO());
			sendToMobile(result);
		} else {
			FailureResult result = new FailureResult("该用户不存在");
			sendToMobile(result);
		}
	}

	public void update() {
		String data = getMobileData();
		Object id = getMobileParameter(data, "id");
		Long idLong = Long.valueOf(0);
		try {
			idLong = Long.valueOf(id.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("id类型错误");
			sendToMobile(result);
			return;
		}
		MemberInf member = memberService.getMemberById(idLong);
		if (member != null && member.getId() > 0) {
			member
					.setMemberName((String) getMobileParameter(data,
							"memberName"));
			member.setNickname((String) getMobileParameter(data, "nickname"));
			member.setTravelerMobile((String) getMobileParameter(data,
					"travelerMobile"));
//			Object sexObj = getMobileParameter(data, "sex");
//			Integer sex = Integer.valueOf(0);
//			try {
//				sex = Integer.valueOf(sexObj.toString());
//			} catch (Throwable ignore) {
//			}		
//			member.setSex(sex);

//			Object ageObj = getMobileParameter(data, "age");
//			Integer age = Integer.valueOf(0);
//			try {
//				age = Integer.valueOf(ageObj.toString());
//			} catch (Throwable ignore) {
//			}
//			;
//			member.setAge(age);

//			Object idTypeObj = getMobileParameter(data, "idType");
//			Integer idType = Integer.valueOf(0);
//			try {
//				idType = Integer.valueOf(idTypeObj.toString());
//			} catch (Throwable ignore) {
//			}
//			;
//			member.setIdType(idType);

//			member.setIdNo((String) getMobileParameter(data, "idNo"));
//			member.setAvatarUrl((String) getMobileParameter(data, "avatarUrl"));
			member.setProfile((String) getMobileParameter(data, "profile"));
			member.setInterest((String) getMobileParameter(data, "interest"));
			member.setUpdateDate(new Timestamp(new Date().getTime()));
			int updateResult = memberService.updateMember(member);
			if (updateResult == 0) {
				SuccessResult<String> result = new SuccessResult<String>(Action.SUCCESS);
				sendToMobile(result);
			} else {
				FailureResult result = new FailureResult("更新失败");
				sendToMobile(result);
			}
		} else {
			FailureResult result = new FailureResult("id不存在");
			sendToMobile(result);
		}
	}
	
	public void updateLocation(){
		String data = getMobileData();
		Object id = getMobileParameter(data, "id");
		Object latitudeObj = getMobileParameter(data, "latitude");
		Object longitudeObj = getMobileParameter(data, "longitude");
		Long idLong = Long.valueOf(0);
		try {
			idLong = Long.valueOf(id.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("id类型错误");
			sendToMobile(result);
			return;
		}
		Double latitude = null;
		Double longitude = null;
		try{
			latitude = Double.valueOf(latitudeObj.toString());
			longitude =Double.valueOf(longitudeObj.toString());
		} catch(Throwable e){
			FailureResult result = new FailureResult("经纬度类型错误");
			sendToMobile(result);
			return;
		}
		MemberInf member = memberService.getMemberById(idLong);
		if (member != null && member.getId() > 0 && member.getTeamInfo() != null && member.getTeamInfo().getId() > 0) {
//			triggerVelocity(data, member);
			triggerVelocity(longitude, latitude, member);
			triggerDistance(longitude, latitude, member);
			triggerViewSpotWarning(longitude, latitude, member);			
			memberService.saveMemberLocation(member, longitude, latitude);
			SuccessResult<String> result = new SuccessResult<String>(Action.SUCCESS);
			sendToMobile(result);
		} else {
			FailureResult result = new FailureResult("id不存在");
			sendToMobile(result);
		}
	}

	/**
	 * @param member
	 */
	private void triggerViewSpotWarning(Double longitude, Double latitude, MemberInf member) {
		try {
			if(member.getMemberType().intValue() == MEMBER_TYPE.GUIDE.getValue() && longitude > 1 && latitude > 1){
				triggerService.triggerViewSpotWarning(member, latitude, longitude);
			}	
		} catch (Throwable e) {
			log.error("更新会员地理位置时，触发景点消息异常", e);
		}
	}
	
	private void triggerVelocity(Double longitude, Double latitude, MemberInf member) {
		try {
			if(longitude > 1 && latitude > 1){
				triggerService.triggerVelocity(member, latitude, longitude);	
			}
		} catch (Throwable e) {
			log.error("更新会员地理位置时", e);
		}
	}

	/**
	 * @param data
	 * @param member
	 */
	@Deprecated
	private void triggerVelocity(String data, MemberInf member) {
		Object vel = getMobileParameter(data, "velocity");
		Double velocity = Double.valueOf(0);
		try {
			velocity = Double.valueOf(vel.toString());
			if(velocity > 0){	
				triggerService.triggerVelocity(member, velocity);
			}
		} catch (Throwable e) {
			log.error("更新会员地理位置时，速度值格式错误velocity = " + vel, e);
		}
	}
	
	/**
	 * @param data
	 * @param member
	 */
	private void triggerDistance(Double longitude, Double latitude, MemberInf member) {
		try {
			if(member.getMemberType().intValue() == MEMBER_TYPE.TRAVELER.getValue() && longitude > 1 && latitude > 1){
				triggerService.triggerDistance(member, latitude, longitude);
			}
		} catch (Throwable e) {
			log.error("更新会员地理位置时，触发距离消息异常", e);
		}
	}
	
	public void uploadChannelId(){
		String data = getMobileData();
		Object id = getMobileParameter(data, "memberId");
		Long idLong = Long.valueOf(0);
		try {
			idLong = Long.valueOf(id.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("memberId类型错误");
			sendToMobile(result);
			return;
		}
		MemberInf member = memberService.getMemberById(idLong);
		if(member != null && member.getId() != null){
			Object baiduUserId = getMobileParameter(data, "baiduUserId");
			if(baiduUserId == null){
				FailureResult result = new FailureResult("baiduUserId类型错误");
				sendToMobile(result);
				return;
			} else {
				member.setBaiduUserId(baiduUserId.toString());
			}
			Object channelId = getMobileParameter(data, "channelId");
			if(channelId != null){
				try{
					member.setChannelId(Long.valueOf(channelId.toString()));
				} catch(Throwable e){
					FailureResult result = new FailureResult("channelId类型错误");
					sendToMobile(result);
					return;
				}
				Object osType = getMobileParameter(data, "osType");
				if(osType != null && StringUtils.equals(osType.toString(), OS_TYPE.IOS.getValue()+"")){
					member.setOsType(OS_TYPE.IOS.getValue());
				} else{
					member.setOsType(OS_TYPE.ANDROID.getValue());
				}
				int num = memberService.updateMember(member);
				if(num == 0){
					SuccessResult<String> result = new SuccessResult<String>(Action.SUCCESS);
					sendToMobile(result);
				} else {
					FailureResult result = new FailureResult("保存channelId错误");
					sendToMobile(result);
				}
			}
		} else {
			FailureResult result = new FailureResult("memberId不存在");
			sendToMobile(result);
		}
	}
	
	public String changePassword(){
		String data = getMobileData();
		Object id = getMobileParameter(data, "id");
		Long idLong = Long.valueOf(0);
		try {
			idLong = Long.valueOf(id.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("id类型错误");
			sendToMobile(result);
			return null;
		}
		Object oldPassword = getMobileParameter(data, "oldPassword");
		Object newPassword = getMobileParameter(data, "newPassword");
		Object confirmPassword = getMobileParameter(data, "confirmPassword");
		if(oldPassword == null || StringUtils.isBlank(oldPassword.toString())){
			FailureResult result = new FailureResult("原密码不能为空");
			result.setCode(1);
			sendToMobile(result);
			return null;
		}
		if(newPassword == null || StringUtils.isBlank(newPassword.toString())){
			FailureResult result = new FailureResult("新密码不能为空");
			result.setCode(2);
			sendToMobile(result);
			return null;
		}
		if(confirmPassword == null || StringUtils.isBlank(confirmPassword.toString())){
			FailureResult result = new FailureResult("确认密码不能为空");
			result.setCode(3);
			sendToMobile(result);
			return null;
		}
		if(!StringUtils.equals(newPassword.toString(), confirmPassword.toString())){
			FailureResult result = new FailureResult("新密码和确认密码不同");
			result.setCode(4);
			sendToMobile(result);
			return null;
		}
		MemberInf member = memberService.getMemberById(idLong);
		if (member != null && member.getId() > 0) {
			if(StringUtils.equals(oldPassword.toString(), member.getPassword())){
				member.setPassword(newPassword.toString());
				memberService.updateMember(member);
				SuccessResult<String> result = new SuccessResult<String>(Action.SUCCESS);
				sendToMobile(result);
				return null;
			} else {
				FailureResult result = new FailureResult("原密码密码不同");
				result.setCode(5);
				sendToMobile(result);
				return null;
			}
		} else {
			FailureResult result = new FailureResult("该用户不存在");
			sendToMobile(result);
			return null;
		}
	}
	
	public String getVisibility(){
		String data = getMobileData();
		Object memberId = getMobileParameter(data, "memberId");
		Long memberIdLong = Long.valueOf(0);
		try {
			memberIdLong = Long.valueOf(memberId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("memberId类型错误");
			sendToMobile(result);
			return null;
		}
		Object teamId = getMobileParameter(data, "teamId");
		Long teamIdLong = Long.valueOf(0);
		try {
			teamIdLong = Long.valueOf(teamId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("teamId类型错误");
			sendToMobile(result);
			return null;
		}
		MemberInf member = memberService.getMemberById(memberIdLong);
		if(member == null || member.getId().intValue() <= 0){
			FailureResult result = new FailureResult("此会员"+memberId+"不存在");
			sendToMobile(result);
			return null;
		}
		if(member.getTeamInfo().getId().longValue() != teamIdLong.longValue()){
			FailureResult result = new FailureResult("此会员"+memberId+"不在此团"+teamIdLong+"中存在");
			sendToMobile(result);
			return null;
		}
		MemberPrivateDTO dto = memberService.getVisiableMember(memberIdLong, teamIdLong);	
		SuccessResult<MemberPrivateDTO> result = new SuccessResult<MemberPrivateDTO>(dto);
		sendToMobile(result);
		return null;
	}
	
	public String addVisibility(){
		String data = getMobileData();
		Object memberId = getMobileParameter(data, "memberId");
		Long memberIdLong = Long.valueOf(0);
		try {
			memberIdLong = Long.valueOf(memberId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("memberId类型错误");
			sendToMobile(result);
			return null;
		}
		
		Object geoVisibleIdListObj = getMobileParameter(data, "geoVisibleIdList");		
		Object phoneVisibleIdListObj = getMobileParameter(data, "phoneVisibleIdList");	
		String geoVisibleIdList = geoVisibleIdListObj == null ? "" : geoVisibleIdListObj.toString();
		String phoneVisibleIdList = phoneVisibleIdListObj == null ? "" : phoneVisibleIdListObj.toString();
		
		memberService.addMemberVisibility(memberIdLong, geoVisibleIdList, VISIBLE_TYPE.GEO);
		memberService.addMemberVisibility(memberIdLong, phoneVisibleIdList, VISIBLE_TYPE.PHONE);
		SuccessResult<String> result = new SuccessResult<String>(Action.SUCCESS);
		sendToMobile(result);
		return null;
	}
	
	public String addAdvice(){
		String data = getMobileData();
		Object memberId = getMobileParameter(data, "memberId");
		Long memberIdLong = Long.valueOf(0);
		try {
			memberIdLong = Long.valueOf(memberId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("memberId类型错误");
			sendToMobile(result);
			return null;
		}
		Object contentObj = getMobileParameter(data, "content");
		if(contentObj == null || StringUtils.isBlank(contentObj.toString())){
			FailureResult result = new FailureResult("content不能为空");
			sendToMobile(result);
			return null;
		}
		Object topicObj = getMobileParameter(data, "topic");
		String topic = topicObj == null ? "" : topicObj.toString();
		memberService.addAdvice(memberIdLong, topic, contentObj.toString());
		SuccessResult<String> result = new SuccessResult<String>(Action.SUCCESS);
		sendToMobile(result);
		return null;
	}
	
//	public String buy(){
//		String data = getMobileData();
//		Object number = getMobileParameter(data, "number");
//		Integer numberInt = null;
//		try{
//			numberInt = Double.valueOf(number.toString()).intValue();
//		} catch(Throwable e){
//			FailureResult result = new FailureResult("number格式错误");
//			sendToMobile(result);
//			return null;
//		}
//		if(numberInt <= 0){
//			FailureResult result = new FailureResult("number必须大于0");
//			sendToMobile(result);
//			return null;
//		}
//		Object memberId = getMobileParameter(data, "memberId");
//		Long memberIdLong = Long.valueOf(0);
//		try {
//			memberIdLong = Long.valueOf(memberId.toString());
//		} catch (Exception e) {
//			FailureResult result = new FailureResult("memberId类型错误");
//			sendToMobile(result);
//			return null;
//		}
//		
//		Object itemId = getMobileParameter(data, "itemId");
//		Long itemIdLong = Long.valueOf(0);
//		try {
//			itemIdLong = Long.valueOf(itemId.toString());
//		} catch (Exception e) {
//			FailureResult result = new FailureResult("itemId类型错误");
//			sendToMobile(result);
//			return null;
//		}
//		MemberInf member = memberService.getMemberById(memberIdLong);
//		if(member == null || member.getId().intValue() <= 0){
//			FailureResult result = new FailureResult("此会员"+memberId+"不存在");
//			sendToMobile(result);
//			return null;
//		}
//		ItemInf item = itemService.getItemById(itemIdLong);
//		if(item == null || item.getId().intValue() <= 0){
//			FailureResult result = new FailureResult("此销售品"+itemId+"不存在");
//			sendToMobile(result);
//			return null;
//		}
//		memberService.buyItems(member, item, numberInt);
//		SuccessResult<String> result = new SuccessResult<String>(Action.SUCCESS);
//		sendToMobile(result);
//		return null;
//	}
	
	public String create(){
		String data = getMobileData();
		Object teamIdObj = getMobileParameter(data, "teamId");
		if(teamIdObj == null || StringUtils.isBlank(teamIdObj.toString())){
			FailureResult result = new FailureResult("teamId不能为空");
			sendToMobile(result);
			return null;
		}
		Object nameObj = getMobileParameter(data, "name");
		if(nameObj == null || StringUtils.isBlank(nameObj.toString())){
			FailureResult result = new FailureResult("name不能为空");
			sendToMobile(result);
			return null;
		}
		Object memberTypeObj = getMobileParameter(data, "memberType");
		if(memberTypeObj == null || StringUtils.isBlank(memberTypeObj.toString())){
			FailureResult result = new FailureResult("memberType不能为空");
			sendToMobile(result);
			return null;
		}
		Object nicknameObj = getMobileParameter(data, "nickname");
		Object phoneNumberObj = getMobileParameter(data, "phoneNumber");
		if(phoneNumberObj == null || StringUtils.isBlank(phoneNumberObj.toString())){
			FailureResult result = new FailureResult("phoneNumber不能为空");
			sendToMobile(result);
			return null;
		}
		Object passwordObj = getMobileParameter(data, "password");		
		if(passwordObj == null || StringUtils.isBlank(passwordObj.toString())){
			FailureResult result = new FailureResult("password不能为空");
			sendToMobile(result);
			return null;
		}
		Object sexObj = getMobileParameter(data, "sex");	
		if(sexObj == null || StringUtils.isBlank(sexObj.toString())){
			FailureResult result = new FailureResult("sex不能为空");
			sendToMobile(result);
			return null;
		}
		Object ageObj = getMobileParameter(data, "age");	
		if(ageObj == null || StringUtils.isBlank(ageObj.toString())){
			FailureResult result = new FailureResult("age不能为空");
			sendToMobile(result);
			return null;
		}
		Object idTypeObj = getMobileParameter(data, "idType");	
		if(idTypeObj == null || StringUtils.isBlank(idTypeObj.toString())){
			FailureResult result = new FailureResult("idType不能为空");
			sendToMobile(result);
			return null;
		}
		Object idNoObj = getMobileParameter(data, "idNo");
		if(idNoObj == null || StringUtils.isBlank(idNoObj.toString())){
			FailureResult result = new FailureResult("idNo不能为空");
			sendToMobile(result);
			return null;
		}
		Object interestObj = getMobileParameter(data, "interest");	
		Object profileObj = getMobileParameter(data, "profile");	
		
		TeamInfo team = teamService.getTeamById(Long.valueOf(teamIdObj.toString()));
		if(team == null){
			FailureResult result = new FailureResult("team不存在");
			sendToMobile(result);
			return null;
		}
		MemberInf memberInf = new MemberInf();
		memberInf.setMemberName(nameObj.toString());
		memberInf.setMemberType(Integer.valueOf(memberTypeObj.toString()));
		memberInf.setNickname(nicknameObj == null ? "" : nicknameObj.toString());
		memberInf.setTravelerMobile(phoneNumberObj.toString());
		memberInf.setPassword(passwordObj.toString());
		memberInf.setSex(Integer.valueOf(sexObj.toString()));
		memberInf.setAge(Integer.valueOf(ageObj.toString()));
		memberInf.setIdType(Integer.valueOf(idTypeObj.toString()));
		memberInf.setIdNo(idNoObj.toString());
		memberInf.setInterest(interestObj == null ? "" : interestObj.toString());
		memberInf.setProfile(profileObj == null ? "" : profileObj.toString());
		memberInf.setTeamInfo(team);
		memberInf.setStatus(MEMBER_STATUS.ACTIVE.getValue());
		SysUser sysUser = sysUserService.getUserByTravelId(team.getTravelInf().getId());
		memberInf.setSysUser(sysUser);
		if(memberService.addMember(memberInf) == 0){
			SuccessResult<String> result = new SuccessResult<String>(Action.SUCCESS);
			sendToMobile(result);	
		} else {
			FailureResult result = new FailureResult("创建会员失败");
			sendToMobile(result);
		}
		return null;
	}
	
	public String guideUpdate(){
		String data = getMobileData();
		Object teamIdObj = getMobileParameter(data, "teamId");
		if(teamIdObj == null || StringUtils.isBlank(teamIdObj.toString())){
			FailureResult result = new FailureResult("teamId不能为空");
			sendToMobile(result);
			return null;
		}
		Object nameObj = getMobileParameter(data, "name");
		if(nameObj == null || StringUtils.isBlank(nameObj.toString())){
			FailureResult result = new FailureResult("name不能为空");
			sendToMobile(result);
			return null;
		}
		Object memberTypeObj = getMobileParameter(data, "memberType");
		if(memberTypeObj == null || StringUtils.isBlank(memberTypeObj.toString())){
			FailureResult result = new FailureResult("memberType不能为空");
			sendToMobile(result);
			return null;
		}
		Object nicknameObj = getMobileParameter(data, "nickname");
		Object phoneNumberObj = getMobileParameter(data, "phoneNumber");
		if(phoneNumberObj == null || StringUtils.isBlank(phoneNumberObj.toString())){
			FailureResult result = new FailureResult("phoneNumber不能为空");
			sendToMobile(result);
			return null;
		}
		Object passwordObj = getMobileParameter(data, "password");		
		if(passwordObj == null || StringUtils.isBlank(passwordObj.toString())){
			FailureResult result = new FailureResult("password不能为空");
			sendToMobile(result);
			return null;
		}
		Object sexObj = getMobileParameter(data, "sex");	
		if(sexObj == null || StringUtils.isBlank(sexObj.toString())){
			FailureResult result = new FailureResult("sex不能为空");
			sendToMobile(result);
			return null;
		}
		Object ageObj = getMobileParameter(data, "age");	
		if(ageObj == null || StringUtils.isBlank(ageObj.toString())){
			FailureResult result = new FailureResult("age不能为空");
			sendToMobile(result);
			return null;
		}
		Object idTypeObj = getMobileParameter(data, "idType");	
		if(idTypeObj == null || StringUtils.isBlank(idTypeObj.toString())){
			FailureResult result = new FailureResult("idType不能为空");
			sendToMobile(result);
			return null;
		}
		Object idNoObj = getMobileParameter(data, "idNo");
		if(idNoObj == null || StringUtils.isBlank(idNoObj.toString())){
			FailureResult result = new FailureResult("idNo不能为空");
			sendToMobile(result);
			return null;
		}
		Object interestObj = getMobileParameter(data, "interest");	
		Object profileObj = getMobileParameter(data, "profile");	
		Object memberIdObj = getMobileParameter(data, "memberId");
		Long memberId = Long.valueOf(0);
		try {
			memberId = Long.valueOf(memberIdObj.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("memberId类型错误");
			sendToMobile(result);
			return null;
		}
		MemberInf memberInf = memberService.getMemberById(memberId);
		if (memberInf == null) {
			FailureResult result = new FailureResult("member不存在");
			sendToMobile(result);
			return null;
		}
		if(memberInf.getTeamInfo().getId().longValue() != Long.valueOf(teamIdObj.toString()).longValue()){
			FailureResult result = new FailureResult("此会员不属于该旅行团，不能修改。");
			sendToMobile(result);
			return null; 
		}
		TeamInfo team = teamService.getTeamById(Long.valueOf(teamIdObj.toString()));
		if(team == null){
			FailureResult result = new FailureResult("team不存在");
			sendToMobile(result);
			return null;
		}
		memberInf.setMemberName(nameObj.toString());
		memberInf.setMemberType(Integer.valueOf(memberTypeObj.toString()));
		memberInf.setNickname(nicknameObj == null ? "" : nicknameObj.toString());
		memberInf.setTravelerMobile(phoneNumberObj.toString());
		memberInf.setPassword(passwordObj.toString());
		memberInf.setSex(Integer.valueOf(sexObj.toString()));
		memberInf.setAge(Integer.valueOf(ageObj.toString()));
		memberInf.setIdType(Integer.valueOf(idTypeObj.toString()));
		memberInf.setIdNo(idNoObj.toString());
		memberInf.setInterest(interestObj == null ? "" : interestObj.toString());
		memberInf.setProfile(profileObj == null ? "" : profileObj.toString());
		memberInf.setTeamInfo(team);
		memberInf.setStatus(MEMBER_STATUS.ACTIVE.getValue());
		if(memberService.updateMember(memberInf) == 0){
			SuccessResult<String> result = new SuccessResult<String>(Action.SUCCESS);
			sendToMobile(result);	
		} else {
			FailureResult result = new FailureResult("创建会员失败");
			sendToMobile(result);
		}
		return null;
	}
	
	public String deleteMember(){
		String data = getMobileData();
		Object teamIdObj = getMobileParameter(data, "teamId");
		if(teamIdObj == null || StringUtils.isBlank(teamIdObj.toString())){
			FailureResult result = new FailureResult("teamId不能为空");
			sendToMobile(result);
			return null;
		}
		Object memberIdObj = getMobileParameter(data, "memberId");		
		Long memberId = Long.valueOf(0);
		try {
			memberId = Long.valueOf(memberIdObj.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("memberId类型错误");
			sendToMobile(result);
			return null;
		}
		MemberInf memberInf = memberService.getMemberById(memberId);
		if (memberInf == null) {
			FailureResult result = new FailureResult("member不存在");
			sendToMobile(result);
			return null;
		}
		if(memberInf.getTeamInfo().getId().longValue() != Long.valueOf(teamIdObj.toString()).longValue()){
			FailureResult result = new FailureResult("此会员不属于该旅行团，不能删除。");
			sendToMobile(result);
			return null; 
		}
		TeamInfo team = teamService.getTeamById(Long.valueOf(teamIdObj.toString()));
		if(team == null){
			FailureResult result = new FailureResult("team不存在");
			sendToMobile(result);
			return null;
		}
		memberInf.setStatus(MEMBER_STATUS.INACTIVE.getValue());
		memberService.updateMember(memberInf);
		SuccessResult<String> result = new SuccessResult<String>(Action.SUCCESS);
		sendToMobile(result);	
		return null;
	}
}
