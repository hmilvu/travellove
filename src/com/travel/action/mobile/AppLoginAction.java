/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.mobile;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.travel.action.BaseAction;
import com.travel.common.dto.FailureResult;
import com.travel.common.dto.MemberDTO;
import com.travel.common.dto.SuccessResult;
import com.travel.entity.MemberInf;
import com.travel.entity.TeamInfo;
import com.travel.service.MemberService;
import com.travel.service.TeamInfoService;

/**
 * @author Lenovo
 *
 */
public class AppLoginAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private MemberService memberService;
	@Autowired
	private TeamInfoService teamService;
	
	public void login(){
		String data = getMobileData();
		if(StringUtils.isBlank(data)){
			FailureResult result = new FailureResult("获取data错误");
			sendToMobile(result);
			return;
		}
		Object teamId = getMobileParameter(data, "teamId");
		Long teamIdLong = Long.valueOf(0);
		try {
			teamIdLong = Long.valueOf(teamId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("团号id类型错误");
			result.setCode(1);
			sendToMobile(result);
			return;
		}
		TeamInfo team = teamService.getTeamById(teamIdLong);
		if(team == null || team.getId() == null || team.getId() == 0){
			FailureResult result = new FailureResult("旅行团不存在");
			result.setCode(1);
			sendToMobile(result);
			return;
		}
		String mobile = (String)getMobileParameter(data, "mobile");
		String password = (String)getMobileParameter(data, "password");
		MemberInf member = memberService.getMemberByCredentials(teamIdLong, mobile, password);
		if(member != null && member.getId() != null && member.getId() > 0){	
			SuccessResult<MemberDTO> result = new SuccessResult<MemberDTO>(member.toDTO());
			sendToMobile(result);
		} else {
			FailureResult result = new FailureResult("手机号或密码错误");
			result.setCode(2);
			sendToMobile(result);
		}
	}
}
