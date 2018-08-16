package com.company.modules.audit.service;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.audit.util.databean.LoanConfirmDataBean;

/**
 *	@Description  贷款确认
 *  @author fzc,fzc@erongdu.com
 *  @CreatTime 2016年8月19日 下午5:42:41
 *  @since version 1.0.0
 */
public interface LoanConfirmService {

	void loanConfirm(LoanConfirmDataBean serviceDataBean, DelegateTask delegateTask)  throws Exception;

}
