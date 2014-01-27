/**
 *
 * Copyright (C) 2010-2011 Fsti Inc.
 *
 */

package com.travel.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * JSON 工具集
 * 
 * @author Song Qing
 * 
 */
public class JsonUtils {
	/**
	 * 字符集
	 */
	private static final String CHARSET = "utf-8";
	/**
	 * 响应格式
	 */
	private static final String MIME = "application/json; charset=" + CHARSET;
	/**
	 * html响应格式
	 */
	private static final String MIME_HTML = "text/html; charset=" + CHARSET;

	private static final Logger log = Logger.getLogger(JsonUtils.class);

	/**
	 * JSON字符串写入输出流 SUCCESS
	 * 
	 * @param response
	 *            响应流
	 * @param str
	 *            字符串
	 * 
	 */
	public static void writeSuccess(HttpServletResponse response) {
		write(response, JsonBinder.toResultJson("SUCCESS"));
	}

	/**
	 * JSON字符串写入输出流 ParamError
	 * 
	 * @param response
	 *            响应流
	 * @param str
	 *            字符串
	 * 
	 */
	public static void writeParamError(HttpServletResponse response) {
		write(response, JsonBinder.toResultJson("ParamError"));
	}

	/**
	 * JSON字符串写入输出流 ExceptionError
	 * 
	 * @param response
	 *            响应流
	 * @param str
	 *            字符串
	 * 
	 */
	public static void writeExceptionError(HttpServletResponse response) {
		write(response, JsonBinder.toResultJson("ExceptionError"));
	}

	/**
	 * JSON字符串写入输出流
	 * 
	 * @param response
	 *            响应流
	 * @param str
	 *            字符串
	 * 
	 */
	public static void write(HttpServletResponse response, String str) {
		response.setContentType(MIME);
		try {
			PrintWriter pr = response.getWriter();
			pr.write(str);
			pr.close();
		} catch (IOException e) {
			log.error("JSON字符串无法写入输出流！", e);
		}
	}

	/**
	 * JSON字符串写入输出流
	 * 
	 * @param response
	 *            响应流
	 * @param str
	 *            字符串
	 * 
	 */
	public static void writeInHtml(HttpServletResponse response, String str) {
		response.setContentType(MIME_HTML);
		try {
			response.getWriter().write(str);
		} catch (IOException e) {
			log.error("JSON字符串无法写入输出流！", e);
		}
	}

}
