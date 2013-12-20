/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.admin;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.travel.action.AuthorityAction;
import com.travel.common.Constants;
import com.travel.common.Constants.MESSAGE_CREATE_TYPE;
import com.travel.common.Constants.MESSAGE_REMIND_MODE;
import com.travel.common.Constants.MESSAGE_STATUS;
import com.travel.common.Constants.MESSAGE_TYPE;
import com.travel.common.admin.dto.SearchMessageDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.MemberInf;
import com.travel.entity.Message;
import com.travel.entity.Reply;
import com.travel.entity.TravelInf;
import com.travel.service.MemberService;
import com.travel.service.MessageService;
import com.travel.utils.JsonUtils;

/**
 * @author Lenovo
 *
 */
public class MessageAction extends AuthorityAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private MessageService messageService;
	@Autowired
	private MemberService memberService;

	public String list(){
		String topic = request.getParameter("topic");
		String teamName = request.getParameter("teamName");
		String type = request.getParameter("type");
		String status = request.getParameter("status");
		String priority = request.getParameter("priority");
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
		SearchMessageDTO dto = new SearchMessageDTO();		
		dto.setTeamName(teamName);
		dto.setTopic(topic);
		if(StringUtils.isNotBlank(status)){
			dto.setStatus(Integer.valueOf(status));
		}
		if(StringUtils.isNotBlank(type)){
			dto.setType(Integer.valueOf(type));		
		}
		if(StringUtils.isNotBlank(priority)){
			dto.setPriority(Integer.valueOf(priority));
		}		
		if(isTravelUser()){
			dto.setTravelId(getCurrentUser().getTravelInf().getId());
		}
		int totalNum = messageService.getTotalMessageNum(dto);
		List<Message> list = messageService.findMessages(dto, pageInfo);
		request.setAttribute("messageList", list);
		request.setAttribute("totalCount", totalNum+"");
		request.setAttribute("topic", topic);
		request.setAttribute("status", status);
		request.setAttribute("priority", priority);
		request.setAttribute("teamName", teamName);
		request.setAttribute("topic", topic);
		request.setAttribute("type", type);
		request.setAttribute("pageNumber", pageNumber == null ? 1 : pageNumber);
		request.setAttribute("startNum", (pageInfo.getPageNumber()-1)*pageInfo.getPageSize());
		return "list";
	}
	
	public String addTeamMsg(){
		return "addTeamMsg";
	}
	
	@SuppressWarnings("static-access")
	public void createTeamMsg(){
		Message msg = setupMessage();
		if(msg == null){
			return;
		}
		String teamIds = request.getParameter("selectteam.id");		
		List<Long>msgIdList = messageService.addMessageForTeam(msg, teamIds);
		if(msgIdList != null && msgIdList.size() > 0){
			messageService.sendPushMsg(msgIdList, msg.getContent());
			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));
		} else {			
			JsonUtils.write(response, binder.toJson("result", Action.ERROR));	
		}
	}
	
	@SuppressWarnings("static-access")
	private Message setupMessage(){
		String topic = request.getParameter("topic");
		String content = request.getParameter("content");
		String type = request.getParameter("type");
		String priority = request.getParameter("priority");
		String remindMode = request.getParameter("remindMode");
		String remindTime = request.getParameter("remindTime");
		Message msg = new Message();
		if(StringUtils.equals(type, MESSAGE_TYPE.NOTIFICATION.getValue()+"") && StringUtils.equals(remindMode,MESSAGE_REMIND_MODE.LATER.getValue()+"")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				Date date = format.parse(remindTime);
				if(date.before(new Date(System.currentTimeMillis() + 5 * 60 * 1000))){
					JsonUtils.write(response, binder.toJson("result", Action.INPUT));		
					return null;
				} else {					
					msg.setRemindTime(new Timestamp(date.getTime()));
					msg.setRemindMode(MESSAGE_REMIND_MODE.LATER.getValue());
				}
			} catch (ParseException e) {
				JsonUtils.write(response, binder.toJson("result", Action.INPUT+"1"));
				return null;
			}
		} else {
			msg.setRemindTime(new Timestamp(new Date().getTime()));
			msg.setRemindMode(MESSAGE_REMIND_MODE.NOW.getValue());
		}
		msg.setStatus(MESSAGE_STATUS.ACTIVE.getValue());
		msg.setPriority(Integer.valueOf(priority));
		msg.setType(Integer.valueOf(type));
		msg.setTopic(topic);
		msg.setContent(content);
		msg.setCreateType(MESSAGE_CREATE_TYPE.SYSUSER.getValue());
		msg.setCreateId(getCurrentUser().getId());
		if(isTravelUser()){
			TravelInf travelInf = new TravelInf();
			travelInf.setId(getCurrentUser().getTravelInf().getId());
			msg.setTravelInf(travelInf);
		}
		return msg;
	}
	
	public String addMemberMsg(){
		return "addMemberMsg";
	}
	
	public void createMemberMsg(){
		Message msg = setupMessage();
		if(msg == null){
			return;
		}
		String memberIds = request.getParameter("selectmember.id");		
		List<MemberInf> memberList = memberService.getMemberByIds(memberIds);
		List<Long>msgIdList = messageService.addMessageForTeam(msg, memberIds);
		if(msgIdList != null && msgIdList.size() > 0){
			messageService.sendPushMsg(msgIdList, msg.getContent(), memberList);
			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));
		} else {			
			JsonUtils.write(response, binder.toJson("result", Action.ERROR));	
		}
	}
	
	public void delete(){
		String ids = request.getParameter("ids");
		messageService.deleteMessageByIds(ids);
		JsonUtils.write(response, "{\"statusCode\":\"200\", \"message\":\"删除成功\", \"navTabId\":\"消息管理\", \"forwardUrl\":\"\", \"callbackType\":\"\", \"rel\":\"\"}");
	}
	
	public String edit(){
		String id = request.getParameter("uid");
		Long idLong = 0L;
		try{
			idLong = Long.valueOf(id);
		}catch(Throwable ignore){	
			return "edit";
		}
		Message msg = messageService.getMessageById(idLong);	
		if(msg != null && msg.getId() > 0){
			request.setAttribute("editMessage", msg);
		}
		return "edit";
	}
	
	@SuppressWarnings("static-access")
	public void update(){
		JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));	
//		if(memberService.updateMember(memberInf) == 0){
//			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));			
//		} else {
//			JsonUtils.write(response, binder.toJson("result", Action.ERROR));
//		}
		
	}
	
	public String reply(){
		String id = request.getParameter("messageId");
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
		Message msg = messageService.getMessageById(Long.valueOf(id));
		int totalNum = messageService.getTotalReplyNum(msg.getId());
		List<Reply> replyList = messageService.getAllRepliesByMessageId(Long.valueOf(id), pageInfo);
		request.setAttribute("message", msg);
		request.setAttribute("totalCount", totalNum+"");
		request.setAttribute("replyList", replyList);
		request.setAttribute("pageNumber", pageNumber == null ? 1 : pageNumber);
		request.setAttribute("startNum", (pageInfo.getPageNumber()-1)*pageInfo.getPageSize());
		return "reply";
	}
	
	public void deleteReply(){
		String id = request.getParameter("id");
		messageService.deleteReplyById(id);
		JsonUtils.write(response, "{\"statusCode\":\"200\", \"message\":\"删除成功\", \"navTabId\":\"replyNavTab\", \"forwardUrl\":\"\", \"callbackType\":\"\", \"rel\":\"\"}");
		
	}
	
	@SuppressWarnings("static-access")
	public void createReply(){
		String msgId = request.getParameter("messageId");
		String content = request.getParameter("content");
		Reply reply = new Reply();
		reply.setSysUser(getCurrentUser());
		Message msg = new Message();
		msg.setId(Long.valueOf(msgId));
		reply.setMessage(msg);
		reply.setContent(content);
		if(messageService.saveReply(reply) == 0){
			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));			
		} else {
			JsonUtils.write(response, binder.toJson("result", Action.ERROR));		
		}
	}
	
	
}
