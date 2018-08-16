package com.company.modules.warrant.util.listener;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.listener.AbstractTaskCompletionListenerTemplate;
import com.company.modules.common.utils.parser.ClassTypeParser;
import com.company.modules.warrant.service.HouseholdInvestigateService;
import com.company.modules.warrant.util.databean.HouseholdInvestigateDataBean;

public class HouseholdInvestigateTaskCompletionListener extends AbstractTaskCompletionListenerTemplate{

	@Override
	protected BasicServiceDataBean parseDataBean(DelegateTask delegateTask,
			ClassTypeParser classTypeParser) {
		String serviceVariables = (String) delegateTask.getVariable(SystemConstant.SERVICE_VARIABLES);
		return classTypeParser.parse(serviceVariables, HouseholdInvestigateDataBean.class);
	}

	@Override
	protected void doComplete(BasicServiceDataBean serviceDataBean,
			DelegateTask delegateTask) {
		try  {
			HouseholdInvestigateService householdInvestigateService = (HouseholdInvestigateService) ApplicationContextHelperBean.getBean("householdInvestigateServiceImpl");
			householdInvestigateService.householdInvestigate((HouseholdInvestigateDataBean) serviceDataBean, delegateTask);
		} catch (Exception e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
		
	}

}
