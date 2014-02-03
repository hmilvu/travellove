/**
 * @author Zhang Zhipeng
 *
 * 2013-12-22
 */
package com.travel.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.travel.utils.HttpUtil;

/**
 * @author Lenovo
 * 
 */
@Service
public class WeatherService {
	private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
	@SuppressWarnings("unchecked")
//	@Scheduled(cron = "0 0 8,20 * * ?") 
	public String[] getWheatherData(Double latitude, Double longitude) {
		log.info("获取天气数据");
		String[] data = new String[50];
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
				log.info("城市名称 city = " + city);
				if(city != null){
					int endInd = city.indexOf("市");
					city = city.substring(0, endInd);
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
			}
		} catch (Throwable e) {
			log.error("根据经纬度获取城市名失败 response = " + response, e);
		}
		return data;
	}
}
