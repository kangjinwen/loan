package com.company.common.utils;

import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public abstract class RSAUtil {

	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * 解密<br>
	 * 用公钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(key);// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	/**
	 * 加密<br>
	 * 用公钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
		// 对公钥解密
		byte[] keyBytes = decryptBASE64(key);
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	public static void main(String[] args) throws Exception {
		String PUB_KYE = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqh51DJuRJCm5um+fKYbY5Nu/hGZC1VDYTJKf1fHMnKAnplYDBaHKceLuR0ri/stC1DGXnuf+UeJqyVr20CEQh/p6kQByQcs7c44bxCgTbuQYWvSr622gEEfBcW/SexkZ337rATymT5+AnPpjJm/Au8w67fs4JCVKyGOBd6ldAoQIDAQAB";
		System.out.println(decryptBASE64(PUB_KYE));
	}
}
