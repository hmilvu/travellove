/**
 *
 * Copyright (C) 2009-2012 Fsti Inc.
 *
 */

package com.travel.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 字符集
 * 
 * @author Song Qing
 * 
 */
public class CharacterUtils {
	private static final Logger log = Logger.getLogger(CharacterUtils.class);
	/**
	 * 系统字符集
	 */
	private static final String SYSTEM_CHARACTER_SET = "UTF-8";

	/**
	 * 解码URL中的中文参数
	 * 
	 * @param str
	 * @return
	 */
	public static String decodeChineseInUrl(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		}
		try {
			return URLDecoder.decode(str, SYSTEM_CHARACTER_SET);
		} catch (UnsupportedEncodingException e) {
			log.error("", e);
			return str;
		}
	}
}