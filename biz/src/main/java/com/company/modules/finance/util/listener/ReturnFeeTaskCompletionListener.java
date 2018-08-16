package com.company.modules.finance.util.listener;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.listener.AbstractTaskCompletionListenerTemplate;
import com.company.modules.common.utils.parser.ClassTypeParser;
import com.company.modules.finance.service.ReturnFeeService;
import com.company.modules.finance.util.databean.ReturnFeeTaskDataBean;

public class ReturnFeeTaskCompletionListener extends AbstractTaskCompletionListenerTemplate{
	
	

	@Override
	protected BasicServiceDataBean parseDataBean(DelegateTask delegateTask,
			ClassTypeParser classTypeParser) {
		String serviceVariables = (String) delegateTask.getVariable(SystemConstant.SERVICE_VARIABLES);
		return classTypeParser.parse(serviceVariables, ReturnFeeTaskDataBean.class);
	}

	@Override
	protected void doComplete(BasicServiceDataBean serviceDataBean,
			DelegateTask delegateTask) {
		// TODO Auto-generated method stub CollectAssessmentFeeService
		try  {
			ReturnFeeService returnFeeService = (ReturnFeeService) ApplicationContextHelperBean.getBean("returnFeeServiceImpl");
			returnFeeService.returnFee((ReturnFeeTaskDataBean) serviceDataBean, delegateTask);
		} catch (Exception e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
	}

}
