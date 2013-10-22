/**
 *
 * Copyright (C) 2009-2011 Fsti Inc.
 *
 */

package com.travel.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * CSV工具类
 * 
 * 
 * @author Song Qing
 *
 */
public class CsvUtils {

	private static Logger logger = Logger.getLogger(CsvUtils.class);
	/**
	 * 文件后缀
	 */
	public static final String FILE_SUFFIX = ".csv";

	/**
	*  创建包含待导出数据CSV文件
	* 
	* @param exportData  待导出数据
	* @param columnNames 列字段名称/列显示名称映射表
	* @param 包含待导出数据CSV文件
	* 
	*/
	public static File createCSVFile(List<Map<String, Object>> exportData, Map<String, String> columnNames) {
		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		final String errorTip = "创建包含待导出数据CSV文件不成功！";
		try {
			csvFile = File.createTempFile("temp", FILE_SUFFIX);
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GBK"), 1024);

			//写入标题
			Set<Entry<String, String>> columnNamesSet = columnNames.entrySet();
			for (Iterator<Entry<String, String>> iterator = columnNamesSet.iterator(); iterator.hasNext();) {
				csvFileOutputStream.write("\"" + iterator.next().getValue().toString() + "\"");
				if (iterator.hasNext()) {
					csvFileOutputStream.write(",");
				}
			}

			//换行
			csvFileOutputStream.newLine();

			//写入数据
			Set<String> toExportColumnNameValues = columnNames.keySet();//待导出列名称值集
			int i = 1;
			for (Iterator<Map<String, Object>> iterator = exportData.iterator(); iterator.hasNext();) {
				Map<String, Object> row = iterator.next();
				Set<String> columnNameValueSet = row.keySet();
				for (Iterator<String> columnNameValueIterator = columnNameValueSet.iterator(); columnNameValueIterator
						.hasNext();) {
					String columnNameValue = columnNameValueIterator.next();
					if (!toExportColumnNameValues.contains(columnNameValue)) {
						continue;
					}
					csvFileOutputStream.write("\"" + row.get(columnNameValue) + "\"");
					if (columnNameValueIterator.hasNext()) {
						csvFileOutputStream.write(",");
					}
				}
				if (iterator.hasNext()) {//换行
					csvFileOutputStream.newLine();
				}
			}
			csvFileOutputStream.flush();
		} catch (Exception e) {
			logger.error(errorTip, e);
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				logger.error(errorTip, e);
			}
		}
		return csvFile;
	}
}
