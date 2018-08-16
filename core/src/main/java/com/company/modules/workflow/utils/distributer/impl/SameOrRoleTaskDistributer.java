package com.company.modules.workflow.utils.distributer.impl;

import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.service.SysUserService;
import com.company.modules.workflow.service.RDHistoryService;
import com.company.modules.workflow.utils.distributer.TaskDistributer;
import com.company.modules.workflow.utils.distributer.TaskWrapper;

public class SameOrRoleTaskDistributer implements TaskDistributer {

	@Override
	public void assignTask(TaskWrapper taskWrapper) {
		DelegateTask delegateTask = taskWrapper.getDelegateTask();
		
		String assignee = delegateTask.getAssignee();
		// "same_as_user_task:taskuser-document-application" -> "taskuser-document-application"
		String taskDefinition = assignee.substring(assignee.indexOf(":") + 1);
		
		String processInstanceId = delegateTask.getProcessInstanceId();
		
		RDHistoryService historyService = ApplicationContextHelperBean.getBean(RDHistoryService.class);
		Map<String, Object> thelatestHistoricTaskInstance;
		SysUserService sysUserService = ApplicationContextHelperBean.getBean(SysUserService.class);
		SysUser sysUser;
		
		// "SAME_AS_TASK_OR_ROLE:usertask-recheck,riskControllManager" -> "riskControllManager"
		String roleName = assignee.substring(assignee.indexOf(",") + 1);
		String assigneeName =null;
		try {
			thelatestHistoricTaskInstance = historyService.queryTheLatestHistoricTask(processInstanceId, taskDefinition);
			if(thelatestHistoricTaskInstance == null){
				// TODO FHJ查询出来的人必须和启动流程的用户是同一个机构
				// 首先查询出启动流程的用户
				String processLauncher = (String) delegateTask.getVariable(SystemConstant.PROCESS_LAUNCHER);
				SysUser launcher = sysUserService.getUserByUserName(processLauncher);
				
				sysUser = sysUserService.queryTheLeastBusyUserByRoleName(roleName, launcher.getOfficeId(),processInstanceId, null);
				
				assigneeName = sysUser.getUserName();
			}else{
				assigneeName = thelatestHistoricTaskInstance.get("ASSIGNEE_").toString();
			}
		} catch (ServiceException e) {
			throw new RDRuntimeException("分配任务人出错", e);
		}
		
		delegateTask.setAssignee(assigneeName);
	}

}
