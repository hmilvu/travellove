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
import com.travel.common.Constants;
import com.travel.common.Constants.MEMBER_STATUS;
import com.travel.common.admin.dto.SearchMessageDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.MemberInf;
import com.travel.entity.Message;
import com.travel.entity.Reply;
import com.travel.entity.TeamInfo;
import com.travel.service.MemberService;
import com.travel.service.MessageService;
import com.travel.service.TeamInfoService;
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
	private TeamInfoService teamService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MessageService messageService;

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
		dto.setTravelId(getCurrentUser().getTravelInf().getId());
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
	
	public String add(){
		return "add";
	}
	
	public void create(){
		String teamId = request.getParameter("teamLookup.id");
		String name = request.getParameter("name");
		String memberType = request.getParameter("memberType");
		String nickname = request.getParameter("nickname");
		String phoneNumber = request.getParameter("phoneNumber");
		String password = request.getParameter("password");		
		String sex = request.getParameter("sex");	
		String age = request.getParameter("age");	
		String idType = request.getParameter("idType");	
		String idNo = request.getParameter("idNo");	
		String interest = request.getParameter("interest");	
		String profile = request.getParameter("profile");	
		
		TeamInfo team = new TeamInfo();
		team.setId(Long.valueOf(teamId));
		MemberInf memberInf = new MemberInf();
		memberInf.setMemberName(name);
		memberInf.setMemberType(Integer.valueOf(memberType));
		memberInf.setNickname(nickname);
		memberInf.setTravelerMobile(phoneNumber);
		memberInf.setPassword(password);
		memberInf.setSex(Integer.valueOf(sex));
		memberInf.setAge(Integer.valueOf(age));
		memberInf.setIdType(Integer.valueOf(idType));
		memberInf.setIdNo(idNo);
		memberInf.setInterest(interest);
		memberInf.setProfile(profile);
		memberInf.setTeamInfo(team);
		memberInf.setStatus(MEMBER_STATUS.ACTIVE.getValue());
		memberInf.setSysUser(getCurrentUser());
		if(memberService.addMember(memberInf) == 0){
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
