package com.company.modules.collateral.workflow.service.impl;

import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.company.modules.collateral.databean.CollateralTaskAssignDataBean;
import com.company.modules.collateral.workflow.service.CollateralRelieveAssignService;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;

@Service(value = "collateralRelieveAssignServiceImpl")
public class CollateralRelieveAssignServiceImpl extends HistoryRecorderService implements CollateralRelieveAssignService{

	private static final Logger logger = LoggerFactory.getLogger(CollateralRelieveServiceImpl.class);
	@Override
	protected void preCheckCurrentWorkflowState(
			ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void relieveTaskAssgin(CollateralTaskAssignDataBean dataBean,
			DelegateTask delegateTask) throws Exception {
		// TODO Auto-generated method stub
		
		logger.info("解押任务分配...");
        if (logger.isDebugEnabled()) {
            logger.debug("参数列表：" +dataBean);
        }
        preCheckBasicParams(dataBean);
        preCheckWorkflowParams(dataBean);
        preCheckCurrentWorkflowState(dataBean);
        
//		delegateTask.setVariable("ManualAssignee", dataBean.getCollateralRelieveAssignee());//设置任务分配人
		
		delegateTask.setVariable("ManualAssignee", dataBean.getManualAssignee());//设置任务分配人
		
			 //记录审批日志
	    recordLoanProcessHistory(dataBean);
	        
	    logger.info("解押任务分配完成...");
	}
		

}
