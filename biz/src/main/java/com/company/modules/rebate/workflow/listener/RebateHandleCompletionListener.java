package com.company.modules.rebate.workflow.listener;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.listener.AbstractTaskCompletionListenerTemplate;
import com.company.modules.common.utils.parser.ClassTypeParser;
import com.company.modules.rebate.databean.RebateAuditDataBean;
import com.company.modules.rebate.workflow.service.RebateHandleService;

/**
 * 返佣管理
 * @author JDM
 * @date 2016年10月25日
 *
 */
public class RebateHandleCompletionListener extends AbstractTaskCompletionListenerTemplate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected BasicServiceDataBean parseDataBean(DelegateTask delegateTask,
			ClassTypeParser classTypeParser) {
		String serviceVariables = (String) delegateTask.getVariable(SystemConstant.SERVICE_VARIABLES);
		return classTypeParser.parse(serviceVariables, RebateAuditDataBean.class);
	}

	@Override
	protected void doComplete(BasicServiceDataBean serviceDataBean,
			DelegateTask delegateTask) {
		try {
			RebateHandleService rebatehandleService=(RebateHandleService) ApplicationContextHelperBean.getBean("rebateHandleServiceImpl");
			rebatehandleService.handle((RebateAuditDataBean)serviceDataBean);
		} catch (Exception e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
	}

}
