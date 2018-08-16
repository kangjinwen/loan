package com.company.modules.loanchange.util.listener;

import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.listener.AbstractTaskCompletionListenerTemplate;
import com.company.modules.common.utils.parser.ClassTypeParser;
import com.company.modules.loanchange.domain.LoanChangeDataBean;
import com.company.modules.loanchange.service.ApproveService;

import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 提前还款审批
 * @author JDM
 * @date 2016年10月20日
 *
 */
public class AheadofReturnLoanApprovalTaskCompletionListener extends AbstractTaskCompletionListenerTemplate {

	private static final long serialVersionUID = 5428036850071367045L;
	private static final Logger log = LoggerFactory.getLogger(AheadofReturnLoanApprovalTaskCompletionListener.class);	

	@Override
	protected BasicServiceDataBean parseDataBean(DelegateTask delegateTask, ClassTypeParser classTypeParser) {
		String serviceVariables = (String) delegateTask.getVariable(SystemConstant.SERVICE_VARIABLES);
		LoanChangeDataBean loanChangeDataBean = classTypeParser.parse(serviceVariables, LoanChangeDataBean.class);
		String assignee = (String) delegateTask.getVariable(SystemConstant.PROCESS_LAUNCHER);
		loanChangeDataBean.setAssignee(assignee);
		return loanChangeDataBean;
	}

	@Override
	protected void doComplete(BasicServiceDataBean serviceDataBean, DelegateTask delegateTask) {
		serviceDataBean.setProcessStateCode("taskuser-aheadof-return-loan-approval");
		ApproveService areadofReturnLoanService = (ApproveService) ApplicationContextHelperBean.getBean("areadofReturnLoanServiceImpl");
		try {
			areadofReturnLoanService.Approve((LoanChangeDataBean)serviceDataBean);
		} catch (PersistentDataException e) {
			log.debug(e.getMessage());
			throw new RDRuntimeException(e.getMessage(), e);
		} catch (ServiceException e) {
			log.debug(e.getMessage());
			throw new RDRuntimeException(e.getMessage(), e);
		}
		
	}
}

