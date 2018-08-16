package com.company.modules.extension.service;

import com.company.common.service.BaseService;
import com.company.modules.common.service.DraftPersistable;
import com.company.modules.extension.utils.databean.ExtensionPreliminaryEvaluationDataBean;
/**
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
public interface ExtensionCustomerEvaluationService extends BaseService, DraftPersistable {
	
	/**
     * 展期客户调查
     * @param bean
     * @throws Exception
     */
	public void customerEvaluation(ExtensionPreliminaryEvaluationDataBean bean) throws Exception;
	
	/**
     * 展期房产评估
     * @param bean
     * @throws Exception
     */
	public void evaluation(ExtensionPreliminaryEvaluationDataBean bean) throws Exception;
}
