/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.mobile;

import org.springframework.beans.factory.annotation.Autowired;

import com.travel.action.BaseAction;
import com.travel.common.dto.FailureResult;
import com.travel.common.dto.MemberDTO;
import com.travel.common.dto.SuccessResult;
import com.travel.common.dto.TeamLocationDTO;
import com.travel.entity.MemberInf;
import com.travel.service.MemberService;
import com.travel.service.TeamInfoService;

/**
 * @author Lenovo
 * 
 */
public class TeamAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private TeamInfoService teamInfoService;
	@Autowired
	private MemberService memberService;

	public String teamMemberLocation() {
		String data = getMobileData();
		Object memberIdObj = getMobileParameter(data, "memberId");
		Long memberId = Long.valueOf(0);
		try {
			memberId = Long.valueOf(memberIdObj.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("团员id类型错误");
			sendToMobile(result);
			return null;
		}
		MemberInf member = memberService.getMemberById(memberId);
		TeamLocationDTO teamLocationDTO = teamInfoService.getTeamMemeberLocation(member.getTeamInfo().getId(), member.getId());
		if (teamLocationDTO != null) {
			SuccessResult<TeamLocationDTO> result = new SuccessResult<TeamLocationDTO>(
					teamLocationDTO);
			sendToMobile(result);
		} else {
			FailureResult result = new FailureResult("团员id错误");
			sendToMobile(result);
		}
		return null;
	}
}
