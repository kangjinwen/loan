package com.company.modules.rebate.workflow.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.context.WorkFlowConstant;
import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.domain.PubProcessBranching;
import com.company.modules.audit.dao.HousLoanfeesDao;
import com.company.modules.audit.domain.HousLoanfees;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.rebate.dao.RebateManageDao;
import com.company.modules.rebate.databean.RebateAuditDataBean;
import com.company.modules.rebate.domain.HousRebate;
import com.company.modules.rebate.workflow.service.RebateHandleService;
import com.company.modules.workflow.utils.observation.TaskAssignerCenter;

@SuppressWarnings("rawtypes")
@Service(value = "rebateHandleServiceImpl")
public class RebateHandleServiceImpl extends HistoryRecorderService implements RebateHandleService{
private static final Logger log = LoggerFactory.getLogger(RebateTaskServiceImpl.class);
	
@Autowired
private	RebateManageDao rebateManageDao;

@Autowired
private PubProcessBranchingDao pubProcessBranchingDao;

@Autowired
private HousLoanfeesDao housLoanfeesDao;

private static final Logger logger = LoggerFactory.getLogger(RebateAuditServiceImpl.class);

	@Override
	protected void preCheckCurrentWorkflowState(
			ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void handle(RebateAuditDataBean dataBean) throws Exception {
		if(WorkFlowConstant.NEXT_STEP_PASS.equalsIgnoreCase(dataBean.getNextStep())){
			Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("branchingProcessId", dataBean.getProcessInstanceId());
			map.put("isActive",WorkFlowConstant.IS_ACTIVE_RETURN_COMMISSION);
	        try {
	        	PubProcessBranching pb = null;
	        	List<PubProcessBranching> list= pubProcessBranchingDao.getPageListByMap(map);
				if(list==null || list.size()==0){
					throw new ServiceException("查询不到指定的分支流程");
				}
				pb = list.get(0);
			//	pb.setProcessingOpinion(pubProcessBranching.getProcessingOpinion());
				
				pb.setModifier(dataBean.getUserName());
				pb.setModifyTime(new Date());
				pb.setTaskId(dataBean.getTaskId());
				
				pubProcessBranchingDao.update(pb); //更新分支流程
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
		        throw new ServiceException(400, e.getMessage());
			}
	        try {
	        	logger.info("返佣审核...");
	            if (logger.isDebugEnabled()) {
	                logger.debug("参数列表：" +dataBean);
	            }
	            preCheckBasicParams(dataBean);
	            preCheckWorkflowParams(dataBean);
	            preCheckCurrentWorkflowState(dataBean);
	            //登记通过
	            preCheckServiceParams(dataBean);
	            
	            TaskAssignerCenter.isNew= true;
	            //保存返佣信息
	            updateHousRebate(dataBean);
	            //更新返费签单信息
	            updateHousLoanfees(dataBean);
	            //记录审批日志
	            recordLoanProcessHistory(dataBean);
	            logger.info("返佣审核完成...");
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
		        throw new ServiceException(400, e.getMessage());
			}
		} else{
			//记录审批日志
	        recordLoanProcessHistory(dataBean);
		}
		
	}
	
	/**
	 * 
	* @Description: 更新返佣信息
	* @param @throws Exception    设定文件 
	* @author wangmc
	* @return void    返回类型 
	* @throws
	 */
	public void updateHousRebate(RebateAuditDataBean dataBean) throws Exception{
		try {
			HousRebate housRebate=dataBean.getHousRebate();
			housRebate.setConsultId(dataBean.getConsultId());
			housRebate.setBankName(housRebate.getReturnBankName());
			housRebate.setProjectId(dataBean.getProjectId());
			housRebate.setProcessInstanceId(dataBean.getProcessInstanceId());
			HousRebate hRebate=rebateManageDao.getHousRebateByInstanceId(dataBean.getProcessInstanceId());
			if(hRebate!=null){
				housRebate.setModifier(dataBean.getLoginUserId());
				housRebate.setModifyTime(new Date());
				housRebate.setFinancialId(dataBean.getLoginUserId());
				housRebate.setId(hRebate.getId());
				rebateManageDao.update(housRebate);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throwServiceExceptionAndLog(logger, "数据库操作失败。", e, Constant.OTHER_CODE_VALUE);
		}
		
		
	}

	public void updateHousLoanfees(RebateAuditDataBean dataBean) throws Exception{
		HousLoanfees housLoanfees = housLoanfeesDao.getItemInfoByProjectId(dataBean.getProjectId());
		if (housLoanfees!=null) {	
			housLoanfees.setReturnCard(dataBean.getHousRebate().getRebateCard());
			housLoanfees.setReturnBankName(dataBean.getHousRebate().getReturnBankName());
			housLoanfeesDao.update(housLoanfees);//更新返费人姓名
		}
	}
}
