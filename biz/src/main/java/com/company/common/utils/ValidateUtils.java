package com.company.common.utils;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具类
 * @author JDM
 *
 */
public class ValidateUtils {
	public static boolean isEmpty(String s) {
		return (s == null) || (s.length() == 0);
	}

	public static <E> boolean isEmpty(Collection<E> c) {
		return (c == null) || (c.size() == 0);
	}

	public static <E> boolean isEmpty(E[] a) {
		return (a == null) || (a.length == 0);
	}

	public static <K, E> boolean isEmpty(Map<K, E> m) {
		return (m == null) || (m.size() == 0);
	}

	public static boolean isEmpty(CharSequence c) {
		return (c == null) || (c.length() == 0);
	}

	public static boolean isNotEmpty(String s) {
		return (s != null) && (s.length() > 0);
	}

	public static <E> boolean isNotEmpty(Collection<E> c) {
		return (c != null) && (c.size() > 0);
	}

	public static <E> boolean isNotEmpty(E[] a) {
		return (a != null) && (a.length > 0);
	}

	public static <K, E> boolean isNotEmpty(Map<K, E> m) {
		return (m != null) && (m.size() > 0);
	}

	public static boolean isNotEmpty(CharSequence c) {
		return (c != null) && (c.length() > 0);
	}

	public static boolean isLetter(char c) {
		return Character.isLetter(c);
	}

	public static boolean isDigit(char c) {
		return Character.isDigit(c);
	}

	public static boolean isLetterOrDigit(char c) {
		return Character.isLetterOrDigit(c);
	}

	public static boolean isAlphanumeric(String s) {
		if (isEmpty(s))
			return false;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (!isLetterOrDigit(c))
				return false;
		}

		return true;
	}

	public static boolean isNumeric(String s) {
		if (isEmpty(s))
			return false;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (!isDigit(c))
				return false;
		}

		return true;
	}
	
	/**
	 * 判断是否是金额
	 * @param str
	 * @return
	 */
	public static boolean isAmount(Object obj) {
		if(obj==null){
			return false;
		}
		Pattern pattern = Pattern.compile("^((\\d{1,16})|([0]{1}))(\\.(\\d){0,2})?|-1$");    
	    return pattern.matcher(obj.toString()).matches();
	}
	
	/**
	 * 判断是否是整数
	 * @param obj
	 * @return
	 */
	public static boolean isInteger(Object obj) {
		if(obj == null){
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");    
	    return pattern.matcher(obj.toString()).matches();  
	}
	
	/**
	 * 校验是否中文
	 * 
	 * @param str 字符串
	 * @return 校验结果 true：通过 false：不通过
	 */
	public static boolean isChinese(String str) {
		Pattern regex = Pattern.compile("[\\u4e00-\\u9fa5]{2,10}");
		Matcher matcher = regex.matcher(isNull(str));
		return matcher.matches();
	}

	/**
	 * 校验Email格式
	 * 
	 * @param email 输入邮箱
	 * @return 校验结果 true：通过 false：不通过
	 */
	public static boolean isEmail(String email) {
		Pattern regex = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher matcher = regex.matcher(isNull(email));
		return matcher.matches();
	}

	/**
	 * 校验手机号格式
	 * 
	 * @param phone 手机号
	 * @return 校验结果 true：通过 false：不通过
	 */
	public static boolean isPhone(String phone) {
		Pattern regex = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|145|147|170|176|177|178|185)\\d{8}$");
		Matcher matcher = regex.matcher(isNull(phone));
		boolean isMatched = matcher.matches();
		return isMatched;
	}
	
	/**
	 * 字符串空处理，去除首尾空格 如果str为null，返回"",否则返回str
	 * 
	 * @param str
	 * @return
	 */
	public static String isNull(String str) {
		if (str == null) {
			return "";
		}
		return str.trim();
	}
}
