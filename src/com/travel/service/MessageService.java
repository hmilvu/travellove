package com.travel.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class MessageService
{
	@Autowired
	private MessageDAO messageDAO;	
	@Autowired
	private ReplyDAO replyDAO;
	
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
	
}
