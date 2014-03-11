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
import com.travel.common.Constants.TRIGGER_TYPE;
import com.travel.entity.MemberInf;
import com.travel.entity.Message;
import com.travel.entity.TriggerConfig;

/**
 * @author Lenovo
 * 
 */
@Component
public class WeatherServiceScheduler {
	private static final Logger log = LoggerFactory.getLogger(WeatherServiceScheduler.class);
	@Autowired
	private TriggerConfigService triggerService;
	@Scheduled(cron = "0 0/30 6-22 * * ?") 
	void doSomethingWithRate() {
		log.info("检查天气触发");
		List<TriggerConfig> list = triggerService.getValidTriggerConfigs();
		for(TriggerConfig trigger : list){
			log.info("自动触发：" + trigger.toString());
			if(trigger.getTriggerType().intValue() == TRIGGER_TYPE.WEATHER.getValue()){
				triggerService.trigger(trigger);
			}
		}	
		
	}
}
