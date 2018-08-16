package com.company.modules.advance.service;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.advance.util.databean.AdvanceConfirmTaskDataBean;

public interface AdvanceTransferFileService {

	void advanceTransferFileTask(AdvanceConfirmTaskDataBean serviceDataBean,DelegateTask delegateTask) throws Exception;	
	

}
