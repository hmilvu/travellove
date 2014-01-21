package com.travel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.common.Constants.TRIGGER_STATUS;
import com.travel.common.admin.dto.SearchTriggerConfigDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.dao.TriggerConfigDAO;
import com.travel.entity.TriggerConfig;

@Service
public class TriggerConfigService extends AbstractBaseService
{
	@Autowired
	private TriggerConfigDAO triggerDao;

	/**
	 * @param id
	 * @param configList
	 */
	public void initTriggerConfig(Long travelId) {
		triggerDao.initTriggerConfig(travelId);		
	}

	/**
	 * @param dto
	 * @return
	 */
	public int getTotalTriggerConfigNum(SearchTriggerConfigDTO dto) {
		return triggerDao.getTotalNum(dto);
	}

	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	public List<TriggerConfig> findTriggerConfigs(SearchTriggerConfigDTO dto,
			PageInfoDTO pageInfo) {
		return triggerDao.findTriggerConfigs(dto, pageInfo);
	}

	/**
	 * @param valueOf
	 * @return
	 */
	public TriggerConfig getTriggerConfigById(Long triggerId) {
		return triggerDao.findById(triggerId);
	}

	/**
	 * @param valueOf
	 */
	public void updateTriggerStatus(Long triggerId) {
		TriggerConfig trigger = triggerDao.findById(triggerId);
		if(trigger.getTriggerStatus().intValue() == TRIGGER_STATUS.ACTIVE.getValue()){
			trigger.setTriggerStatus(TRIGGER_STATUS.INACTIVE.getValue());
		} else {
			trigger.setTriggerStatus(TRIGGER_STATUS.ACTIVE.getValue());
		}
		triggerDao.update(trigger);
	}

	/**
	 * @param trigger
	 */
	public void updateTrigger(TriggerConfig trigger) {
		triggerDao.update(trigger);
	}	
	
}
