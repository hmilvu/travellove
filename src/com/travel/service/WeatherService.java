/**
 * @author Zhang Zhipeng
 *
 * 2013-12-22
 */
package com.travel.service;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.WeatherDataDAO;
import com.travel.entity.WeatherData;
import com.travel.utils.HttpUtil;

/**
 * @author Lenovo
 * 
 */
@Service
public class WeatherService {
	private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
	@Autowired
	private WeatherDataDAO weahterDao;
	
	@SuppressWarnings("unchecked")
	public String[] getWheatherData(Double latitude, Double longitude) {
		log.info("获取天气数据");
		String[] data = new String[0];
		String apiKey = "SHUp4teTcYTAGKHZef97IbGP";//Config.getProperty("baidu.appkey");
		Double lat = latitude;// 37.942125;
		Double lng = longitude;//105.961462;
		String url = "http://api.map.baidu.com/geocoder?location=" + lat + "," + lng + "&output=json&key=" + apiKey;
		String response = HttpUtil.post(url, null);
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES , true);
        Map<String, Object> reponseMapper = null;;
		try {
			reponseMapper = objectMapper.readValue(response, HashMap.class);
			Object status = reponseMapper.get("status");
			if(status != null && StringUtils.equals(status.toString(), "OK")){
				HashMap result = (HashMap)reponseMapper.get("result");
				HashMap address = (HashMap)result.get("addressComponent");
				String city = (String)address.get("city");
				data = getDataByCityName(city);  
			}
		} catch (Throwable e) {
			log.error("根据经纬度获取城市名失败 response = " + response, e);
		}
		return data;
	}
	/**
	 * @param data
	 * @param city
	 */
	private String[] getDataByCityName(String city) {
		String[] data = new String[50];
		log.info("城市名称 city = " + city);
		if(city != null){
			int endInd = city.indexOf("市");
			if(endInd > 0){
				city = city.substring(0, endInd);
			} else {
				log.error("城市名称里没有市为关键字 city = " + city);
				endInd = city.indexOf("省");
				if(endInd > 0){
					city = city.substring(0, endInd);
				} else {
					log.info("查询天气城市名称：" + city);
				}
			}
		}
		Map<String, String>params = new HashMap<String, String>();
		params.put("theCityName", city);
		String wheatherUrl = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx/getWeatherbyCityName";
		String wheatherResponse = HttpUtil.post(wheatherUrl, params);
		
		try {
			Document doc = DocumentHelper.parseText(wheatherResponse); 
			Element rootElt = doc.getRootElement(); 
			Iterator<Element> it = rootElt.elementIterator("string");
			int i = 0;
			while (it.hasNext()) {
				Element elm = it.next();
				data[i] = elm.getText();
				i++;
			}
			log.info("wheather : " + Arrays.asList(data));
		} catch (Exception ex) {
			log.error("获取天气失败 wheatherResponse = " + wheatherResponse);
		}
		return data;
	}
	/**
	 * @param trim
	 * @return
	 */
	public WeatherData getWheatherData(String cityName) {
		cityName = "上海";
		WeatherData weatherData = weahterDao.seachByCityName(cityName);
		if(weatherData == null || !DateUtils.isSameDay(new Date(System.currentTimeMillis()), weatherData.getCreateDate())){
			if(weatherData == null){
				weatherData = new WeatherData();
				weatherData.setCityName(cityName);
			}
			String []data = getDataByCityName(cityName);  
			weatherData.setData1(data[5]);
			weatherData.setData2(data[6]);
			weatherData.setData3(data[7]);
			weatherData.setCreateDate(new Date(System.currentTimeMillis()));
			weahterDao.saveOrUpdate(weatherData);
		}		
		return weatherData;
	}
}
