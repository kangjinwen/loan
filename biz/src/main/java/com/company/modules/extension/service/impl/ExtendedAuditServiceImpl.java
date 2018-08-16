package com.company.modules.extension.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.company.common.context.WorkFlowConstant;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.extension.service.ExtendedAuditService;
import com.company.modules.extension.utils.databean.ExtendedAuditDataBean;
/**
 * @author Administrator
 *
 */
@Service
public class ExtendedAuditServiceImpl extends HistoryRecorderService implements ExtendedAuditService{
	private static final Logger logger = LoggerFactory.getLogger(ExtendedAuditServiceImpl.class);
	
	
	

	@Override
	protected void preCheckCurrentWorkflowState(ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}
	




	@Override
	public void audit(ExtendedAuditDataBean dataBean) throws Exception {
		// TODO Auto-generated method stub
		logger.info("展期审批开始...");
        if (logger.isDebugEnabled()) {
            logger.debug("参数列表：" +dataBean);
        }
        preCheckBasicParams(dataBean);
        preCheckWorkflowParams(dataBean);
        preCheckCurrentWorkflowState(dataBean);
        //登记通过
        if (WorkFlowConstant.NEXT_STEP_PASS.equals(dataBean.getNextStep())) {
            preCheckServiceParams(dataBean);
        }        
        //记录审批日志
        recordLoanProcessHistory(dataBean);
        logger.info("展期审批结束...");
	}
	
}
