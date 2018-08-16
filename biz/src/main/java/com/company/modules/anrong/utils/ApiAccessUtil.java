package com.company.modules.anrong.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * 访问接口工具类，忽略了https证书
 * 
 * @author tengHy
 *
 */
public class ApiAccessUtil {
	public static enum ApiHttpMethod{
		GET
		, POST
	}
	
	// 编码方式
	private static final String CONTENT_CHARSET = "UTF-8";

	// 连接超时时间
	public static final int CONNECTION_TIMEOUT = 15000;

	// 读数据超时时间
	public static final int READ_DATA_TIMEOUT = 15000;
	
	private static final String ua = "Java Allwin SDK Client";
	
	private static String buildQueryData(Map<String, String> queryParams){
		String queryStr = "", sp = "";
		for(Entry<String, String> e : queryParams.entrySet()){
			String paramName = e.getKey() == null ? "" : e.getKey().trim();
			if(paramName.length() <= 0)
				continue;
			String paramValue = e.getValue() == null ? "" : e.getValue().trim();
			try {
				queryStr += sp + URLEncoder.encode(paramName, "utf-8") 
						+ "=" + URLEncoder.encode(paramValue, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				throw new RuntimeException(e1);
			}
			sp = "&";
		}
		return queryStr;
	}
	
	/**
	 * 根据查询参数及接口url构造get的url请求
	 * @param baseUrl
	 * @param queryParams
	 * @return
	 */
	public static String buildQueryUrl(String baseUrl, Map<String, String> queryParams){
		String queryStr = buildQueryData(queryParams);
		if(queryStr.length() <= 0)
			return baseUrl;
		return baseUrl + (baseUrl.indexOf("?") >= 0 ? "&" : "?") + queryStr;
	}
	
	/**
	 * 发送http请求
	 * 
	 * @param url
	 *            请求URL地址
	 * @param params
	 *            请求参数
	 * @param method
	 *            使用的http方法
	 * @return 服务器响应的请求结果
	 * @throws OpensnsException
	 *             网络故障时抛出异常。
	 */
	public static String request(String url,
			LinkedHashMap<String, String> params, ApiHttpMethod method) throws IOException {
		url = url == null ? "" : url.trim();
		if(url.length() <= 0)
			throw new RuntimeException("URL地址为空！请检查您的配置");

		if(method == ApiHttpMethod.GET){
			// 如果是get方法的话，那么用拼接url来调用，这样在日志里可以看到完整的url地址，便于调试
			url = buildQueryUrl(url, params);
	//		params = null;
		}
		
		HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();
		try {
			conn.setDoInput(true);
			conn.setDoOutput(true);
			
			if (url.toLowerCase().startsWith("https")) {
				// 兼容https
				resolveSslConn(conn);
			}
			
			if(method == ApiHttpMethod.GET){
				conn.setRequestMethod("GET");
			}else{
				conn.setRequestMethod("POST");
			}
			
			// 设置User-Agent
			conn.setRequestProperty("User-Agent", ua);
	
			// 设置建立连接超时时间
			conn.setConnectTimeout(CONNECTION_TIMEOUT);
	
			// 设置读数据超时时间
			conn.setReadTimeout(READ_DATA_TIMEOUT);
			
			// 如果是post提交数据
			if(method == ApiHttpMethod.POST){
				OutputStream os = conn.getOutputStream();
				try{
					os.write(buildQueryData(params).getBytes("utf-8"));
				}finally{
					os.close();
				}
			}

			int statusCode = conn.getResponseCode();

			if (statusCode != HttpURLConnection.HTTP_OK) {
				throw new IOException(
						"Request [" + url + "] failed:" + statusCode);
			}

			// 读取内容
			InputStream is = conn.getInputStream();
			try{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try{
					byte[] cache = new byte[1024];
					int i;
					while(true){
						i = is.read(cache);
						if(i == -1)
							break;
						baos.write(cache, 0, i);
					}
					
					String ret = baos.toString(CONTENT_CHARSET);

					System.out.println("请求：" + method + " " + url + "\n参数：" + params + "\n返回：" + ret);
					
					return ret;
				}finally{
					baos.close();
				}
			}finally{
				is.close();
			}
		}catch(IOException ioe){
			throw new IOException("发生IO异常了，有可能由以下原因造成：您当前网络环境不能访问外网；如果是对接生产地址，还未绑定您的ip给我们，请联系我们客服", ioe);
		}finally {
			// 释放链接
			try{
				conn.disconnect();
			}catch(Exception e){
				;
			}
		}
	}
	
	public static void resolveSslConn(HttpURLConnection conn){
		try{
			if(!(conn instanceof HttpsURLConnection))
				return;
			
			HttpsURLConnection.setDefaultHostnameVerifier(
				new HostnameVerifier() {
					public boolean verify(String hostname, SSLSession session) {
						System.out.println("Warning: URL Host: " + hostname + " vs. "
								+ session.getPeerHost());
						return true;
					}
				});
			
			HttpsURLConnection hsuc = (HttpsURLConnection)conn;
			hsuc.setSSLSocketFactory(IgnoreX509Util.ignoreSSLSocketConnectionFactory);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
