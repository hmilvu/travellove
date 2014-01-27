/**
 * @author Zhang Zhipeng
 *
 * 2013-12-22
 */
package com.travel.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.travel.common.Constants.MESSAGE_RECEIVER_TYPE;
import com.travel.entity.MemberInf;
import com.travel.entity.Message;
import com.travel.entity.TriggerConfig;

/**
 * @author Lenovo
 * 
 */
@Component
public class MessageServiceScheduler {
	private static final Logger log = LoggerFactory.getLogger(MessageServiceScheduler.class);
	@Autowired
	private MessageService messageService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private TriggerConfigService triggerService;
	@Scheduled(fixedRate = 60000)
	void doSomethingWithRate() {
		log.info("检查预约消息，准备推送");
		List<Message> messageList = messageService.getNeedToPushMessages();
		for(Message msg : messageList){
			log.info("开始推送");
			if(msg.getReceiverType().intValue() == MESSAGE_RECEIVER_TYPE.TEAM.getValue()){
				messageService.sendTeamPushMsg(messageList, msg.getContent(), msg.getReceiverId()+"");
			} else {
				MemberInf member = memberService.getMemberById(msg.getReceiverId());
				List <MemberInf> memberList = new ArrayList<MemberInf>();
				memberList.add(member);
				messageService.sendMemberPushMsg(messageList, msg.getContent(), memberList);
			}
		}
		
//		List<TriggerConfig> list = triggerService.getValidTriggerConfigs();
//		for(TriggerConfig trigger : list){
//			log.info("自动触发：" + trigger.toString());
//			triggerService.trigger(trigger);
//		}		
	}
}
