package com.company.modules.instance.service;

import org.activiti.engine.delegate.DelegateTask;

import com.company.common.service.BaseService;
import com.company.modules.common.service.DraftPersistable;
import com.company.modules.instance.utils.databean.PreliminaryEvaluationDataBean;
/**
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
public interface CustomerEvaluationService extends BaseService, DraftPersistable {
	
	/**
     * 评估
     * @param bean
     * @throws Exception
     */
	public void evaluation(PreliminaryEvaluationDataBean bean,DelegateTask delegateTask) throws Exception;
}
