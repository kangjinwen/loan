package com.company.modules.workflow.utils.distributer.impl;

import com.company.common.utils.ParamChecker;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.service.SysRoleService;
import com.company.modules.system.service.SysUserService;
import com.company.modules.workflow.utils.distributer.TaskDistributer;
import com.company.modules.workflow.utils.distributer.TaskWrapper;
import org.activiti.engine.delegate.DelegateTask;

/**
 * 把任务分配指定人员的上级（上级只有一个人）
 * @author FHJ
 *
 */
public class SuperiorForUserDistributer implements TaskDistributer{

	@Override
	public void assignTask(TaskWrapper taskWrapper) {
        ParamChecker.checkEmpty(taskWrapper, "taskWrapper");

		DelegateTask delegateTask = taskWrapper.getDelegateTask();
		String processInstanceId = delegateTask.getProcessInstanceId();
		String assignee = delegateTask.getAssignee();
		
		// "SUPERIOR_FOR_USER:process_launcher" -> "process_launcher"
		String processVar = assignee.substring(assignee.indexOf(":") + 1);

        String userName = (String) delegateTask.getVariable(processVar);

		SysUserService userService = ApplicationContextHelperBean.getBean(SysUserService.class);
		SysRoleService roleService = ApplicationContextHelperBean.getBean(SysRoleService.class);
        String bossUserID = null;

        SysUser user = null;
        /*try {
            Map sysUser = userService.getUserInfoByUserName(userName);
            // 找出该执行者的上司，两人一定属于同一个组中
            String officeId = (String) sysUser.get("officeId");
            Integer roleId = (Integer) sysUser.get("roleId");

            SysRole role = roleService.getRoleById(new Long(roleId));
            String roleName = role.getNid();

            String superiorRoleName = null;
            if ("customerServiceStaff".equals(roleName)) {
                superiorRoleName = "customerManager";
            }

            user = userService.getUserByRoleAndOfficeId(superiorRoleName, new Long(officeId));
        } catch (ServiceException e) {
            throw new RDRuntimeException(e.getMessage(), e);
        }
        if (user == null) {
            throw new RDRuntimeException("找不到上级人员");
        }*/

        bossUserID = user.getUserName();

		delegateTask.setAssignee(bossUserID);
	}

}