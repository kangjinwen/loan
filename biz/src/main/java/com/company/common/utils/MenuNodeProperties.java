package com.company.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuNodeProperties {
	
	public static Logger LOGGER = LoggerFactory.getLogger(MenuNodeProperties.class);
	
	public static Properties properties = new Properties();
	/**
	 * 消费方地址
	 */
	
	static{
		loadProperties();
	}
	
	public static void loadProperties(){
		InputStream is = MenuNodeProperties.class.getResourceAsStream("/config/menuNode.properties");
		try {
			properties.load(is);
		} catch (IOException e) {
			LOGGER.error("load properties error",e);
		}
	}
}
