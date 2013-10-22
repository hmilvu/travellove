/**
 *
 * Copyright (C) 2009-2012 Fsti Inc.
 *
 */

package com.travel.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * SQL 工具类
 * 
 * @author Song Qing
 * 
 */
public class SqlUtils {
	/**
	 * 最后一条记录所在行号名称
	 */
	public static final String LAST_ROWNUM_NAME = "ROWNUM";

	/**
	 * 首条记录所在行号名称
	 */
	public static final String FIRST_ROWNUM_NAME = "RN";

	/**
	 * 生成求记录数SQL语句 形如： SELECT COUNT(1) FROM ...
	 * 
	 * @param sql
	 *            原始SQL
	 * @return
	 */
	public static String countSql(String sql) {
		if (StringUtils.isBlank(sql)) {
			return sql;
		}

		String selectFragment = "";
		if (StringUtils.contains(sql, " from ")) {
			selectFragment = StringUtils.substringBefore(sql, " from ");
		}
		if (StringUtils.contains(sql, " FROM ")) {
			selectFragment = StringUtils.substringBefore(sql, " FROM ");
		}

		if (StringUtils.containsIgnoreCase(selectFragment, "COUNT")) {
			return "SELECT COUNT(1)  FROM (" + sql + ")";
		}

		String surplusSql = StringUtils.substringAfter(sql, selectFragment);
		if (StringUtils.isBlank(surplusSql)) {
			return sql;
		}

		return "SELECT COUNT(1) " + surplusSql;
	}

	/**
	 * 生成分页SQL语句
	 * 
	 * @param sql
	 *            原始SQL
	 * @param isUseName
	 *            是否使用名称占位符
	 * @return
	 */
	public static String pageSql(String sql, boolean isUseName) {
		if (StringUtils.isBlank(sql)) {
			return sql;
		}

		StringBuilder pageSql = new StringBuilder();
		pageSql.append("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (");
		pageSql.append(sql);
		if (isUseName) {
			pageSql.append(") A WHERE ROWNUM <= :" + LAST_ROWNUM_NAME
					+ ") WHERE RN >= :" + FIRST_ROWNUM_NAME);
		} else {
			pageSql.append(") A WHERE ROWNUM <= ?) WHERE RN >= ? ");
		}
		return pageSql.toString();
	}

	/**
	 * 生成分页SQL语句，使用名称占位符，形如： SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (SELECT
	 * * FROM TABLE_NAME) A WHERE ROWNUM <= :ROWNUM ) WHERE RN >= :RN
	 * 
	 * @param sql
	 *            原始SQL
	 * @return
	 */
	public static String pageSqlInName(String sql) {
		return pageSql(sql, true);
	}

	/**
	 * 生成分页SQL语句，使用传统占位符，形如： SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (SELECT
	 * * FROM TABLE_NAME) A WHERE ROWNUM <= ? ) WHERE RN >= ?
	 * 
	 * @param sql
	 *            原始SQL
	 * @return
	 */
	public static String pageSqlInCommon(String sql) {
		return pageSql(sql, false);
	}

}
