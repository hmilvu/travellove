package com.travel.utils;

import java.security.MessageDigest;
import java.util.Arrays;

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
	public static String decode(String str, String keyIn3DES) {
		return CryptoUtils.decryptIn3DES(str, keyIn3DES);
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
	public static String encode(String str, String keyIn3DES) {
		if (StringUtils.isBlank(keyIn3DES)) {
			log.error("3DES 密钥不能为空串！");
			return str;
		}

		// 加密数据 = URLEncoding (Base64 ( 3DES(数据 )))
		return Base64.encodeBase64URLSafeString(encryptIn3DES(str, keyIn3DES));
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
	public static String decryptIn3DES(String value, String key) {
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
	
	public static void main(String[] args) {
		String value = "{\"user\":\"express\",\"pwd\":\"express\",\"message\":{\"phoneNumber\":\"119\"}}";
		String key = "04890B9237D9F14A4564FED52926496804890B9237D9F14A";
		byte[] aaa = encryptIn3DES(value, key);
		System.out.println(Arrays.toString(aaa));
		String bbb = Base64.encodeBase64URLSafeString(aaa);
		System.out.println(bbb);
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
	public static String digest(String str) {
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

//	static void main(String[] args) {

		/*
		 * String str = "{test1:\"测试\",test2:\"234Ab!@#\"}"; //String str =
		 * "{user:\"user1\",pwd:\"pwd1\",checkMessage:\"checkMessage1\",message:\"message1\"}"
		 * ;
		 * 
		 * String encode = CryptoUtils.encode(str,
		 * "04890B9237D9F14A4564FED52926496804890B9237D9F14A");
		 * System.out.println("加密:" + encode);
		 * 
		 * Decode decode = CryptoUtils.decode(encode,
		 * "04890B9237D9F14A4564FED52926496804890B9237D9F14A");
		 * System.out.println("解密:" + decode);
		 */
//	}

}
