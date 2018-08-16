package com.company.modules.extension.service;

import org.activiti.engine.delegate.DelegateTask;

import com.company.common.service.BaseService;
import com.company.modules.common.service.DraftPersistable;
import com.company.modules.extension.utils.databean.ExtensionNotarizationAssignDataBean;
/**
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
public interface ExtensionNotarizationAssignService extends BaseService, DraftPersistable {
	
	/**
     * 评估
     * @param bean
     * @throws Exception
     */
	public void evaluation(ExtensionNotarizationAssignDataBean bean,DelegateTask delegateTask) throws Exception;
}
