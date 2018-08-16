package com.company.modules.extension.utils.listener;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.listener.AbstractTaskCompletionListenerTemplate;
import com.company.modules.common.utils.parser.ClassTypeParser;
import com.company.modules.extension.service.ExtensionNotarizationAssignService;
import com.company.modules.extension.utils.databean.ExtensionNotarizationAssignDataBean;

/**
 * 展期公证分配监听器
 * @author wulb
 *
 */
public class ExtensionNotarizationAssignTaskCompletionListener extends AbstractTaskCompletionListenerTemplate {
	private static final long serialVersionUID = 1L;

	@Override
	protected BasicServiceDataBean parseDataBean(DelegateTask delegateTask, ClassTypeParser classTypeParser) {
		String serviceVariables = (String) delegateTask.getVariable(SystemConstant.SERVICE_VARIABLES);
		return classTypeParser.parse(serviceVariables, ExtensionNotarizationAssignDataBean.class);
	}

	@Override
	protected void doComplete(BasicServiceDataBean serviceDataBean, DelegateTask delegateTask) {
		try  {
			ExtensionNotarizationAssignService extensionNotarizationAssignService = (ExtensionNotarizationAssignService) ApplicationContextHelperBean.getBean("extensionNotarizationAssignServiceImpl");
			extensionNotarizationAssignService.evaluation((ExtensionNotarizationAssignDataBean)serviceDataBean,delegateTask);
		} catch (Exception e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
	}
}
