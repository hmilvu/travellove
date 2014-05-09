/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.mobile;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import com.travel.action.BaseAction;
import com.travel.common.Constants.MEMBER_TYPE;
import com.travel.common.Constants.MESSAGE_CREATE_TYPE;
import com.travel.common.Constants.MESSAGE_RECEIVER_TYPE;
import com.travel.common.Constants.MESSAGE_REMIND_MODE;
import com.travel.common.Constants.MESSAGE_STATUS;
import com.travel.common.Constants.MESSAGE_TYPE;
import com.travel.common.dto.FailureResult;
import com.travel.common.dto.MessageDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.common.dto.ReplyDTO;
import com.travel.common.dto.SuccessResult;
import com.travel.entity.MemberInf;
import com.travel.entity.Message;
import com.travel.entity.Reply;
import com.travel.service.MemberService;
import com.travel.service.MessageService;

/**
 * @author Lenovo
 * 
 */
public class MessageAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private MessageService messageService;
	@Autowired
	private MemberService memberService;
		
	public void list(){
		String data = getMobileData();
		Object memberId = getMobileParameter(data, "memberId");
		Long idLong = Long.valueOf(0);
		try {
			idLong = Long.valueOf(memberId.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("id类型错误");
			sendToMobile(result);
			return;
		}
		MemberInf member = memberService.getMemberById(idLong);
		if(member == null || member.getId().intValue() <= 0){
			FailureResult result = new FailureResult("此会员"+memberId+"不存在");
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
		List<MessageDTO> list = messageService.getMessageByMemberId(idLong, member.getTeamInfo().getId(), pageInfo);
		SuccessResult<List<MessageDTO>> result = new SuccessResult<List<MessageDTO>>(list);
		sendToMobile(result);
		
	}
	
	public void detail(){
		String data = getMobileData();
		Object msgId = getMobileParameter(data, "id");
		Long idLong = Long.valueOf(0);
		try {
			idLong = Long.valueOf(msgId.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("id类型错误");
			sendToMobile(result);
			return;
		}
		Message msg = messageService.getMessageById(idLong);
		SuccessResult<MessageDTO> result = new SuccessResult<MessageDTO>(msg.toDTO());
		sendToMobile(result);
	}
	
	public void replies(){
		String data = getMobileData();
		Object messageId = getMobileParameter(data, "messageId");
		Long idLong = Long.valueOf(0);
		try {
			idLong = Long.valueOf(messageId.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("id类型错误");
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
		List<ReplyDTO> list = messageService.getRepliesByMessageId(idLong, pageInfo);
		SuccessResult<List<ReplyDTO>> result = new SuccessResult<List<ReplyDTO>>(list);
		sendToMobile(result);
	}
	
	public void createReply(){
		String data = getMobileData();
		Object msgId = getMobileParameter(data, "messageId");
		Long msgIdLong = Long.valueOf(0);
		try {
			msgIdLong = Long.valueOf(msgId.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("messageId类型错误");
			sendToMobile(result);
			return;
		}
		Message msg = messageService.getMessageById(msgIdLong);
		if(msg == null || msg.getId().intValue() <= 0){
			FailureResult result = new FailureResult("此消息"+msgId+"不存在");
			sendToMobile(result);
			return;
		}
		Object memberId = getMobileParameter(data, "memberId");
		Long memberIdLong = Long.valueOf(0);
		try {
			memberIdLong = Long.valueOf(memberId.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("memberId类型错误");
			sendToMobile(result);
			return;
		}
		MemberInf member = memberService.getMemberById(memberIdLong);
		if(member == null || member.getId().intValue() <= 0){
			FailureResult result = new FailureResult("此会员"+memberId+"不存在");
			sendToMobile(result);
			return;
		}
		String content = getMobileParameter(data, "content").toString();
		Reply reply = new Reply();
		reply.setMemberInf(member);
		reply.setMessage(msg);
		reply.setContent(content);
		if(messageService.saveReply(reply) == 0){
			messageService.deleteVisible(memberIdLong, msgIdLong);
			SuccessResult<String> result = new SuccessResult<String>("success");
			sendToMobile(result);		
		} else {
			FailureResult result = new FailureResult("回复失败");
			sendToMobile(result);
		}
	}
	
	public void createTeamNote(){
		String data = getMobileData();
		Object topic = getMobileParameter(data, "topic");
		Object content = getMobileParameter(data, "content");
		Message msg = new Message();
		msg.setRemindTime(new Timestamp(new Date().getTime()));
		msg.setRemindMode(MESSAGE_REMIND_MODE.NOW.getValue());
		msg.setStatus(MESSAGE_STATUS.ACTIVE.getValue());
		msg.setPriority(Integer.valueOf(1));
		msg.setType(Integer.valueOf(MESSAGE_TYPE.NOTE.getValue()));
		if(topic != null){
			msg.setTopic(topic.toString());
		}
		if(content != null){
			msg.setContent(content.toString());
		}
		msg.setCreateType(MESSAGE_CREATE_TYPE.MEMBER.getValue());
		Object memberId = getMobileParameter(data, "memberId");
		Long memberIdLong = Long.valueOf(0);
		try {
			memberIdLong = Long.valueOf(memberId.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("memberId类型错误");
			sendToMobile(result);
			return;
		}
		MemberInf member = memberService.getMemberById(memberIdLong);
		if(member == null || member.getId().intValue() <= 0){
			FailureResult result = new FailureResult("此会员"+memberId+"不存在");
			sendToMobile(result);
			return;
		}
		msg.setCreateId(memberIdLong);
		Hibernate.initialize(member.getTeamInfo());
		msg.setTravelInf(member.getTeamInfo().getTravelInf());
		String teamIds = member.getTeamInfo().getId()+"";		
		List<Message>msgList = messageService.addMessageForReceiver(msg, teamIds, MESSAGE_RECEIVER_TYPE.TEAM);
		if(msgList != null && msgList.size() > 0){
			SuccessResult<String> result = new SuccessResult<String>("success");
			sendToMobile(result);
		} else {			
			FailureResult result = new FailureResult("回复失败");
			sendToMobile(result);
		}
		return;
	}
	
	public void delete(){
		String data = getMobileData();
		Object memberId = getMobileParameter(data, "memberId");
		Object messageId = getMobileParameter(data, "messageId");
		Long idLong = Long.valueOf(0);
		try {
			idLong = Long.valueOf(messageId.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("id类型错误");
			sendToMobile(result);
			return;
		}
		Message msg = messageService.getMessageById(idLong);
		if(msg!= null && StringUtils.equals(memberId.toString(), msg.getCreateId().toString())){
			messageService.deleteMessageByIds(idLong + "");
			SuccessResult<String> result = new SuccessResult<String>("success");
			sendToMobile(result);
		} else {
			FailureResult result = new FailureResult("此消息不是该会员发送，不能删除");
			sendToMobile(result);
		}
	}
	
	public void deleteReply(){
		String data = getMobileData();
		Object replyId = getMobileParameter(data, "replyId");
		Long idLong = Long.valueOf(0);
		try {
			idLong = Long.valueOf(replyId.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("replyId类型错误");
			sendToMobile(result);
			return;
		}
		Reply reply = messageService.getReplyById(idLong);
		if(reply != null){
			messageService.deleteReplyById(idLong + "");
			SuccessResult<String> result = new SuccessResult<String>("success");
			sendToMobile(result);
		} else {
			FailureResult result = new FailureResult("此回复不存在，不能删除");
			sendToMobile(result);
		}
	}
	
	public void addVisibility(){
		String data = getMobileData();
		Object memberId = getMobileParameter(data, "memberId");
		Object messageId = getMobileParameter(data, "messageId");
		Long memberIdLong = Long.valueOf(0);
		try {
			memberIdLong = Long.valueOf(memberId.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("memberId类型错误");
			sendToMobile(result);
			return;
		}
		Long messageIdLong = Long.valueOf(0);
		try {
			messageIdLong = Long.valueOf(messageId.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("messageId类型错误");
			sendToMobile(result);
			return;
		}
		messageService.addVisibility(memberIdLong, messageIdLong);
		SuccessResult<String> result = new SuccessResult<String>("success");
		sendToMobile(result);
	}
	
	public void guideDelete(){
		String data = getMobileData();
		Object memberId = getMobileParameter(data, "memberId");
		Object messageId = getMobileParameter(data, "messageId");
		Long memberIdLong = Long.valueOf(0);
		try {
			memberIdLong = Long.valueOf(memberId.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("memberId类型错误");
			sendToMobile(result);
			return;
		}
		Long idLong = Long.valueOf(0);
		try {
			idLong = Long.valueOf(messageId.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("messageId类型错误");
			sendToMobile(result);
			return;
		}
		MemberInf memberInf = memberService.getMemberById(memberIdLong);
		if(memberInf.getMemberType().intValue() != MEMBER_TYPE.GUIDE.getValue()){
			FailureResult result = new FailureResult("memberId不是导游，不能做删除动作");
			sendToMobile(result);
			return;
		}
		Message msg = messageService.getMessageById(idLong);
		if(msg == null){
			FailureResult result = new FailureResult("此消息不存在");
			sendToMobile(result);
			return;
		}
		messageService.deleteMessageByIds(idLong + "");
		SuccessResult<String> result = new SuccessResult<String>("success");
		sendToMobile(result);

	}
	
}
