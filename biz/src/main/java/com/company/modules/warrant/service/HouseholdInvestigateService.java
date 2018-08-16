package com.company.modules.warrant.service;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.warrant.util.databean.HouseholdInvestigateDataBean;

public interface HouseholdInvestigateService {

	void householdInvestigate(HouseholdInvestigateDataBean householdInvestigateDataBean,DelegateTask delegateTask) throws Exception;
	Boolean insertOrUpdateHousinfo(HouseholdInvestigateDataBean householdInvestigateDataBean)throws Exception;
}
