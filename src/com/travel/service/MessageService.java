package com.travel.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.travel.common.Constants.OS_TYPE;
import com.travel.common.Constants.PUSH_STATUS;
import com.travel.common.Constants.SMS_STATUS;
import com.travel.common.Constants.SMS_TRIGGER;
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
			int commentCount = messageDAO.getTotalReplyNum(msg.getId());
			dto.setCommentCount(commentCount);
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
			String memberName = memberDAO.findById(msg.getReceiverId()).getMemberName();
			msg.setReceiverName(memberName);
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
		msg.setScore(0);
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
			MemberInf m = memberDAO.findById(msg.getReceiverId());
			String memberName = m.getMemberName();
			msg.setReceiverName(memberName);
			msg.setOsType(m.getOsType());
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
	
	 private static String doGetRequest(String urlstr) {
	        String res = null;
	        try {
	            URL url = new URL(urlstr);
	            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
	            httpConn.setRequestMethod("GET");
	            httpConn.setRequestProperty("Content-Type", "text/html; charset=GB2312");
	            System.setProperty("sun.net.client.defaultConnectTimeout", "5000");//jdk1.4换成这个,连接超时
	            System.setProperty("sun.net.client.defaultReadTimeout", "10000"); //jdk1.4换成这个,读操作超时
	            //httpConn.setConnectTimeout(5000);//jdk 1.5换成这个,连接超时
	            //httpConn.setReadTimeout(10000);//jdk 1.5换成这个,读操作超时
	            httpConn.setDoInput(true);
	            int rescode = httpConn.getResponseCode();
	            if (rescode == 200) {
	                BufferedReader bfw = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
	                res = bfw.readLine();
	            } else {
	                res = "Http request error code :" + rescode;
	            }
	        } catch (Exception e) {
	            System.out.println(e.toString());
	        }
	        return res;
	    }
	 /** 
     * Hex编码字符组
     */
    private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    private static String encodeHexStr(int dataCoding, String realStr) {
        String hexStr = null;

        if (realStr != null) {
            byte[] data = null;
            try {
                if (dataCoding == 15) {
                    data = realStr.getBytes("GBK");
                } else if ((dataCoding & 0x0C) == 0x08) {
                    data = realStr.getBytes("UnicodeBigUnmarked");
                } else {
                    data = realStr.getBytes("ISO8859-1");
                }
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.toString());
            }

            if (data != null) {
                int len = data.length;
                char[] out = new char[len << 1];
                // two characters form the hex value.
                for (int i = 0, j = 0; i < len; i++) {
                    out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
                    out[j++] = DIGITS[0x0F & data[i]];
                }
                hexStr = new String(out);
            }
        }
        return hexStr;
    }
	
	 /**
     * 将 短信下行 请求响应字符串解析到一个HashMap中
     * @param resStr
     * @return
     */
    @SuppressWarnings("unchecked")
	private static HashMap parseResStr(String resStr) {
        HashMap pp = new HashMap();
        try {
            String[] ps = resStr.split("&");
            for (int i = 0; i < ps.length; i++) {
                int ix = ps[i].indexOf("=");
                if (ix != -1) {
                    pp.put(ps[i].substring(0, ix), ps[i].substring(ix + 1));
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return pp;
    }

	/**
	 * @param msg
	 * @param member
	 */
	public void sendSMS(Message msg, MemberInf member) {
		if(msg == null || member == null){
			log.error("msg or member is null. No need to send SMS." );
			return;
		}
		if(msg.getSmsTrigger().intValue() != SMS_TRIGGER.ACTIVE.getValue()){
			log.info("msg SMS trigger is disabled. No need to send SMS. msg.id = " + msg.getId());
			return;
		}
		try{
			String mtUrl=Config.getProperty("sms_url");
			//操作命令、SP编号、SP密码，必填参数
	        String command = "MULTI_MT_REQUEST";
	        String spid = Config.getProperty("sms_spid");
	        String sppassword = Config.getProperty("sms_password");
	        //sp服务代码，可选参数，默认为 00
	        String spsc = "00";
	        //源号码，可选参数
	        String sa = Config.getProperty("sms_sender");
	        
	        String das = "86" + member.getTravelerMobile();
	        //目标号码组，必填参数
	        //下行内容以及编码格式，必填参数
	        int dc = 15;
	        String sm = encodeHexStr(dc, msg.getContent());//下行内容进行Hex编码，此处dc设为15，即使用GBK编码格式
	
	        //组成url字符串
	        String smsUrl = mtUrl + "?command=" + command + "&spid=" + spid + "&sppassword=" + sppassword + "&spsc=" + spsc + "&sa=" + sa + "&das=" + das + "&sm=" + sm + "&dc=" + dc;
	        log.info("smsUrl = " + smsUrl);
	        //发送http请求，并接收http响应
	        String resStr = doGetRequest(smsUrl.toString());
	        log.info("resStr = " + resStr);
	
	        //解析响应字符串
	        HashMap pp = parseResStr(resStr);
	        if(pp != null && pp.get("mterrcode") != null && StringUtils.equals("000", pp.get("mterrcode").toString())){
	        	msg.setSmsStatus(SMS_STATUS.SENT.getValue());
	        } else {
	        	msg.setSmsStatus(SMS_STATUS.SEND_FAILED.getValue());
	        }
		} catch(Throwable e){
			log.error("发送短信失败", e);
			msg.setSmsStatus(SMS_STATUS.SEND_FAILED.getValue());
		} finally{
			messageDAO.update(msg);
		}
	        
		
	}

	/**
	 * @param message
	 * @param memberInf
	 */
	public void sendPushMsg(Message msg, MemberInf member) {
		if(msg == null || member == null){
			log.error("msg or member is null. No need to push message." );
			return;
		}
		try {		
			String apiKey = null;
			String secretKey = null;
			Integer deployStatus = null;
			// 1. 设置developer平台的ApiKey/SecretKey
			if(member.getOsType() != null && member.getOsType().intValue() == OS_TYPE.IOS.getValue()){
				apiKey = Config.getProperty("ios.baidu.appkey");
				secretKey = Config.getProperty("ios.baidu.secretkey");
				deployStatus = Integer.valueOf(Config.getProperty("ios.baidu.deploy.status"));
			} else {
				apiKey = Config.getProperty("android.baidu.appkey");
				secretKey = Config.getProperty("android.baidu.secretkey");
			}
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
	
			// 4. 创建请求类对象, 手机端的ChannelId， 手机端的UserId
			PushUnicastMessageRequest request = new PushUnicastMessageRequest();
			// device_type => 1: web 2: pc 3:android 4:ios 5:wp		
			if(member.getOsType() != null && member.getOsType().intValue() == OS_TYPE.IOS.getValue()){
				request.setDeployStatus(deployStatus); // DeployStatus => 1: Developer 2: Production 
				request.setDeviceType(4);	
			} else {
				request.setDeviceType(3);
			}
			request.setChannelId(member.getChannelId());	
			request.setUserId(member.getBaiduUserId());	
			request.setMessageType(1);	
			String content = msg.getContent();
			if(member.getOsType() != null && member.getOsType().intValue() == OS_TYPE.IOS.getValue()){
				request.setMessage("{\"aps\":{\"alert\":\""+content+"\",\"sound\":\"default\"}}");
			} else {
				request.setMessage("{\"title\":\"旅游关爱消息中心\",\"description\":\""+content+"\"}");
			}
			
			// 5. 调用pushMessage接口
			PushUnicastMessageResponse response = channelClient.pushUnicastMessage(request);						
			// 6. 认证推送成功
			log.info("单点推送成功 memberId = " + member.getId() +", channelId = " + member.getChannelId());
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

	/**
	 * @param msg
	 * @param teamIds
	 * @return
	 */
	public List<Message> addMessageForTeamMembers(List<MemberInf> memberList, Message msg, String teamIds) {
		String memberIds = "";
		for(MemberInf m : memberList){
			memberIds += m.getId() + ",";
		}
		if(memberIds.length() > 0){
			memberIds = memberIds.substring(0, memberIds.length() - 1);
		}
		return addMessageForReceiver(msg, memberIds, MESSAGE_RECEIVER_TYPE.TEAM);
	}
}
