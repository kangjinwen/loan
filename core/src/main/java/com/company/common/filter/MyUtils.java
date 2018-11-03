package com.company.common.filter;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.common.utils.JudgeValue;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import net.sf.json.JSONObject;


public class MyUtils {

	/**
	 * 获取当前调用者方法名
	 * 
	 * @return
	 */
	public static String getMethodNameOfCaller() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}

	/**
	 * log4j日志打印
	 * 
	 * @param params
	 *            参数对象
	 * @param object
	 *            调用者 类对象
	 */
	public static void writeLog(Object params, Object object) {
		Logger logger = Logger.getLogger(object.getClass());
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		String className = object.getClass().getName();
		if (null != params) {
			logger.info("\r\n\nClass: " + className + "\r\n Mehod: " + methodName + "\r\n params:[ "
					+ removeNullParams(params) + " ] \r\n");
		} else {
			logger.info("\r\n\nClass: " + className + "\r\n Mehod: " + methodName);
		}
	}

	/**
	 * 将请求头内的所有用户传进来的参数取出，然后组装成字符串返回
	 * 若用户未传任何参数则返回null
	 * @param request
	 * @return
	 */
	public static String queryParamsFromRequst(HttpServletRequest request) {
		Enumeration<String> paramNames = request.getParameterNames();
		StringBuffer params = new StringBuffer();
		while (paramNames.hasMoreElements()) {
			String param = paramNames.nextElement();
			params.append(param+"="+request.getParameter(param)+" , ");
		}
		if (params.length()>2) {
			return params.substring(0, params.length()-2);
		}
		return null;
	}
	/**
	 * 将请求头内的所有用户传进来的参数取出，然后组装成字符串返回
	 * 若用户未传任何参数则返回null
	 * @param request
	 * @return
	 */
	public static JSONObject queryParamsFromRequstToJson(HttpServletRequest request) {
		Enumeration<String> paramNames = request.getParameterNames();
		JSONObject jo = new JSONObject();
		while (paramNames.hasMoreElements()) {
			String param = paramNames.nextElement();
			jo.put(param, request.getParameter(param));
		}
		return jo;
	}
	/**
	 * log4j日志打印
	 * (将请求头内的参数取出)
	 * @param request
	 * @param object
	 */
	public static void writeLog(HttpServletRequest request, Object object) {
		Logger logger = Logger.getLogger(object.getClass());
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		String className = object.getClass().getName();
		Enumeration<String> paramNames = request.getParameterNames();
		StringBuffer params = new StringBuffer();
		while (paramNames.hasMoreElements()) {
			String param = paramNames.nextElement();
			params.append(param+"="+request.getParameter(param)+" , ");
		}
		if (params.length()>2) {
			logger.info("\r\n\nClass: " + className + "\r\n Mehod: " + methodName + "\r\n params:[ "
					+ params.substring(0, params.length()-2) + " ] \r\n");
			
		}else {
			
			logger.info("\r\n\nClass: " + className + "\r\n Mehod: " + methodName + "\r\n params:[无参数 ] \r\n");
		}
	}
	
	/**
	 * log4j日志打印
	 * (将请求头内的参数取出)
	 * @param msg
	 * @param object
	 */
	public static void writeLog(String msg, Object object) {
		if (StringUtils.isEmpty(msg)) {
			return;
		}
		Logger logger = Logger.getLogger(object.getClass());
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		String className = object.getClass().getName();
		logger.info("\r\n\nClass: " + className + "\r\n Mehod: " + methodName + "\r\n msg:[ "+msg+" ] \r\n");
	}
	/**
	 * 为某个类做日志
	 * @param info
	 * @param object
	 */
	public static void writeLogInfo(String info, Object object) {
		if (!JudgeValue.isNullOfString(info)) {
			Logger.getLogger(object.getClass()).info(info);
		}
	}
	/**
	 * 将一个对象里面的null属性移除，并返回字符串
	 * 
	 * @param params
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static String removeNullParams(Object params) {
		if (null == params) {
			return "无参数";
		}
		StringBuffer paramStr = new StringBuffer();
		Field[] fs1 = params.getClass().getSuperclass().getDeclaredFields();
		if (fs1.length > 0) {
			try {
				String superParam = getSuperFields(params, params.getClass().getSuperclass());
				paramStr.append(superParam + ",");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Field[] fs = params.getClass().getDeclaredFields();
		for (Field f : fs) {
			f.setAccessible(true);
			String fn = f.getName();
			try {
				Object v = f.get(params);
				if (null != v) {
					paramStr.append(fn + "=" + v + ",");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return paramStr.substring(0, paramStr.length() - 1);
	}

	@SuppressWarnings("rawtypes")
	public static String getSuperFields(Object params, Class superclass) {
		StringBuffer paramStr = new StringBuffer();
		Field[] fs1 = superclass.getSuperclass().getDeclaredFields();
		if (fs1.length > 0) {
			try {
				String superParam = getSuperFields(params, superclass.getSuperclass());
				paramStr.append(superParam + ",");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Field[] fs = superclass.getDeclaredFields();
		for (Field f : fs) {
			f.setAccessible(true);
			String fn = f.getName();
			try {
				Object v = f.get(params);
				if (null != v) {
					paramStr.append(fn + "=" + v + " , ");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return paramStr.substring(0, paramStr.length() - 2);
	}


	/**
	 * 获取客户端的IP
	 * 
	 * @param request
	 * @param name
	 *            最外级转发的名称
	 * @return
	 */
	public static String getRemoteIp(HttpServletRequest request, String name) {
		// 记录登入信息
		String remoteIp = request.getHeader(name);
		if (null == remoteIp || remoteIp.contains("0:0:0:0:0:0:0:")) {// 判断是否是转发
			remoteIp = request.getRemoteAddr();
		}
		if (remoteIp.contains(",")) {// 如果多级转发就不止一个Ip，获取第一个IP就是客户Ip
			remoteIp = remoteIp.substring(0, remoteIp.indexOf(","));
		}
		return remoteIp;
	}

	/**
	 * 获取客户端的IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteIp(HttpServletRequest request) {
		// 记录登入信息
		String remoteIp = request.getHeader("X-Real-IP");

		if (null == remoteIp || remoteIp.contains("0:0:0:0:0:0:0:")) {// 判断是否是转发
			remoteIp = request.getRemoteAddr();
		}
		if (remoteIp.contains(",")) {// 如果多级转发就不止一个Ip，获取第一个IP就是客户Ip
			remoteIp = remoteIp.substring(0, remoteIp.indexOf(","));
		}
		return remoteIp;
	}
	
	/**
	 * 根据cookie的名字从请求头内获取对应的cookie值
	 * @param request
	 * @param name
	 * @return
	 */
	public static String findCookieByName(HttpServletRequest request,String name){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Cookie[] cookies = request.getCookies();
		if (null == cookies || cookies.length == 0) {
			return null;
		}
		String co = null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				co = cookie.getValue();
				return co;
			}
		}
		return co;
	}
	
	/**
	 * 将内容放入指定的cookie里面返回前端
	 * @param cookieName 指定cookie名称
	 * @param cookieContent cookie内容
	 * @param response
	 * @return
	 */
	public static boolean addCookie(String cookieName,String cookieContent, HttpServletResponse response) {
		try {
			Cookie cookie = new Cookie(cookieName, cookieContent);
			cookie.setPath("/");
			response.addCookie(cookie);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	/**
	 * 将内容放入指定的cookie里面返回前端（可设定超时时间）
	 * @param cookieName 指定cookie名称
	 * @param cookieContent cookie内容
	 * @param maxAge 超时时间  单位  秒   一个月=2592000
	 * 
	 * @param response
	 * @return
	 */
	public static boolean addCookie(String cookieName,String cookieContent, HttpServletResponse response,Integer maxAge) {
		try {
			Cookie cookie = new Cookie(cookieName, cookieContent);
			cookie.setPath("/");
			cookie.setMaxAge(maxAge);
			response.addCookie(cookie);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	/**
	 * 根据某个字段进行分组
	 * 字段的值作为键
	 * 具有相同值的则放到一起
	 * 
	 * @param list 需要分组的数组  
	 * @param cloumn 根据哪个字段进行分组
	 * @return
	 */
	public static Map<Object, List<Object>> groupByAnyCloumn2Map(@SuppressWarnings("rawtypes") List list,String cloumn) {
		Map<Object, List<Object>> map = new TreeMap<>();
		 for (Object o : list) {
			try {
				Field f = o.getClass().getDeclaredField(cloumn);
				f.setAccessible(true);
				try {
					Object o1 = f.get(o);
//					System.out.println(o1);
					if (null != map && map.keySet().contains(o1)) {
						List<Object> l1=map.get(o1);
						l1.add(o);
						map.put(o1, l1);
					}else {
						List<Object> l1 = new ArrayList<>();
						l1.add(o);
						map.put(o1, l1);
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		 return map;
	}
	
}
