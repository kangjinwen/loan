package com.company.modules.workflow.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.company.modules.common.exception.ServiceException;

public interface RDZZHistoryService {

	/**
	 * 通过projectId查询客服岗查看的审批历史
	 * @param projectId
	 * @param pageBean
	 * @return
	 * @throws ServiceException
	 */
	PageInfo<Map<String, Object>> queryAllHistoricLoanProcessListToCustomerByProjectId(
			String projectId, int currentPage,int pageSize) throws ServiceException;
	
	/**
	 * 通过projectId查询审批历史
	 * @param projectId
	 * @param pageBean
	 * @return
	 * @throws ServiceException
	 */
	PageInfo<Map<String, Object>> queryAllHistoricLoanProcessListByProjectId(
			String projectId, int currentPage,int pageSize) throws ServiceException;
	
	Map<String, Object> queryHistoryIdentityLinkByMap(Map<String,Object> paramMap) throws ServiceException;

	Map<String, Object> queryHistoryTaskInfo(Map<String, Object> paramMap);
}
