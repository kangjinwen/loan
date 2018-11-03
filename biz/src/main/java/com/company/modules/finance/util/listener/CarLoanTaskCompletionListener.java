package com.company.modules.finance.util.listener;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.databean.LoanDataBean;
import com.company.modules.common.utils.listener.AbstractTaskCompletionListenerTemplate;
import com.company.modules.common.utils.parser.ClassTypeParser;
import com.company.modules.finance.service.MoneyLoanService;
import com.company.modules.system.domain.SysUser;

/**
 * 放款 任务结点 监听器
 * 
 * @author FHJ
 *
 */
public class CarLoanTaskCompletionListener extends AbstractTaskCompletionListenerTemplate {

	private static final long serialVersionUID = 1L;

	@Override
	protected BasicServiceDataBean parseDataBean(DelegateTask delegateTask, ClassTypeParser defaultClassTypeParser) {
		String serviceVariables = (String) delegateTask.getVariable(SystemConstant.SERVICE_VARIABLES);
		SysUser user = (SysUser) delegateTask.getVariable(SystemConstant.LOGIN_INFO_VARIABLES);
		LoanDataBean loanDataBean = defaultClassTypeParser.parse(serviceVariables, LoanDataBean.class);
		loanDataBean.setUserId(user.getId());
		return loanDataBean;
	}

	@Override
	protected void doComplete(BasicServiceDataBean serviceDataBean, DelegateTask delegateTask) {
		try {
			MoneyLoanService loanService = (MoneyLoanService) ApplicationContextHelperBean
					.getBean("zzCarMoneyLoanServiceImpl");
			loanService.loan((LoanDataBean) serviceDataBean);
		} catch (Exception e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
	}
}
