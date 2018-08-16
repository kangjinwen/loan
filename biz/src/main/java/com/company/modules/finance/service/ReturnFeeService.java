package com.company.modules.finance.service;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.finance.util.databean.ReturnFeeTaskDataBean;

public interface ReturnFeeService {

	void returnFee(ReturnFeeTaskDataBean serviceDataBean,DelegateTask delegateTask) throws Exception;
	

}
