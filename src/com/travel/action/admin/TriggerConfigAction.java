/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.admin;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.travel.action.AuthorityAction;
import com.travel.action.admin.form.ViewSpotItemForm;
import com.travel.common.Constants;
import com.travel.common.admin.dto.SearchMessageDTO;
import com.travel.common.admin.dto.SearchTriggerConfigDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.Message;
import com.travel.entity.TriggerConfig;
import com.travel.entity.TriggerViewSpot;
import com.travel.service.MemberService;
import com.travel.service.MessageService;
import com.travel.service.TeamInfoService;
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
	@Autowired
	private MessageService messageService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private TeamInfoService teamService;
	private List<ViewSpotItemForm>items;
	
	public List<ViewSpotItemForm> getItems() {
		return items;
	}

	public void setItems(List<ViewSpotItemForm> items) {
		this.items = items;
	}

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
		List<TriggerViewSpot> list = triggerService.getTriggerViewSpots(trigger.getId());
		if(trigger != null && trigger.getId() != null){
			request.setAttribute("editTriggerConfig", trigger);
			request.setAttribute("triggerViewSpotList", list);
		}
		return "edit";
	}
	
	public void changeStatus(){
		String id = request.getParameter("uid");
		triggerService.updateTriggerStatus(Long.valueOf(id));		
		JsonUtils.write(response, "{\"statusCode\":\"200\", \"message\":\"更新成功\", \"navTabId\":\"触发器管理\", \"forwardUrl\":\"\", \"callbackType\":\"\", \"rel\":\"\"}");
	}
	
	@SuppressWarnings("static-access")
	public void update(){
		String id = request.getParameter("triggerId");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
//		String triggerType = request.getParameter("triggerType");
		String times = request.getParameter("times");
		String conditionValue = request.getParameter("conditionValue");
		String content = request.getParameter("content");
		String sendSMS = request.getParameter("sendSMS");
		TriggerConfig trigger = triggerService.getTriggerConfigById(Long.valueOf(id));
		trigger.setConditionValue(conditionValue);
		trigger.setStartTime(startTime);
		trigger.setEndTime(endTime);
		trigger.setTimes(Integer.valueOf(times));
//		trigger.setTriggerType(Integer.valueOf(triggerType));
		trigger.setContent(content);
		if(StringUtils.equals("1", sendSMS)){
			trigger.setSendSMS(1);
		} else{
			trigger.setSendSMS(0);
		}
		triggerService.updateTrigger(trigger, items);
		JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));	
	}

	public String messageList(){
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
		String triggerId = request.getParameter("triggerId");
		SearchMessageDTO dto = new SearchMessageDTO();		
		dto.setTriggerId(Long.valueOf(triggerId));
		if(isTravelUser()){
			dto.setTravelId(getCurrentUser().getTravelInf().getId());
		}
		int totalNum = messageService.getTotalTriggerMessageNum(dto);
		List<Message> list = messageService.findTriggerMessages(dto, pageInfo);
		request.setAttribute("triggerId", triggerId);
		request.setAttribute("messageList", list);
		request.setAttribute("totalCount", totalNum+"");
		request.setAttribute("pageNumber", pageNumber == null ? 1 : pageNumber);
		request.setAttribute("startNum", (pageInfo.getPageNumber()-1)*pageInfo.getPageSize());
		return "messageList";
	}
	
	public String deleteMessage(){
		String ids = request.getParameter("id");
		messageService.deleteMessageByIds(ids);
		JsonUtils.write(response, "{\"statusCode\":\"200\", \"message\":\"删除成功\", \"navTabId\":\"\", \"forwardUrl\":\"\", \"callbackType\":\"\", \"rel\":\"\"}");
		return null;
	}
}
