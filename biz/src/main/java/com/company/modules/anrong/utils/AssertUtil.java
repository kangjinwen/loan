package com.company.modules.anrong.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AssertUtil {
	public static void notNull(Object obj, String message){
		if(obj == null)
			throw new NullPointerException(message);
	}
	
	public static void hasText(String text, String message){
		if(text == null || text.trim().length() <= 0)
			throw new IllegalArgumentException(message);
	}
	
	public static void verifyDate(String str){
		if(str == null || str.trim().length() <= 0)
			return;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try{
			d = sdf.parse(str);
		}catch(Exception e){
			;
		}
		if(d == null)
			throw new IllegalArgumentException("参数值：" + str + " 不符合yyyy-MM-dd时间格式");
	}
}
