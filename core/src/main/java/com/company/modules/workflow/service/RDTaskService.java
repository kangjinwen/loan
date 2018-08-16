package com.company.modules.workflow.service;

import java.util.List;
import java.util.Map;

import com.company.modules.common.exception.ServiceException;


/**
 * 融都Task业务接口
 * @author FHJ
 *
 */
public interface RDTaskService {
	/**
	 * 完成任务
	 * @param taskId 任务ID
	 * @param executor 执行者名称
	 * @param bpmValues 流程变量
	 * @throws ServiceException
	 */
	void completeTask(String taskId, String executor, Map<String, Object> bpmValues) throws ServiceException;

	/**
	 * 查询贷后变更申请审批任务
	 * @param roleId
	 * @param isCompleted
	 * @param paramMap
	 * @return
	 */
	List<Map> queryLoanChangeApprovalTasks(Long roleId, boolean isCompleted, Map<String, Object> paramMap);
	

	/**
	 * 手动分配任务
	 * @param taskId 任务ID
	 * @param newTaskAssignee 新的任务执行者（被分配者）
	 * @param assigner 分配者
	 */
	void assignTask(String taskId, String newTaskAssignee, String assigner) throws ServiceException;
	
	/**
	 * @description
	 * 查询当前任务处理人  同一角色  同一部门的处理人
	 * @param taskId
	 * @return
	 * @throws ServiceException
	 * @author wangdk
	 * @return List<Map<String,Object>>
	 * @since  1.0.0
	*/
	List<Map<String,Object>> queryUserList(String taskId) throws ServiceException;

	void completeTask(String taskId, String userName, Map<String, Object> serviceVarMap,Map<String, Object> bpmVariables) throws ServiceException;

	void completeOrgaFirstTask(String taskId, String userName, Map<String,Object> serviceVarMap, Map<String,Object> bpmVariables) throws ServiceException;
}
