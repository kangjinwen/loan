package com.company.modules.workflow.utils.listener;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;

import com.company.common.utils.ParamChecker;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.domain.SysUserRole;
import com.company.modules.system.service.SysUserRoleService;
import com.company.modules.system.service.SysUserService;
import com.company.modules.workflow.utils.distributer.TaskDistributer;
import com.company.modules.workflow.utils.distributer.TaskWrapper;

/**
 * 根据任务发起者角色决定分配者的任务分配器
 * 任务审批角色与发起任务角色是同一个角色，则任务审批人指定为任务发起人
 * @See 
 * @author huy
 *
 */
public class ProcessStartByRoleTaskDistributer implements TaskDistributer{
	@Override
	public void assignTask(TaskWrapper taskWrapper) {
        ParamChecker.checkEmpty(taskWrapper, "taskWrapper");
        
		DelegateTask delegateTask = taskWrapper.getDelegateTask();
		//String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
		String processInstanceId = delegateTask.getProcessInstanceId();
		
		String assignee = delegateTask.getAssignee();
		// "role:业务部" -> "业务部"
		String roleName = assignee.substring(assignee.indexOf(":") + 1);

		SysUserService sysUserService = ApplicationContextHelperBean.getBean(SysUserService.class);
		SysUserRoleService sysUserRoleService = ApplicationContextHelperBean.getBean(SysUserRoleService.class);
		SysUser sysUser;
		
		try {
			String taskDefinition = null;
			// TODO FHJ查询出来的人必须和启动流程的用户是同一个机构
			// 首先查询出启动流程的用户
			String processLauncher = (String) delegateTask.getVariable(SystemConstant.PROCESS_LAUNCHER);
			SysUser launcher = sysUserService.getUserByUserName(processLauncher);
			
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("userId", launcher.getId());
			paramMap.put("nid", roleName);
			SysUserRole sr = sysUserRoleService.getSysUserRoleByNid(paramMap);
			if(sr != null){
				sysUser = launcher;
			}else{
				sysUser = sysUserService.queryTheLeastBusyUserByRoleName(roleName, launcher.getOfficeId(),processInstanceId, taskDefinition);
			}
		} catch (ServiceException e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
        if (sysUser == null) {
            throw new RDRuntimeException("后续任务分配失败。");
        }
		delegateTask.setAssignee(sysUser.getUserName());
	}

}
