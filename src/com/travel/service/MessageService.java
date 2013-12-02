package com.travel.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.common.admin.dto.SearchMessageDTO;
import com.travel.common.dto.MessageDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.common.dto.ReplyDTO;
import com.travel.dao.MessageDAO;
import com.travel.dao.ReplyDAO;
import com.travel.entity.MemberInf;
import com.travel.entity.Message;
import com.travel.entity.Reply;

@Service
public class MessageService extends AbstractBaseService
{
	@Autowired
	private MessageDAO messageDAO;	
	@Autowired
	private ReplyDAO replyDAO;
	@Autowired
	private MemberService memberService;
	
	public Message getMessageById(Long id){
		return messageDAO.findById(id);
	}

	/**
	 * @param idLong
	 * @return
	 */
	public List<MessageDTO> getMessageByMemberId(Long memberId, PageInfoDTO pageInfo) {
		List <Message>list = messageDAO.findByReceiverId(memberId, pageInfo);
		List <MessageDTO> result = new ArrayList<MessageDTO>();
		for(Message msg : list){
			MessageDTO dto = msg.toDTO();
			result.add(dto);
		}
		return result;
	}

	/**
	 * @param idLong
	 * @param pageInfo
	 * @return
	 */
	public List<ReplyDTO> getRepliesByMessageId(Long messageId,
			PageInfoDTO pageInfo) {
		List <Object[]>list = messageDAO.findRepliesByMessageId(messageId, pageInfo);
		List <ReplyDTO> result = new ArrayList<ReplyDTO>();
		for(Object[] objs : list){
			Reply reply = (Reply)objs[0];
			MemberInf memberInf = (MemberInf)objs[1];
			Message message = (Message)objs[2];
			reply.setMemberInf(memberInf);
			reply.setMessage(message);
			ReplyDTO dto = reply.toDTO();
			result.add(dto);
		}
		return result;
	}

	/**
	 * @param dto
	 * @return
	 */
	public int getTotalMessageNum(SearchMessageDTO dto) {
		return messageDAO.getTotalNum(dto);
	}

	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	public List<Message> findMessages(SearchMessageDTO dto, PageInfoDTO pageInfo) {
		return messageDAO.findMessages(dto, pageInfo);
	}

	/**
	 * @param ids
	 */
	public void deleteMessageByIds(String msgIds) {
		messageDAO.deleteByIds(msgIds);		
	}

	/**
	 * @param valueOf
	 * @param pageInfo
	 * @return
	 */
	public List<Reply> getAllRepliesByMessageId(Long msgId,
			PageInfoDTO pageInfo) {
		List <Reply>list = messageDAO.findAdminRepliesByMessageId(msgId, pageInfo);
		return list;
	}

	/**
	 * @param id
	 * @return
	 */
	public int getTotalReplyNum(Long id) {
		return messageDAO.getTotalReplyNum(id);
	}

	/**
	 * @param valueOf
	 */
	public void deleteReplyById(String id) {
		replyDAO.deleteByIds(id);		
	}

	/**
	 * @param reply
	 * @param msgId
	 * @param content
	 */
	public int saveReply(Reply reply) {
		reply.setCreateDate(new Timestamp(new Date().getTime()));
		reply.setUpdateDate(reply.getCreateDate());
		return replyDAO.save(reply);
	}

	/**
	 * @param ids
	 * @return
	 */
	public List<Long> addMessageForTeam(Message msg, String teamIds) {
		msg.setCreateDate(new Timestamp(new Date().getTime()));
		msg.setUpdateDate(msg.getCreateDate());
		String []idArray = StringUtils.split(teamIds, ",");
		List<Long>idList = new ArrayList<Long>();
		for(String id : idArray){
			idList.add(Long.valueOf(id));
		}
		List<MemberInf> memberList = memberService.findAllMembersByTeamIds(idList);
		List<Long>msgIdList = new ArrayList<Long>();
		for(MemberInf member : memberList){
			msg.setReceiverId(member.getId());
			msg.setTeamInfo(member.getTeamInfo());
			try{
				msg.setId(null);
				msgIdList.add(messageDAO.save(msg));
			} catch(Throwable e){
				log.error("创建会员消息失败", e);
			}
			msg = (Message) msg.clone();
		}
		return msgIdList;
	}

	/**
	 * @param msgIdList
	 * @param content
	 */
	public void sendPushMsg(List<Long> msgIdList, String content) {
		// TODO Auto-generated method stub
		
	}
	
}
