package com.company.modules.rebate.workflow.service.impl;

import java.math.BigDecimal;
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
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.company.common.context.Constant;
import com.company.modules.audit.dao.HousLoanfeesDao;
import com.company.modules.audit.domain.HousLoanfees;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.rebate.workflow.dao.RebateTaskDao;
import com.company.modules.rebate.workflow.service.RebateTaskService;
import com.company.modules.system.dao.SysRoleDao;
import com.company.modules.system.dao.SysUserDao;
import com.company.modules.system.domain.SysRole;
import com.company.modules.system.domain.SysUser;
import com.company.modules.workflow.utils.observation.TaskAssignerCenter;
import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.dao.PubProjectProcessDao;
import com.company.common.dao.PubRepaymentdetailDao;
import com.company.common.domain.PubProcessBranching;
import com.company.common.domain.PubProjectProcess;
import com.company.common.service.PubProcessBranchingService;

@SuppressWarnings("rawtypes")
@Service(value = "rebateTaskServiceImpl")
@Transactional
public class RebateTaskServiceImpl implements RebateTaskService{

private static final Logger log = LoggerFactory.getLogger(RebateTaskServiceImpl.class);
	
	@Autowired
	private	RebateTaskDao  rebateTaskDao;
	
	@Autowired
	private SysRoleDao sysRoleDao;
	
	@Autowired
	private SysUserDao sysUserDao;
	
	@Autowired
	private PubRepaymentdetailDao pubRepaymentdetailDao;
	
	//与分支流程相关的服务
	@Autowired
	private HousLoanfeesDao housLoanfeesDao;
	
	@Autowired
	private PubProcessBranchingService pubProcessBranchingService;
	@Autowired
	private PubProcessBranchingDao pubProcessBranchingDao;
	@Autowired
	private PubProjectProcessDao pubProjectProcessDao;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	private Double SECONDARY_AUDIT_AMOUNT_THRESHOLD = (double) 200000;// null;
	private Double SECONDARY_FINANCIAL_CONFIRMATION_AMOUNT_THRESHOLD = (double) 200000; // null;
	private Double MANAGER_CONFIRMATION_AMOUNT_THRESHOLD = (double) 500000; // null;
	
	
	private static final String USERTASK_RETURNCOMMISSIONAUDIT= "usertask-returnCommissionAudit"; //返佣审核
	
	private static final String USERTASK_RETURNCOMMISSION= "usertask-returnCommission"; //返佣
	
	@Override
	public PageInfo<Map<String, Object>> getRebateAuditPageList(
			Map<String, Object> data) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> returnMap = null;
		try {
			data.put("taskDefinition",USERTASK_RETURNCOMMISSIONAUDIT);
			Boolean isCompleted=(Boolean) data.get("isCompleted");
			String roleName = getRoleName((Long)data.get("roleId"));
			data.put("nid", roleName);
			
			PageHelper.startPage((Integer)data.get("currentPage"),(Integer)data.get("pageSize"));
			if(isCompleted){
				data.put("isCompleted", isCompleted);
				returnMap=rebateTaskDao.getDoneRebateAuditList(data);
			} else{
				returnMap=rebateTaskDao.getUnDoneRebateAuditList(data);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		//返回已经查询完毕的参数
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



	@Override
	public PageInfo<Map<String, Object>> getRebateHandlePageList(
			Map<String, Object> data) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> returnMap = null;
		try {
			data.put("taskDefinition",USERTASK_RETURNCOMMISSION);
			Boolean isCompleted=(Boolean) data.get("isCompleted");
			String roleName = getRoleName((Long)data.get("roleId"));
			data.put("nid", roleName);
			
			PageHelper.startPage((Integer)data.get("currentPage"),(Integer)data.get("pageSize"));
			if(isCompleted){
				returnMap=rebateTaskDao.getDoneRebateHandleList(data);
			} else{
				returnMap=rebateTaskDao.getUnDoneRebateHandleList(data);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		//返回已经查询完毕的参数
		PageInfo<Map<String, Object>> page = new PageInfo<>(returnMap);
		return page;
	}
	
	
	/**
	 * 
	* @Description: 创建返佣任务
	* @param @param processInstanceId
	* @param @param projectId
	* @param @param userName
	* @param @throws ServiceException
	* @param @throws PersistentDataException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Override
	 public void createRebateTasks(Map<String, Object> map
	            ) throws Exception {
		
	       String processInstanceId=(String)map.get("processInstanceId");
	       Long projectId=Long.valueOf((String)map.get("projectId"));
	       Integer period=(Integer)map.get("period");
	       long userId=(Long)map.get("userId");
	       int repaymentType=(Integer)map.get("repaymentType");
	       
	       SysUser user=sysUserDao.getByPrimary(userId);
	       String userName=user.getUserName();
	       
	        HousLoanfees  housLoanfees=housLoanfeesDao.getItemInfoByProcessInstanceId(processInstanceId.toString());
	        if(housLoanfees!=null){
	        	BigDecimal returnRate=housLoanfees.getReturnRate();
	        	BigDecimal returnFee=housLoanfees.getReturnFee();
	        	
	        	if(returnRate.equals(BigDecimal.ZERO)||returnFee.equals(BigDecimal.ZERO)){
	        		 log.info("返费率为0，无需返佣");
	        		return;
	        	}
	        } else {
	        	log.info("请先补充完费率信息");
	        	return;
	        }
	        
	        //最后一期不需要返佣
//	        Map<String, Object> repayMap=new HashMap<String,Object>();
//	        repayMap.put("processInstanceId", processInstanceId);
//	        List<PubRepaymentdetail> detail = pubRepaymentdetailDao.getPageListByMap(repayMap);
	        Integer totalPeriod=(byte)map.get("totalPeriod")+1;
	        if (period >= totalPeriod) {
	        	log.info("最后一期没有返佣");
	        	return;
			}
//	        if (detail == null) {
//	            throw new RuntimeException("查询不到该还款计划");
//	        }else{
//	        	int size=detail.size()+1;
//	        	if(period>=size){
//	        		log.info("最后一期没有返佣");
//	        		return;
//	        	}
//	        }
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
	        pb.setRemark2("返佣");

	        List<Task> task = startProcess(processInstanceId.toString(), "ReturnCommissionProcess", userName,projectId, null);

	        // 几个小流程，都设置为 贷后分支流程类别 需要在任务中进行统一处理。
	        pb.setTaskId(task.get(0).getId());
	        pb.setBranchingProcessId(task.get(0).getProcessInstanceId());
	        pb.setBranchingProcessType((byte) 4);
	        pb.setRepaymentProcess((byte)repaymentType); 
	        pb.setProcessingOpinion("noprocess");
	        pb.setProjectId(projectId);
	        pb.setCreateTime(new Date());
	        pb.setCreator(userName);
	        pb.setIsDelete((byte) 0);
	        pb.setPeriod(period.byteValue());
	        pb.setIsActive((byte) 2); //返佣状态
	        pb.setProcessStatus("usertask-returnCommissionAudit"); //设置成返佣待审批状态
	        pubProcessBranchingDao.insert(pb);

	    }
	 
	  /**
	     * @description (启动新的审批流程（包括：返佣）)
	     * @param originalProcessInstanceId 前一流程id
	     * @param processDefinitionName 流程名称
	     * @param userName
	     * @param projectId
	     * @param variablesMap
	     * @throws ServiceException
	     * @throws PersistentDataException
	     * @return Task
	     * @since 1.0.0
	     */
	    private List<Task> startProcess(String originalProcessInstanceId, String processDefinitionName, String userName,
	            Long projectId, Map<String, Object> variablesMap) throws Exception {

	        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
	                .processDefinitionName(processDefinitionName).orderByProcessDefinitionVersion().desc().listPage(0, 1);

	        ProcessDefinition latestProcessDefinition = processDefinitions.get(0);
	        identityService.setAuthenticatedUserId(userName); // 
	        if (variablesMap == null) {
	            variablesMap = new HashMap<String, Object>();
	        }
	        try {
	            variablesMap.put(SystemConstant.PROCESS_LAUNCHER, userName); // 
	            // TODO FHJ 把这些值做成系统配置参数
	            variablesMap.put(SystemConstant.SECONDARY_AUDIT_AMOUNT_THRESHOLD, SECONDARY_AUDIT_AMOUNT_THRESHOLD);
	            variablesMap.put(SystemConstant.SECONDARY_FINANCIAL_CONFIRMATION_AMOUNT_THRESHOLD,SECONDARY_FINANCIAL_CONFIRMATION_AMOUNT_THRESHOLD);
	            variablesMap.put(SystemConstant.MANAGER_CONFIRMATION_AMOUNT_THRESHOLD, MANAGER_CONFIRMATION_AMOUNT_THRESHOLD);
	            // 分之流程的根流程id
	            variablesMap.put(SystemConstant.ORIGINAL_PROCESSINSTANCEID, originalProcessInstanceId);
	            // TODO FHJ just for testing
	            TaskAssignerCenter.processDefinitionId = latestProcessDefinition.getId();

	            ProcessInstance processInstance = runtimeService.startProcessInstanceById(latestProcessDefinition.getId(),
	                    projectId + latestProcessDefinition.getId(), variablesMap); // 
	            List<Task> task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();//

	            // 维护 项目流程关系表 向里面插入数据
	            PubProjectProcess ppp = pubProjectProcessDao.getProjectProcessByProjectId(projectId);
	            PubProjectProcess pubProjectProcess = new PubProjectProcess();
	            
	            if (processDefinitionName.equals("ReturnCommissionProcess"))  {
	                Byte extensionNumber = 0;
	                if (ppp != null && ppp.getExtensionNumber() != null) {
	                    extensionNumber = ppp.getExtensionNumber();
	                }
	                pubProjectProcess.setExtensionNumber(extensionNumber);
	                pubProjectProcess.setProcessType((byte) 6);  //返佣
	                pubProjectProcess.setProjectId(projectId);
	                pubProjectProcess.setProcessInstanceId(task.get(0).getProcessInstanceId());// processInstance.getId());
	            }
	            pubProjectProcessDao.insert(pubProjectProcess);
	            return task;
	        } catch (Exception e) {
	            // 在这里捕获所有在监听器中throw的runtime异常，并且统一把它们包装成ServiceException从Service层抛出去
	            throw new ServiceException(e.getMessage(), e);
	        }

	    }

	
	

}
