package com.company.modules.workflow.utils.listener;

import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;

import com.company.common.utils.ParamChecker;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.service.SysUserService;
import com.company.modules.workflow.service.RDHistoryService;
import com.company.modules.workflow.utils.distributer.TaskDistributer;
import com.company.modules.workflow.utils.distributer.TaskWrapper;

/**
 * 根据主流程任务发起者角色决定分配者的任务分配器 
 * 该配置器适应于分支流程，任务审批角色与主流程发起任务角色是同一个角色，则任务审批人指定为主流程的任务发起人
 * @See 
 * @author huy
 *
 */
public class SameAsMainProcessTaskOrRoleDistributer implements TaskDistributer{
	@Override
	public void assignTask(TaskWrapper taskWrapper) {
        ParamChecker.checkEmpty(taskWrapper, "taskWrapper");
		DelegateTask delegateTask = taskWrapper.getDelegateTask();
		String processInstanceId = delegateTask.getProcessInstanceId();
		String assignee = delegateTask.getAssignee();
		// "same_as_user_task:taskuser-document-application" -> "taskuser-document-application"
		String taskDefinition = assignee.substring(assignee.indexOf(":") + 1);
		// "role:业务部" -> "业务部"
		String roleName = assignee.substring(assignee.indexOf(":") + 1);
		RDHistoryService historyService = ApplicationContextHelperBean.getBean(RDHistoryService.class);
		SysUserService sysUserService = ApplicationContextHelperBean.getBean(SysUserService.class);
		SysUser sysUser;
		Map<String, Object> thelatestHistoricTaskInstance;
		String assigneeName =null;
		try {
			String mainProcessId = (String)delegateTask.getVariable(SystemConstant.ORIGINAL_PROCESSINSTANCEID);
			thelatestHistoricTaskInstance = historyService.queryTheLatestHistoricTask(mainProcessId, taskDefinition);
			if(thelatestHistoricTaskInstance == null){
				// TODO FHJ查询出来的人必须和启动流程的用户是同一个机构
				// 首先查询出启动流程的用户
				String processLauncher = (String) delegateTask.getVariable(SystemConstant.PROCESS_LAUNCHER);
				SysUser launcher = sysUserService.getUserByUserName(processLauncher);
				sysUser = sysUserService.queryTheLeastBusyUserByRoleName(roleName, launcher.getOfficeId(),processInstanceId, null);
				assigneeName = sysUser.getUserName();
				//临时处理
				//assigneeName="xxsczy";
			}else{
				assigneeName = thelatestHistoricTaskInstance.get("ASSIGNEE_").toString();
			}
		} catch (ServiceException e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}catch (Exception e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
        if (assigneeName == null) {
            throw new RDRuntimeException("后续任务分配失败。");
        }
		delegateTask.setAssignee(assigneeName);
	}

}
