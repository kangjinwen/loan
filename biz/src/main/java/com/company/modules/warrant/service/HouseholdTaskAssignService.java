package com.company.modules.warrant.service;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.warrant.util.databean.HouseholdDataBean;

public interface HouseholdTaskAssignService {

	void HouseholdTaskAssign(HouseholdDataBean householdDataBean,DelegateTask delegateTask) throws Exception;
}
