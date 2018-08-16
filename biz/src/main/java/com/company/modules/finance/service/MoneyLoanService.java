package com.company.modules.finance.service;

import com.company.common.service.BaseService;
import com.company.modules.common.domain.PubLoan;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.databean.LoanDataBean;

public interface MoneyLoanService extends BaseService {
	/**
	 * 放款
	 * @param loanDataBean
	 * @throws ServiceException
	 */
	void loan(LoanDataBean loanDataBean) throws Exception;
	
	PubLoan getLoanInformation(String processInstanceId) throws ServiceException;
}
