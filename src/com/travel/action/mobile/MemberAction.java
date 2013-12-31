/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.mobile;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.travel.action.BaseAction;
import com.travel.common.dto.FailureResult;
import com.travel.common.dto.MemberDTO;
import com.travel.common.dto.MessageDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.common.dto.SuccessResult;
import com.travel.entity.MemberInf;
import com.travel.entity.Message;
import com.travel.service.MemberService;
import com.travel.service.MessageService;

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
	private MessageService messageService;
	
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
			Object sexObj = getMobileParameter(data, "sex");
			Integer sex = Integer.valueOf(0);
			try {
				sex = Integer.valueOf(sexObj.toString());
			} catch (Throwable ignore) {
			}
			;
			member.setSex(sex);

			Object ageObj = getMobileParameter(data, "age");
			Integer age = Integer.valueOf(0);
			try {
				age = Integer.valueOf(ageObj.toString());
			} catch (Throwable ignore) {
			}
			;
			member.setAge(age);

			Object idTypeObj = getMobileParameter(data, "idType");
			Integer idType = Integer.valueOf(0);
			try {
				idType = Integer.valueOf(idTypeObj.toString());
			} catch (Throwable ignore) {
			}
			;
			member.setIdType(idType);

			member.setIdNo((String) getMobileParameter(data, "idNo"));
			member.setAvatarUrl((String) getMobileParameter(data, "avatarUrl"));
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
			memberService.updateMemberLocation(member, longitude, latitude);
			SuccessResult<String> result = new SuccessResult<String>(Action.SUCCESS);
			sendToMobile(result);
		} else {
			FailureResult result = new FailureResult("id不存在");
			sendToMobile(result);
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
	
}
