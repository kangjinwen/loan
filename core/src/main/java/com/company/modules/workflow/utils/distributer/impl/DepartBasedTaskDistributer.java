package com.company.modules.workflow.utils.distributer.impl;

import com.company.common.utils.ParamChecker;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.service.SysUserService;
import com.company.modules.workflow.utils.distributer.TaskDistributer;
import com.company.modules.workflow.utils.distributer.TaskWrapper;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

/**
 * 根据部门，随机指定执行人
 */
public class DepartBasedTaskDistributer implements TaskDistributer {

    private static final String TASKUSER_PLEDGE_ESTIMATION = "taskuser-pledge-estimation";
    private static final String TASK_DEF_KEY_XIAHU = "usertask-xiahu";//下户
    private static final String TASK_DEF_KEY_FK_CHUSHEN = "usertask-collateralAssess";//风控初审
    private static final String TASK_DEF_KEY_FK_FUSHEN = "usertask-control-review";//风控复审
    private static final String TASK_DEF_KEY_JG_CHUSHEN = "usertask-organization-initialAudit";//机构初审
    private static final String ROSE_XIAHU = "xiahu";
    @Override
    public void assignTask(TaskWrapper taskWrapper) throws ServiceException {
        //参数判空
        ParamChecker.checkEmpty(taskWrapper,"taskWrapper");

        DelegateTask delegateTask = taskWrapper.getDelegateTask();
        //当前流程实例id
        String processInstanceId = delegateTask.getProcessInstanceId();
        //当前经办人（例如：DEPART:initialAuditor）
        String assignee = delegateTask.getAssignee();

        // 拆分"DEPART:1102" -> "1102"(或当 下户时，"ROLE:xiahu"->xiahu)
        String departIdOrRole = assignee.substring(assignee.indexOf(":") + 1);

        SysUserService sysUserService = ApplicationContextHelperBean.getBean(SysUserService.class);

        SysUser sysUser = null;


        try {
            String taskDefinition = null;
            // TODO FHJ查询出来的人必须和启动流程的用户是同一个机构
            // 首先查询出启动流程的用户
            String processLauncher = (String) delegateTask.getVariable(SystemConstant.PROCESS_LAUNCHER);
            SysUser launcher = sysUserService.getUserByUserName(processLauncher);
            String newOfficeId = launcher.getOfficeId();//设置接下来从哪个部门挑选下个任务的执行人
            if (!assignee.contains(ROSE_XIAHU)){
				/*1.此处方法就是随机分配任务执行人
			  2.融都交付的版本，是根据流程启动的人所在的部门去随机的，也就是launcher
			  3.我们需要根据gz需求更改，进入风控流程时候，指定的任务执行人，应该为风控部门的人
			*/
                //1.去任务列表查询当前流程的任务进度
                TaskService taskService = ApplicationContextHelperBean.getBean(TaskService.class);
                TaskEntity taskEntity = (TaskEntity) taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
                if (null==taskEntity){
                    throw new RDRuntimeException("流程实例id："+processInstanceId+"下的任务不存在");
                }
                String nowTaskDefKey = taskEntity.getTaskDefinitionKey();

                switch (nowTaskDefKey){
                    case TASK_DEF_KEY_XIAHU :
                        newOfficeId = departIdOrRole;
                        break;
                    case TASK_DEF_KEY_FK_CHUSHEN :
                        newOfficeId = departIdOrRole;
                        break;
                    case TASK_DEF_KEY_FK_FUSHEN :
                        newOfficeId = departIdOrRole;
                        break;
                    default:
                        newOfficeId = launcher.getOfficeId();
                }
            }
            //2.如果当前任务是处于 下户，风控初审，风控复审，就将指定接下来的任务执行人为 风控部门  随机一个人
            sysUser = sysUserService.queryTheLeastBusyUserByDepartName(null,newOfficeId,processInstanceId, taskDefinition);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        if (sysUser == null) {
            throw new RDRuntimeException("后续任务分配失败。");
        }

        delegateTask.setAssignee(sysUser.getUserName());

    }

}
