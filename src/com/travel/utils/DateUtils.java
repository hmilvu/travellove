/**
 *
 * Copyright (C) 2009-2012 Fsti Inc.
 *
 */

package com.travel.utils;

import hirondelle.date4j.DateTime;
import hirondelle.date4j.DateTime.DayOverflow;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 日期
 * 
 * @author Song Qing
 * 
 */
public class DateUtils {

	private static final Logger log = Logger.getLogger(DateUtils.class);
	/**
	 * 日期格式
	 */
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	/**
	 * Oracle日期+时间格式字符串
	 */
	private static final String ORACLE_DATE_TIME_FORMAT_STR = "'yyyy-mm-dd hh24:mi:ss'";
	/**
	 * 日期+时间格式字符串
	 */
	public static final String DATE_TIME_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 日期+时间格式 yyyy-MM-dd hh:mm:ss
	 */
	public static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat(
			DATE_TIME_FORMAT_STR);

	/**
	 * 日期+时间格式 yyyy年MM月dd日 HH时mm分ss秒
	 */
	private static final DateFormat WEB_DATE_TIME_FORMAT = new SimpleDateFormat(
			"yyyy年MM月dd日 HH时mm分ss秒");

	/**
	 * 转换为Oracle日期格式
	 * 
	 * @param str
	 * @return
	 */
	public static String toOracleDate(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		if (!str.contains("-")) {
			return "";
		}
		if (!str.contains(":")) {
			return StringUtils.trim(str) + " 00:00:00";
		}
		return str;
	}

	/**
	 * 转换为Oracle时间格式 yyyy年MM月dd日 HH时mm分ss秒 转为 yyyy-MM-dd HH-mm-ss
	 * 
	 * @param str
	 * @return
	 */
	public static String toOracleDateTime(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		}
		try {
			Date date = WEB_DATE_TIME_FORMAT.parse(str);
			return toStr(date);
		} catch (ParseException e) {
			log.info("无法转换为转换为Oracle时间格式！", e);
			return str;
		}

	}

	/**
	 * 转换为日期字符串（Oracle varchar格式）
	 * 
	 * str为空，转换为：to_date(?, 'yyyy-mm-dd hh24:mi:ss')； str不为空,转换为：to_date(str,
	 * 'yyyy-mm-dd hh24:mi:ss')；
	 * 
	 * @param str
	 * 
	 * @return
	 * 
	 */
	public static String toStrInOracleVarchar(String str) {
		if (StringUtils.isBlank(str)) {
			return ("TO_DATE(?, " + ORACLE_DATE_TIME_FORMAT_STR + ")");
		} else {
			return ("TO_DATE(" + str + ", " + ORACLE_DATE_TIME_FORMAT_STR + ")");
		}
	}

	/**
	 * 转换为日期字符串（Oracle varchar格式）
	 * 
	 * str为空，转换为：TO_CHAR(?, 'yyyy-mm-dd hh24:mi:ss')； str不为空,转换为：TO_CHAR(str,
	 * 'yyyy-mm-dd hh24:mi:ss')；
	 * 
	 * @param str
	 * 
	 * @return
	 * 
	 */
	public static String toCharInOracleVarchar(String str) {
		if (StringUtils.isBlank(str)) {
			return ("TO_CHAR(?, " + ORACLE_DATE_TIME_FORMAT_STR + ")");
		} else {
			return ("TO_CHAR(" + str + ", " + ORACLE_DATE_TIME_FORMAT_STR + ")");
		}
	}

	/**
	 * 转换为日期字符串 yyyy-MM-dd hh:mm:ss
	 * 
	 * @param date
	 * @return
	 * 
	 */
	public static String toStr(Date date) {
		if (date == null) {
			return "";
		}
		return DATE_TIME_FORMAT.format(date);
	}
	
	public static String toTimeStr(Date date) {
		if (date == null) {
			return "";
		}
		DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return DATE_FORMAT.format(date);
	}
	
	public static String toTimeStr2(Date date) {
		if (date == null) {
			return "";
		}
		DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return DATE_FORMAT.format(date);
	}

	/**
	 * 为包含下级地区的查询,生成like字段,截去'00'的字段(00表示有下级地区),并增加'%'后缀
	 * 
	 */
	public static String getAreaCodeLikeStr(String areaCode) {
		return areaCode.replace("00", "").concat("%");
	}

	/**
	 * 转换为时间 yyyy-MM-dd hh:mm:ss
	 * 
	 * 若无法转换，则返回NULL
	 * 
	 * @param str
	 * @return
	 * 
	 */
	public static Date toTime(String str) {
		try {
			return DATE_TIME_FORMAT.parse(str);
		} catch (ParseException e) {
			log.info("无法转换为时间格式！", e);
		}
		return null;
	}
	
	public static Date toTime2(String str) {
		try {
			DateFormat format = new SimpleDateFormat(
					 "yyyy-MM-dd HH:mm");
			return format.parse(str);
		} catch (ParseException e) {
			log.info("无法转换为时间格式！", e);
		}
		return null;
	}

	/**
	 * 转换为日期 yyyy-MM-dd
	 * 
	 * 若无法转换，则返回NULL
	 * 
	 * @param str
	 * @return
	 * 
	 */
	public static Date toDate(String str) {
		try {
			return DATE_FORMAT.parse(str);
		} catch (Throwable e) {
			//log.info("无法转换为日期格式！", e);
		}
		return null;
	}

	/**
	 * 日期时间格式 （date4j）
	 * 
	 * YYYY-MM-DD hh:mm:ss
	 */
	private static String DATE_TIME_FORMAT_DATE4J = "YYYY-MM-DD hh:mm:ss";

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static DateTime getCurrentTime() {
		return DateTime.now(TimeZone.getDefault());// 当前时间
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getCurrentTimeStr(DateTime currentTime) {
		return currentTime.format(DATE_TIME_FORMAT_DATE4J);
	}

	/**
	 * 获取在当前时间多少分钟前的时间
	 * 
	 * @param minutes
	 *            分钟数
	 * @return
	 */
	public static String getCurrentTimeBeforeInMinStr(DateTime currentTime,
			int minutes) {
		DateTime time = currentTime.minus(0, 0, 0, 0, 30, 0, 0,
				DayOverflow.LastDay);
		return time.format(DATE_TIME_FORMAT_DATE4J);
	}

	/**
	 * @param date
	 * @return
	 */
	public static String toSimpleStr(Date date) {
		if (date == null) {
			return "";
		}
		return DATE_FORMAT.format(date);
	}
}
