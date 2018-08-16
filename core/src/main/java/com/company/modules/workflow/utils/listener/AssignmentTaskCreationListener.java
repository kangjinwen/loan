package com.company.modules.workflow.utils.listener;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import com.company.modules.workflow.utils.distributer.impl.*;
import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.workflow.service.RDHistoryService;
import com.company.modules.workflow.utils.distributer.TaskDistributer;
import com.company.modules.workflow.utils.distributer.TaskWrapper;
import com.company.modules.workflow.utils.observation.TaskAssignerCenter;

/**
 * 
 * 自定义一套规则来分配任务
 *
 * @author FHJ
 *
 */
@SuppressWarnings("serial")
public class AssignmentTaskCreationListener extends DefaultTaskListener {
	private static final String DEFAULT_ROLE_STRING = "ROLE:";
	private static final String HEAD_DEFAULT_ROLE_STRING = "HEAD:";
	private static final String SAME_AS_USER_TASK = "SAME_AS_TASK:";
    private static final String DIRECT_USER = "DIRECT_USER:";
    private static final String SUPERIOR_FOR_TASK = "SUPERIOR_FOR_TASK:";
    private static final String SUPERIOR_FOR_USER = "SUPERIOR_FOR_USER:";
    private static final String PROCESS_STARTER="PROCESS_STARTER";
    private static final String MANUAL="MANUAL:";
    private static final String SAME_AS_TASK_OR_ROLE="SAME_AS_TASK_OR_ROLE:";
    private static final String TASK_DEF_KEY = "usertask-organization-initialAudit";
	private static final String TASK_XIAHU = "usertask-xiahu";
	private static final String DEPARTMENT = "DEPART:";
	private static Logger logger = LoggerFactory.getLogger(AssignmentTaskCreationListener.class);

	public AssignmentTaskCreationListener() {
	}

	//
	@Override
	public void onCreate(DelegateTask delegateTask) {
		
		logger.info("开始分配任务");
		// 这里是activiti任务监听器 分配任务的入口
		assignTask(delegateTask);
		logger.info("开始分配结束");
	}

	protected void assignTask(DelegateTask delegateTask) {
		// 根据对应情况获取任务分配器（转到本页77行）
		TaskDistributer taskDistributer = getTaskdistributerByTaskProperty(delegateTask);
		// 用任务分配器分配任务
		try {
			taskDistributer.assignTask(new TaskWrapper(delegateTask));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		// TODO FHJ 这个用来测试
		TaskAssignerCenter.taskAssignee.set(delegateTask.getAssignee());
		TaskAssignerCenter.isNew = true;
	}

	/**
	 *
	 * 根据任务配置属性决定用哪种任务分配器，返回对应类型的 任务分配器
	 * 
	 * @param delegateTask
	 * @return
	 */
	private TaskDistributer getTaskdistributerByTaskProperty(
			DelegateTask delegateTask) {
		// TODO FHJ 由于assignee是会在数据库中直接关联查询的，所以在这里不要把规则定义在这个字段中。
		//拿到 流程图中任务节点 中 activiti:assignee="ROLE:salesman"
		String assignee = delegateTask.getAssignee();
		if (StringUtils.isEmpty(assignee)) {
			// 这里属于没有办法拯救的错误，直接runtime exception抛出去
			throw new RDRuntimeException("流程配置出错");
		}
		TaskDistributer taskDistributer = null;

		// 非常重要！有几种情况
		// 1.
		// 使用者在每个可以驳回的结点或者之后的结点上都有可能执行过，所以第一先需要查询下这个任务结点在当下流程实例中最近是由哪个用户执行的，如果能查询出来，
		// 将该用户设置成当前任务的执行人
		// 2. 如果#1没有查询出结果，那么该任务结点没有被执行过，按照以下的逻辑走
		//（此方法跳到本页127行）
		tryToRetrieveTaskAssigneeFromHistory(delegateTask);
		
		assignee = delegateTask.getAssignee();
		
		if (assignee.contains(HEAD_DEFAULT_ROLE_STRING)) {
			taskDistributer = new HeadRoleBasedTaskDistributer();
		}else if(assignee.contains(SAME_AS_USER_TASK)) {
			taskDistributer = new SelectedUserBasedTaskDistributer();
		} else if (assignee.contains(SUPERIOR_FOR_TASK)) {
			taskDistributer = new SuperiorForTaskDistributer();
		} else if (assignee.contains(SUPERIOR_FOR_USER)) {
            taskDistributer = new SuperiorForUserDistributer();
        } else if (assignee.contains(DIRECT_USER)) {
            taskDistributer = new DirectUserBasedTaskDistributer();
        } else if(assignee.contains(PROCESS_STARTER)){
            taskDistributer = new ProcessStartTaskDistributer();
        }else if(assignee.contains(MANUAL)){
        	taskDistributer = new ManualTaskDistributer();
        }else if(assignee.contains(SAME_AS_TASK_OR_ROLE)){
        	taskDistributer = new SameOrRoleTaskDistributer();
        }else if(assignee.contains(DEFAULT_ROLE_STRING)) {
			taskDistributer = new RoleBasedTaskDistributer();
		}else if (assignee.contains(DEPARTMENT)){
			taskDistributer = new DepartBasedTaskDistributer();
		}
		else{
            taskDistributer = new EmptyTaskDistributer();
        }
        return taskDistributer;
	}

	/**
	 * 尝试获取历史中的任务执行者
	 * @param delegateTask
	 */
	private void tryToRetrieveTaskAssigneeFromHistory(DelegateTask delegateTask) {
		String processInstanceId = delegateTask.getProcessInstanceId();
		String taskDef = delegateTask.getTaskDefinitionKey();
		//判断当前任务节点是不是  机构初审（usertask-organization-initialAudit）  ，如果是，直接设置assignee为下户时候的执行人
		if (null!=taskDef&&TASK_DEF_KEY.equalsIgnoreCase(taskDef)){
			//1.当此时是机构初审，就去历史任务查该流程下户的时候assignee是谁，并设置到当前任务人里
			Map<String, Object> theLastHistoricInstance;
			try {
				RDHistoryService historyService = ApplicationContextHelperBean.getBean(RDHistoryService.class);
				theLastHistoricInstance = historyService.queryTheLatestHistoricTask(processInstanceId, TASK_XIAHU);
			} catch (ServiceException e) {
				e.printStackTrace();
				throw new RDRuntimeException(e.getMessage());
			}
			if(theLastHistoricInstance != null) {

				// 把历史执行人设置到当前任务执行人里
				delegateTask.setAssignee(theLastHistoricInstance.get("ASSIGNEE_").toString());
			}
		}else {
			Map<String, Object> theLastHistoricInstance;
			try {
				RDHistoryService historyService = ApplicationContextHelperBean.getBean(RDHistoryService.class);
				theLastHistoricInstance = historyService.queryTheLatestHistoricTask(processInstanceId, taskDef);
			} catch (ServiceException e) {
				e.printStackTrace();
				throw new RDRuntimeException(e.getMessage());
			}
			if(theLastHistoricInstance != null) {
				// 把历史执行人设置到当前任务执行人里
				delegateTask.setAssignee(theLastHistoricInstance.get("ASSIGNEE_").toString());
			}
		}
	}
}