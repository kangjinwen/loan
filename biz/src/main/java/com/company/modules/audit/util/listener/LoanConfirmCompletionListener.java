package com.company.modules.audit.util.listener;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.audit.service.LoanConfirmService;
import com.company.modules.audit.util.databean.LoanConfirmDataBean;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.listener.AbstractTaskCompletionListenerTemplate;
import com.company.modules.common.utils.parser.ClassTypeParser;

/**
 * 放款确认
 * @author JDM
 * @date 2016年9月28日
 *
 */
public class LoanConfirmCompletionListener extends AbstractTaskCompletionListenerTemplate{

	@Override
	protected BasicServiceDataBean parseDataBean(DelegateTask delegateTask, ClassTypeParser classTypeParser) {
		String serviceVariables = (String) delegateTask.getVariable(SystemConstant.SERVICE_VARIABLES);
		return classTypeParser.parse(serviceVariables, LoanConfirmDataBean.class);
	}

	@Override
	protected void doComplete(BasicServiceDataBean serviceDataBean, DelegateTask delegateTask) {
		try  {
			LoanConfirmService loanConfirmService = (LoanConfirmService) ApplicationContextHelperBean.getBean("loanConfirmServiceImpl");
			loanConfirmService.loanConfirm((LoanConfirmDataBean) serviceDataBean, delegateTask);
		} catch (Exception e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
		
	}

}
