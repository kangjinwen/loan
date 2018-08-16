package com.company.modules.advance.service;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.advance.util.databean.AdvanceConfirmTaskDataBean;

public interface AdvanceConfirmService {

	void advanceConfirmTask(AdvanceConfirmTaskDataBean serviceDataBean,DelegateTask delegateTask) throws Exception;	
	
	void advanceCustomerServiceConfirmTask(AdvanceConfirmTaskDataBean serviceDataBean,DelegateTask delegateTask) throws Exception;	
	
	void advanceDisposeServiceConfirmTask(AdvanceConfirmTaskDataBean serviceDataBean,DelegateTask delegateTask) throws Exception;	
	
}
