package com.company.modules.warrant.service.impl;

import org.activiti.engine.delegate.DelegateTask;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.warrant.service.HouseholdTaskAssignService;
import com.company.modules.warrant.util.databean.HouseholdDataBean;

@Service
public class HouseholdTaskAssignServiceImpl extends HistoryRecorderService implements HouseholdTaskAssignService{

	@Override
	protected void preCheckCurrentWorkflowState(
			ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void HouseholdTaskAssign(HouseholdDataBean householdDataBean,DelegateTask delegateTask)
			throws Exception {
		//notCheckBank不核行就走以前的流程
		if("notCheckBank".equals(householdDataBean.getNextStep())){
			String manualAssignee = householdDataBean.getManualAssignee();
			if(!StringUtils.isEmpty(manualAssignee)){
				delegateTask.setVariable("ManualAssignee", manualAssignee);//设置任务分配人
			}else{
				throw new ServiceException("任务分配人不能为空");
			}
		}else if("needCheckBank".equals(householdDataBean.getNextStep())){
			String manualAssignee = householdDataBean.getManualAssignee();
			String assignCheckBank = householdDataBean.getAssignCheckBank();
			if(!StringUtils.isEmpty(manualAssignee)){
				delegateTask.setVariable("ManualAssignee", manualAssignee);//设置任务分配人
				delegateTask.setVariable("AssignCheckBank", assignCheckBank);//设置任务分配人(核行)
			}else{
				throw new ServiceException("任务分配人不能为空");
			}
		}
		 //记录审批日志
        recordLoanProcessHistory(householdDataBean);
	}

}
