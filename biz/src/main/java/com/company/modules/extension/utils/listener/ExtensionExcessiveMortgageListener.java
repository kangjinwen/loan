package com.company.modules.extension.utils.listener;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.collateral.databean.CollateralRegistDataBean;
import com.company.modules.collateral.workflow.service.CollateralRegistService;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.listener.AbstractTaskCompletionListenerTemplate;
import com.company.modules.common.utils.parser.ClassTypeParser;

/**
 * 押品登记
 * @author Administrator
 *
 */
public class ExtensionExcessiveMortgageListener extends AbstractTaskCompletionListenerTemplate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected BasicServiceDataBean parseDataBean(DelegateTask delegateTask,
			ClassTypeParser classTypeParser) {
		// TODO Auto-generated method stub
		String serviceVariables = (String) delegateTask.getVariable(SystemConstant.SERVICE_VARIABLES);
		return classTypeParser.parse(serviceVariables, CollateralRegistDataBean.class);
	}

	@Override
	protected void doComplete(BasicServiceDataBean serviceDataBean,
			DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		try {
			CollateralRegistService collateralRegistService=(CollateralRegistService) ApplicationContextHelperBean.getBean("collateralRegistServiceImpl");
			collateralRegistService.registExcessiveMortgage((CollateralRegistDataBean)serviceDataBean);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RDRuntimeException(e.getMessage(), e);
		}
		
	}

}
