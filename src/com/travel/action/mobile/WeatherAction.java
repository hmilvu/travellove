/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.mobile;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.travel.action.BaseAction;
import com.travel.common.dto.FailureResult;
import com.travel.common.dto.SuccessResult;
import com.travel.common.dto.WeatherDataDTO;
import com.travel.entity.WeatherData;
import com.travel.service.WeatherService;

/**
 * @author Lenovo
 * 
 */
public class WeatherAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private WeatherService weatherService;
	
	public void getWeatherData(){
		String data = getMobileData();
		Object cityObj = getMobileParameter(data, "cityName");
		if(cityObj == null){
			FailureResult result = new FailureResult("cityName不能为空");
			sendToMobile(result);
			return;
		}
		if(StringUtils.isBlank(cityObj.toString())){
			FailureResult result = new FailureResult("cityName不能为空");
			sendToMobile(result);
			return;
		}
		WeatherData weatherData = weatherService.getWheatherData(cityObj.toString().trim());
		SuccessResult<WeatherDataDTO> result = new SuccessResult<WeatherDataDTO>(weatherData.toDTO());
		sendToMobile(result);
	}
}
