package com.company.modules.collateral.workflow.service;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.collateral.databean.CollateralTaskAssignDataBean;

public interface CollateralRelieveAssignService {
	
	void relieveTaskAssgin(CollateralTaskAssignDataBean dataBean,DelegateTask delegateTask) throws Exception;
}
