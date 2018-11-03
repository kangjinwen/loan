package com.company.modules.collateral.workflow.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.dao.PubProjectProcessDao;
import com.company.common.domain.PubProcessBranching;
import com.company.common.domain.PubProjectProcess;
import com.company.modules.collateral.workflow.dao.CollateralTaskDao;
import com.company.modules.collateral.workflow.service.CollateralTaskService;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.dao.PubLoanprocessDao;
import com.company.modules.common.domain.ExportHousMortgageRegistration;
import com.company.modules.common.domain.PubLoanprocess;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.dao.SysRoleDao;
import com.company.modules.system.dao.SysUserDao;
import com.company.modules.system.domain.SysRole;
import com.company.modules.system.domain.SysUser;
import com.company.modules.workflow.utils.observation.TaskAssignerCenter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 押品任务
 * 
 * @author Administrator
 *
 */

@Service(value = "collateralTaskServiceImpl")
public class CollateralTaskServiceImpl implements CollateralTaskService {

	private static final Logger logger = LoggerFactory.getLogger(CollateralTaskServiceImpl.class);

	@Autowired
	private RuntimeService runtimeService;

	private static final String USERTASK_OFFLINE_TASKS = "usertask-offline-tasks";

	private static final String USERTASK_RELIEVE_TASKS = "usertask-relieve-tasks";

	private static final String USERTASK_COLLATERALTASKASSIGN_TASKS = "usertask-collateralTaskAssign";

	private static final String USERTASK_RELIEVETASKASSIGN_TASKS = "usertask-relieveTaskAssign";

	@Autowired
	private CollateralTaskDao collateralTaskDao;

	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private PubProjectProcessDao pubProjectProcessDao;

	@Autowired
	private PubProcessBranchingDao pubProcessBranchingDao;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private PubLoanprocessDao pubLoanprocessDao;

	private Double SECONDARY_AUDIT_AMOUNT_THRESHOLD = (double) 200000;// null;
	private Double SECONDARY_FINANCIAL_CONFIRMATION_AMOUNT_THRESHOLD = (double) 200000; // null;
	private Double MANAGER_CONFIRMATION_AMOUNT_THRESHOLD = (double) 500000; // null;

	/**
	 * 押品登记表表,查询数据
	 * 
	 * @param housMortgageRegistration 押品登记表类
	 * @return 返回页面map
	 * @throws ServiceException
	 */
	public PageInfo<Map<String, Object>> getCollateralRegistList(Map<String, Object> map) throws Exception {
		List<Map<String, Object>> returnMap = new ArrayList<Map<String, Object>>();
		try {
			map.put("taskDefinition", USERTASK_OFFLINE_TASKS);
			Boolean isCompleted = (Boolean) map.get("isCompleted");
			String roleName = getRoleName((Long) map.get("roleId"));
			map.put("nid", roleName);

			PageHelper.startPage((Integer) map.get("currentPage"), (Integer) map.get("pageSize"));
			if (isCompleted) {
				returnMap = collateralTaskDao.getDoneCollateralRegistList(map);
			} else {
				returnMap = collateralTaskDao.getUnCollateralRegistList(map);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}
		// 返回已经查询完毕的参数
		PageInfo<Map<String, Object>> page = new PageInfo<>(returnMap);
		return page;
	}

	/**
	 * 押品登记表表,查询数据
	 * 
	 * @param housMortgageRegistration 押品登记表类
	 * @return 返回页面map
	 * @throws ServiceException
	 */
	public List<ExportHousMortgageRegistration> queryCollateralRegistList(Map<String, Object> map) throws Exception {
		List<ExportHousMortgageRegistration> returnMap = new ArrayList<ExportHousMortgageRegistration>();
		try {
			map.put("taskDefinition", USERTASK_OFFLINE_TASKS);
			String roleName = getRoleName((Long) map.get("roleId"));
			map.put("nid", roleName);
			returnMap = collateralTaskDao.getExportDoneCollateralRegistList(map);
//			if(isCompleted){
//				returnMap=collateralTaskDao.getDoneCollateralRegistList(map);
//			} else{
//				returnMap=collateralTaskDao.getUnCollateralRegistList(map);	
//			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}
		return returnMap;
	}

	/**
	 * 押品解押查询
	 * 
	 * @param housMortgageRegistration 押品登记表类
	 * @return 返回页面map
	 * @throws ServiceException
	 */
	public PageInfo<Map<String, Object>> getCollateralRelieveList(Map<String, Object> map) throws Exception {
		List<Map<String, Object>> returnMap = new ArrayList<Map<String, Object>>();
		try {
			map.put("taskDefinition", USERTASK_RELIEVE_TASKS);
			Boolean isCompleted = (Boolean) map.get("isCompleted");
			String roleName = getRoleName((Long) map.get("roleId"));
			map.put("nid", roleName);

			PageHelper.startPage((Integer) map.get("currentPage"), (Integer) map.get("pageSize"));
			if (isCompleted) {
				returnMap = collateralTaskDao.getDoneCollateralRelieveList(map);
			} else {
				returnMap = collateralTaskDao.getUnCollateralRelieveList(map);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}
		// 返回已经查询完毕的参数
		PageInfo<Map<String, Object>> page = new PageInfo<>(returnMap);
		return page;
	}

	@Override
	public PageInfo<Map<String, Object>> getCollateralRegistAssignTaskList(Map<String, Object> map) throws Exception {
		List<Map<String, Object>> returnMap = new ArrayList<Map<String, Object>>();
		try {
			map.put("taskDefinition", USERTASK_COLLATERALTASKASSIGN_TASKS);
			Boolean isCompleted = (Boolean) map.get("isCompleted");
			String roleName = getRoleName((Long) map.get("roleId"));
			map.put("nid", roleName);

			PageHelper.startPage((Integer) map.get("currentPage"), (Integer) map.get("pageSize"));
			if (isCompleted) {
				returnMap = collateralTaskDao.getDoneCollateralRegistAssignList(map);
			} else {
				returnMap = collateralTaskDao.getUnCollateralRegistAssignList(map);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}
		// 返回已经查询完毕的参数
		PageInfo<Map<String, Object>> page = new PageInfo<>(returnMap);
		return page;
	}

	@Override
	public PageInfo<Map<String, Object>> getCollateralRelieveAssignTaskList(Map<String, Object> map) throws Exception {
		List<Map<String, Object>> returnMap = new ArrayList<Map<String, Object>>();
		try {
			map.put("taskDefinition", USERTASK_RELIEVETASKASSIGN_TASKS);
			Boolean isCompleted = (Boolean) map.get("isCompleted");
			String roleName = getRoleName((Long) map.get("roleId"));
			map.put("nid", roleName);

			PageHelper.startPage((Integer) map.get("currentPage"), (Integer) map.get("pageSize"));
			if (isCompleted) {
				returnMap = collateralTaskDao.getDoneCollateralRelieveAssignList(map);
			} else {
				returnMap = collateralTaskDao.getUnCollateralRelieveAssignList(map);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}
		// 返回已经查询完毕的参数
		PageInfo<Map<String, Object>> page = new PageInfo<>(returnMap);
		return page;
	}

	private String getRoleName(Long roleId) throws ServiceException {
		SysRole role = null;
		try {
			role = sysRoleDao.getByPrimary(roleId);
		} catch (Exception e) {
			throw new ServiceException("角色查询失败:" + e.getMessage(), e);
		}
		return role.getNid();
	}

//	//启动解押流程
//	@Override
//	public void startRelieveHouse(String processInstanceId,String assignee) throws Exception{
//		try {
//			 PubProjectProcess ppp = pubProjectProcessDao.getItemByProcessInstanceId(Long.parseLong(processInstanceId));
//			 
//			 Map<String, Object> variablesMap = new HashMap<String, Object>();
//			  variablesMap.put(SystemConstant.PROCESS_LAUNCHER, assignee);
//			 ProcessInstance processInstance =runtimeService.startProcessInstanceByKey(RELIEVE_HOUSE_PROCESS, variablesMap);//启动解押流程
//			 
//			  	PubProjectProcess pubProjectProcess = new PubProjectProcess();
//		        pubProjectProcess.setExtensionNumber((byte) 0);
//		        pubProjectProcess.setProcessType((byte) 5); //结算流程
//		        pubProjectProcess.setProjectId((Long)ppp.getProjectId());
//		        pubProjectProcess.setProcessInstanceId(processInstance.getProcessInstanceId());// processInstance.getId());
//		        pubProjectProcessDao.insert(pubProjectProcess);
//		} catch (Exception e) {
//			 throw new ServiceException(e.getMessage(), e);
//		}
//		
//	}

	/**
	 * 
	 * @Description: 创建解押任务 @param @param processInstanceId @param @param
	 *               projectId @param @param userName @param @throws
	 *               ServiceException @param @throws PersistentDataException
	 *               设定文件 @return void 返回类型 @throws
	 */
	public void startRelieveHouse(String processInstanceId, Long projectId, long userId) throws Exception {

		SysUser user = sysUserDao.getByPrimary(userId);
		String userName = user.getUserName();

//	        Map param = new HashMap();
//	        param.put("processInstanceId", processInstanceId);
//	        param.put("isActive",WorkFlowConstant.IS_ACTIVE_TRUE);
//	        // 锁定之前的还款记录
//
//	        List<PubProcessBranching> list = pubProcessBranchingService.getPageListByMap(param);
//
//	        // 将前一个分支流程设置成非活动状态
//	        if (list != null && list.size() > 0) {
//	            for (PubProcessBranching branching : list) {
//	                branching.setIsActive((byte) 0);
//	                branching.setModifier(userName);
//	                branching.setModifyTime(new Date());
//	                pubProcessBranchingDao.update(branching);
//	            }
//	        }

		PubProcessBranching pb = null;

		// 新生成一个分支流程 并插入分支流程表
		pb = new PubProcessBranching();
		pb.setProcessInstanceId(processInstanceId);
		pb.setRemark2("解押");

		List<Task> task = startProcess(processInstanceId.toString(), "RelieveProcess", userName, projectId, null);

		// 几个小流程，都设置为 贷后分支流程类别 需要在任务中进行统一处理。
		pb.setTaskId(task.get(0).getId());
		pb.setBranchingProcessId(task.get(0).getProcessInstanceId());
		pb.setBranchingProcessType((byte) 4);
		pb.setProcessingOpinion("noprocess");
		pb.setProjectId(projectId);

		pb.setCreateTime(new Date());
		pb.setCreator(userName);

		pb.setIsDelete((byte) 0);
		pb.setIsActive((byte) 1);
		pb.setProcessStatus("usertask-relieveTaskAssign"); // 设置解押分配状态
		pubProcessBranchingDao.insert(pb);

		// 插入下一步审批人
		PubLoanprocess pubLoanprocess = new PubLoanprocess();
		pubLoanprocess.setCreateTime(new Date());
		pubLoanprocess.setIsDelete(SystemConstant.IS_DELETE_NORMAL);
		pubLoanprocess.setProcessInstanceId(processInstanceId);
		pubLoanprocess.setProjectId(projectId);
		pubLoanprocess.setProcessState("usertask-relieveTaskAssign");
		pubLoanprocess.setTaskId(task.get(0).getProcessInstanceId());
		pubLoanprocess.setNextAssignee(task.get(0).getAssignee());
		pubLoanprocessDao.insert(pubLoanprocess);

//	        // 修改咨询表的状态
//	        PlCreditconsult consult = plCreditconsultService.getPlCreditconsultByProjectId(Long
//	                .toString(carLoanChangeDataBean.getProjectId()));
//	        consult.setStatus("taskuser-aheadof-return-loan-approval");
//	        consult.setModifyTime(new Date());
//	        plCreditconsultService.updatePlCreditconsult(consult);

	}

	/**
	 * @description (启动新的审批流程（包括解押）)
	 * @param originalProcessInstanceId 前一流程id
	 * @param processDefinitionName     流程名称
	 * @param userName
	 * @param projectId
	 * @param variablesMap
	 * @throws ServiceException
	 * @throws PersistentDataException
	 * @return Task
	 * @since 1.0.0
	 */
	private List<Task> startProcess(String originalProcessInstanceId, String processDefinitionName, String userName,
			Long projectId, Map<String, Object> variablesMap) throws ServiceException, PersistentDataException {
		// 锁定还款记录
		// lockRepayLoanInfo(originalProcessInstanceId, projectId);
		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
				.processDefinitionName(processDefinitionName).orderByProcessDefinitionVersion().desc().listPage(0, 1);

		ProcessDefinition latestProcessDefinition = processDefinitions.get(0);
		identityService.setAuthenticatedUserId(userName); // creditConsultDataBean.getUserName());
		if (variablesMap == null) {
			variablesMap = new HashMap<String, Object>();
		}
		try {
			variablesMap.put(SystemConstant.PROCESS_LAUNCHER, userName); // creditConsultDataBean.getUserName());
			// FHJ 把这些值做成系统配置参数
			variablesMap.put(SystemConstant.SECONDARY_AUDIT_AMOUNT_THRESHOLD, SECONDARY_AUDIT_AMOUNT_THRESHOLD);
			variablesMap.put(SystemConstant.SECONDARY_FINANCIAL_CONFIRMATION_AMOUNT_THRESHOLD,
					SECONDARY_FINANCIAL_CONFIRMATION_AMOUNT_THRESHOLD);
			variablesMap.put(SystemConstant.MANAGER_CONFIRMATION_AMOUNT_THRESHOLD,
					MANAGER_CONFIRMATION_AMOUNT_THRESHOLD);
			// 分之流程的根流程id
			variablesMap.put(SystemConstant.ORIGINAL_PROCESSINSTANCEID, originalProcessInstanceId);
			// FHJ just for testing
			TaskAssignerCenter.processDefinitionId = latestProcessDefinition.getId();

			ProcessInstance processInstance = runtimeService.startProcessInstanceById(latestProcessDefinition.getId(),
					projectId + latestProcessDefinition.getId(), variablesMap); // creditConsultDataBean.getProjectId()
			List<Task> task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();// .singleResult();

			// 维护 项目流程关系表 向里面插入数据
			PubProjectProcess ppp = pubProjectProcessDao.getProjectProcessByProjectId(projectId);
			PubProjectProcess pubProjectProcess = new PubProjectProcess();

			if (processDefinitionName.equals("RelieveProcess")) {
				Byte extensionNumber = 0;
				if (ppp != null && ppp.getExtensionNumber() != null) {
					extensionNumber = ppp.getExtensionNumber();
				}
				pubProjectProcess.setExtensionNumber(extensionNumber);
				pubProjectProcess.setProcessType((byte) 6); // 返佣
				pubProjectProcess.setProjectId(projectId);
				pubProjectProcess.setProcessInstanceId(task.get(0).getProcessInstanceId());// processInstance.getId());
			}

			pubProjectProcessDao.insert(pubProjectProcess);
			return task;
		} catch (RDRuntimeException e) {
			// 在这里捕获所有在监听器中throw的runtime异常，并且统一把它们包装成ServiceException从Service层抛出去
			throw new ServiceException(e.getMessage(), e);
		}

	}

}
