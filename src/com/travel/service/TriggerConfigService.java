package com.travel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
}
