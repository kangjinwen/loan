package com.company.modules.workflow.service;

import java.util.Map;

import com.company.modules.common.exception.ServiceException;

public interface RDHistoryService {
	/**
	 * 根据流程实例ID和任务定义，查询最近的任务记录
	 * @param processInstanceId
	 * @param taskDefinition
	 * @return
	 * @throws ServiceException
	 */
	Map<String, Object> queryTheLatestHistoricTask(
			String processInstanceId, String taskDefinition) throws ServiceException;
	
}
