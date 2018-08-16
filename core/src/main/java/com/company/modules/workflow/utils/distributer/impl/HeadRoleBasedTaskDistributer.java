package com.company.modules.workflow.utils.distributer.impl;

import org.activiti.engine.delegate.DelegateTask;

import com.company.common.utils.ParamChecker;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.service.SysUserService;
import com.company.modules.workflow.utils.distributer.TaskDistributer;
import com.company.modules.workflow.utils.distributer.TaskWrapper;

/**
 * 基于角色的任务分配器
 * 任务会分配给一个角色中的某个成员
 * @See RoledBasedTaskDistributionStrategy
 * @author FHJ
 *
 */
public class HeadRoleBasedTaskDistributer implements TaskDistributer{
	@Override
	public void assignTask(TaskWrapper taskWrapper) {
        ParamChecker.checkEmpty(taskWrapper, "taskWrapper");
        
		DelegateTask delegateTask = taskWrapper.getDelegateTask();
		
		String assignee = delegateTask.getAssignee();
		// "role:业务部" -> "业务部"
		String roleName = assignee.substring(assignee.indexOf(":") + 1);

		SysUserService sysUserService = ApplicationContextHelperBean.getBean(SysUserService.class);
		
		SysUser sysUser;
		
		try {
			String processInstanceId = delegateTask.getProcessInstanceId();
			String taskDefinition = null;
			// TODO FHJ查询出来的人必须和启动流程的用户是同一个机构
			// 首先查询出启动流程的用户
			String processLauncher = (String) delegateTask.getVariable(SystemConstant.PROCESS_LAUNCHER);
			SysUser launcher = sysUserService.getUserByUserName(processLauncher);
			
			//sysUser = sysUserService.queryTheLeastBusyUserByHeadRoleName(roleName, processInstanceId, taskDefinition);
			sysUser = sysUserService.queryTheLeastBusyUserByHeadRoleName(roleName,launcher.getOfficeId(),processInstanceId, taskDefinition);
		} catch (ServiceException e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}

        if (sysUser == null) {
            throw new RDRuntimeException("后续任务分配失败。");
        }
		
		delegateTask.setAssignee(sysUser.getUserName());
		
	}

}
