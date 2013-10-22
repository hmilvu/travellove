package com.travel.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 文件
 * 
 * @author deniro
 */
public class FileUtils {

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtensionName(String fileName) {
		return StringUtils.substringAfterLast(fileName, ".");
	}

	public static void main(String[] args) {
		System.out.println(FileUtils.getExtensionName("xx.xx1"));
	}
}
