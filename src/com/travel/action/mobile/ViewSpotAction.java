/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.mobile;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.travel.action.BaseAction;
import com.travel.common.Constants.MESSAGE_RECEIVER_TYPE;
import com.travel.common.admin.dto.SearchViewSpotDTO;
import com.travel.common.dto.FailureResult;
import com.travel.common.dto.ItemInfDTO;
import com.travel.common.dto.MessageDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.common.dto.SuccessResult;
import com.travel.common.dto.ViewSpotDTO;
import com.travel.entity.MemberInf;
import com.travel.entity.Message;
import com.travel.entity.TeamInfo;
import com.travel.entity.ViewSpotInfo;
import com.travel.service.ItemInfService;
import com.travel.service.MemberService;
import com.travel.service.MessageService;
import com.travel.service.TeamInfoService;
import com.travel.service.ViewSpotService;
import com.travel.utils.JsonUtils;

/**
 * @author Lenovo
 *
 */
public class ViewSpotAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ViewSpotService viewSpotService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private TeamInfoService teamService;
	@Autowired
	private ItemInfService itemService;
	
	public void list(){
		String data = getMobileData();
		Object teamId = getMobileParameter(data, "teamId");
		Long idLong = Long.valueOf(0);
		try {
			idLong = Long.valueOf(teamId.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("teamId类型错误");
			sendToMobile(result);
			return;
		}
		TeamInfo team = teamService.getTeamById(idLong);
		if(team == null){
			FailureResult result = new FailureResult("此"+teamId+"不存在");
			sendToMobile(result);
			return;
		}
		Object pageSize = binder.getValue(data, "pageSize");
		Object pageNumber = binder.getValue(data, "pageNumber");
		PageInfoDTO pageInfo = new PageInfoDTO();
		try{
			pageInfo.setPageNumber(Integer.valueOf(pageNumber.toString()));
			pageInfo.setPageSize(Integer.valueOf(pageSize.toString()));
		}catch(Throwable ignore){			
		}
		SearchViewSpotDTO dto = new SearchViewSpotDTO();		
		Object keyword = getMobileParameter(data, "keyword");
		if(keyword != null && StringUtils.isNotBlank(keyword.toString())){
			dto.setName(keyword.toString().trim());
		}
		dto.setTravelId(team.getTravelInf().getId());
		List<ViewSpotDTO> list = viewSpotService.findViewSpotsDTO(dto, pageInfo);		
		SuccessResult<List<ViewSpotDTO>> result = new SuccessResult<List<ViewSpotDTO>>(list);
		sendToMobile(result);		
	}
	
	public String search(){
		String data = getMobileData();
		Object id = getMobileParameter(data, "id");
		Long idLong = Long.valueOf(0);
		try{
			idLong = Long.valueOf(id.toString());
		}catch(Exception e){
			FailureResult result = new FailureResult("id类型错误");
			JsonUtils.write(response, binder.toJson(result));
			return null;
		}
		ViewSpotInfo viewSpot = viewSpotService.getViewSpotById(idLong);
		if(viewSpot != null && viewSpot.getId() > 0){
			List<ItemInfDTO> itemList = itemService.getItemByViewSpotId(viewSpot.getId());
			viewSpot.setItemList(itemList);
			SuccessResult <ViewSpotDTO>result = new SuccessResult<ViewSpotDTO>(viewSpot.toDTO());
			JsonUtils.write(response, binder.toJson(result));
		} else {
			FailureResult result = new FailureResult("景点不存在");
			JsonUtils.write(response, binder.toJson(result));
		}
		return null;
	}
	
	public void commentList(){
		String data = getMobileData();
		Object viewspotId = getMobileParameter(data, "viewspotId");
		Long idLong = Long.valueOf(0);
		try {
			idLong = Long.valueOf(viewspotId.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("id类型错误");
			sendToMobile(result);
			return;
		}
		ViewSpotInfo view = viewSpotService.getViewSpotById(idLong);
		if(view == null){
			FailureResult result = new FailureResult("此景点"+viewspotId+"不存在");
			sendToMobile(result);
			return;
		}	
	
		Object pageSize = binder.getValue(data, "pageSize");
		Object pageNumber = binder.getValue(data, "pageNumber");
		PageInfoDTO pageInfo = new PageInfoDTO();
		try{
			pageInfo.setPageNumber(Integer.valueOf(pageNumber.toString()));
			pageInfo.setPageSize(Integer.valueOf(pageSize.toString()));
		}catch(Throwable ignore){			
		}
		List<MessageDTO> list = messageService.getMessageDTOByReceiverId(MESSAGE_RECEIVER_TYPE.VIEW_SPOT, idLong, pageInfo);
		SuccessResult<List<MessageDTO>> result = new SuccessResult<List<MessageDTO>>(list);
		sendToMobile(result);		
	}
	
	public void createComment(){
		String data = getMobileData();
		Object viewspotId = getMobileParameter(data, "viewspotId");
		Long idLong = Long.valueOf(0);
		try {
			idLong = Long.valueOf(viewspotId.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("id类型错误");
			sendToMobile(result);
			return;
		}
		ViewSpotInfo view = viewSpotService.getViewSpotById(idLong);
		if(view == null){
			FailureResult result = new FailureResult("此景点"+viewspotId+"不存在");
			sendToMobile(result);
			return;
		}
		Object memberId = getMobileParameter(data, "memberId");
		Long memberIdLong = Long.valueOf(0);
		try {
			memberIdLong = Long.valueOf(memberId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("memberId类型错误");
			sendToMobile(result);
			return;
		}
		MemberInf member = memberService.getMemberById(memberIdLong);
		if (member == null) {
			FailureResult result = new FailureResult("该用户不存在memberId = " + memberId);
			sendToMobile(result);
			return;
		}
		Object content = getMobileParameter(data, "content");
		if(content == null || StringUtils.isBlank(content.toString())){
			FailureResult result = new FailureResult("评论不能为空");
			sendToMobile(result);
			return;
		}
		
		messageService.saveViewspotMessage(member, idLong, content.toString());
		SuccessResult<String> result = new SuccessResult<String>(Action.SUCCESS);
		sendToMobile(result);
		return;		
	}
	
	public void deleteComment(){
		String data = getMobileData();
		Object commentId = getMobileParameter(data, "commentId");
		Long commendIdLong = Long.valueOf(0);
		try {
			commendIdLong = Long.valueOf(commentId.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("commentId类型错误");
			sendToMobile(result);
			return;
		}
		Message msg = messageService.getMessageById(commendIdLong);
		if(msg == null){
			FailureResult result = new FailureResult("此评论"+commendIdLong+"不存在");
			sendToMobile(result);
			return;
		}
		Object memberId = getMobileParameter(data, "memberId");
		Long memberIdLong = Long.valueOf(0);
		try {
			memberIdLong = Long.valueOf(memberId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("memberId类型错误");
			sendToMobile(result);
			return;
		}
		MemberInf member = memberService.getMemberById(memberIdLong);
		if (member == null) {
			FailureResult result = new FailureResult("该用户不存在memberId = " + memberId);
			sendToMobile(result);
			return;
		}
		if(msg.getCreateId().longValue() != member.getId().longValue() || msg.getReceiverType().intValue() != MESSAGE_RECEIVER_TYPE.VIEW_SPOT.getValue()){
			FailureResult result = new FailureResult("该评论不是次团员创建，不能删除memeberId="+memberId + " msgId=" + commentId);
			sendToMobile(result);
			return;
		}
		messageService.deleteMessage(msg);
		SuccessResult<String> result = new SuccessResult<String>(Action.SUCCESS);
		sendToMobile(result);
		return;
	}
	
}
