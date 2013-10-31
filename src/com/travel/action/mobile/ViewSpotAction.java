/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.mobile;

import org.springframework.beans.factory.annotation.Autowired;

import com.travel.action.BaseAction;
import com.travel.common.dto.FailureResult;
import com.travel.common.dto.SuccessResult;
import com.travel.common.dto.ViewSpotDTO;
import com.travel.entity.ViewSpotInfo;
import com.travel.service.ViewSpotService;
import com.travel.utils.JsonUtils;

/**
 * @author Lenovo
 *
 */
public class ViewSpotAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ViewSpotService viewSpotService;

	public String search(){
		String data = getMobileData();
		Object id = getMobileParameter(data, "id");
		Long idLong = Long.valueOf(0);
		try{
			idLong = Long.valueOf(id.toString());
		}catch(Exception e){
			FailureResult result = new FailureResult("id类型错误");
			JsonUtils.write(response, binder.toJson(result));
			return null;
		}
		ViewSpotInfo viewSpot = viewSpotService.getViewSpotById(idLong);
		if(viewSpot != null && viewSpot.getId() > 0){
			SuccessResult <ViewSpotDTO>result = new SuccessResult<ViewSpotDTO>(viewSpot.toDTO());
			JsonUtils.write(response, binder.toJson(result));
		} else {
			FailureResult result = new FailureResult("景点不存在");
			JsonUtils.write(response, binder.toJson(result));
		}
		return null;
	}
}
