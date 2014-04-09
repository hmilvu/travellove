package com.travel.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageResponse;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.travel.common.Constants.MESSAGE_CREATE_TYPE;
import com.travel.common.Constants.MESSAGE_RECEIVER_TYPE;
import com.travel.common.Constants.MESSAGE_REMIND_MODE;
import com.travel.common.Constants.MESSAGE_STATUS;
import com.travel.common.Constants.MESSAGE_TYPE;
import com.travel.common.Constants.PUSH_STATUS;
import com.travel.common.admin.dto.SearchMessageDTO;
import com.travel.common.dto.MessageDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.common.dto.ReplyDTO;
import com.travel.dao.MemberInfDAO;
import com.travel.dao.MemberMessageVisibilityDAO;
import com.travel.dao.MessageDAO;
import com.travel.dao.ReplyDAO;
import com.travel.dao.TeamInfoDAO;
import com.travel.entity.MemberInf;
import com.travel.entity.MemberMessageVisibility;
import com.travel.entity.Message;
import com.travel.entity.Reply;
import com.travel.entity.TravelInf;
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
	@Autowired
	private MemberMessageVisibilityDAO mvDao;
	public Message getMessageById(Long id){
		return messageDAO.findById(id);
	}

	/**
	 * @param idLong
	 * @return
	 */
	public List<MessageDTO> getMessageByMemberId(Long memberId, Long teamId, PageInfoDTO pageInfo) {
		List <Message>list = messageDAO.findByReceiverId(memberId, teamId, pageInfo);
		List <Long>visibleIdList = mvDao.getVisibleIdList(memberId);		
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
			if(visibleIdList.contains(msg.getId())){
				dto.setRead(1);
			} else {
				dto.setRead(0);
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
		String []teamIdArr = StringUtils.split(teamIds, ",");
		List<Message> newMsgList = new ArrayList<Message>();
		List<MemberInf> newMemberList = new ArrayList<MemberInf>();
		for(int i = 0; i < teamIdArr.length; i++){
			String teamId = teamIdArr[i];
			List<Long> idList = new ArrayList<Long>();
			idList.add(Long.valueOf(teamId));
			List<MemberInf> memberList = memberService.findAllMembersByTeamIds(idList);
			newMemberList.addAll(memberList);
			for(int j = 0; j < memberList.size(); j++){
				newMsgList.add(messageList.get(i));
			}
		}
		if(newMemberList.size() == 0){
			for(Message msg : messageList){
				msg.setPushStatus(PUSH_STATUS.PUSHED.getValue());
				messageDAO.update(msg);
			}
		} else {
			int result = sendMemberPushMsg(newMsgList, content, newMemberList);
			if(result == newMemberList.size()){
				log.info("全部推送成功");
			} else {
				log.info("部分或全部推送失败");
			}
		}
/*		// 1. 设置developer平台的ApiKey/SecretKey
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
		}	*/	
	}

	/**
	 * @param msgIdList
	 * @param content
	 * @param memberList
	 * @brief	推送单播消息(消息类型为透传，由开发方应用自己来解析消息内容)
	* 			message_type = 0 (默认为0)
	 */
	public int sendMemberPushMsg(List<Message>messageList, String content,
			List<MemberInf> memberList) {
		int result = 0;
		// 1. 设置developer平台的ApiKey/SecretKey
		String apiKey = Config.getProperty("baidu.appkey");
		String secretKey = Config.getProperty("baidu.secretkey");
		if(memberList.size() == 0){
			for(Message msg : messageList){
				msg.setPushStatus(PUSH_STATUS.PUSHED.getValue());
				messageDAO.update(msg);
			}
		} else {
			for(int i = 0; i < memberList.size(); i++){
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
			
				MemberInf member = memberList.get(i);
				Message msg = messageList.get(i);
				try {				
					// 4. 创建请求类对象, 手机端的ChannelId， 手机端的UserId
					PushUnicastMessageRequest request = new PushUnicastMessageRequest();
					request.setDeviceType(3);	// device_type => 1: web 2: pc 3:android 4:ios 5:wp		
					request.setChannelId(member.getChannelId());	
					request.setUserId(member.getBaiduUserId());	
					request.setMessageType(1);	
					request.setMessage("{\"title\":\"旅游关爱消息中心\",\"description\":\""+content+"\"}");
					
					// 5. 调用pushMessage接口
					PushUnicastMessageResponse response = channelClient.pushUnicastMessage(request);						
					// 6. 认证推送成功
					log.info("单点推送成功 memberId = " + member.getId() +", channelId = " + member.getChannelId());
					log.info("push amount : " + response.getSuccessAmount()); 	
					msg.setPushStatus(PUSH_STATUS.PUSHED.getValue());
					result++;
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
		return result;
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
		msg.setPushStatus(PUSH_STATUS.NOT_PUSH.getValue());
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

	/**
	 * @param idLong
	 * @return
	 */
	public Reply getReplyById(Long idLong) {
		return replyDAO.findById(idLong);
	}

	/**
	 * @param idLong
	 * @param pageInfo
	 * @return
	 */
	public List<MessageDTO> getMessageDTOByReceiverId(MESSAGE_RECEIVER_TYPE type, Long recieverId,
			PageInfoDTO pageInfo) {
		List<Message> list = messageDAO.findMessages(type, recieverId, pageInfo);
		List<MessageDTO> resultList = new ArrayList<MessageDTO>();
		for(Message msg : list){
			MemberInf member = memberDAO.findById(msg.getCreateId());
			MessageDTO dto = msg.toDTO();
			dto.setCreatorName(member.getNickname());
			resultList.add(dto);
		}
		return resultList;
	}

	/**
	 * @param memberIdLong
	 * @param idLong
	 * @param content
	 */
	public void saveViewspotMessage(MemberInf member, Long viewSpotId,
			String content, Integer scoreInt) {
		Message msg = setupComment(member, viewSpotId, content, scoreInt);
		msg.setReceiverType(MESSAGE_RECEIVER_TYPE.VIEW_SPOT.getValue());
		messageDAO.save(msg);
	}

	/**
	 * @param member
	 * @param viewSpotId
	 * @param content
	 * @return
	 */
	private Message setupComment(MemberInf member, Long viewSpotId,
			String content, Integer score) {
		Message msg = new Message();
		msg.setRemindTime(new Timestamp(new Date().getTime()));
		msg.setRemindMode(MESSAGE_REMIND_MODE.NOW.getValue());
		msg.setStatus(MESSAGE_STATUS.ACTIVE.getValue());
		msg.setPriority(Integer.valueOf(0));
		msg.setType(MESSAGE_TYPE.NOTE.getValue());
		msg.setTopic("");
		msg.setContent(content);
		msg.setCreateType(MESSAGE_CREATE_TYPE.MEMBER.getValue());
		msg.setCreateId(member.getId());
		msg.setCreateDate(new Timestamp(new Date().getTime()));
		msg.setUpdateDate(msg.getCreateDate());
		msg.setPushStatus(PUSH_STATUS.NOT_PUSH.getValue());
		Hibernate.initialize(member.getTeamInfo());
		TravelInf travelInf = new TravelInf();
		travelInf.setId(member.getTeamInfo().getTravelInf().getId());
		msg.setReceiverId(viewSpotId);
		msg.setTravelInf(travelInf);
		msg.setScore(score);
		return msg;
	}

	/**
	 * @param valueOf
	 * @return
	 */
	public int getTotalMessageNumByReceiverId(MESSAGE_RECEIVER_TYPE type, Long recieverId) {
		return messageDAO.getTotalMessageNum(type, recieverId);
	}

	/**
	 * @param valueOf
	 * @param pageInfo
	 * @return
	 */
	public List<Message> findMessageByReceiverId(MESSAGE_RECEIVER_TYPE type, Long receiverId,
			PageInfoDTO pageInfo) {
		List<Message> list = messageDAO.findMessages(type, receiverId, pageInfo);
		List<Message> result = new ArrayList<Message>();
		for(Message msg : list){
			String memberName = memberDAO.findById(msg.getCreateId()).getMemberName();
			msg.setReceiverName(memberName);
			result.add(msg);
		}
		return result;
	}

	/**
	 * @param member
	 * @param itemIdLong
	 * @param string
	 */
	public void savItemMessage(MemberInf member, Long itemIdLong, String content, int scoreInt) {
		Message msg = setupComment(member, itemIdLong, content, scoreInt);
		msg.setReceiverType(MESSAGE_RECEIVER_TYPE.ITEM.getValue());
		messageDAO.save(msg);
	}
	
	/**
	 * @param dto
	 * @return
	 */
	public int getTotalTriggerMessageNum(SearchMessageDTO dto) {
		return messageDAO.getTriggerTotalNum(dto);
	}

	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	public List<Message> findTriggerMessages(SearchMessageDTO dto,
			PageInfoDTO pageInfo) {
		List<Message> list = messageDAO.findTriggerMessages(dto, pageInfo);
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
	 * @param memberIdLong
	 * @param messageIdLong
	 */
	public void addVisibility(Long memberId, Long messageId) {
		MemberMessageVisibility v = new MemberMessageVisibility();
		MemberInf m = new MemberInf();
		m.setId(memberId);
		
		Message msg = new Message();
		msg.setId(messageId);
		
		v.setMemberInf(m);
		v.setMessage(msg);
		
		mvDao.save(v);
		
	}

	/**
	 * @param memberIdLong
	 * @param msgIdLong
	 */
	public void deleteVisible(Long memberId, Long msgId) {
		mvDao.deleteVisible(memberId, msgId);
		
	}
	
	
	
}
