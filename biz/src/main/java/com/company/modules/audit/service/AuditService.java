package com.company.modules.audit.service;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.audit.util.databean.FaceAuditDataBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;

/**
 *	@Description  审批Service
 *  @author fzc,fzc@erongdu.com
 *  @CreatTime 2016年8月14日 下午3:45:20
 *  @since version 1.0.0
 */
public interface AuditService {

	void audit(BasicServiceDataBean serviceDataBean, DelegateTask delegateTask) throws Exception;
	
	boolean insertOrUpdateAuditInfo(FaceAuditDataBean serviceDataBean) throws Exception;

}
