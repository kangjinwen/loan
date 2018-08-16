package com.company.modules.audit.service.impl;

import org.activiti.engine.delegate.DelegateTask;
import org.springframework.stereotype.Service;

import com.company.modules.audit.service.AbstractAuditService;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;

@Service(value="recheckRefuseServiceImpl")
public class RecheckRefuseServiceImpl extends AbstractAuditService {

	@Override
	public void audit(BasicServiceDataBean serviceDataBean, DelegateTask delegateTask) throws Exception {
		//记录审批日志
        recordLoanProcessHistory(serviceDataBean);
	}

	@Override
	protected void preCheckCurrentWorkflowState(ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub

	}

}
