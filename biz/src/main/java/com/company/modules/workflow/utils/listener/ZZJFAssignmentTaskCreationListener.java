package com.company.modules.workflow.utils.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.company.modules.workflow.utils.distributer.impl.*;
import org.activiti.engine.HistoryService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.history.HistoricTaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.domain.PubProcessBranching;
import com.company.common.domain.PubProjectProcess;
import com.company.common.service.PubProjectProcessService;
import com.company.common.utils.DateUtil;
import com.company.modules.common.dao.PubLoanprocessDao;
import com.company.modules.common.domain.PlConsult;
import com.company.modules.common.domain.PubLoanprocess;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.PlConsultService;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.workflow.service.RDHistoryService;
import com.company.modules.workflow.utils.distributer.TaskDistributer;
import com.company.modules.workflow.utils.distributer.TaskWrapper;
import com.company.modules.workflow.utils.observation.TaskAssignerCenter;

/**
 * 自定义一套规则来分配任务
 * 
 * @author FHJ
 *
 */
@SuppressWarnings("serial")
public class ZZJFAssignmentTaskCreationListener extends AssignmentTaskCreationListener {
	private static Logger logger = LoggerFactory.getLogger(ZZJFAssignmentTaskCreationListener.class);

	/** 根据角色分配任务 */
	private static final String DEFAULT_ROLE_STRING = "ROLE:";
	private static final String HEAD_DEFAULT_ROLE_STRING = "HEAD:";
	/** 和指定任务相同分配任务 */
	private static final String SAME_AS_USER_TASK = "SAME_AS_TASK:";
    private static final String DIRECT_USER = "DIRECT_USER:";
    private static final String SUPERIOR_FOR_TASK = "SUPERIOR_FOR_TASK:";
    private static final String SUPERIOR_FOR_USER = "SUPERIOR_FOR_USER:";
    private static final String PROCESS_STARTER="PROCESS_STARTER";
    private static final String MANUAL="MANUAL:";
    private static final String SAME_AS_TASK_OR_ROLE="SAME_AS_TASK_OR_ROLE:";
    private static final String SAME_AS_START_ROLE_TASK="SAME_AS_START_ROLE_TASK:";
    private static final String SAME_AS_MAIN_PROCESS_START="SAME_AS_MAIN_PROCESS_START:";
    private static final String SAME_AS_MAIN_TASK="SAME_AS_MAIN_TASK:";
    private static final String SAME_AS_TWO_TASK = "SAME_AS_TWO_TASK:";
    /** 和之前分配相同分配任务 */
    private static final String SAME_AS_BEFORE_TASK = "SAME_AS_BEFORE_TASK:";
    private static final String MANUAL_ORG="MANUAL_ORG:";
	private static final String DEPARTMENT = "DEPART:";
	@Override
	public void onCreate(DelegateTask delegateTask) {
		logger.info("开始分配任务");
		// 维护系统的流程状态
		try {
			changeProcessState(delegateTask);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 分配任务
		this.assignTask(delegateTask);
		logger.info("开始分配结束");
	}

	protected void assignTask(DelegateTask delegateTask) {
		// 根据对应情况获取任务分配器
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
	 * 根据任务配置属性决定用哪种任务分配器
	 * 
	 * @param delegateTask
	 * @return
	 */
	private TaskDistributer getTaskdistributerByTaskProperty(
			DelegateTask delegateTask) {
		// TODO FHJ 由于assignee是会在数据库中直接关联查询的，所以在这里不要把规则定义在这个字段中。
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
		}else if(assignee.contains(SAME_AS_START_ROLE_TASK)) {
			taskDistributer = new ProcessStartByRoleTaskDistributer();
		}else if(assignee.contains(SAME_AS_MAIN_PROCESS_START)){
			taskDistributer = new SameAsMainProcessStartByRoleTaskDistributer();
		}else if(assignee.contains(SAME_AS_MAIN_TASK)){
			taskDistributer = new SameAsMainProcessTaskOrRoleDistributer();
		}else if(assignee.contains(SAME_AS_TWO_TASK)){
			taskDistributer = new SelectedUserTwoBasedTaskDistributer();
		}else if(assignee.contains(SAME_AS_BEFORE_TASK)){
			taskDistributer = new RebateHandleTaskDistributer();
		}else if(assignee.contains(MANUAL_ORG)){
			taskDistributer = new ManualOrgTaskDistributer();
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
	
	/**
	 * 维护系统的流程状态 有两个状态不能由本方法来维护，第1个是流程启动后的第一个状态，第2个是流程走完后的最后一个状态。
	 * 原因是在调用runtimeService.startProcessInstanceById时，所有观察者(listener)
	 * 的监听方法都会被立即调用，此时processInstanceId还没有生成出来，
	 * 系统业务表来不及把processInstanceId存进去。只有当所有listener的代码执行完毕后，
	 * startProcessInstanceById方法才返回processInstance.
	 * 
	 * @param delegateTask
	 * @throws Exception 
	 */
	private void changeProcessState(DelegateTask delegateTask) throws Exception {
		String processInstanceId = delegateTask.getProcessInstanceId();
		String taskDef = delegateTask.getTaskDefinitionKey();
		// TODO FHJ 最好改成DAO
		PlConsultService creditconsultService = ApplicationContextHelperBean
				.getBean(PlConsultService.class);
		HistoryService historyService = ApplicationContextHelperBean.getBean(HistoryService.class);
		PubLoanprocessDao loanprocessDao = ApplicationContextHelperBean.getBean(PubLoanprocessDao.class);
		PubProcessBranchingDao pubProcessBranchingDao = ApplicationContextHelperBean
				.getBean(PubProcessBranchingDao.class);
		// 找到上一条任务的ID
		String previousTaskId = getPrevisousTaskId(processInstanceId, historyService);

		try {
			if (previousTaskId != null) {
				updateLoanProcess(delegateTask, loanprocessDao, previousTaskId, processInstanceId);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("branchingProcessId", processInstanceId);
			PubProcessBranching ppb = pubProcessBranchingDao.getItemByMap(param);
			if (ppb == null) {
				// 主流程
				updateCreditConsult(processInstanceId, taskDef, creditconsultService);
			} else {
				// 贷后变更
				updatePubBranchingProcess(processInstanceId, taskDef, ppb, pubProcessBranchingDao);
				if (ppb.getBranchingProcessType() == 6 || ppb.getBranchingProcessType() == 5) {
					// 展期
					updateCreditConsult(processInstanceId, taskDef, creditconsultService);
				}
			}
		} catch (ServiceException e) {
			throw new RDRuntimeException("changeProcessState失败！", e);
		} catch (PersistentDataException e) {
			e.printStackTrace();
			throw new RDRuntimeException("数据库操作失败请稍后重试！", e);
		}
	}
	
	/**
	 * @description 如果当前流程属于分支流程，则更新分支流程状态
	 * @param processInstanceId
	 * @param taskDef
	 * @param pubProcessBranchingDao
	 * @throws ServiceException
	 * @author wtc
	 * @return void
	 * @since  1.0.0
	*/
	private void updatePubBranchingProcess(String processInstanceId, String taskDef,PubProcessBranching ppb,PubProcessBranchingDao pubProcessBranchingDao) throws Exception{
	    Map<String,Object> param=new HashMap<String,Object>();
	    param.put("branchingProcessId", processInstanceId);
	    if(ppb!=null){
		    param.clear();
		    param.put("id", ppb.getId());
		    param.put("processStatus", taskDef);
		    param.put("modifyTime", new Date());
		    pubProcessBranchingDao.updatePubProcessBranchingById(param);
		}
	}

	private String getPrevisousTaskId(String processInstanceId, HistoryService historyService) {
		int firstResult = 0;
		int maxResults = 1;
		List<HistoricTaskInstance> listPage = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).orderByTaskId().desc().listPage(firstResult, maxResults);
		if (listPage.size() > 0) {
			if ("taskuser-intial-audit".equals(listPage.get(0).getTaskDefinitionKey())// 如果是面审或者评估的任务，查询申请进件时的任务id
					|| "taskuser-pledge-estimation".equals(listPage.get(0).getTaskDefinitionKey())) {
				listPage = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId)
						.taskDefinitionKey("taskuser-document-application").orderByTaskId().desc()
						.listPage(firstResult, maxResults);
			}
		}
		return listPage.size() == 0 ? null : listPage.get(0).getId();
	}

	private void updateCreditConsult(String processInstanceId, String taskDef,
			PlConsultService plConsultService) throws Exception {
		PlConsult creditConsult = null;
		creditConsult = plConsultService.getItemInfoByProcessInstanceId(processInstanceId);

		if (creditConsult == null) {
			PubProjectProcessService projectProcessService = ApplicationContextHelperBean
					.getBean(PubProjectProcessService.class);
			PubProjectProcess pubProjectProcess = projectProcessService
					.getItemInfoByProcessInstanceId(processInstanceId);
			if (pubProjectProcess == null) {
				return;
			}

			creditConsult = plConsultService.getPlConsultByProjectId(pubProjectProcess.getProjectId());
		}

		if (creditConsult != null) {
			creditConsult.setStatus(taskDef);
			creditConsult.setModifyTime(DateUtil.now());
			plConsultService.update(creditConsult);
		}
	}

	private void updateLoanProcess(DelegateTask delegateTask, PubLoanprocessDao loanprocessDao, String previousTaskId,
			String processInstanceId) {
		if (previousTaskId == null) {
			// 如果previousTaskId为null，有两种情况，第一种是流程的第一个任务是没有所谓的"上一个任务"的，所以直接返回就好。
			// 第二种情况是程序出错，需要抛出异常
			if (isTheFirstTask(loanprocessDao, processInstanceId)) {
				return;
			} else {
				throw new RDRuntimeException("更新任务数据失败。");
			}
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("taskId", previousTaskId);
		//根据上一个任务的ID先把历史查询出来
		PubLoanprocess pubLoanprocess = loanprocessDao.getByTaskId(previousTaskId);
		if (pubLoanprocess!=null) {			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", pubLoanprocess.getId());
			param.put("newTaskId", delegateTask.getId());
			loanprocessDao.updateNewTaskId(param);
		}
	}

	private boolean isTheFirstTask(PubLoanprocessDao loanprocessDao, String processInstanceId) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("processInstanceId", processInstanceId);
		long pubLoanprocessCount = 0;
		pubLoanprocessCount = loanprocessDao.getPubLoanprocessCount(data);
		if (pubLoanprocessCount == 0) {
			return true;
		} else {
			return false;
		}
	}
}