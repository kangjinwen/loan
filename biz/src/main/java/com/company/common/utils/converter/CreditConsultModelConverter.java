package com.company.common.utils.converter;

import com.company.common.utils.DateUtil;
import com.company.modules.common.domain.PlConsult;
import com.company.modules.common.utils.converter.DataBeanConverter;
import com.company.modules.common.utils.databean.BasicDataBean;
import com.company.modules.instance.utils.databean.PreliminaryEvaluationDataBean;

public class CreditConsultModelConverter<T> extends DataBeanConverter<T> {

	@SuppressWarnings("unchecked")
	@Override
	public T convert(BasicDataBean basicDataBean) {
		PreliminaryEvaluationDataBean preliminaryEvaluationDataBean = (PreliminaryEvaluationDataBean) basicDataBean;
		PlConsult plCreditconsult = new PlConsult();
		plCreditconsult.setCreator(preliminaryEvaluationDataBean.getLoginUserId());
		plCreditconsult.setCreateTime(DateUtil.now());
		plCreditconsult.setCuruserid(preliminaryEvaluationDataBean.getLoginUserId());
//		plCreditconsult.setIdcard(preliminaryEvaluationDataBean.getHousBorrowingBasics().getCertNumber());
//		plCreditconsult.setMobile(preliminaryEvaluationDataBean.getHousBorrowingBasics().getPhone());
//		plCreditconsult.setName(preliminaryEvaluationDataBean.getHousBorrowingBasics().getName());
		plCreditconsult.setCustomerManager(preliminaryEvaluationDataBean.getLoginUserId());
		return (T) plCreditconsult;
	}

}
