package com.company.modules.audit.util.listener;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.audit.service.LoanInfoService;
import com.company.modules.audit.util.databean.LoanInfoDataBean;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.listener.AbstractTaskCompletionListenerTemplate;
import com.company.modules.common.utils.parser.ClassTypeParser;

/**
 * 填写放款单
 * @author JDM
 * @date 2016年9月28日
 *
 */
public class WriteLoanInfoCompletionListener extends AbstractTaskCompletionListenerTemplate{

	@Override
	protected BasicServiceDataBean parseDataBean(DelegateTask delegateTask, ClassTypeParser classTypeParser) {
		String serviceVariables = (String) delegateTask.getVariable(SystemConstant.SERVICE_VARIABLES);
		return classTypeParser.parse(serviceVariables, LoanInfoDataBean.class);
	}

	@Override
	protected void doComplete(BasicServiceDataBean serviceDataBean, DelegateTask delegateTask) {
		try  {
			LoanInfoService loanInfoService = (LoanInfoService) ApplicationContextHelperBean.getBean("loanInfoServiceImpl");
			loanInfoService.writeLoanInfo((LoanInfoDataBean) serviceDataBean, delegateTask);
		} catch (Exception e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
	}

}
