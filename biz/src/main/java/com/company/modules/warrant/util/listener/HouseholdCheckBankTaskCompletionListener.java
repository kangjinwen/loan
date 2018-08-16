package com.company.modules.warrant.util.listener;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.listener.AbstractTaskCompletionListenerTemplate;
import com.company.modules.common.utils.parser.ClassTypeParser;
import com.company.modules.warrant.service.HouseholdCheckBankService;
import com.company.modules.warrant.util.databean.HouseholdInvestigateDataBean;

public class HouseholdCheckBankTaskCompletionListener extends AbstractTaskCompletionListenerTemplate{

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
			HouseholdCheckBankService householdCheckBankService = (HouseholdCheckBankService) ApplicationContextHelperBean.getBean("householdCheckBankServiceImpl");
			householdCheckBankService.householdInvestigate((HouseholdInvestigateDataBean) serviceDataBean, delegateTask);
		} catch (Exception e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
		
	}

}
