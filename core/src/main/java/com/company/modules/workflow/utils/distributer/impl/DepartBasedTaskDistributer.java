package com.company.modules.workflow.utils.distributer.impl;

import com.alibaba.fastjson.JSONObject;
import com.company.common.utils.ParamChecker;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.system.dao.SysOfficeDao;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.service.SysUserService;
import com.company.modules.workflow.utils.distributer.TaskDistributer;
import com.company.modules.workflow.utils.distributer.TaskWrapper;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

import java.util.List;
import java.util.Map;

/**
 * 根据部门，随机指定执行人
 * @author wdx
 */
public class DepartBasedTaskDistributer implements TaskDistributer {
    @Override
    public void assignTask(TaskWrapper taskWrapper) throws ServiceException {
        //参数判空
        ParamChecker.checkEmpty(taskWrapper,"taskWrapper");

        DelegateTask delegateTask = taskWrapper.getDelegateTask();
        //当前流程实例id
        String processInstanceId = delegateTask.getProcessInstanceId();
        //拿到流程任务节点的经办人（即流程图的activiti:assignee=“”）
        String assignee = delegateTask.getAssignee();
        // 拆分"DEPART:1102" -> "1102"(或当 下户时，"ROLE:xiahu"->xiahu)
        String departIdOrRole = assignee.substring(assignee.indexOf(":") + 1);

        SysUserService sysUserService = ApplicationContextHelperBean.getBean(SysUserService.class);

        SysUser sysUser = null;


        try {
            //根据  部门id  挑选下个任务的执行人
            sysUser = sysUserService.getNextAssigneeByOfficeId(null,departIdOrRole,processInstanceId, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sysUser == null) {
            throw new RDRuntimeException("后续任务分配失败。");
        }

        delegateTask.setAssignee(sysUser.getUserName());

    }

}
