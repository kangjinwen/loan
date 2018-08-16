package com.company.modules.advance.service;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.advance.util.databean.AdvanceNotarizeTaskDataBean;

public interface AdvanceNotarizeService {

	void advanceNotarizeTask(AdvanceNotarizeTaskDataBean serviceDataBean,DelegateTask delegateTask) throws Exception;	
	

}
