/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.travel.action.AuthorityAction;
import com.travel.common.Constants;
import com.travel.common.admin.dto.SearchTriggerConfigDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.TeamInfo;
import com.travel.entity.TravelInf;
import com.travel.entity.TriggerConfig;
import com.travel.entity.ViewSpotInfo;
import com.travel.service.TriggerConfigService;
import com.travel.utils.JsonUtils;

/**
 * @author Lenovo
 *
 */
public class TriggerConfigAction extends AuthorityAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private TriggerConfigService triggerService;

	public String list(){
		String name = request.getParameter("name");		
		String pageSize = request.getParameter("numPerPage");
		String pageNumber = request.getParameter("pageNum");
		PageInfoDTO pageInfo = new PageInfoDTO();
		try{
			pageInfo.setPageNumber(Integer.valueOf(pageNumber.toString()));
		}catch(Throwable ignore){	
			pageInfo.setPageNumber(1);
		}
		try{			
			pageInfo.setPageSize(Integer.valueOf(pageSize.toString()));
		}catch(Throwable ignore){	
			pageInfo.setPageSize(Constants.ADMIN_DEFAULT_PAGE_SIZE);
		}
		SearchTriggerConfigDTO dto = new SearchTriggerConfigDTO();		
		dto.setName(name);
		if(isTravelUser()){
			triggerService.initTriggerConfig(getCurrentUser().getTravelInf().getId());
			dto.setTravelId(getCurrentUser().getTravelInf().getId());
		}
		int totalNum = triggerService.getTotalTriggerConfigNum(dto);
		List<TriggerConfig> list = triggerService.findTriggerConfigs(dto, pageInfo);
		request.setAttribute("triggerConfigList", list);
		request.setAttribute("totalCount", totalNum+"");
		request.setAttribute("name", name);
		request.setAttribute("pageNumber", pageNumber == null ? 1 : pageNumber);
		request.setAttribute("startNum", (pageInfo.getPageNumber()-1)*pageInfo.getPageSize());
		return "list";
	}
	
	public String edit(){
		String id = request.getParameter("uid");
		TriggerConfig trigger = triggerService.getTriggerConfigById(Long.valueOf(id));
		if(trigger != null && trigger.getId() != null){
			request.setAttribute("editTriggerConfig", trigger);
		}
		return "edit";
	}
	
	public void changeStatus(){
		String id = request.getParameter("uid");
		triggerService.updateTriggerStatus(Long.valueOf(id));		
		JsonUtils.write(response, "{\"statusCode\":\"200\", \"message\":\"更新成功\", \"navTabId\":\"触发器管理\", \"forwardUrl\":\"\", \"callbackType\":\"\", \"rel\":\"\"}");
	}
	
	public void update(){
		String id = request.getParameter("triggerId");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String triggerType = request.getParameter("triggerType");
		String times = request.getParameter("times");
		String conditionValue = request.getParameter("conditionValue");
		String content = request.getParameter("content");
		TriggerConfig trigger = triggerService.getTriggerConfigById(Long.valueOf(id));
		trigger.setConditionValue(Double.valueOf(conditionValue));
		trigger.setStartTime(startTime);
		trigger.setTimes(Integer.valueOf(times));
		trigger.setTriggerType(Integer.valueOf(triggerType));
		trigger.setContent(content);
		triggerService.updateTrigger(trigger);
		JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));	
	}
}
