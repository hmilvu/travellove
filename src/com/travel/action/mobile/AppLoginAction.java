/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.mobile;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.travel.action.BaseAction;
import com.travel.common.dto.FailureResult;
import com.travel.common.dto.MemberDTO;
import com.travel.common.dto.SuccessResult;
import com.travel.entity.MemberInf;
import com.travel.service.MemberService;
import com.travel.utils.JsonUtils;

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
	
	public void login(){
		String data = getMobileData();
		Object teamId = getMobileParameter(data, "teamId");
		Long teamIdLong = Long.valueOf(0);
		try {
			teamIdLong = Long.valueOf(teamId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("团号id类型错误");
			sendToMobile(result);
			return;
		}
		String mobile = (String)getMobileParameter(data, "mobile");
		String password = (String)getMobileParameter(data, "password");
		MemberInf member = memberService.getMemberByCredentials(teamIdLong, mobile, password);
		if(member != null && member.getId() != null && member.getId() > 0){	
			SuccessResult<String> result = new SuccessResult<String>(Action.SUCCESS);
			sendToMobile(result);
		} else {
			FailureResult result = new FailureResult("用户名或密码错误");
			sendToMobile(result);
		}
	}
}
