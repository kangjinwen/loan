package com.company.modules.collateral.workflow.listener;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.collateral.databean.CollateralTaskAssignDataBean;
import com.company.modules.collateral.workflow.service.CollateralRelieveAssignService;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.listener.AbstractTaskCompletionListenerTemplate;
import com.company.modules.common.utils.parser.ClassTypeParser;

public class CollateralRelieveAssignCompletionListener extends AbstractTaskCompletionListenerTemplate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected BasicServiceDataBean parseDataBean(DelegateTask delegateTask,
			ClassTypeParser classTypeParser) {
		String serviceVariables = (String) delegateTask.getVariable(SystemConstant.SERVICE_VARIABLES);
		return classTypeParser.parse(serviceVariables, CollateralTaskAssignDataBean.class);
	}

	@Override
	protected void doComplete(BasicServiceDataBean serviceDataBean,
			DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		try {
			CollateralRelieveAssignService collateralRelieveAssignService=(CollateralRelieveAssignService) ApplicationContextHelperBean.getBean("collateralRelieveAssignServiceImpl");
			collateralRelieveAssignService.relieveTaskAssgin((CollateralTaskAssignDataBean)serviceDataBean, delegateTask);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RDRuntimeException(e.getMessage(), e);
		}
		
	}

}
