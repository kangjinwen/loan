package com.company.modules.workflow.utils.listener;

import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.workflow.service.RDHistoryService;
import com.company.modules.workflow.utils.distributer.TaskDistributer;
import com.company.modules.workflow.utils.distributer.TaskWrapper;

/**
 * 两个任务节点中选定用户 任务分配器
 * 先根据第一个任务查询执行人，查不到再根据第二个任务查询
 * @author FHJ
 *
 */
public class SelectedUserTwoBasedTaskDistributer implements TaskDistributer{
	@Override
	public void assignTask(TaskWrapper taskWrapper) {
		DelegateTask delegateTask = taskWrapper.getDelegateTask();
		
		String assignee = delegateTask.getAssignee();
		// "same_as_task:taskuser-document-application," -> "taskuser-document-application"
		String taskDefinitionStr = assignee.substring(assignee.indexOf(":") + 1);
		
		RDHistoryService historyService = ApplicationContextHelperBean.getBean(RDHistoryService.class);
		Map<String, Object> thelatestHistoricTaskInstance;
		try {
			String[] taskDefinitions = taskDefinitionStr.split(",");
			String processInstanceId = delegateTask.getProcessInstanceId();
			thelatestHistoricTaskInstance = historyService.queryTheLatestHistoricTask(processInstanceId, taskDefinitions[0]);
			if(thelatestHistoricTaskInstance == null) {
				thelatestHistoricTaskInstance = historyService.queryTheLatestHistoricTask(processInstanceId, taskDefinitions[1]);
				if(thelatestHistoricTaskInstance == null){
					throw new RDRuntimeException("没有找到指定任务的历史执行人");
				}
			}
		} catch (ServiceException e) {
			throw new RDRuntimeException("queryTheLatestHistoricTaskByProcessInstanceAndTaskDefinition出错", e);
		}
		delegateTask.setAssignee(thelatestHistoricTaskInstance.get("ASSIGNEE_").toString());
	}
}
