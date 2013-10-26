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
import com.travel.entity.MemberInf;
import com.travel.service.MemberService;
import com.travel.utils.JsonUtils;

/**
 * @author Lenovo
 *
 */
public class MemberAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private MemberService memberService;

	public String search(){
		String data = request.getParameter("data");
		Object id = binder.getValue(data, "id");
		Long idLong = Long.valueOf(0);
		try{
			idLong = Long.valueOf(id.toString());
		}catch(Exception e){
			FailureResult result = new FailureResult("id类型错误");
			JsonUtils.write(response, binder.toJson(result));
			return null;
		}
		MemberInf member = memberService.getMemberById(idLong);
		if(member != null && member.getId() > 0){
			SuccessResult <MemberDTO>result = new SuccessResult<MemberDTO>(member.toDTO());
			JsonUtils.write(response, binder.toJson(result));
		} else {
			FailureResult result = new FailureResult("该用户不存在");
			JsonUtils.write(response, binder.toJson(result));
		}
		return null;
	}
}
