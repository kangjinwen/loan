package com.company.modules.workflow.utils.distributer.impl;

import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

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
 *	@Description 分配任务给流程启动者
 *  @author wtc,wtc@erongdu.com
 *  @CreatTime 2015年6月25日 下午4:24:41
 *  @since version 1.0.0
 */
public class ProcessStartTaskDistributer implements TaskDistributer {
    private static final Logger logger = LoggerFactory.getLogger(ProcessStartTaskDistributer.class);
    @Override
    public void assignTask(TaskWrapper task) {
        ParamChecker.checkEmpty(task, "taskWrapper");
        DelegateTask delegateTask = task.getDelegateTask();
        String userName = delegateTask.getVariable(SystemConstant.PROCESS_LAUNCHER,String.class);
        Long userRoleId = delegateTask.getVariable(SystemConstant.PROCESS_LAUNCHER_ROLEID,Long.class);
		if (StringUtils.isEmpty(userName)) {
			throw new RDRuntimeException("后续任务分配失败。");
		}
		logger.info("任务[taskId:{}]被分配给[{}]", delegateTask.getId(), userName);
		delegateTask.setAssignee(userName);
    }

}
