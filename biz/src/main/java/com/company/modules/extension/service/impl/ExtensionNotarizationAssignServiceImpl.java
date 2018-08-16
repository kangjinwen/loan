package com.company.modules.extension.service.impl;

import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.extension.service.ExtensionNotarizationAssignService;
import com.company.modules.extension.utils.databean.ExtensionNotarizationAssignDataBean;
/**
 * @author Administrator
 *
 */
@Service
public class ExtensionNotarizationAssignServiceImpl extends HistoryRecorderService implements ExtensionNotarizationAssignService{
	private static final Logger logger = LoggerFactory.getLogger(ExtensionNotarizationAssignServiceImpl.class);
	
	
	@Override
	public void saveDraft(BasicServiceDataBean serviceDataBean) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void preCheckCurrentWorkflowState(ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	
	

	@Override
	public void evaluation(ExtensionNotarizationAssignDataBean bean,DelegateTask delegateTask) throws Exception {
		if("pass".equals(bean.getNextStep())){
			String manualAssignee = bean.getManualAssignee();
			if(!StringUtils.isEmpty(manualAssignee)){
				delegateTask.setVariable("ManualAssignee", manualAssignee);//设置任务分配人
			}else{
				throw new ServiceException("任务分配人不能为空");
			}
		}
		 //记录审批日志
        recordLoanProcessHistory(bean);
	}
	
}
