package com.company.modules.credit.service;

import java.util.List;
import java.util.Map;

import com.company.modules.common.exception.ServiceException;
import com.company.modules.credit.domain.PubCustomerRelation;

public interface PubCustomerRelationService {

	
	/**
	 * 客户联系列表
	 * @param paramap
	 * @return
	 */
	List<Map<String, Object>> getCustomerRelationList(Map<String, Object> paramap) throws ServiceException;
	
	/**
	 * 添加客户联系
	 * @param pubCustomerRelation
	 * @return
	 */
	boolean addCustomerRelation(PubCustomerRelation pubCustomerRelation) throws ServiceException;

	/**
	 * 客户联系明细列表
	 * @param paramap
	 * @return
	 * @throws ServiceException
	 */
	List<Map<String, Object>> getCustomerRelationDetail(Map<String, Object> paramap) throws ServiceException;
	
}

