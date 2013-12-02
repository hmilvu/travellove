package com.travel.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * <p>
 * 加密、解密、生成数据摘要工具类
 * </p>
 * 
 * <p>
 * JDK版本：1.6及其以上
 * </p>
 * 
 * <p>
 * 依赖包： commons-lang3-3.1.jar commons-codec-1.8.jar log4j-1.2.16.jar
 * </p>
 * 
 * 
 * @author deniro
 */
public class CryptoUtils {
	private static final Logger log = Logger.getLogger(CryptoUtils.class);
	
	private final static String KEY = "9DEE8503748053FDF526835BA8819A6C";
	private final static String SEPARATOR = "{array}";

	/**
	 * 字符集
	 */
	private static String CHARSET_NAME = "UTF-8";
	/**
	 * 3DES transformation
	 */
	private static String TRANSFORMATION = "DESede/CBC/PKCS5Padding";
	/**
	 * 3DES provider
	 */
	private static String PROVIDER = "SunJCE";

	/**
	 * 3DES iv
	 */
	private static IvParameterSpec IV = new IvParameterSpec(
			"38437283".getBytes());

	/**
	 * <p>
	 * 3DES 解密
	 * </p>
	 * 
	 * <pre>
	 * CryptoUtils.decode(str, &quot;04890B9237D9F14A4564FED52926496804890B9237D9F14A&quot);
	 * </pre>
	 * 
	 * @param str
	 *            待解密字符串
	 * @param keyIn3DES
	 *            密钥
	 * @return 解密后的字符串
	 * 
	 */
	public static String decode(String str) {
		String data = CryptoUtils.decryptIn3DES(str, KEY);
		String []dataArray = StringUtils.split(data, SEPARATOR);
		String newDigest = digest(dataArray[1]);
		if(StringUtils.equals(dataArray[0], newDigest)){
			return dataArray[1];
		} else {
			return "";
		}
	}

	/**
	 * <p>
	 * 3DES 加密
	 * </p>
	 * 
	 * 
	 * <pre>
	 * CryptoUtils.encode(str, &quot;04890B9237D9F14A4564FED52926496804890B9237D9F14A&quot);
	 * </pre>
	 * 
	 * @param str
	 *            待加密字符串
	 * @param keyIn3DES
	 *            3DES 密钥
	 * @return 加密后的字符串
	 */
	public static String encode(String str) {
		String digest = digest(str);
		String data = digest + SEPARATOR + str;
		String encryptedData = Base64.encodeBase64URLSafeString(encryptIn3DES(data, KEY));
		return encryptedData;
	}

	/**
	 * <p>
	 * 3DES 解密
	 * </p>
	 * 
	 * <pre>
	 * CryptoUtils.decryptIn3DES(str, &quot;04890B9237D9F14A4564FED52926496804890B9237D9F14A&quot);
	 * </pre>
	 * 
	 * @param value
	 *            待解密字符串
	 * @param key
	 *            密钥
	 * @return 解密后的字符串
	 */
	private static String decryptIn3DES(String value, String key) {
		SecretKey secretKey = Encrypter(key);

		try {

			Cipher dcipher = Cipher.getInstance(TRANSFORMATION, PROVIDER);
			dcipher.init(Cipher.DECRYPT_MODE, secretKey, IV);

			if (value == null)
				return null;

			// Base64 解码
			byte[] dec = Base64.decodeBase64(value.getBytes());

			// 解密
			byte[] utf8 = dcipher.doFinal(dec);

			// 解码
			return new String(utf8, CHARSET_NAME);
		} catch (Exception e) {
			log.error("3DES 解密", e);
			return null;
		}
	}

	/**
	 * 3DES 加密
	 * 
	 * @param value
	 * @param key
	 * @return
	 */
	private static byte[] encryptIn3DES(String value, String key) {
		SecretKey secretKey = Encrypter(key);

		try {
			Cipher ecipher = Cipher.getInstance(TRANSFORMATION, PROVIDER);
			ecipher.init(Cipher.ENCRYPT_MODE, secretKey, IV);

			if (value == null)
				return null;

			// 编码
			byte[] utf8 = value.getBytes(CHARSET_NAME);

			// 加密
			return ecipher.doFinal(utf8);

		} catch (Exception e) {
			log.error("3DES 加密", e);
			return null;
		}

	}

	/**
	 * 生成3DES 密钥
	 * 
	 * @param key
	 * @return
	 */
	private static SecretKey Encrypter(String key) {
		try {
			final MessageDigest md = MessageDigest.getInstance("md5");
			final byte[] digestOfPassword = md.digest(Base64.decodeBase64(key
					.getBytes(CHARSET_NAME)));
			final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			for (int j = 0, k = 16; j < 8;) {
				keyBytes[k++] = keyBytes[j++];
			}

			DESedeKeySpec keySpec = new DESedeKeySpec(keyBytes);
			return SecretKeyFactory.getInstance("DESede").generateSecret(
					keySpec);
		} catch (Exception e) {
			log.error("生成3DES 密钥", e);
			return null;
		}
	}

	/**
	 * <p>
	 * 生成数据摘要
	 * </p>
	 * 
	 * <pre>
	 * CryptoUtils.digest(str);
	 * </pre>
	 * 
	 * 
	 * @param str
	 *            数据字符串
	 * @return 数据摘要
	 */
	private static String digest(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		}

		try {// 数据摘要 = Base64(SHA-1(数据))
			MessageDigest messageDigest = DigestUtils.getSha1Digest();
			return Base64.encodeBase64String(messageDigest.digest(str
					.getBytes(CHARSET_NAME)));
		} catch (Exception e) {
			log.error("生成数据摘要", e);
			return str;
		}
	}

	public static void main(String[] args) throws IOException {

		Map<String, String>params = new HashMap<String, String>();
		params.put("t1", "t1v");
		params.put("tt", "好");
		params.put("abc", "abcv");
		System.out.println(getSignForBaidu("queryUrl", params, "secret"));
	}
	
	public static String getSignForBaidu(String queryUrl, Map<String, String> paramValues, String secret) throws IOException{
		try {
            StringBuilder sb = new StringBuilder(queryUrl);
            List<String> paramNames = new ArrayList<String>(paramValues.size());
            paramNames.addAll(paramValues.keySet());            
            Collections.sort(paramNames);
            for (String paramName : paramNames) {
                sb.append(paramName).append(paramValues.get(paramName));
            }
            sb.append(secret);
            byte[] sha1Digest = getMD5Digest(URLEncoder.encode(sb.toString(), CHARSET_NAME));
            return byte2hex(sha1Digest);
        } catch (IOException e) {
            throw e;
        } 
	}

	private static byte[] getMD5Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes(CHARSET_NAME));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }
	
	private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
}
