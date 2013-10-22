package com.travel.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 启动缓存
 * 
 * @author deniro
 */
public class CacheBeanPostProcessor implements BeanPostProcessor {

	private static final Logger log = LoggerFactory
			.getLogger(CacheBeanPostProcessor.class);

	@Override
	public Object postProcessAfterInitialization(Object obj, String arg1)
			throws BeansException {
		// try {
		// if (obj instanceof AreaService) {
		// AreaService areaService = (AreaService) obj;
		// AreaService.firstAreas = areaService.getFirstArea();
		// AreaService.areas = areaService.getAreas();
		// log.info("成功加载【区域】树形结构数据！");
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		return obj;
	}

	@Override
	public Object postProcessBeforeInitialization(Object arg0, String arg1)
			throws BeansException {
		return arg0;
	}

}
