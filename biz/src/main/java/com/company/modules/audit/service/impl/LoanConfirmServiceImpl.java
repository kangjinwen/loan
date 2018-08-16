package com.company.modules.audit.service.impl;

import org.activiti.engine.delegate.DelegateTask;
import org.springframework.stereotype.Service;

import com.company.modules.audit.service.LoanConfirmService;
import com.company.modules.audit.util.databean.LoanConfirmDataBean;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;

@Service("loanConfirmServiceImpl")
public class LoanConfirmServiceImpl extends HistoryRecorderService implements LoanConfirmService {


	@Override
	protected void preCheckCurrentWorkflowState(ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loanConfirm(LoanConfirmDataBean serviceDataBean, DelegateTask delegateTask) throws Exception {
		//记录审批日志
        recordLoanProcessHistory(serviceDataBean);
		
	}


}
