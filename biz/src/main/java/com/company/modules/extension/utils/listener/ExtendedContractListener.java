package com.company.modules.extension.utils.listener;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.listener.AbstractTaskCompletionListenerTemplate;
import com.company.modules.common.utils.parser.ClassTypeParser;
import com.company.modules.extension.service.ExtendedContractService;
import com.company.modules.extension.utils.databean.ExtendedContractDataBean;

/**
 * 展期审批
 * @author Administrator
 *
 */
public class ExtendedContractListener extends AbstractTaskCompletionListenerTemplate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected BasicServiceDataBean parseDataBean(DelegateTask delegateTask,
			ClassTypeParser classTypeParser) {
		// TODO Auto-generated method stub
		String serviceVariables = (String) delegateTask.getVariable(SystemConstant.SERVICE_VARIABLES);
		return classTypeParser.parse(serviceVariables, ExtendedContractDataBean.class);
	}

	@Override
	protected void doComplete(BasicServiceDataBean dataBean,
			DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		try {
			ExtendedContractService extendedContractService=(ExtendedContractService) ApplicationContextHelperBean.getBean("extendedContractServiceImpl");
			extendedContractService.contract((ExtendedContractDataBean)dataBean);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RDRuntimeException(e.getMessage(), e);
		}
		
	}

}
