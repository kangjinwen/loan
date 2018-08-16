package com.company.modules.anrong.utils;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * https访问辅助
 * @author tengHy
 */
public class IgnoreX509Util {
	/**
	 * 无验证证书逻辑的TrustManager
	 * @author tengHy
	 *
	 */
	public static class NoCheckX509TrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
    }

	/**
	 * 忽略ssl证书的连接工厂
	 */
	public final static SSLSocketFactory ignoreSSLSocketConnectionFactory;
	static{
		try{
			SSLContext ctx = SSLContext.getInstance("SSL");
			ctx.init(null, new X509TrustManager[]{new NoCheckX509TrustManager()}, new SecureRandom());
			ignoreSSLSocketConnectionFactory = ctx.getSocketFactory();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
