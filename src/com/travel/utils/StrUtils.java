package com.travel.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * @author deniro
 */
public class StrUtils {

	/**
	 * 是否为空，包含null，空串，“null”，“NULL”
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNotBlank(String value) {
		if (StringUtils.isNotBlank(value)
				&& !StringUtils.equalsIgnoreCase("null", value)) {
			return true;
		} else {
			return false;
		}

	}
}
