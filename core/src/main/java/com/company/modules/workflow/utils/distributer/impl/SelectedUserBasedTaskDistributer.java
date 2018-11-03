package com.company.modules.workflow.utils.distributer.impl;

import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.workflow.service.RDHistoryService;
import com.company.modules.workflow.utils.distributer.TaskDistributer;
import com.company.modules.workflow.utils.distributer.TaskWrapper;

/**
 * [其实是从历史任务中选人去执行，wdx]
 * 选定用户 任务分配器
 * @author FHJ
 *
 */
public class SelectedUserBasedTaskDistributer implements TaskDistributer{
	@Override
	public void assignTask(TaskWrapper taskWrapper) {
		DelegateTask delegateTask = taskWrapper.getDelegateTask();
		
		String assignee = delegateTask.getAssignee();
		// "same_as_user_task:taskuser-document-application" -> "taskuser-document-application"
		String taskDefinition = assignee.substring(assignee.indexOf(":") + 1);
		
		String processInstanceId = delegateTask.getProcessInstanceId();
		
		RDHistoryService historyService = ApplicationContextHelperBean.getBean(RDHistoryService.class);
		Map<String, Object> thelatestHistoricTaskInstance;
		try {
			thelatestHistoricTaskInstance = historyService.queryTheLatestHistoricTask(processInstanceId, taskDefinition);
		} catch (ServiceException e) {
			throw new RDRuntimeException("queryTheLatestHistoricTaskByProcessInstanceAndTaskDefinition出错", e);
		}
		
		if(thelatestHistoricTaskInstance == null) {
			throw new RDRuntimeException("没有找到指定任务的历史执行人");
		}
		delegateTask.setAssignee(thelatestHistoricTaskInstance.get("ASSIGNEE_").toString());
	}

}
