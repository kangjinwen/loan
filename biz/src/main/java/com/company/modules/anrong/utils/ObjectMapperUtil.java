package com.company.modules.anrong.utils;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json对象转换工具类
 * @author tengHy
 *
 */
public class ObjectMapperUtil {
	public static final ObjectMapper mapper2 = new ObjectMapper();
	static {
		// 对输出json时，进行key排序，这个特性有时比较有用，方便调试
		// mapper2.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
		// mapper2.configure(ORDER_MAP_ENTRIES_BY_KEYS, true);
		// 当key对不上的时候是否抛出异常
		mapper2.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		mapper2.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true) ;
		mapper2.configure(Feature.ALLOW_SINGLE_QUOTES, true) ;
		mapper2.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
	}
	
	/**
	 * 即使要转换的bean的某个属性，在json中不存在，也不会异常
	 * 
	 * @param json
	 * @param clz
	 * @return
	 */
	public static <T> T getObjectSafe(String json, Class<T> clz) {
		try {
			return (T) mapper2.readValue(json, clz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 即使要转换的bean的某个属性，在json中不存在，也不会异常
	 * 
	 * @param json
	 * @param clz
	 * @return
	 */
	public static <T> T getObjectSafe(String json,
			com.fasterxml.jackson.core.type.TypeReference<T> retType) {
		try {
			return (T) mapper2.readValue(json, retType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 对象转为json字串，保证不会抛出异常
	 * 
	 * @param obj
	 * @return
	 */
	public static String getJsonSafe(Object obj) {
		if (obj == null)
			return null;
		try {
			return mapper2.writeValueAsString(obj);
		} catch (Exception e) {
			return null;
		}
	}
}
