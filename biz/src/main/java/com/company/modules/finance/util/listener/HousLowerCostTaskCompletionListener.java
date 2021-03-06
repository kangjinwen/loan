package com.company.modules.finance.util.listener;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.listener.AbstractTaskCompletionListenerTemplate;
import com.company.modules.common.utils.parser.ClassTypeParser;
import com.company.modules.finance.service.CollectAssessmentFeeService;
import com.company.modules.finance.util.databean.HousLowerCostTaskDataBean;

/**
 * 下付费收取
 * @author JDM
 * @date 2016年9月28日
 *
 */
public class HousLowerCostTaskCompletionListener extends AbstractTaskCompletionListenerTemplate{

	@Override
	protected BasicServiceDataBean parseDataBean(DelegateTask delegateTask,
			ClassTypeParser classTypeParser) {
		String serviceVariables = (String) delegateTask.getVariable(SystemConstant.SERVICE_VARIABLES);
		return classTypeParser.parse(serviceVariables, HousLowerCostTaskDataBean.class);
	}

	@Override
	protected void doComplete(BasicServiceDataBean serviceDataBean,
			DelegateTask delegateTask) {
		// TODO Auto-generated method stub CollectAssessmentFeeService
		try  {
			CollectAssessmentFeeService collectAssessmentFeeService = (CollectAssessmentFeeService) ApplicationContextHelperBean.getBean("collectAssessmentFeeServiceImpl");
			collectAssessmentFeeService.collectAssessmentFee((HousLowerCostTaskDataBean) serviceDataBean, delegateTask);
		} catch (Exception e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
	}

}
