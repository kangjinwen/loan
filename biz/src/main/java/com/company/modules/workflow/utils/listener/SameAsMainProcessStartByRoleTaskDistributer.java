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
import com.company.modules.workflow.service.RDZZHistoryService;
import com.company.modules.workflow.utils.distributer.TaskDistributer;
import com.company.modules.workflow.utils.distributer.TaskWrapper;

/**
 * 根据主流程任务发起者角色决定分配者的任务分配器 
 * 该配置器适应于分支流程，任务审批角色与主流程发起任务角色是同一个角色，则任务审批人指定为主流程的任务发起人
 * @See 
 * @author huy
 *
 */
public class SameAsMainProcessStartByRoleTaskDistributer implements TaskDistributer{
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
		SysUser sysUser = null;
		RDZZHistoryService historyService = ApplicationContextHelperBean.getBean(RDZZHistoryService.class);
		Map<String, Object> taskInstance;
		String taskDefinition = "";
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			String mainProcessId = (String)delegateTask.getVariable(SystemConstant.ORIGINAL_PROCESSINSTANCEID);
			paramMap = new HashMap<String,Object>();
			paramMap.put("type", "starter");
			paramMap.put("processInstanceId", mainProcessId);
			taskInstance = historyService.queryHistoryIdentityLinkByMap(paramMap);
			SysUser mainLauncher = sysUserService.getUserByUserName(String.valueOf(taskInstance.get("USER_ID_")));
			paramMap = new HashMap<String,Object>();
			paramMap.put("userId", mainLauncher.getId());
			paramMap.put("nid", roleName);
			SysUserRole sr = sysUserRoleService.getSysUserRoleByNid(paramMap);
			if(sr != null){
				sysUser = mainLauncher;
			}else{
				String processLauncher = (String) delegateTask.getVariable(SystemConstant.PROCESS_LAUNCHER);
				SysUser launcher = sysUserService.getUserByUserName(processLauncher);
				sysUser = sysUserService.queryTheLeastBusyUserByRoleName(roleName, launcher.getOfficeId(),processInstanceId, taskDefinition);
			}
			
		} catch (ServiceException e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}catch (Exception e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
        if (sysUser == null) {
            throw new RDRuntimeException("后续任务分配失败。");
        }
		delegateTask.setAssignee(sysUser.getUserName());
	}

}
