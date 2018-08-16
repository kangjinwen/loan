package com.company.modules.advance.util.listener;

import org.activiti.engine.delegate.DelegateTask;

import com.company.modules.advance.service.AdvanceTransferFileService;
import com.company.modules.advance.util.databean.AdvanceConfirmTaskDataBean;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.listener.AbstractTaskCompletionListenerTemplate;
import com.company.modules.common.utils.parser.ClassTypeParser;

public class AdvanceTransferFileTaskCompletionListener extends AbstractTaskCompletionListenerTemplate{
	
	/**
	 * serialVersionUID:TODO
	 *
	 * @since 1.0.0
	 */
	
	private static final long serialVersionUID = 270676362749276652L;

	@Override
	protected BasicServiceDataBean parseDataBean(DelegateTask delegateTask,
			ClassTypeParser classTypeParser) {
		String serviceVariables = (String) delegateTask.getVariable(SystemConstant.SERVICE_VARIABLES);
		return classTypeParser.parse(serviceVariables, AdvanceConfirmTaskDataBean.class);
	}

	@Override
	protected void doComplete(BasicServiceDataBean serviceDataBean,
			DelegateTask delegateTask) {
		// TODO Auto-generated method stub CollectAssessmentFeeService
		try  {
			AdvanceTransferFileService advanceConfirmService = (AdvanceTransferFileService) ApplicationContextHelperBean.getBean("advanceTransferFileServiceImpl");
			advanceConfirmService.advanceTransferFileTask((AdvanceConfirmTaskDataBean)serviceDataBean, delegateTask);
		} catch (Exception e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
	}

}
