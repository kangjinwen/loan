package com.company.modules.audit.service;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.audit.util.databean.LoanInfoDataBean;
/**
 *	@Description 放款单据填写
 *  @author fzc,fzc@erongdu.com
 *  @CreatTime 2016年8月18日 下午3:45:52
 *  @since version 1.0.0
 */
public interface LoanInfoService {

	void writeLoanInfo(LoanInfoDataBean serviceDataBean, DelegateTask delegateTask) throws Exception;
	
	boolean insertOrUpdatehousLoanfees(LoanInfoDataBean serviceDataBean) throws Exception;

}
