package com.travel.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushTagMessageRequest;
import com.baidu.yun.channel.model.PushTagMessageResponse;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageResponse;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.travel.common.Constants.MESSAGE_CREATE_TYPE;
import com.travel.common.Constants.MESSAGE_RECEIVER_TYPE;
import com.travel.common.Constants.PUSH_STATUS;
import com.travel.common.admin.dto.SearchMessageDTO;
import com.travel.common.dto.MessageDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.common.dto.ReplyDTO;
import com.travel.dao.MemberInfDAO;
import com.travel.dao.MessageDAO;
import com.travel.dao.ReplyDAO;
import com.travel.dao.TeamInfoDAO;
import com.travel.entity.MemberInf;
import com.travel.entity.Message;
import com.travel.entity.Reply;
import com.travel.utils.Config;

@Service
public class MessageService extends AbstractBaseService
{
	@Autowired
	private MessageDAO messageDAO;	
	@Autowired
	private ReplyDAO replyDAO;
	@Autowired
	private MemberService memberService;
	@Autowired
	private TeamInfoDAO teamDAO;
	@Autowired
	private MemberInfDAO memberDAO;
	public Message getMessageById(Long id){
		return messageDAO.findById(id);
	}

	/**
	 * @param idLong
	 * @return
	 */
	public List<MessageDTO> getMessageByMemberId(Long memberId, Long teamId, PageInfoDTO pageInfo) {
		List <Message>list = messageDAO.findByReceiverId(memberId, teamId, pageInfo);
		List <MessageDTO> result = new ArrayList<MessageDTO>();
		for(Message msg : list){
			MessageDTO dto = msg.toDTO();
			if(msg.getCreateType().intValue() == MESSAGE_CREATE_TYPE.SYSUSER.getValue()){
				dto.setCreatorName("系统管理员");
			} else {
				MemberInf member = memberService.getMemberById(msg.getCreateId());
				if(StringUtils.isNotBlank(member.getNickname())){
					dto.setCreatorName(member.getNickname());
				} else {
					dto.setCreatorName(member.getMemberName());
				}
			}
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
		List <Reply>list = messageDAO.findRepliesByMessageId(messageId, pageInfo);
		List <ReplyDTO> result = new ArrayList<ReplyDTO>();
		for(Reply reply : list){
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
		List<Message> list = messageDAO.findMessages(dto, pageInfo);
		List<Message> result = new ArrayList<Message>();
		for(Message msg : list){
			if(msg.getReceiverType().intValue() == MESSAGE_RECEIVER_TYPE.TEAM.getValue()){
				String teamName = teamDAO.findById(msg.getReceiverId()).getName();
				msg.setReceiverName(teamName);
			} else {
				String memberName = memberDAO.findById(msg.getReceiverId()).getMemberName();
				msg.setReceiverName(memberName);
			}
			result.add(msg);
		}
		return result;
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
	 * @param msgIdList
	 * @param content
	 */
	public void sendTeamPushMsg(List<Message>messageList, String content, String teamIds) {
		// 1. 设置developer平台的ApiKey/SecretKey
		String apiKey = Config.getProperty("baidu.appkey");
		String secretKey = Config.getProperty("baidu.secretkey");
		ChannelKeyPair pair = new ChannelKeyPair(apiKey, secretKey);		
		// 2. 创建BaiduChannelClient对象实例
		BaiduChannelClient channelClient = new BaiduChannelClient(pair);		
		// 3. 若要了解交互细节，请注册YunLogHandler类
		channelClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				log.info(event.getMessage());
			}
		});
		
		String []teamIdArray = StringUtils.split(teamIds, ",");
		for(int i = 0; i < teamIdArray.length; i++){
			String teamId = teamIdArray[i];
			Message msg = messageList.get(i);
			try {			
				// 4. 创建请求类对象
				PushTagMessageRequest request = new PushTagMessageRequest();
				request.setDeviceType(3); 	// device_type => 1: web 2: pc 3:android 4:ios 5:wp	
				request.setTagName("TAG_" + teamId);
				request.setMessage(content);
				// 若要通知，
				//	request.setMessageType(1);
				//	request.setMessage("{\"title\":\"Notify_title_danbo\",\"description\":\"Notify_description_content\"}");		
				// 5. 调用pushMessage接口
				PushTagMessageResponse response = channelClient.pushTagMessage(request);
				// 6. 认证推送成功
				log.info("push amount : " + response.getSuccessAmount()); 
				msg.setPushStatus(PUSH_STATUS.PUSHED.getValue());
			} catch (ChannelClientException e) {
				// 处理客户端错误异常
				log.error("团队推送失败 TAG = ", "TAG_" + teamId);
				msg.setPushStatus(PUSH_STATUS.PUSH_FAILED.getValue());
			} catch (ChannelServerException e) {
				// 处理服务端错误异常
				log.error("团队推送失败 TAG = ", "TAG_" + teamId);
				log.error(String.format("request_id: %d, error_code: %d, error_message: %s" , 
							e.getRequestId(), e.getErrorCode(), e.getErrorMsg()
							));
				msg.setPushStatus(PUSH_STATUS.PUSH_FAILED.getValue());
			} finally{
				messageDAO.update(msg);
			}
		}		
	}

	/**
	 * @param msgIdList
	 * @param content
	 * @param memberList
	 * @brief	推送单播消息(消息类型为透传，由开发方应用自己来解析消息内容)
	* 			message_type = 0 (默认为0)
	 */
	public void sendMemberPushMsg(List<Message>messageList, String content,
			List<MemberInf> memberList) {
		// 1. 设置developer平台的ApiKey/SecretKey
		String apiKey = Config.getProperty("baidu.appkey");
		String secretKey = Config.getProperty("baidu.secretkey");
		ChannelKeyPair pair = new ChannelKeyPair(apiKey, secretKey);
		
		// 2. 创建BaiduChannelClient对象实例
		BaiduChannelClient channelClient = new BaiduChannelClient(pair);
		
		// 3. 若要了解交互细节，请注册YunLogHandler类
		channelClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				log.info(event.getMessage());
			}
		});
		
		for(int i = 0; i < memberList.size(); i++){
			MemberInf member = memberList.get(i);
			Message msg = messageList.get(i);
			try {				
				// 4. 创建请求类对象, 手机端的ChannelId， 手机端的UserId
				PushUnicastMessageRequest request = new PushUnicastMessageRequest();
				request.setDeviceType(3);	// device_type => 1: web 2: pc 3:android 4:ios 5:wp		
				request.setChannelId(member.getChannelId());	
				request.setUserId(member.getBaiduUserId());	 					
				request.setMessage(content);				
				// 5. 调用pushMessage接口
				PushUnicastMessageResponse response = channelClient.pushUnicastMessage(request);						
				// 6. 认证推送成功
				log.info("push amount : " + response.getSuccessAmount()); 	
				msg.setPushStatus(PUSH_STATUS.PUSHED.getValue());
			} catch (ChannelClientException e) {
				log.error("单点推送失败 memberId = " + member.getId() +", channelId = " + member.getChannelId(), e);
				msg.setPushStatus(PUSH_STATUS.PUSH_FAILED.getValue());
			} catch (ChannelServerException e) {
				log.error("单点推送失败 memberId = " + member.getId() +", channelId = " + member.getChannelId(), e);
				log.error(
						String.format("request_id: %d, error_code: %d, error_message: %s" , 
							e.getRequestId(), e.getErrorCode(), e.getErrorMsg()
							)
						);
				msg.setPushStatus(PUSH_STATUS.PUSH_FAILED.getValue());
			} catch (Throwable e){
				log.error("单点推送失败 memberId = " + member.getId() +", channelId = " + member.getChannelId(), e);
				msg.setPushStatus(PUSH_STATUS.PUSH_FAILED.getValue());
			} finally{
				messageDAO.update(msg);
			}
		}
	}

	/**
	 * @param msg
	 * @param memberIds
	 * @param member
	 * @return
	 */
	public List<Message> addMessageForReceiver(Message msg, String receiverIds,
			MESSAGE_RECEIVER_TYPE receiverType) {
		if(StringUtils.isBlank(msg.getTopic())){
			msg.setTopic("");
		}
		msg.setCreateDate(new Timestamp(new Date().getTime()));
		msg.setUpdateDate(msg.getCreateDate());
		String []idArray = StringUtils.split(receiverIds, ",");		
		List<Message>msgList = new ArrayList<Message>();
		for(String receiverId : idArray){
			msg.setReceiverId(Long.valueOf(receiverId));
			msg.setReceiverType(receiverType.getValue());
			try{
				msg.setId(null);
				messageDAO.save(msg);
				msgList.add(msg);
			} catch(Throwable e){
				log.error("创建会员消息失败", e);
			}
			msg = (Message) msg.clone();
		}
		return msgList;
	}

	/**
	 * @param valueOf
	 */
	public void deleteMessage(Message msg) {		
		messageDAO.delete(msg);		
	}

	/**
	 * @return
	 */
	public List<Message> getNeedToPushMessages() {
		return messageDAO.getNeedToPush();
	}
	
}
