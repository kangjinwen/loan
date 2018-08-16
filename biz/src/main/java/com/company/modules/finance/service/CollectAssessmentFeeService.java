package com.company.modules.finance.service;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.finance.util.databean.HousLowerCostTaskDataBean;

public interface CollectAssessmentFeeService {

	void collectAssessmentFee(HousLowerCostTaskDataBean serviceDataBean,DelegateTask delegateTask) throws Exception;	
	

}
