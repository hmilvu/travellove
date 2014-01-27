/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.travel.action.AuthorityAction;
import com.travel.entity.AreaInf;
import com.travel.service.AreaService;
import com.travel.utils.JsonUtils;

/**
 * @author Lenovo
 *
 */
public class AreaInfAction extends AuthorityAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AreaService areaService;

	public String cityList(){
		String cityCode = request.getParameter("cityCode");
		List<AreaInf> cityList = null;
		if(StringUtils.isNotBlank(cityCode)){
			cityList = areaService.getSubCitiesByCode(cityCode);
		} else {
			cityList = new ArrayList<AreaInf>();
		}
		StringBuilder s = new StringBuilder("[");
		s.append("[\"\", \"请选择城市\"],");
		if(cityList != null && cityList.size() > 0){
			for(AreaInf area : cityList){
				s.append("[\"" + area.getCityCode() + "\", \"" + area.getCityName() + "\"],");
			}
		}
		s.setLength(s.length() - 1);
		s.append("]");
		JsonUtils.write(response, s.toString());
		return null;
	}
}
