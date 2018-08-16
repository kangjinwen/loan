package com.company.common.utils.converter;

import com.company.common.domain.PlBorrow;
import com.company.common.utils.DateUtil;
import com.company.modules.audit.util.databean.AuditDataBean;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.utils.converter.DataBeanConverter;
import com.company.modules.common.utils.databean.BasicDataBean;

public class CarBorrowModelConverter<T> extends DataBeanConverter<T>{

    private static final Byte DEFAULT_ZERO = 0;

    @SuppressWarnings("unchecked")
	@Override
	public T convert(BasicDataBean basicDataBean) {
    	AuditDataBean auditDataBean = (AuditDataBean) basicDataBean;
		
		//PlBorrow plBorrow = new PlBorrow();
    	PlBorrow plBorrow = auditDataBean.getBorrow();

        plBorrow.setProjectId(auditDataBean.getProjectId());
		//plBorrow.setAccount(auditDataBean.getPlBorrowRequirement().getAccount());
		plBorrow.setProcessInstanceId(auditDataBean.getProcessInstanceId());

		plBorrow.setConsultId(auditDataBean.getConsultId());
//        plBorrow.setProductId(auditDataBean.getProduct().getId());
//		plBorrow.setOverduePenaltyRate(auditDataBean.getProduct().getOverduePenaltyRate());
		//plBorrow.setRepaymentRate(auditDataBean.getProduct().getRepaymentRate());
//		plBorrow.setRepaymentType(auditDataBean.getProduct().getRepaymentType());
		//Byte TimeLimit = auditDataBean.getPlBorrowRequirement().getTimeLimit();
		//plBorrow.setTimeLimit(TimeLimit);
//		plBorrow.setRepaymentDefault(auditDataBean.getProduct().getRepaymentDefault());
		plBorrow.setRepaymentStatus(SystemConstant.REPAYMENT_STATUS_NORMAL);
		plBorrow.setModifyTime(DateUtil.now());
		plBorrow.setModifier(auditDataBean.getLoginUserId());
//		plBorrow.setExpectTime(auditDataBean.getPlBorrowRequirement().getBorrowingTime());
//		plBorrow.setRequirementId(auditDataBean.getPlBorrowRequirement().getId());//关联接口需求表
//		plBorrow.setLoanUser(auditDataBean.getBorrowRequirement().getAddUser());
		plBorrow.setLoanOffice(auditDataBean.getOfficeId());

		return (T) plBorrow;
	}

}
