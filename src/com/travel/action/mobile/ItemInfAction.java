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
import com.travel.common.Constants;
import com.travel.common.Constants.MEMBER_TYPE;
import com.travel.common.Constants.MESSAGE_RECEIVER_TYPE;
import com.travel.common.admin.dto.SearchItemDTO;
import com.travel.common.dto.FailureResult;
import com.travel.common.dto.ItemInfDTO;
import com.travel.common.dto.MessageDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.common.dto.SuccessResult;
import com.travel.entity.ItemInf;
import com.travel.entity.MemberInf;
import com.travel.entity.Message;
import com.travel.entity.TeamInfo;
import com.travel.entity.ViewSpotInfo;
import com.travel.service.ItemInfService;
import com.travel.service.MemberService;
import com.travel.service.MessageService;
import com.travel.service.OrderService;
import com.travel.service.TeamInfoService;

/**
 * @author Lenovo
 * 
 */
public class ItemInfAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private TeamInfoService teamService;
	@Autowired
	private ItemInfService itemService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private MemberService memberService;
	
	public void search() {
		String data = getMobileData();
		Object teamId = getMobileParameter(data, "teamId");
		Long teamIdLong = Long.valueOf(0);
		try {
			teamIdLong = Long.valueOf(teamId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("teamId类型错误");
			sendToMobile(result);
			return;
		}
		Object pageSize = getMobileParameter(data, "pageSize");
		Object pageNumber = getMobileParameter(data, "pageNumber");
		PageInfoDTO pageInfo = new PageInfoDTO();
		try{
			pageInfo.setPageNumber(Integer.valueOf(pageNumber.toString()));
			pageInfo.setPageSize(Integer.valueOf(pageSize.toString()));
		}catch(Throwable ignore){			
		}
		SearchItemDTO dto = new SearchItemDTO();
		Object keyword = getMobileParameter(data, "keyword");
		if(keyword != null && StringUtils.isNotBlank(keyword.toString())){
			dto.setName(keyword.toString().trim());
		}
//		TeamInfo team = teamService.getTeamById(teamIdLong);
//		dto.setTravelId(team.getTravelInf().getId());
		dto.setTeamId(teamIdLong);
		List<ItemInfDTO> list = itemService.getItemList(dto, pageInfo);
		SuccessResult<List<ItemInfDTO>> result = new SuccessResult<List<ItemInfDTO>>(list);
		sendToMobile(result);
		return;
	}
	
	public void detail(){
		String data = getMobileData();
		Object itemId = getMobileParameter(data, "itemId");
		Long itemIdLong = Long.valueOf(0);
		try {
			itemIdLong = Long.valueOf(itemId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("itemId类型错误");
			sendToMobile(result);
			return;
		}
		ItemInf item = itemService.getItemById(itemIdLong);
		if(item != null){
			SuccessResult<ItemInfDTO> result = new SuccessResult<ItemInfDTO>(item.toDTO());
			sendToMobile(result);
		} else{
			FailureResult result = new FailureResult("产品不存在itemId=" + itemId);
			sendToMobile(result);
		}
		return;
	}	
	

	public void commentList(){
		String data = getMobileData();
		Object itemId = getMobileParameter(data, "itemId");
		Long itemIdLong = Long.valueOf(0);
		try {
			itemIdLong = Long.valueOf(itemId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("itemId类型错误");
			sendToMobile(result);
			return;
		}
		ItemInf item = itemService.getItemById(itemIdLong);
		if(item == null){
			FailureResult result = new FailureResult("此销售品"+itemId+"不存在");
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
		List<MessageDTO> list = messageService.getMessageDTOByReceiverId(MESSAGE_RECEIVER_TYPE.ITEM, itemIdLong, pageInfo);
		SuccessResult<List<MessageDTO>> result = new SuccessResult<List<MessageDTO>>(list);
		sendToMobile(result);		
	}
	
	public void createComment(){
		String data = getMobileData();
		Object itemId = getMobileParameter(data, "itemId");
		Long itemIdLong = Long.valueOf(0);
		try {
			itemIdLong = Long.valueOf(itemId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("itemId类型错误");
			sendToMobile(result);
			return;
		}
		ItemInf item = itemService.getItemById(itemIdLong);
		if(item == null){
			FailureResult result = new FailureResult("此销售品"+itemId+"不存在");
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
		Object score = getMobileParameter(data, "score");
		Integer scoreInt = Integer.valueOf(0);
		try {
			scoreInt = Double.valueOf(score.toString()).intValue();
			if(scoreInt < 0){
				scoreInt = 0;
			} else if(scoreInt > 5){
				scoreInt = 5;
			}
		} catch (Exception ignore) {			
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
		
		messageService.savItemMessage(member, itemIdLong, content.toString(), scoreInt);
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
		if(msg.getCreateId().longValue() != member.getId().longValue() || msg.getReceiverType().intValue() != MESSAGE_RECEIVER_TYPE.ITEM.getValue()){
			FailureResult result = new FailureResult("该评论不是此团员创建，不能删除memeberId="+memberId + " msgId=" + commentId);
			sendToMobile(result);
			return;
		}
		messageService.deleteMessage(msg);
		SuccessResult<String> result = new SuccessResult<String>(Action.SUCCESS);
		sendToMobile(result);
		return;
	}
	
	public void guideDeleteComment(){
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
		Object itemId = getMobileParameter(data, "itemId");
		Long itemIdLong = Long.valueOf(0);
		try {
			itemIdLong = Long.valueOf(itemId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("itemId类型错误");
			sendToMobile(result);
			return;
		}
		ItemInf item = itemService.getItemById(itemIdLong);
		if(item == null){
			FailureResult result = new FailureResult("此itemId不存在");
			sendToMobile(result);
			return;
		}
		Message msg = messageService.getMessageById(commendIdLong);
		if(msg == null){
			FailureResult result = new FailureResult("此评论"+commendIdLong+"不存在");
			sendToMobile(result);
			return;
		}
		if(msg.getReceiverType().intValue() != Constants.MESSAGE_RECEIVER_TYPE.ITEM.getValue() || msg.getReceiverId().longValue() != item.getId().longValue()){
			FailureResult result = new FailureResult("此评论不是该销售品的评论，不能删除");
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
		if(member.getMemberType().intValue() != MEMBER_TYPE.GUIDE.getValue()){
			FailureResult result = new FailureResult("memberId不是导游，不能做删除动作");
			sendToMobile(result);
			return;
		}
		messageService.deleteMessage(msg);
		SuccessResult<String> result = new SuccessResult<String>(Action.SUCCESS);
		sendToMobile(result);
		return;
	}
}
