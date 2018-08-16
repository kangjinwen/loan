package com.company.modules.extension.utils.listener;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.listener.AbstractTaskCompletionListenerTemplate;
import com.company.modules.common.utils.parser.ClassTypeParser;
import com.company.modules.extension.service.ExtensionCustomerEvaluationService;
import com.company.modules.extension.utils.databean.ExtensionPreliminaryEvaluationDataBean;

/**
 * 展期客服调查监听器
 * @author wulb
 *
 */
public class ExtensionCustomerSurveyTaskCompletionListener extends AbstractTaskCompletionListenerTemplate {
	private static final long serialVersionUID = 1L;

	@Override
	protected BasicServiceDataBean parseDataBean(DelegateTask delegateTask, ClassTypeParser classTypeParser) {
		String serviceVariables = (String) delegateTask.getVariable(SystemConstant.SERVICE_VARIABLES);
		return classTypeParser.parse(serviceVariables, ExtensionPreliminaryEvaluationDataBean.class);
	}

	@Override
	protected void doComplete(BasicServiceDataBean serviceDataBean, DelegateTask delegateTask) {
		try  {
			ExtensionCustomerEvaluationService extensionCustomerEvaluationService = (ExtensionCustomerEvaluationService) ApplicationContextHelperBean.getBean("extensionCustomerEvaluationServiceImpl");
			extensionCustomerEvaluationService.customerEvaluation((ExtensionPreliminaryEvaluationDataBean)serviceDataBean);
		} catch (Exception e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
	}
}
