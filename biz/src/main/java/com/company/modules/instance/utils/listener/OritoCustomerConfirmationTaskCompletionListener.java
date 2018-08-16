package com.company.modules.instance.utils.listener;

import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.listener.AbstractTaskCompletionListenerTemplate;
import com.company.modules.common.utils.parser.ClassTypeParser;
import com.company.modules.instance.service.CustomerEvaluationService;
import com.company.modules.instance.utils.databean.PreliminaryEvaluationDataBean;

import org.activiti.engine.delegate.DelegateTask;

/**
 * 下户确认
 * @author wulb
 *
 */
public class OritoCustomerConfirmationTaskCompletionListener extends AbstractTaskCompletionListenerTemplate {
	private static final long serialVersionUID = 1L;

	@Override
	protected BasicServiceDataBean parseDataBean(DelegateTask delegateTask, ClassTypeParser classTypeParser) {
		String serviceVariables = (String) delegateTask.getVariable(SystemConstant.SERVICE_VARIABLES);
		return classTypeParser.parse(serviceVariables, PreliminaryEvaluationDataBean.class);
	}

	@Override
	protected void doComplete(BasicServiceDataBean serviceDataBean, DelegateTask delegateTask) {
		try  {
			CustomerEvaluationService customerEvaluationService = (CustomerEvaluationService) ApplicationContextHelperBean.getBean("oritoCustomerConfirmationServiceImpl");
			customerEvaluationService.evaluation((PreliminaryEvaluationDataBean)serviceDataBean,delegateTask);
		} catch (Exception e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
	}
}
