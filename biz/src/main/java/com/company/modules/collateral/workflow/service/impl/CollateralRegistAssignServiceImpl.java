package com.company.modules.collateral.workflow.service.impl;

import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.company.modules.collateral.databean.CollateralTaskAssignDataBean;
import com.company.modules.collateral.workflow.service.CollateralRegistAssignService;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;

@Service(value = "collateralRegistAssignServiceImpl")
public class CollateralRegistAssignServiceImpl extends HistoryRecorderService implements CollateralRegistAssignService{
	
	private static final Logger logger = LoggerFactory.getLogger(CollateralRegistServiceImpl.class);

	@Override
	protected void preCheckCurrentWorkflowState(
			ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registTaskAssgin(CollateralTaskAssignDataBean dataBean,DelegateTask delegateTask) throws Exception {
		// TODO Auto-generated method stub
		logger.info("抵押任务分配...");
        if (logger.isDebugEnabled()) {
            logger.debug("参数列表：" +dataBean);
        }
        preCheckBasicParams(dataBean);
        preCheckWorkflowParams(dataBean);
        preCheckCurrentWorkflowState(dataBean);
        
//        String manualAssignee = dataBean.getManualAssignee();
		//if(!StringUtils.isEmpty(manualAssignee)){
//			delegateTask.setVariable("ManualAssignee", manualAssignee);//设置任务分配人
		//}else{
			//throw new ServiceException("任务分配人不能为空");
		//}
	//	delegateTask.setVariable("ManualAssignee", dataBean.getCollateralRegistAssignee());//设置任务分配人
			 //记录审批日志
	    recordLoanProcessHistory(dataBean);
	        
	    logger.info("抵押任务分配完成...");
	}
	

}
