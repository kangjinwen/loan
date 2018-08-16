package com.company.modules.finance.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
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
import com.company.common.dao.PubProjectProcessDao;
import com.company.common.domain.PubProjectProcess;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.common.utils.StringUtil;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.dao.PlConsultDao;
import com.company.modules.common.dao.PubLoanprocessDao;
import com.company.modules.common.domain.PlConsult;
import com.company.modules.common.domain.PubLoanprocess;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.finance.dao.HousLowerCostDao;
import com.company.modules.finance.domain.HousLowerCost;
import com.company.modules.finance.service.HousLowerCostService;
import com.company.modules.system.domain.SysUser;

/**
* User:    fzc
* DateTime:2016-08-12 10:23:50
* details: 下户费表,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "housLowerCostServiceImpl")
public class HousLowerCostServiceImpl extends BaseServiceImpl implements HousLowerCostService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(HousLowerCostServiceImpl.class);
    /**
	 * 下户费表dao层
	 */
    @Autowired
    private HousLowerCostDao housLowerCostDao;
    @Autowired
    private PlConsultDao plConsultDao;
    @Autowired
    private PubLoanprocessDao pubLoanprocessDao;
    @Autowired
	private RepositoryService repositoryService;
    @Autowired
	private RuntimeService runtimeService;
    @Autowired
	private PubProjectProcessDao pubProjectProcessDao;
    @Autowired
	private TaskService taskServiceReturnFee;   
    

	/**
	 * 下户费表表,插入数据
	 * @param collateral 下户费表类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(HousLowerCost housLowerCost) throws Exception {
		try {
			return housLowerCostDao.insert(housLowerCost);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 下户费表表,修改数据
	* @param collateral 下户费表类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(HousLowerCost housLowerCost) throws Exception {
		try {
			return housLowerCostDao.update(housLowerCost);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 下户费表表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<HousLowerCost> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return housLowerCostDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 下户费表表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousLowerCost getItemInfoById(long id) throws Exception {
		try {
			return housLowerCostDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 下户费表表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousLowerCost getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return housLowerCostDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 下户费表表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return housLowerCostDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return housLowerCostDao;
	}

	@Override
	public Map<String, Object> getHousLowerCostBasicInfo(long projectId)
			throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data = housLowerCostDao.getHousLowerCostBasicInfo(projectId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		return data;
	}

	@Override
	public Map<String, Object> getHousLowerCostInfo(long processInstanceId)
			throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data = housLowerCostDao.getHousLowerCostInfo(processInstanceId);
			if(data == null ){
				Map<String, Object> conditions = new HashMap<String, Object>();
				conditions.put("processInstanceId", processInstanceId);
				conditions.put("processState", "usertask-customerServiceStaffConfirm");
				data = (Map<String, Object>) pubLoanprocessDao.getOne(conditions);
			}else{
				data.put("receiveType", data.get("collectionForm"));
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		return data;
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void createReturnFeeTasks(Long processInstanceId, Long projectId, SysUser sysUser, BigDecimal applyRefundAmount,String type,String remark)
			throws Exception {
        HousLowerCost  housLowerCost = housLowerCostDao.getItemInfoByProcessInstanceId(String.valueOf(processInstanceId));
        PlConsult plConsult = plConsultDao.getItemInfoByProcessInstanceId(String.valueOf(processInstanceId));
        if(housLowerCost!=null){
        	housLowerCost.setApplyRefundAmount(applyRefundAmount);        	
        	housLowerCost.setApplyRefundTime(new Date());
        	housLowerCost.setRefundOperator(sysUser.getName());
        	housLowerCostDao.update(housLowerCost);
        	plConsult.setFeeSuccess((byte)2);//发生退费申请状态，用于查询区分
        	plConsultDao.update(plConsult);        	
        }              
        Map<String,Object> variablesMap = new HashMap<String, Object>();
        variablesMap.put(SystemConstant.PROCESS_LAUNCHER, sysUser.getUserName());
        try {
        	   ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("returnFeeProcess", variablesMap);
               String procInstId = processInstance.getProcessInstanceId();//得到分支流程的流程id
               String taskId = processInstance.getId();//得到分支流程的主键id
               Task task = taskServiceReturnFee.createTaskQuery().processInstanceId(procInstId).singleResult();
               if (StringUtil.isNotBlank(procInstId)) {
               	//维护 项目流程关系表 向里面插入数据
               	PubProjectProcess pubProjectProcess = new PubProjectProcess();
               	pubProjectProcess.setProjectId(projectId);
               	pubProjectProcess.setProcessInstanceId(procInstId);
               	pubProjectProcess.setExtensionNumber(SystemConstant.IS_DELETE_NORMAL);
               	pubProjectProcess.setProcessType(SystemConstant.returnFee);
               	pubProjectProcessDao.insert(pubProjectProcess);
               	//审批意见和备注插入审批历史表
               	PubLoanprocess pubLoanprocess = new PubLoanprocess();
               	pubLoanprocess.setCreator(sysUser.getId());
               	pubLoanprocess.setCreateTime(new Date());
               	pubLoanprocess.setIsDelete(SystemConstant.IS_DELETE_NORMAL);
               	pubLoanprocess.setOfficeId(sysUser.getOfficeId());
               	pubLoanprocess.setProcessInstanceId(procInstId);
               	pubLoanprocess.setProjectId(projectId);
               	pubLoanprocess.setConsultId(housLowerCost.getConsultId());
               	pubLoanprocess.setType(type);
               	pubLoanprocess.setRemark(remark);
               	pubLoanprocess.setProcessState("usertask-returnFeeApply");
               	pubLoanprocess.setTaskId(taskId);
               	if (task!=null) {
               		pubLoanprocess.setNextAssignee(task.getAssignee());
				}
               	pubLoanprocessDao.insert(pubLoanprocess);               	
       		}
		} catch (RDRuntimeException e) {
			// 在这里捕获所有在监听器中throw的runtime异常，并且统一把它们包装成ServiceException从Service层抛出去
            throw new ServiceException(e.getMessage(), e);
		}     
	}
	
	
}