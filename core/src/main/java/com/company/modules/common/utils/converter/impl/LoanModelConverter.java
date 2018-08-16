package com.company.modules.common.utils.converter.impl;

import com.company.common.utils.DateUtil;
import com.company.modules.common.domain.PubLoan;
import com.company.modules.common.utils.converter.DataBeanConverter;
import com.company.modules.common.utils.databean.BasicDataBean;
import com.company.modules.common.utils.databean.LoanDataBean;

public class LoanModelConverter<T> extends DataBeanConverter<T>{
	protected PubLoan loan;
	private static final Byte NORMAL = 0;
	
	@SuppressWarnings("unchecked")
	@Override
	public T convert(BasicDataBean basicDataBean) {
		LoanDataBean loanDataBean = (LoanDataBean) basicDataBean;
		loan = new PubLoan();
		
		loan.setLoanTime(loanDataBean.getLoanTime());
		loan.setAccount(loanDataBean.getAccount());
		loan.setBankAccount(loanDataBean.getBankAccount());
		loan.setCustomerName(loanDataBean.getCustomerName());
		loan.setBankFlag(loanDataBean.getBankFlag());
		loan.setBankName(loanDataBean.getBankName());
		loan.setCreateTime(DateUtil.now());
		loan.setCreator(loanDataBean.getLoginUserId());
		loan.setProcessinstanceid(loanDataBean.getProcessInstanceId());
		loan.setIsDelete(NORMAL);
		loan.setModifier(loanDataBean.getLoginUserId());
		loan.setModifyTime(DateUtil.now());
		loan.setOperator(loanDataBean.getLoginUserId());
		loan.setProjectId(loanDataBean.getProjectId());
		loan.setLoanTime(loanDataBean.getLoanTime());
		
		return (T) loan;
	}

}
