package com.company.modules.loanchange.util.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.common.service.CompleteProcessService;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.parser.ClassTypeParser;
import com.company.modules.common.utils.parser.impl.DefaultClassTypeParser;

/**
 *	@Description 提前还款流程终点
 *  @author wtc,wtc@erongdu.com
 *  @CreatTime 2015年6月23日 下午6:09:19
 *  @since version 1.0.0
 */
public class CompleteAheadOfReturnLoanListener extends BranchProcessExcutionListener{
    private static final long serialVersionUID = 1L;
    private static Logger log=LoggerFactory.getLogger(CompleteAheadOfReturnLoanListener.class);
    protected ClassTypeParser defaultClassTypeParser = new DefaultClassTypeParser();
    @Override
    public void notifyEndEvent(RDDelegateExecution execution) throws ServiceException {
        String serviceVariables=execution.getVariable(SystemConstant.SERVICE_VARIABLES).toString();  
        log.debug("CompleteAheadOfReturnLoan:"+serviceVariables);
        CompleteProcessService cps = ApplicationContextHelperBean.getBean("areadofReturnLoanServiceImpl",CompleteProcessService.class);
        cps.completeProcess(defaultClassTypeParser.parse(serviceVariables, BasicServiceDataBean.class),execution);
    }

    @Override
    public void notifyStartEvent(RDDelegateExecution execution) {
       
    }

    @Override
    protected boolean needUpdateProcessBranching() {
        return true;
    }

}
