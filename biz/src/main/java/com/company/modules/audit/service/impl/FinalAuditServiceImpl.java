package com.company.modules.audit.service.impl;

import org.activiti.engine.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.WorkFlowConstant;
import com.company.modules.audit.service.AbstractAuditService;
import com.company.modules.audit.util.databean.FinalAuditDataBean;
import com.company.modules.common.dao.PlProductDao;
import com.company.modules.common.domain.PlProduct;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;

@Service(value="finalAuditServiceImpl")
public class FinalAuditServiceImpl extends AbstractAuditService {
	
	@Autowired
	private PlProductDao  productDao;
	
	@Override
	public void audit(BasicServiceDataBean serviceDataBean,
			DelegateTask delegateTask) throws Exception {
		FinalAuditDataBean finalAuditDataBean =(FinalAuditDataBean) serviceDataBean;
		if (finalAuditDataBean.getPlBorrowRequirement()!=null) {			
			PlProduct product = productDao.getItemInfoById(finalAuditDataBean.getPlBorrowRequirement().getProductId());
			finalAuditDataBean.setProduct(product);
		}
		
		if(WorkFlowConstant.NEXT_STEP_PASS.equals(finalAuditDataBean.getNextStep()) || WorkFlowConstant.NEXT_STEP_RECHECK.equals(finalAuditDataBean.getNextStep())){
			createOrUpdatePlBorrowRequirement(finalAuditDataBean);//插入或更新借款需求表
			createOrUpdateBorrowInfo(finalAuditDataBean);//插入或更新borrow表
		}
		 //记录审批日志
        recordLoanProcessHistory(finalAuditDataBean);

	}


	@Override
	protected void preCheckCurrentWorkflowState(
			ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

}
