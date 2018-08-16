package com.company.modules.advance.util.listener;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.advance.service.AdvanceNotarizeService;
import com.company.modules.advance.util.databean.AdvanceNotarizeTaskDataBean;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.listener.AbstractTaskCompletionListenerTemplate;
import com.company.modules.common.utils.parser.ClassTypeParser;

public class AdvanceNotarizeTaskCompletionListener extends AbstractTaskCompletionListenerTemplate{
	
	

	@Override
	protected BasicServiceDataBean parseDataBean(DelegateTask delegateTask,
			ClassTypeParser classTypeParser) {
		String serviceVariables = (String) delegateTask.getVariable(SystemConstant.SERVICE_VARIABLES);
		return classTypeParser.parse(serviceVariables, AdvanceNotarizeTaskDataBean.class);
	}

	@Override
	protected void doComplete(BasicServiceDataBean serviceDataBean,
			DelegateTask delegateTask) {
		// TODO Auto-generated method stub CollectAssessmentFeeService
		try  {
			AdvanceNotarizeService advanceNotarizeService = (AdvanceNotarizeService) ApplicationContextHelperBean.getBean("advanceNotarizeServiceImpl");
			advanceNotarizeService.advanceNotarizeTask((AdvanceNotarizeTaskDataBean) serviceDataBean, delegateTask);
		} catch (Exception e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
	}

}
