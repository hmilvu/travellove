/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.mobile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.travel.action.BaseAction;
import com.travel.common.dto.AppVersionDTO;
import com.travel.common.dto.FailureResult;
import com.travel.common.dto.SuccessResult;
import com.travel.entity.AppVersion;
import com.travel.service.AppVersionService;
import com.travel.utils.JsonUtils;

/**
 * @author Lenovo
 *
 */
public class AppVersionAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private AppVersionService appVersionService;

	public String search(){
		String data = getMobileData();
		Object appTypeStr = getMobileParameter(data, "appType");
		Object osTypeStr =getMobileParameter(data, "osType");
		int appType = 0;
		int osType = 0;
		try{
			appType = Integer.valueOf(appTypeStr.toString());
			osType = Integer.valueOf(osTypeStr.toString());
		} catch(Exception e){
			FailureResult result = new FailureResult("输入参数类型错误");
			JsonUtils.write(response, binder.toJson(result));
			return null;
		}
		List<AppVersion> list = appVersionService.findByVersionAndType(appType, osType);
		if(list != null && list.size() > 0){
			SuccessResult <AppVersionDTO>result = new SuccessResult<AppVersionDTO>(list.get(0).toDTO());
			JsonUtils.write(response, binder.toJson(result));
		} else {
			FailureResult result = new FailureResult("未找到版本信息");
			JsonUtils.write(response, binder.toJson(result));
		}
		return null;
	}
}
