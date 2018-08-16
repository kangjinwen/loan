package com.company.modules.advance.service;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.common.utils.databean.BasicServiceDataBean;

public interface AdvanceAssignService {

	void advanceAssignTask(BasicServiceDataBean serviceDataBean,DelegateTask delegateTask) throws Exception;	
	

}
