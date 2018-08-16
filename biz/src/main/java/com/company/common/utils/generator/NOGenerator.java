package com.company.common.utils.generator;

import java.util.Map;

import com.company.modules.common.exception.ServiceException;

/**
 * 编号生成器
 * 
 * @author FHJ
 *
 */
public abstract class NOGenerator {
	public abstract String generateName(Map<String,Object> params) throws ServiceException;
}
