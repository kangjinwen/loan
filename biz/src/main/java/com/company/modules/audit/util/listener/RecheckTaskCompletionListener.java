package com.company.modules.audit.util.listener;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.audit.service.AuditService;
import com.company.modules.audit.util.databean.FinalAuditDataBean;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.listener.AbstractTaskCompletionListenerTemplate;
import com.company.modules.common.utils.parser.ClassTypeParser;

public class RecheckTaskCompletionListener extends AbstractTaskCompletionListenerTemplate {

	@Override
	protected BasicServiceDataBean parseDataBean(DelegateTask delegateTask,
			ClassTypeParser classTypeParser) {
		String serviceVariables = (String) delegateTask.getVariable(SystemConstant.SERVICE_VARIABLES);
		return classTypeParser.parse(serviceVariables, FinalAuditDataBean.class);
	}

	@Override
	protected void doComplete(BasicServiceDataBean serviceDataBean,
			DelegateTask delegateTask) {
		try  {
			AuditService auditService = (AuditService) ApplicationContextHelperBean.getBean("finalAuditServiceImpl");
			auditService.audit((FinalAuditDataBean) serviceDataBean, delegateTask);
		} catch (Exception e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
		
	}

}
