package com.company.modules.advance.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.dao.PubProjectProcessDao;
import com.company.common.domain.PubProcessBranching;
import com.company.common.domain.PubProjectProcess;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.common.utils.StringUtil;
import com.company.modules.advance.dao.HousAdvanceApplyDao;
import com.company.modules.advance.domain.HousAdvanceApply;
import com.company.modules.advance.service.HousAdvanceApplyService;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.dao.PlConsultDao;
import com.company.modules.common.dao.PubLoanprocessDao;
import com.company.modules.common.domain.PlConsult;
import com.company.modules.common.domain.PubLoanprocess;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.domain.SysUser;


/**
* User:    gaoshan
* DateTime:2016-09-14 11:54:13
* details: 垫资申请,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "housAdvanceApplyServiceImpl")
public class HousAdvanceApplyServiceImpl extends BaseServiceImpl implements HousAdvanceApplyService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(HousAdvanceApplyServiceImpl.class);
    /**
	 * 垫资申请dao层
	 */
    @Autowired
    private HousAdvanceApplyDao housAdvanceApplyDao;
    @Autowired
    private PlConsultDao plConsultDao;
    @Autowired
  	private RuntimeService runtimeService;
    @Autowired
    private PubLoanprocessDao pubLoanprocessDao;
    @Autowired
	private PubProjectProcessDao pubProjectProcessDao;
    @Autowired
    private PubProcessBranchingDao pubProcessBranchingDao;
    @Autowired
    private TaskService taskService;
	/**
	 * 垫资申请表,插入数据
	 * @param collateral 垫资申请类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(HousAdvanceApply housAdvanceApply) throws Exception {
		try {
			return housAdvanceApplyDao.insert(housAdvanceApply);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 垫资申请表,修改数据
	* @param collateral 垫资申请类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(HousAdvanceApply housAdvanceApply) throws Exception {
		try {
			return housAdvanceApplyDao.update(housAdvanceApply);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 垫资申请表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<HousAdvanceApply> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return housAdvanceApplyDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 垫资申请表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousAdvanceApply getItemInfoById(long id) throws Exception {
		try {
			return housAdvanceApplyDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 垫资申请表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousAdvanceApply getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return housAdvanceApplyDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 垫资申请表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return housAdvanceApplyDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return housAdvanceApplyDao;
	}

	@Transactional
	@Override
	public void createAdvanceTasks(Long processInstanceId, Long projectId, Long consultId, SysUser sysUser,
			BigDecimal advanceApplyAmount, BigDecimal advanceRateAmount, BigDecimal advanceAmount, String type,
			String remark) throws Exception {
		PlConsult plConsult = plConsultDao.getItemInfoByProcessInstanceId(String.valueOf(processInstanceId));
		HousAdvanceApply housAdvanceApply = new HousAdvanceApply();		
		Map<String,Object> variablesMap = new HashMap<String, Object>();
	    variablesMap.put(SystemConstant.PROCESS_LAUNCHER, sysUser.getUserName());
	    try {
	    	 ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("houseLoaningProcess", variablesMap);
	         String procInstId = processInstance.getProcessInstanceId();//得到分支流程的流程id
	         String taskId = processInstance.getId();//得到分支流程的主键id
	         List<Task> task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();//.singleResult();

	         if (StringUtil.isNotBlank(procInstId)) {
	        	//向垫资申请表插数据
	        	housAdvanceApply.setCreateTime(new Date());
	     		housAdvanceApply.setConsultId(consultId);
	     		housAdvanceApply.setProcessInstanceId(procInstId);
	     		housAdvanceApply.setProjectId(projectId);
	     		housAdvanceApply.setAdvanceApplyAmount(advanceApplyAmount);
	     		housAdvanceApply.setAdvanceRateAmount(advanceRateAmount);
	     		housAdvanceApply.setAdvanceAmount(advanceAmount);
	     		housAdvanceApply.setAdvanceApplyOperator(sysUser.getName());
	     		housAdvanceApply.setAdvanceApplyTime(new Date());
	     		housAdvanceApply.setStatus(HousAdvanceApply.USERTASK_WARRANTMANAGER_ASSIGNED);
	     		housAdvanceApplyDao.insert(housAdvanceApply);
	     		if (plConsult!=null) {
	     			plConsult.setAdvanceApply((byte)2);
	     			plConsultDao.update(plConsult);      
	     		}
	        	//维护 项目流程关系表 向里面插入数据
	        	PubProjectProcess pubProjectProcess = new PubProjectProcess();
	        	pubProjectProcess.setProjectId(projectId);
	        	pubProjectProcess.setProcessInstanceId(procInstId);
	        	pubProjectProcess.setExtensionNumber(SystemConstant.IS_DELETE_NORMAL);
	        	pubProjectProcess.setProcessType(SystemConstant.advance);
	        	pubProjectProcessDao.insert(pubProjectProcess);
	        	//审批意见和备注插入审批历史表
	        	PubLoanprocess pubLoanprocess = new PubLoanprocess();
	        	pubLoanprocess.setCreator(sysUser.getId());
	        	pubLoanprocess.setCreateTime(new Date());
	        	pubLoanprocess.setIsDelete(SystemConstant.IS_DELETE_NORMAL);
	        	pubLoanprocess.setOfficeId(sysUser.getOfficeId());
	        	pubLoanprocess.setProcessInstanceId(procInstId);
	        	pubLoanprocess.setProjectId(projectId);
	        	pubLoanprocess.setConsultId(consultId);
	        	pubLoanprocess.setType(type);
	        	pubLoanprocess.setRemark(remark);
	        	pubLoanprocess.setProcessState(HousAdvanceApply.USERTASK_ADVANCE_APPLY);
	        	pubLoanprocess.setTaskId(taskId);
	        	pubLoanprocess.setNextAssignee(task.get(0).getAssignee());
	        	pubLoanprocessDao.insert(pubLoanprocess);
	        	
	        	PubProcessBranching pb = new PubProcessBranching();
		        pb.setProcessInstanceId(String.valueOf(processInstanceId));
		        pb.setRemark2("垫资");
		        pb.setProcessInstanceId(String.valueOf(processInstanceId));
		        pb.setBranchingProcessId(procInstId);
		        pb.setBranchingProcessType(SystemConstant.advance);
		        pb.setProcessingOpinion("noprocess");
		        pb.setProjectId(projectId);
		        pb.setCreateTime(new Date());
		        pb.setCreator(sysUser.getUserName());
		        pb.setIsDelete((byte) 0);
		        pb.setIsActive((byte) 1);
		        pb.setProcessStatus(HousAdvanceApply.USERTASK_WARRANTMANAGER_ASSIGNED);
		        pubProcessBranchingDao.insert(pb);
			}
	    }catch (RDRuntimeException e) {
			// 在这里捕获所有在监听器中throw的runtime异常，并且统一把它们包装成ServiceException从Service层抛出去
            throw new ServiceException(e.getMessage(), e);
		}     
		
	}

	@Override
	public Map<String, Object> getHousAdvanceApplyInfo(long projectId) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data = housAdvanceApplyDao.getHousAdvanceApplyInfo(projectId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		return data;
	}

	@Override
	public Map<String, Object> getHousAdvanceApply(long processInstanceId) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data = housAdvanceApplyDao.getHousAdvanceApply(processInstanceId);		
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		return data;
	}
}