package com.company.modules.loanchange.util.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

/** 
* @Description: 违约减免审批 
* @author Lkf
* @version 1.0
* @since 2015年4月2日
*/
public class BreachofContractReliefApprovalTaskCompletionListener extends AbstractTaskCompletionListenerTemplate {
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/
	private static final long serialVersionUID = -5018228062524092922L;
	private static final Logger logger = LoggerFactory.getLogger(BreachofContractReliefApprovalTaskCompletionListener.class);

	@Override
	protected BasicServiceDataBean parseDataBean(DelegateTask delegateTask, ClassTypeParser classTypeParser) {
		String serviceVariables = (String) delegateTask.getVariable(SystemConstant.SERVICE_VARIABLES);
//		return classTypeParser.parse(serviceVariables, LoanChangeDataBean.class);
		LoanChangeDataBean carLoanChangeDataBean = classTypeParser.parse(serviceVariables, LoanChangeDataBean.class);
		String assignee = (String) delegateTask.getVariable(SystemConstant.PROCESS_LAUNCHER);
		carLoanChangeDataBean.setAssignee(assignee);
		return carLoanChangeDataBean;
	}

	@Override
	protected void doComplete(BasicServiceDataBean serviceDataBean, DelegateTask delegateTask) {
		ApproveService carBreachofContractReliefService = (ApproveService) ApplicationContextHelperBean.getBean("carBreachofContractReliefService"); //initialAuditServiceImpl
		try {
			carBreachofContractReliefService.Approve((LoanChangeDataBean)serviceDataBean);
		} catch (PersistentDataException e) {
			logger.error(e.getMessage());
			throw new RDRuntimeException(e.getMessage(), e);
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new RDRuntimeException(e.getMessage(), e);
		}		
	}

}
