package com.company.modules.contract.utils.listener;

import org.activiti.engine.delegate.DelegateTask;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.listener.AbstractTaskCompletionListenerTemplate;
import com.company.modules.common.utils.parser.ClassTypeParser;
import com.company.modules.contract.service.ContractService;
import com.company.modules.contract.utils.databean.ContractDataBean;

/**
 * 签合同
 * @author FHJ
 *
 */
@SuppressWarnings("serial")
public class ContractTaskCompletionListener extends AbstractTaskCompletionListenerTemplate {

	@Override
	protected BasicServiceDataBean parseDataBean(DelegateTask delegateTask, ClassTypeParser classTypeParser) {
		String serviceVariables = (String) delegateTask.getVariable(SystemConstant.SERVICE_VARIABLES);
		return classTypeParser.parse(serviceVariables, ContractDataBean.class);
	}

	@Override
	protected void doComplete(BasicServiceDataBean serviceDataBean, DelegateTask delegateTask) {
		try {
			ContractService contractService = (ContractService) ApplicationContextHelperBean.getBean("contractServiceImpl");
			contractService.signContract((ContractDataBean)serviceDataBean);
		} catch (Exception e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
	}
}
