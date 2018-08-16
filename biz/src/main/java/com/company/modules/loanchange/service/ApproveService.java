package com.company.modules.loanchange.service;

import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.loanchange.domain.LoanChangeDataBean;

/** 
 * 审批业务接口
 * @author JDM
 * @date 2016年9月21日
 *
 */
public interface ApproveService{
	
	/**
	 * 审批
	 * @param loanChangeDataBean
	 * @throws ServiceException
	 * @throws PersistentDataException
	 */
	void Approve(LoanChangeDataBean loanChangeDataBean) throws ServiceException, PersistentDataException;
}
