/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.mobile;

import org.springframework.beans.factory.annotation.Autowired;

import com.travel.action.BaseAction;
import com.travel.common.dto.FailureResult;
import com.travel.common.dto.PageInfoDTO;
import com.travel.common.dto.SuccessResult;
import com.travel.common.dto.TeamRouteDTO;
import com.travel.service.TeamInfoService;
import com.travel.utils.JsonUtils;

/**
 * @author Lenovo
 *
 */
public class RouteInfAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private TeamInfoService teamInfoService;

	public String search(){
		String data = request.getParameter("data");
		Object id = binder.getValue(data, "id");
		Object pageSize = binder.getValue(data, "pageSize");
		Object pageNumber = binder.getValue(data, "pageNumber");
		Long idLong = Long.valueOf(0);
		PageInfoDTO pageInfo = new PageInfoDTO();
		try{
			idLong = Long.valueOf(id.toString());			
		}catch(Exception e){
			FailureResult result = new FailureResult("id类型错误");
			JsonUtils.write(response, binder.toJson(result));
			return null;
		}
		try{
			pageInfo.setPageNumber(Integer.valueOf(pageNumber.toString()));
			pageInfo.setPageSize(Integer.valueOf(pageSize.toString()));
		}catch(Throwable ignore){			
		}
		TeamRouteDTO teamRouteDTO= teamInfoService.getRouteInfByTeamId(idLong, pageInfo);
		if(teamRouteDTO != null){
			SuccessResult <TeamRouteDTO>result = new SuccessResult<TeamRouteDTO>(teamRouteDTO);
			JsonUtils.write(response, binder.toJson(result));
		} else {
			FailureResult result = new FailureResult("旅游团不存在");
			JsonUtils.write(response, binder.toJson(result));
		}
		return null;
	}
}
