/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.mobile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.travel.action.BaseAction;
import com.travel.common.dto.FailureResult;
import com.travel.common.dto.ItemInfDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.common.dto.SuccessResult;
import com.travel.entity.ItemInf;
import com.travel.entity.TeamInfo;
import com.travel.service.ItemInfService;
import com.travel.service.OrderService;
import com.travel.service.TeamInfoService;

/**
 * @author Lenovo
 * 
 */
public class ItemInfAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private TeamInfoService teamService;
	@Autowired
	private ItemInfService itemService;
	@Autowired
	private OrderService orderService;
	
	public void search() {
		String data = getMobileData();
		Object teamId = getMobileParameter(data, "teamId");
		Long teamIdLong = Long.valueOf(0);
		try {
			teamIdLong = Long.valueOf(teamId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("teamId类型错误");
			sendToMobile(result);
			return;
		}
		Object pageSize = getMobileParameter(data, "pageSize");
		Object pageNumber = getMobileParameter(data, "pageNumber");
		PageInfoDTO pageInfo = new PageInfoDTO();
		try{
			pageInfo.setPageNumber(Integer.valueOf(pageNumber.toString()));
			pageInfo.setPageSize(Integer.valueOf(pageSize.toString()));
		}catch(Throwable ignore){			
		}
		TeamInfo team = teamService.getTeamById(teamIdLong);
		List<ItemInfDTO> list = itemService.getItemList(team.getTravelInf().getId(), pageInfo);
		SuccessResult<List<ItemInfDTO>> result = new SuccessResult<List<ItemInfDTO>>(list);
		sendToMobile(result);
		return;
	}
	
	public void detail(){
		String data = getMobileData();
		Object itemId = getMobileParameter(data, "itemId");
		Long itemIdLong = Long.valueOf(0);
		try {
			itemIdLong = Long.valueOf(itemId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("itemId类型错误");
			sendToMobile(result);
			return;
		}
		ItemInf item = itemService.getItemById(itemIdLong);
		if(item != null){
			SuccessResult<ItemInfDTO> result = new SuccessResult<ItemInfDTO>(item.toDTO());
			sendToMobile(result);
		} else{
			FailureResult result = new FailureResult("产品不存在itemId=" + itemId);
			sendToMobile(result);
		}
		return;
	}	
}
