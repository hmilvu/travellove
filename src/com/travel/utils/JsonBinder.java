/**
 *
 * Copyright (C) 2010-2011 Fsti Inc.
 *
 */

package com.travel.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 封装Jackson
 * 
 * @author calvin
 * @author Song Qing
 * 
 */
public class JsonBinder {

	private final ObjectMapper mapper;

	private static final Logger log = Logger.getLogger(JsonBinder.class);

	/**
	 * 创建只输出初始值被改变的属性到Json字符串的Binder.
	 */
	public static JsonBinder buildNonDefaultBinder() {
		return new JsonBinder(Include.NON_DEFAULT);
	}

	/**
	 * 创建输出全部属性到Json字符串的Binder.
	 */
	public static JsonBinder buildNormalBinder() {
		return new JsonBinder(Include.ALWAYS);
	}

	/**
	 * 创建输出非NULL属性到Json字符串的Binder.
	 */
	public static JsonBinder buildNotNullBinder() {
		return new JsonBinder(Include.NON_NULL);
	}

	public JsonBinder(Include include) {
		mapper = new ObjectMapper();

		// 设置输出时包含属性的风格
		if (include != null) {
			mapper.setSerializationInclusion(include);
		}

		// 设置时间格式
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mapper.setDateFormat(df);

		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	/**
	 * 反序列化POJO或简单Collection如List<String>.
	 * 
	 * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String,JavaType)
	 * 
	 * @see #fromJson(String, JavaType)
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			log.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/**
	 * 反序列化复杂Collection如List<Bean>, 先使用函数createCollectionType构造类型,然后调用本函数.
	 * 
	 * @see #createCollectionType(Class, Class...)
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromJson(String jsonString, JavaType javaType) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return (T) mapper.readValue(jsonString, javaType);
		} catch (IOException e) {
			log.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/**
	 * 構造泛型的Collection Type如: ArrayList<MyBean>,
	 * 则调用constructCollectionType(ArrayList.class,MyBean.class)
	 * HashMap<String,MyBean>, 则调用(HashMap.class,String.class, MyBean.class)
	 */
	public JavaType createCollectionType(Class<?> collectionClass,
			Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass,
				elementClasses);
	}

	/**
	 * 设置转换日期类型的format pattern,如果不设置默认打印 yyyy-MM-dd HH:mm:ss.
	 * 
	 * @param pattern
	 *            形如：yyyy-MM-dd
	 */
	public void setDateFormat(String pattern) {
		if (StringUtils.isNotBlank(pattern)) {
			DateFormat df = new SimpleDateFormat(pattern);
			mapper.setDateFormat(df);
		}
	}

	/**
	 * 名值对转JSON串 ；{\"name\":\"A\"}
	 * 
	 * 
	 * @param name
	 *            对象
	 * @param value
	 *            值
	 * @return JSON串
	 */
	public static String toJson(String name, String value) {
		return "{\"" + name + "\":\"" + value + "\"}";
	}

	/**
	 * 名值对转JSON串 ；{\"result\":\"A\"}
	 * 
	 * 
	 * @param name
	 *            对象
	 * @param value
	 *            值
	 * @return JSON串
	 */
	public static String toResultJson(String value) {
		return "{\"result\":\"" + value + "\"}";
	}

	/**
	 * 对象转JSON串
	 * 
	 * 如果对象为Null,返回"null". 如果集合为空集合,返回"[]".
	 * 
	 * @param object
	 *            对象
	 * @return JSON串
	 */
	public String toJson(Object object) {
		try {
			String json = mapper.writeValueAsString(object);
			// log.info("json：" + json);
			return json;
		} catch (Exception e) {
			log.error("对象写入Json串不成功！" + object, e);
			return null;
		}
	}
}
