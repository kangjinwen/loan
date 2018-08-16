package com.company.modules.workflow.utils.listener;

import org.activiti.engine.delegate.DelegateTask;

import com.company.common.utils.ParamChecker;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.service.SysUserService;
import com.company.modules.workflow.utils.distributer.TaskDistributer;
import com.company.modules.workflow.utils.distributer.TaskWrapper;

/**
 *  手动指定单位任务分配器
 *	@Description
 *  @author huy,yhu@erongdu.com
 *  @CreatTime 2016年12月9日 上午11:27:38
 *  @since version 1.0.0
 */
public class ManualOrgTaskDistributer implements TaskDistributer{

	@Override
	public void assignTask(TaskWrapper task) {
		ParamChecker.checkEmpty(task, "taskWrapper");
		DelegateTask delegateTask = task.getDelegateTask();		
		String assignee = delegateTask.getAssignee();
		String roleName = assignee.substring(assignee.indexOf(":") + 1);
		String orgId = (String) delegateTask.getVariable("assigneeOrg");
		SysUserService sysUserService = ApplicationContextHelperBean.getBean(SysUserService.class);
		String processInstanceId = delegateTask.getProcessInstanceId();
		SysUser sysUser = null;
		try {
			sysUser = sysUserService.queryTheLeastBusyUserByRoleName(roleName, orgId,processInstanceId, null);
		} catch (ServiceException e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
		if (sysUser == null) {
            throw new RDRuntimeException("后续任务分配失败。");
        }
		delegateTask.setAssignee(sysUser.getUserName());
	}

}
