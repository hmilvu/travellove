package com.travel.utils;

/**
 * Company:福建邮科
 * Title:
 * Description: 
 * @author ilikeido
 * @version 1.0
 * @createtime 2009-8-18
 */
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Config {
    private static Log log = LogFactory.getLog(Config.class);
    private static String CONFIG_FILENAME = "config.properties";
    private static Properties prop = null;

    public Config() {
        if (prop == null) {
            loadProperties();
        }
    };

    private synchronized static void loadProperties() {
        try {
            InputStream is = Config.class.getResourceAsStream("/"
                    + CONFIG_FILENAME);
            prop = new Properties();
            prop.load(is);
        } catch (Exception e) {
            System.err.println("读取配置文件失败！！！");
            prop = null;
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 得到属性值
     *
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        if (prop == null) {
            loadProperties();
        }
        String value = prop.getProperty(key);
        if (value == null) {
            return null;
        }
        return value.trim();
    }

    /**
     * 得到内容包含汉字的属性值
     *
     * @param key
     * @return
     */
    public static String getGBKProperty(String key) {
        String value = getProperty(key);
        try {
            value = new String(value.getBytes("ISO8859-1"), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value.trim();
    }

    /**
     * 得到内容包含汉字的属性值
     *
     * @param key
     * @return
     */
    public static String getUTF8KProperty(String key) {
        String value = getProperty(key);
        try {
            value = new String(value.getBytes("ISO8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value.trim();
    }

    /**
     * 得到属性值，
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getProperty(String key, String defaultValue) {
        if (prop == null) {
            loadProperties();
        }
        String value = prop.getProperty(key, defaultValue);
        if (value == null) {
            return null;
        }
        return value.trim();
    }

    /**
     * 得到内容包含汉字的属性值，如果不存在则使用默认值
     *
     * @param key
     * @return
     */
    public static String getGBKProperty(String key, String defaultValue) {
        String value = getProperty(key, defaultValue);
        try {
            value = new String(value.getBytes("ISO8859-1"), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value.trim();
    }

    public static String getUTFProperty(String key, String defaultValue) {
        String value = getProperty(key, defaultValue);
        try {
            value = new String(value.getBytes("ISO8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value.trim();
    }

    /**
     * 根据replaceChar字符值，自动替换属性值对应的字符串。
     *
     * @param key
     * @param replaceChar
     * @return
     */
    public static String getPropertyValuetoreplace(String key,
                                                   String arrValue[], String replaceChar) {
        String propertyValue = getGBKProperty(key);
        for (int i = 0; i < arrValue.length; i++) {
            propertyValue = propertyValue.replace(
                    replaceChar.trim() + Integer.toString(i),
                    arrValue[i].trim());
        }
        return propertyValue;
    }

    public static void setPropertyValue(String key, String value) {
        prop.put(key, value);
    }
}